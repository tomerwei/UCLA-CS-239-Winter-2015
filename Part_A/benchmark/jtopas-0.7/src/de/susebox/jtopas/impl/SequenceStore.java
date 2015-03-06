/*
 * SequenceStore.java: string, comment and special sequence handling in tokenizers
 *
 * Copyright (C) 2003 Heiko Blau
 *
 * This file belongs to the JTopas Library.
 * JTopas is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; either version 2.1 of the License, or (at your 
 * option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with JTopas. If not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330, 
 *   Boston, MA 02111-1307 
 *   USA
 *
 * or check the Internet: http://www.fsf.org
 *
 * Contact:
 *   email: heiko@susebox.de 
 */

package de.susebox.jtopas.impl;

//-----------------------------------------------------------------------------
// Imports
//
import java.util.Iterator;
import java.util.TreeMap;
import java.util.NoSuchElementException;

import de.susebox.java.lang.ExtRuntimeException;

import de.susebox.jtopas.Token;
import de.susebox.jtopas.TokenizerProperty;
import de.susebox.jtopas.TokenizerProperties;
import de.susebox.jtopas.TokenizerException;

import de.susebox.jtopas.spi.SequenceHandler;
import de.susebox.jtopas.spi.DataProvider;

//-----------------------------------------------------------------------------
// Class SequenceStore
//

/**<p>
 * This class is used by {@link de.susebox.jtopas.StandardTokenizerProperties}
 * to store and search special sequences, comments and strings. The class is not
 * suitable for standalone use since it does not check parameters for <code>null</code>
 * values etc. It assumes that these checks have been done in the calling module.
 *</p>
 *
 * @see     de.susebox.jtopas.StandardTokenizerProperties
 * @see     de.susebox.jtopas.spi.SequenceHandler
 * @author  Heiko Blau
 */
public class SequenceStore implements SequenceHandler {
  
  //---------------------------------------------------------------------------
  // Constants
  //
  
  /**
   * This number is the size of the array that is directly indexed by letters
   */
  public static char DIRECT_INDEX_COUNT = 256;
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The constructor gets the flags controlling case-sensitivity.
   *
   * @param flags     <code>F_...</code> flags as defined in {@link de.susebox.jtopas.Tokenizer}
   */
  public SequenceStore(int flags) {
    _flags  = flags;
  }

  
  //---------------------------------------------------------------------------
  // Methods of the SequenceHandler interface
  //
  
  /**
   * This method is a dummy implementation of the one declared in the 
   * {@link de.susebox.jtopas.spi.SequenceHandler} interface.
   *
   * @return  always <code>true</code>.
   */
  public boolean hasSequenceCommentOrString() {
    return (_asciiArray != null || _nonASCIIMap != null);
  }
  
  /**
   * This method checks if a given range of data starts with a special sequence,
   * a comment or a string. See {@link de.susebox.jtopas.spi.SequenceHandler} for
   * details.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a special sequence, 
   *          comment or string could be detected, <code>null</code> otherwise
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty startsWithSequenceCommentOrString(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException
  {
    if (dataProvider.getLength() > 0) {
      char[]        data      = dataProvider.getData();
      int           startPos  = dataProvider.getStartPosition();
      char[]        startChar = { data[startPos] };
      PropertyList  list      = getList(startChar);
      
      while (list != null) {
        TokenizerProperty prop  = list._property;
        String            image = prop.getImages()[0];

        // compare only if the enough data is available
        if (image.length() <= dataProvider.getLength()) {
          if (comparePrefix(image, data, 1, startPos) == 0) {
            return prop;          // single point of success
          }
        }
        list = list._next;
      }
    }
    
    // not found
    return null;
  }

  /**
   * This method returns the length of the longest special sequence, comment or
   * string prefix that is known to this <code>SequenceStore</code>. See 
   * {@link de.susebox.jtopas.spi.SequenceHandler} for details.
   *
   * @return  the number of characters needed in the worst case to identify a 
   *          special sequence
   */
  public int getSequenceMaxLength() {
    return _maxLength;
  }

  //---------------------------------------------------------------------------
  // Implementation
  //
  
  /**
   * Addingt or replacing a special sequence, comment or string.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  public TokenizerProperty addSpecialSequence(TokenizerProperty property) {
    String  image     = property.getImages()[0];
    int     length    = image.length();
    char    startChar;
    
    if ((_flags & TokenizerProperties.F_NO_CASE) != 0) {
      startChar = Character.toUpperCase(image.charAt(0));
    } else {
      startChar = image.charAt(0);
    }
    if (_maxLength < length) {
      _maxLength = length;
    }
    if (startChar >= 0 && startChar < DIRECT_INDEX_COUNT) {
      return insertDirect(startChar, property);
    } else {
      return insertMapped(startChar, property);
    }
  }

  /**
   * Removing a special sequence from the store. If the special sequence denoted
   * by the given string does not exist the method returns <code>null</code>.
   *
   * @param  image  sequence to remove
   * @return the removed property or <code>null</code> if the sequence was not found
   */  
  public TokenizerProperty removeSpecialSequence(String image) {
    return searchString(image, true);
  }
  
  
  /**
   * Get the full description of a special sequence property.
   *
   * @param   image   sequence to find
   * @return  the full sequence description or <code>null</code>
   */
  public TokenizerProperty getSpecialSequence(String image) {
    return searchString(image, false);
  }

  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   *
   * @param   type  type of special sequence like {@link de.susebox.jtopas.Token#STRING}
   * @return  enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getSpecialSequences(int type) {
    return new SpecialSequencesIterator(this, type);
  }
  
  
  /**
   * Get the property list for a given character
   *
   * @param startChar   start character
   */
  private PropertyList getList(char[] startChar) {
    // Normalize start character
    if ((_flags & TokenizerProperties.F_NO_CASE) != 0) {
      startChar[0] = Character.toUpperCase(startChar[0]);
    }
    
    // get the list
    PropertyList  list  = null;
    char          cc    = startChar[0];
    
    if (cc >= 0 && cc < DIRECT_INDEX_COUNT) {
      // direct indexed sequence
      if (_asciiArray != null) {
        list = _asciiArray[cc];
      }
    } else {
      // mapped sequence
      if (_nonASCIIMap != null) {
        list = (PropertyList)_nonASCIIMap.get(new Character(cc));
      }
    }
    return list;
  }


  /**
   * Search a string in the given list. Optionally, remove it. Removal may also
   * reorganize the indexed array or non-ASCII map.
   *
   * @param   image     sequence to search
   * @param   removeIt  if <code>true</code> remove a found sequence from the list
   * @return  the property or <code>null</code> if the sequence was not found
   */
  private TokenizerProperty searchString(String image, boolean removeIt) {
    char[]        startChar = { image.charAt(0) };
    PropertyList  list      = getList(startChar);
    PropertyList  prev      = null;
    
    while (list != null) {
      TokenizerProperty prop  = list._property;
      String            img   = prop.getImages()[0];
      int               res   = compare(img, image, 1);

      if (res == 0) {
        if (removeIt) {
          if (prev != null) {
            prev._next = list._next;
          } else {
            char cc = startChar[0];
            list = list._next;
            if (cc >= 0 && cc < DIRECT_INDEX_COUNT) {
              _asciiArray[cc] = list;
            } else if (list != null) {
              _nonASCIIMap.put(new Character(cc), list);
            } else {
              _nonASCIIMap.remove(new Character(cc));
            }
          }
        }
        return prop;
      } else if (res < 0) {
        break;
      }
      prev = list;
      list = list._next;
    }
    return null;
  }
  
  
  /**
   * Insert a new property into the direct-index array.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty insertDirect(char startChar, TokenizerProperty property) {
    // create the array for ASCII start letters
    if (_asciiArray == null) {
      _asciiArray = new PropertyList[DIRECT_INDEX_COUNT];
    }
    
    // the first element with the given start letter ...
    if (_asciiArray[startChar] == null) {
      _asciiArray[startChar] = new PropertyList(property);
      return null;

    // ... or inserting/replacing in an existing list
    } else {
      return putIntoList(_asciiArray[startChar], property);
    }
  }
    

  /**
   * Insert a new property into the hash table for real unicode letters.
   *
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty insertMapped(char startChar, TokenizerProperty property) {
    // create the hash map for non-ASCII letters
    if (_nonASCIIMap == null) {
      _nonASCIIMap = new TreeMap();
    }
    
    // insert new element
    Character key;
    
    if ((_flags & TokenizerProperties.F_NO_CASE) != 0) {
      key = new Character(Character.toUpperCase(startChar));
    } else {
      key = new Character(startChar);
    }
    
    // does a list with the given start character exists ? if not insert one
    PropertyList  list = (PropertyList)_nonASCIIMap.get(key);
    
    if (list == null) {
      _nonASCIIMap.put(key, new PropertyList(property));
      return null;
    } else {
      return putIntoList(list, property);
    }
  }

  
  /**
   * Insert/replace a property in a property list. The list is ordered by string
   * comparison. This is important for the search in {@link #startsWithSequenceCommentOrString}.
   *
   * @param   list      insert or replace in this list
   * @param   property  the description of the new sequence
   * @return  the previously version of the given property or <code>null</code>
   */
  private TokenizerProperty putIntoList(PropertyList list, TokenizerProperty property) {
    String        newImage = property.getImages()[0];
    PropertyList  prev;

    do {
      TokenizerProperty prop  = list._property;
      String            image = prop.getImages()[0];
      int               res   = compare(image, newImage, 1);

      if (res == 0) {
        list._property = property;
        return prop;
      } else if (res < 0) {
        list._next     = new PropertyList(prop, list._next);
        list._property = property;
        return null;
      }
      prev = list;
    } while ((list = prev._next) != null);
    
    // Append element
    prev._next = new PropertyList(property);
    return null;
  }

  
  /**
   * Compare method for sequences. Longer Strings are greater, shorter are lesser. 
   * Strings with equal lengths are compared in the usual way.
   *
   * @param thisImage   first string to compare
   * @param thatImage   second string to compare
   * @param fromIndex   start comparison from this index
   * @return 0 if equal, < 0 if thisImage < thatImage, > 0 otherwise
   */
  private int compare(String thisImage, String thatImage, int fromIndex) {
    int thisLength = thisImage.length();
    int thatLength = thatImage.length();
    
    if (thisLength != thatLength) {
      return thisLength - thatLength;
    }
    
    while (fromIndex < thisLength) {
      char  cc1 = thisImage.charAt(fromIndex);
      char  cc2 = thatImage.charAt(fromIndex);
      int   res;

      if ((res = cc1 - cc2) != 0) {
        if ((_flags & TokenizerProperties.F_NO_CASE) != 0) {
          cc1 = Character.toUpperCase(cc1);
          cc2 = Character.toUpperCase(cc2);
          if ((res = cc1 - cc2) != 0) {
            return res;
          }
        } else {
          return res;
        }
      }
      fromIndex++;
    }
    return 0;
  }
  
  
  /**
   * Compare method for a string and a character array. The method assumes that
   * the character array holds at least as many characters as the given string.
   *<br>
   * See {@link #compare} for details how the comparison is performed.
   *
   * @param prefix      string to compare
   * @param data        character data to compare
   * @param fromIndex   start comparison from this index
   * @param offset      offset in the data array
   * @return 0 if equal, < 0 if thisImage < thatImage, > 0 otherwise
   */
  private int comparePrefix(String prefix, char[] data, int fromIndex, int offset) {
    while (fromIndex < prefix.length()) {
      char cc1 = prefix.charAt(fromIndex);
      char cc2 = data[offset + fromIndex];
      int  res;

      if ((res = cc1 - cc2) != 0) {
        if ((_flags & TokenizerProperties.F_NO_CASE) != 0) {
          cc1 = Character.toUpperCase(cc1);
          cc2 = Character.toUpperCase(cc2);
          if ((res = cc1 - cc2) != 0) {
            return res;
          }
        } else {
          return res;
        }
      }
      fromIndex++;
    }
    return 0;
  }
  
  
  //---------------------------------------------------------------------------
  // Inner class
  //
  
  /**
   * List element for equaly starting special sequences.
   */
  final class PropertyList {

    /**
     * Constructor taking the {@link TokenizerProperty} as its single argument.
     *
     * @param property  a {@link TokenizerProperty} instance
     */
    PropertyList(TokenizerProperty property) {
      this(property, null);
    }

    /**
     * Constructor taking a {@link TokenizerProperty} and the next list element. 
     * For the next element, <code>null</code> is a valid value.
     *
     * @param property  a {@link TokenizerProperty} instance
     * @param next      the following {@link PropertyList} element 
     */
    PropertyList(TokenizerProperty property, PropertyList next) {
      _property = property;
      _next     = next;
    }

    // members
    public PropertyList       _next;
    public TokenizerProperty  _property;
  }
  

  /**
   * Iterator for comments, strings and ordinary special sequences.
   * Instances of this inner class are returned when a call to {@link #getSpecialSequences}
   * is done. Each element of the enumeration contains a {@link TokenizerProperty}
   * element, that in turn has the comment, special sequence etc. together with
   * its companion
   */
  final class SpecialSequencesIterator implements Iterator {

    /**
     * constructor taking the calling <code>Tokenizer</code> and the type of the
     * {@link TokenizerProperty}. If the type is 0 then special sequences, line and 
     * block comments are returned in one iterator
     *
     * @param parent  the calling tokenizer
     * @param type    type of the <code>TokenizerProperty</code> 
     */
    public SpecialSequencesIterator(SequenceStore parent, int type) {
      _type      = type;
      _parent    = parent;
    }

    /**
     * Checking for the next element in a special sequence list, that has the
     * required type. This method is the one that ultimately decides if there are
     * more elements or not.
     *
     * @return <code>true</code> if there is a matching {@link TokenizerProperty}
     *         element, <code>false</code> otherwise
     */
    private boolean listHasNext() {
      while (_currentList != null) {
        if (_type == 0 || _currentList._property.getType() == _type) {
          return true;
        }
        _currentList = _currentList._next;
      }
      return false;
    }

    /**
     * The well known method from the {@link java.util.Iterator} interface.
     *
     * @return <code>true</code> if there are more {@link TokenizerProperty}
     *         elements, <code>false</code> otherwise
     */
    public boolean hasNext() {
      // simple: check the current list for a successor
      if (listHasNext()) {
        return true;
      }

      // already reached the tree map iterator ?
      if (_mapIterator != null) {
        while (_mapIterator.hasNext()) {
          _currentList = (PropertyList)_mapIterator.next();
          if (listHasNext()) {
            return true;
          }
        }
        
      // ... or still the direct index array ?
      } else {
        if (_parent._asciiArray != null) {
          while (++_currentIndex < DIRECT_INDEX_COUNT) {
            if ((_currentList = _parent._asciiArray[_currentIndex]) != null) {
              if (listHasNext()) {
                return true;
              }
            }
          }
        }
        if (_parent._nonASCIIMap != null) {
          _mapIterator = _parent._nonASCIIMap.values().iterator();
          _currentList = null;
          return hasNext();
        }
      }

      // no (more) sequences
      return false;
    }

    /**
     * Retrieve the next {@link TokenizerProperty} in this enumeration.
     *
     * @return a {@link TokenizerProperty} of the desired type or <code>null</code>
     * @throws NoSuchElementException if there is no more element in this iterator
     */
    public Object next() throws NoSuchElementException {
      if (! hasNext()) {
        throw new NoSuchElementException();
      }
      
      _currentElem = _currentList;
      _currentList = _currentList._next;
      return _currentElem._property;
    }

    /**
     * Remove the current special sequence entry from the collection. This is an
     * alternative to {@link Tokenizer#removeSpecialSequence}.
     *
     * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
     *          <code>remove</code> has been called already after the last <code>next</code>.
     */
    public void remove() throws IllegalStateException {
      // if current element is not set
      if (_currentElem == null) {
        throw new IllegalStateException();
      }

      // remove current element
      TokenizerProperty prop  = _currentElem._property;

      _currentElem = null;
      _parent.searchString(prop.getImages()[0], true);
    }


    // members
    private SequenceStore _parent       = null;
    private int           _type         = Token.UNKNOWN;
    private Iterator      _mapIterator  = null;
    private int           _currentIndex = -1;
    private PropertyList  _currentList  = null;
    private PropertyList  _currentElem  = null;
  }


  //---------------------------------------------------------------------------
  // Members
  //
  private PropertyList[]  _asciiArray   = null;
  private TreeMap         _nonASCIIMap  = null;
  private int             _maxLength    = 0;
  int                     _flags        = 0;
}

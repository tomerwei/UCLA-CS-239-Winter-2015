/*
 * PatternMatcher.java: Interface for pattern-aware tokenizers.
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import de.susebox.jtopas.TokenizerProperty;
import de.susebox.jtopas.TokenizerProperties;
import de.susebox.jtopas.TokenizerException;

import de.susebox.jtopas.spi.PatternHandler;
import de.susebox.jtopas.spi.DataProvider;


//-----------------------------------------------------------------------------
// Class PatternMatcher
//

/**<p>
 * Implementation of the {@link PatternHandler} interface using the JDK 1.4 
 * package <code>java.util.regex</code>.
 *</p>
 *
 * @author  Heiko Blau
 */
public class PatternMatcher implements PatternHandler {
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * The constructor takes a pattern and the {@link TokenizerProperty} object
   * associated with this instance of <code>PatternMatcher</code>.
   *
   * @param   prop  the {@link TokenizerProperty} associated with this object
   * @throws  NullPointerException if the given parameter is <code>null</code>
   */ 
  public PatternMatcher(TokenizerProperty prop) throws NullPointerException {
    setProperty(prop);
  }
  

  //---------------------------------------------------------------------------
  // Methods of the PatternHandler interface
  //
  
  /**
   * The method is a dummy implementation for the interface {@link PatternHandler}
   * and always returns <code>true</code>.
   *
   * @return  always <code>true</code>
   */
  public boolean hasPattern() {
    return true;
  }
  
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern. See {@link PatternHandler#matches}
   * for details.
   *
   * @param   dataProvider    the source to get the data from
   * @param   freePatternOnly if <code>true</code> only unbounded pattern should be
   *                          checked (pattern not enclosed in whitespaces, separators etc.)
   * @param   lengthOfMatch   if a match is found, the method places the length of
   *                          it into the first element of this array
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} object or <code>null</code> if no
   *          match was found
   * @throws  TokenizerException    generic exception
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty matches(DataProvider dataProvider, boolean freePatternOnly, int[] lengthOfMatch)
    throws TokenizerException, NullPointerException
  {
    // is this a free pattern ?
    if (freePatternOnly && (_property.getFlags() & TokenizerProperties.F_FREE_PATTERN) == 0) {
      return null;
    }
    
    // invoke JDK 1.4 or jakarta regexp API
    try {
      Matcher matcher = _pattern.matcher(new DataProviderCharSequence(dataProvider));
      boolean result  = matcher.lookingAt();
      
      if (result) {
        lengthOfMatch[0] = matcher.end();
        return _property;
      } else {
        return null;
      }
    } catch (Exception ex) {
      throw new TokenizerException(ex);
    }
  }

  //---------------------------------------------------------------------------
  // Methods
  //

  /**
   * Setting the {@link TokenizerProperty} for this <code>PatternMatcher</code>.
   * This method will recompile the regular expression pattern. 
   *
   * @param prop  the {@link TokenizerProperty} associated with this object
   * @throws  NullPointerException if the given parameter is <code>null</code>
   */
  public void setProperty(TokenizerProperty prop) throws NullPointerException {
    // no pattern given
    if (prop == null) {
      throw new NullPointerException("No property given.");
    } else if (prop.getImages() == null || prop.getImages().length < 1 || prop.getImages()[0] == null) {
      throw new NullPointerException("Property contains no pattern image.");
    }
    
    // compile the pattern
    int flags = Pattern.MULTILINE | Pattern.DOTALL;

    if ((prop.getFlags() & TokenizerProperties.F_NO_CASE) != 0) {
      flags |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
    }
    _pattern = Pattern.compile(prop.getImages()[0], flags);
    
    // set property
    _property = prop;
  }
  
  /**
   * Retrieving the {@link TokenizerProperty} of this <code>PatternMatcher</code>.
   *
   * @return  the {@link TokenizerProperty} associated with this object
   */
  public TokenizerProperty getProperty() {
    return _property;
  }
  
  
  //---------------------------------------------------------------------------
  // Inner Class 
  //
  
  /**
   * An implementation of the JDK 1.4 {@link java.lang.CharSequence} interface
   * backed by a {@link DataProvider}.
   */
  private final class DataProviderCharSequence implements CharSequence {
    
    /**
     * The constructor takes the reference to the {@link DataProvider}.
     *
     * @param dataProvider  the backing <code>DataProvider</code>
     */
    public DataProviderCharSequence(DataProvider dataProvider) {
      this(dataProvider, dataProvider.getStartPosition(), dataProvider.getLength());
    }
    
    /**
     * The constructor takes the reference to the {@link DataProvider}, the
     * start position and length. It is nessecary for the {@link #subSequence}
     * method
     *
     * @param dataProvider  the backing <code>DataProvider</code>
     */
    private DataProviderCharSequence(DataProvider dataProvider, int start, int length) {
      _dataProvider = dataProvider;
      _start        = start;
      _length       = length;
    }
    
    /** 
     * Returns the character at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first character of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing. </p>
     *
     * @param   index   the index of the character to be returned
     * @return  the specified character
     * @throws  IndexOutOfBoundsException
     *          if the <tt>index</tt> argument is negative or not less than
     *          <tt>length()</tt>
     */
    public char charAt(int index) {
      if (index < 0 || index >= length()) {
        throw new IndexOutOfBoundsException();
      }
      return _dataProvider.getData()[_start + index];
    }
    
    /** Returns the length of this character sequence.  The length is the number
     * of 16-bit Unicode characters in the sequence. </p>
     *
     * @return  the number of characters in this sequence
     *
     */
    public int length() {
      return _length;
    }
    
    /** 
     * Returns a new character sequence that is a subsequence of this sequence.
     * See {@link java.lang.CharSequence#subSequence} for details.
     *
     * @param   start   the start index, inclusive
     * @param   end     the end index, exclusive
     * @return  the specified subsequence
     * @throws  IndexOutOfBoundsException
     *          if <tt>start</tt> or <tt>end</tt> are negative,
     *          if <tt>end</tt> is greater than <tt>length()</tt>,
     *          or if <tt>start</tt> is greater than <tt>end</tt>
     */
    public CharSequence subSequence(int start, int end) {
      if (start < 0 || end < 0 || end > length() || start > end) {
        throw new IndexOutOfBoundsException();
      }
      return new DataProviderCharSequence(_dataProvider, _start + start, end - (_start + start));
    }
    
    // members
    private DataProvider  _dataProvider = null;
    private int           _start        = 0;
    private int           _length       = 0;
  }

  
  //---------------------------------------------------------------------------
  // Members
  //
  private TokenizerProperty _property = null;
  private Pattern           _pattern  = null;
}
  

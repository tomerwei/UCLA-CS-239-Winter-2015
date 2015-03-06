/*
 * StandardTokenizerProperties.java: general-use TokenizerProperties implementation
 *
 * Copyright (C) 2002 Heiko Blau
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

package de.susebox.jtopas;

//-----------------------------------------------------------------------------
// Imports
//
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

import de.susebox.java.lang.ExtRuntimeException;
import de.susebox.java.lang.ExtUnsupportedOperationException;
import de.susebox.java.lang.ExtIllegalArgumentException;

import de.susebox.jtopas.spi.DataMapper;
import de.susebox.jtopas.spi.DataProvider;
import de.susebox.jtopas.spi.PatternHandler;

import de.susebox.jtopas.impl.PatternMatcher;
import de.susebox.jtopas.impl.SequenceStore;


//-----------------------------------------------------------------------------
// Class StandardTokenizerProperties
//

/**<p>
 * The class <code>StandardTokenizerProperties</code> provides a simple implementation
 * of the {@link TokenizerProperties} interface for use in most situations.
 *</p>
 *
 * @see TokenizerProperties
 * @see Tokenizer
 * @author Heiko Blau
 */
public class StandardTokenizerProperties 
  implements TokenizerProperties, DataMapper
{
  
  //---------------------------------------------------------------------------
  // Properties
  //
  
  /**
   * Maximum length of a non-free pattern match. These are patterns that dont
   * have the {@link TokenizerProperties#F_FREE_PATTERN} flag set. A common 
   * example are number patterns.
   */
  public static final short MAX_NONFREE_MATCHLEN = 1024;
  
  
  //---------------------------------------------------------------------------
  // Constructors
  //
  
  /**
   * Default constructor that intitializes an instance with teh default whitespaces
   * and separator sets.
   */  
  public StandardTokenizerProperties() {
    this(0);
  }

  /**
   * This constructor takes the control flags to be used. It is a shortcut to:
   * <pre>
   *   TokenizerProperties props = new StandardTokenizerProperties();
   *
   *   props.setParseFlags(flags);
   * </pre>
   * See the {@link TokenizerProperties} interface for the supported flags.
   *<br>
   * The {@link TokenizerProperties#DEFAULT_WHITESPACES} and 
   * {@link TokenizerProperties#DEFAULT_SEPARATORS} are used for whitespace and 
   * separator handling if no explicit calls to {@link #setWhitespaces} and 
   * {@link #setSeparators} will follow subsequently.
   *
   * @param flags     tokenizer control flags
   * @see   #setParseFlags
   */  
  public StandardTokenizerProperties(int flags) {
    this(flags, DEFAULT_WHITESPACES, DEFAULT_SEPARATORS);
  }
  
  
  /**
   * This constructor takes the whitespace and separator sets to be used. It is 
   * a shortcut to:
   * <pre>
   *   TokenizerProperties props = new StandardTokenizerProperties();
   *
   *   props.setWhitespaces(ws);
   *   props.setSeparators(sep);
   * </pre>
   *
   * @param flags       tokenizer control flags
   * @param whitespaces the whitespace set
   * @param separators  the set of separating characters
   * @see   #setParseFlags
   * @see   #setWhitespaces
   * @see   #setSeparators
   */  
  public StandardTokenizerProperties(int flags, String whitespaces, String separators) {
    setParseFlags(flags);
    setWhitespaces(whitespaces);
    setSeparators(separators);
  }
  
  
  //---------------------------------------------------------------------------
  // Methods of the TokenizerProperties interface
  //
  
  /**
   * See the method description in {@link TokenizerProperties}.
   *
   * @param flags the parser control flags
   * @see   #getParseFlags
   */
  public void setParseFlags(int flags) {
    // normalize flags
    flags = normalizeFlags(flags);
    
    // set flags
    synchronized(this) {
      int oldFlags = _flags;
      
      _flags = flags;
      if (oldFlags != _flags) {
        notifyListeners(new TokenizerPropertyEvent(
                              TokenizerPropertyEvent.PROPERTY_MODIFIED,
                              new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, 
                                                    new String[] { Integer.toBinaryString(_flags) } ),
                              new TokenizerProperty(TokenizerProperty.PARSE_FLAG_MASK, 
                                                    new String[] { Integer.toBinaryString(oldFlags) } )));
      }
    }
  }

   /**
    * See the method description in {@link TokenizerProperties}.
    *
    * @return the current parser control flags
    * @see    #setParseFlags
    */
  public int getParseFlags() {
    return _flags;
  }
  
  /**
   * Registering a string description. See the method description in the interface
   * {@link TokenizerProperties}.
   *
   * @param   start     the starting sequence of a string
   * @param   end       the finishing sequence of a string
   * @param   escape    the escape sequence inside the string
   * @throws  IllegalArgumentException when <code>null</code> or an empty string 
   *          is passed for start or end
   * @see     #removeString
   * @see     #addString(String, String, String, Object)
   */
  public void addString(String start, String end, String escape) 
    throws IllegalArgumentException
  {
    addString(start, end, escape, null);
  }

  /**
   * Registering a the sequences that are used for string-like text parts.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of a string
   * @param end the finishing sequence of a string
   * @param escape the escape sequence inside the string
   * @param companion the associated information
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start or end
   *
   */
  public void addString(String start, String end, String escape, Object companion)
    throws IllegalArgumentException
  {
    addString(start, end, escape, companion, getParseFlags());
  }
  
  
  /** 
   * Registering a the sequences that are used for string-like text parts.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of a string
   * @param end the finishing sequence of a string
   * @param escape the escape sequence inside the string
   * @param companion the associated information
   * @param flags modification flags
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start or end
   */
  public void addString(String start, String end, String escape, Object companion, int flags)
    throws IllegalArgumentException
  {
    addSpecialSequence(
      new TokenizerProperty(Token.STRING, new String[] { start, end, escape }, 
                            companion, flags)
    );
  }
  
  /** 
   * Removing a string description.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of a string
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start
   */  
  public void removeString(String start) throws IllegalArgumentException {
    removeSpecialSequence(start);
  }
  
    
  /** 
   * Retrieving the information associated with a certain string. See the method 
   * description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of a string
   * @return the associated information or <code>null</code>
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start
   */
  public Object getStringCompanion(String start) throws IllegalArgumentException {
    return getSpecialSequenceCompanion(start);
  }
  

  /**
   * Checks if the given starting sequence of the string is known to the parser.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start     the starting sequence of a string
   * @return <code>true</code> if the string is registered, 
   *         <code>false</code> otherwise
   */
  public boolean stringExists(String start) {
    return getString(start) != null;
  }

  
  /** Get the full description of a string property starting with the given
   * prefix. See the method description in {@link TokenizerProperties}.
   * @param start the starting sequence of a string
   * @return the full string description or <code>null</code>
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start
   */
  public TokenizerProperty getString(String start) throws IllegalArgumentException {
    return getSpecialSequence(start);
  }
  

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects. See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getStrings() {
    return new SpecialSequencesIterator(this, Token.STRING);
  }
  
  /**
   * Setting the whitespace character set of the tokenizer. 
   * See the method description in {@link TokenizerProperties}.
   *
   * @param whitespaces the whitespace set
   */
  public void setWhitespaces(String whitespaces) {
    synchronized(_syncWhitespaces) {
      String ws;
      
      // set whitespaces and detect if end-of-line characters are part of them
      ws = (whitespaces != null) ? whitespaces : "";
      if (ws.indexOf('\n') >= 0 || ws.indexOf('\r') >= 0) {
        _newlineIsWhitespace = true;
      }

      // for fast whitespace scanning check for the most common ones
      if (   ws.equals(DEFAULT_WHITESPACES)
          || (   ws.length()      == 4
              && ws.indexOf('\n') >= 0 
              && ws.indexOf('\r') >= 0
              && ws.indexOf(' ')  >= 0
              && ws.indexOf('\t') >= 0)) {
        _defaultWhitespaces = true;
      }
      
      // set the right whitespaces
      String oldValue;
      String removed;
      
      if ((_flags & F_NO_CASE) == 0) {
        oldValue            = _whitespacesCase;
        removed             = _whitespacesNoCase;
        _whitespacesCase    = ws;
        _whitespacesNoCase  = "";
      } else {
        ws                  = ws.toUpperCase();
        oldValue            = _whitespacesNoCase;
        removed             = _whitespacesCase;
        _whitespacesCase    = "";
        _whitespacesNoCase  = ws;
      }
      handleEvent(Token.WHITESPACE, ws, oldValue, removed);
    }
  }

  /**
   * Obtaining the whitespace character set.
   * See the method description in {@link TokenizerProperties}.
   *
   * @see #setWhitespaces
   * @return the currently active whitespace set
   */
  public String getWhitespaces() {
    synchronized(_syncWhitespaces) {
      return _whitespacesCase + _whitespacesNoCase;
    }
  }
  
  /**
   * Setting the separator set. 
   * See the method description in {@link TokenizerProperties}.
   *
   * @param separators the set of separating characters
   */
  public void setSeparators(String separators) {
    synchronized(_syncSeparators) {
      String sep = (separators != null) ? separators : "";
      String oldValue;
      String removed;
      
      if ((_flags & F_NO_CASE) == 0) {
        oldValue          = _separatorsCase;
        removed           = _separatorsNoCase;
        _separatorsCase   = sep;
        _separatorsNoCase = "";
      } else {
        sep               = sep.toUpperCase();
        oldValue          = _separatorsNoCase;
        removed           = _separatorsCase;
        _separatorsCase   = "";
        _separatorsNoCase = sep;
      }
      handleEvent(Token.SEPARATOR, sep, oldValue, removed);
    }
  }
  
  /**
   * Obtaining the separator set of the <code>Tokenizer</code>.
   * See the method description in {@link TokenizerProperties}.
   *
   * @see #setSeparators
   * @return the currently used set of separating characters
   */
  public String getSeparators() {
    synchronized(_syncSeparators) {
      return _separatorsCase + _separatorsNoCase;
    }
  }
  
  /** 
   * Registering a the starting sequence of a line comment.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param lineComment the starting sequence of the line comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the line comment
   */
  public void addLineComment(String lineComment) throws IllegalArgumentException {
    addLineComment(lineComment, null);
  }

  /** 
   * Registering a the starting sequence of a line comment.
   *
   * See the method description in {@link TokenizerProperties}.
   * @param lineComment the starting sequence of a line comment
   * @param companion the associated information
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the line comment
   */
  public void addLineComment(String lineComment, Object companion) throws IllegalArgumentException {
    addLineComment(lineComment, companion, getParseFlags());
  }

  
  /** 
   * Registering a the starting sequence of a line comment.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param lineComment the starting sequence of a line comment
   * @param companion the associated information
   * @param flags modification flags
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   *         is passed for start sequence of the line comment
   */
  public void addLineComment(String lineComment, Object companion, int flags) throws IllegalArgumentException {
    addSpecialSequence(
      new TokenizerProperty(Token.LINE_COMMENT, new String[] { lineComment }, 
                            companion, flags)
    );
  }  

  /** Removing a certain line comment.
   * See the method description in {@link TokenizerProperties}.
   * @param lineComment the starting sequence of the line comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the line comment
   */  
  public void removeLineComment(String lineComment) throws IllegalArgumentException {
    removeSpecialSequence(lineComment);
  }
  
  
  /** Retrieving the associated object of a certain line comment.
   * See the method description in {@link TokenizerProperties}.
   * @param lineComment the starting sequence of the line comment
   * @return the object associated with the line comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the line comment
   */  
  public Object getLineCommentCompanion(String lineComment) throws IllegalArgumentException {
    return getSpecialSequenceCompanion(lineComment);
  }

  /**
   * Checks if the give line comment is known.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param lineComment the starting sequence of the line comment
   * @return <code>true</code> if the line comment is known, 
   *         <code>false</code> otherwise
   */  
  public boolean lineCommentExists(String lineComment) {
    return getLineComment(lineComment) != null;
  }
  
  /** Get the full description of a line comment property starting with the given
   * prefix.
   * See the method description in {@link TokenizerProperties}.
   * @param lineComment the starting sequence of the line comment
   * @return the full line comment description or <code>null</code>
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the line comment
   */
  public TokenizerProperty getLineComment(String lineComment) throws IllegalArgumentException {
    return getSpecialSequence(lineComment);
  }

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getLineComments() {
    return new SpecialSequencesIterator(this, Token.LINE_COMMENT);
  }
  
  /** Registering a block comment. See the method description in
   * {@link TokenizerProperties}.
   * @param start the starting sequence of the block comment
   * @param end the finishing sequence of the block comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   *        is passed for start / end sequence of the block comment
   */  
  public void addBlockComment(String start, String end) throws IllegalArgumentException {
    addBlockComment(start, end, null);
  }
  
  /** 
   * Registering a block comment.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of the block comment
   * @param end the finishing sequence of the block comment
   * @param companion information object associated with this block comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   *        is passed for start / end sequence of the block comment
   */  
  public void addBlockComment(String start, String end, Object companion) throws IllegalArgumentException {
    addBlockComment(start, end, companion, getParseFlags());
  }
  
  /** 
   * Registering a block comment.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of the block comment
   * @param end the finishing sequence of the block comment
   * @param companion information object associated with this block comment
   * @param flags modification flags
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   *        is passed for start / end sequence of the block comment
   */
  public void addBlockComment(String start, String end, Object companion, int flags) throws IllegalArgumentException {
    addSpecialSequence(
      new TokenizerProperty(Token.BLOCK_COMMENT, new String[] { start, end }, 
                            companion, flags)
    );
  }
  
  /** Removing a certain block comment.
   * See the method description in {@link TokenizerProperties}.
   * @param start the starting sequence of the block comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string is passed for start sequence of the block comment
   */  
  public void removeBlockComment(String start) throws IllegalArgumentException {
    removeSpecialSequence(start);
  }
  
  /** 
   * Retrieving a certain block comment.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of the block comment
   * @return the associated object of the block comment
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   * is passed for start sequence of the block comment
   */  
  public Object getBlockCommentCompanion(String start) throws IllegalArgumentException {
    return getSpecialSequenceCompanion(start);
  }
  
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getBlockComments() {
    return new SpecialSequencesIterator(this, Token.BLOCK_COMMENT);
  }
  
  
  /**
   * Checks if the given block comment is known.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of the block comment
   * @return <code>true</code> if the block comment is known, 
   *         <code>false</code> otherwise
   */  
  public boolean blockCommentExists(String start) {
    return getBlockComment(start) != null;
  }
  
  
  /** 
   * Get the full description of a block comment property starting with the given
   * prefix.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param start the starting sequence of the block comment
   * @return the full block comment description or <code>null</code>
   * @throws IllegalArgumentException when <code>null</code> or an empty string 
   *        is passed for start sequence of the block comment
   */
  public TokenizerProperty getBlockComment(String start) throws IllegalArgumentException {
    return getSpecialSequence(start);
  }

  /**
   * Registering a special sequence of characters.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq   special sequence to register
   * @throws IllegalArgumentException if the given sequence is empty or null
   * @see   #addKeyword
   * @see   #setSeparators
   */
  public void addSpecialSequence(String specSeq) throws IllegalArgumentException {
    addSpecialSequence(specSeq, null);
  }
  
  
  /**
   * Registering a special sequence of characters.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq     special sequence to register
   * @param companion information object associated with this special sequence
   * @throws IllegalArgumentException if the given sequence is empty or null
   * @see #addKeyword
   * @see #setSeparators
   */  
  public void addSpecialSequence(String specSeq, Object companion) throws IllegalArgumentException {
    addSpecialSequence(specSeq, companion, getParseFlags());
  }

  
  /**
   * Registering a special sequence of characters.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq     special sequence to register
   * @param companion   information object associated with this special sequence
   * @param flags       modification flags
   * @throws IllegalArgumentException if the given sequence is empty or null
   * @see #addKeyword
   * @see #setSeparators
   */
  public void addSpecialSequence(String specSeq, Object companion, int flags) throws IllegalArgumentException {
    addSpecialSequence(
      new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { specSeq }, 
                            companion, flags)
    );
  }  
    

  /**
   * Deregistering a special sequence from the parser.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq   sequence to remove
   * @throws IllegalArgumentException if the given sequence is empty or null
   */  
  public void removeSpecialSequence(String specSeq) throws IllegalArgumentException {
    checkArgument(specSeq, "Special sequence");
    
    synchronized(_sequences) {
      for (int pos = 0; pos < _sequences.length; ++pos) {
        if (_sequences[pos] != null) {
          TokenizerProperty prop = _sequences[pos].removeSpecialSequence(specSeq);
          
          if (prop != null) {
            notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, prop));
          }
        }
      }
    }
  }
  
  
  /**
   * Retrieving the companion of the given special sequence.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq   sequence to remove
   * @return the object associated with the special sequence
   * @throws IllegalArgumentException if the given sequence is empty or null
   */
  public Object getSpecialSequenceCompanion(String specSeq) throws IllegalArgumentException {
    // check parameter
    checkArgument(specSeq, "Special sequence");
    
    // fetch companion
    TokenizerProperty prop = searchSequence(specSeq);

    if (prop != null) {
      return prop.getCompanion();
    } else {
      return null;
    }
  }
  

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getSpecialSequences() {
    return new SpecialSequencesIterator(this, Token.SPECIAL_SEQUENCE);
  }
  
  
  /**
   * Checks if the given special sequence is known to the <code>Tokenizer</code>.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq sequence to check
   * @return <code>true</code> if the block comment is known,
   *       <code>false</code> otherwise
   */  
  public boolean specialSequenceExists(String specSeq) {
    return getSpecialSequence(specSeq) != null;
  }
  
  
  /**
   * Get the full description of a special sequence property.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param specSeq sequence to find
   * @return the full sequence description or <code>null</code>
   * @throws IllegalArgumentException if the given sequence is empty or null
   */
  public TokenizerProperty getSpecialSequence(String specSeq) throws IllegalArgumentException {
    if (specSeq != null) {
      return searchSequence(specSeq);
    } else {
      return null;
    }
  }

  /**
   * Registering a keyword. 
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to register
   * @throws IllegalArgumentException if the given keyword is empty or null
   */
  public void addKeyword(String keyword) throws IllegalArgumentException {
    addKeyword(keyword, null);
  }
  
  
  /**
   * Registering a keyword.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to register
   * @param companion information object associated with this keyword
   * @throws IllegalArgumentException if the given keyword is empty or null
   */  
  public void addKeyword(String keyword, Object companion) throws IllegalArgumentException {
    addKeyword(keyword, companion, getParseFlags());
  }
  
  
  /**
   * Registering a keyword.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to register
   * @param companion information object associated with this keyword
   * @throws IllegalArgumentException if the given keyword is empty or null
   */  
  public void addKeyword(String keyword, Object companion, int flags) throws IllegalArgumentException {
    // check parameter
    checkArgument(keyword, "Keyword");
    
    // normalize flags
    flags = normalizeFlags(flags);

    // there is a HashMap for case-sensitive and one for non case-sensitive
    // keywords
    // case-insensitive comparison must be done by comparing normalized strings
    // we choose the upper case (lower case would be fine as well)
    synchronized(_keywords) {
      HashMap table;

      if ((flags & F_CASE) != 0) {
        if (_keywords[0] == null) {
          _keywords[0] = new HashMap();
        }
        table = _keywords[0];
      } else {
        if (_keywords[1] == null) {
          _keywords[1] = new HashMap();
        }
        table   = _keywords[1];
        keyword = keyword.toUpperCase();
      }

      // put keyword and its property
      TokenizerProperty prop    = new TokenizerProperty(Token.KEYWORD, 
                                                        new String[] { keyword }, 
                                                        companion, flags);
      TokenizerProperty oldProp = (TokenizerProperty)table.put(keyword, prop);

      if (oldProp == null) {
        notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_ADDED, prop));
      } else if ( ! oldProp.equals(prop)) {
        notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, prop, oldProp));
      }
    }
  }
  
  
  /**
   * Deregistering a keyword.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to remove
   * @throws IllegalArgumentException if the given keyword is empty or null
   */  
  public void removeKeyword(String keyword) throws IllegalArgumentException {
    // check parameter
    checkArgument(keyword, "Keyword");

    // remove it
    synchronized(_keywords) {
      TokenizerProperty prop;
      
      if (_keywords[0] != null) {
        if ((prop = (TokenizerProperty)_keywords[0].remove(keyword)) != null) {
          notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, prop));
        }
      }
      if (_keywords[1] != null) {
        if ((prop = (TokenizerProperty)_keywords[1].remove(keyword.toUpperCase())) != null) {
          notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, prop));
        }
      }
    }
  }
  
  
  /**
   * Retrieving the companion of the given keyword.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword thats companion is sought
   * @return the object associated with the keyword
   * @throws IllegalArgumentException if the given keyword is empty or null
   */
  public Object getKeywordCompanion(String keyword) throws IllegalArgumentException {
    TokenizerProperty prop = getKeyword(keyword);
    
    if (prop != null) {
      return prop.getCompanion();
    } else {
      return null;
    }
  }

  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return iteration of {@link TokenizerProperty} objects
   */  
  public Iterator getKeywords() {
    synchronized(_keywords) {
      return new MapIterator(this, _keywords[0], _keywords[1]);
    }
  }
  
  
  /**
   * Checks if the given keyword is known to the <code>Tokenizer</code>.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to search
   * @return <code>true</code> if the keyword is known,
   *        <code>false</code> otherwise
   */  
  public boolean keywordExists(String keyword) {
    try {
      return getKeyword(keyword) != null;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }

  
  /**
   * Get the full description of a keyword property.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param keyword   keyword to search
   * @return the full sequence description or <code>null</code>
   * @throws IllegalArgumentException if the given keyword is empty or null
   */
  public TokenizerProperty getKeyword(String keyword) throws IllegalArgumentException {
    // check parameter
    checkArgument(keyword, "Keyword");

    // fetch the keyword property
    TokenizerProperty prop;
    
    synchronized(_keywords) {
      if (_keywords[0] != null) {
        if ((prop = (TokenizerProperty)_keywords[0].get(keyword)) != null) {
          return prop;
        }
      }
      if (_keywords[1] != null) {
        if ((prop = (TokenizerProperty)_keywords[1].get(keyword.toUpperCase())) != null) {
          return prop;
        }
      }
      return null;
    }
  }
  
  
  //---------------------------------------------------------------------------
  // pattern properties
  //
  
  /**
   * Registering a pattern. See the method description in {@link TokenizerProperties}.
   *
   * @param   pattern   the regular expression to be added
   * @throws  IllegalArgumentException when <code>null</code> or an empty pattern
   *          is passed
   * @see     #removePattern
   * @see     #addPattern(String, Object)
   * @see     #addPattern(String, Object, int)
   */
  public void addPattern(String pattern) throws IllegalArgumentException {
    addPattern(pattern, null);
  }

  /**
   * Registering a pattern with an associated object. See the method description 
   * in {@link TokenizerProperties}.
   *
   * @param   pattern     the regular expression to be added
   * @param   companion   information object associated with this pattern
   * @throws  IllegalArgumentException when <code>null</code> or an empty pattern
   *          is passed
   * @see     #removePattern
   * @see     #addPattern(String)
   * @see     #addPattern(String, Object, int)
   */
  public void addPattern(String pattern, Object companion) throws IllegalArgumentException {
    addPattern(pattern, companion, getParseFlags());
  }

  /**
   * Registering a pattern with an associated object. See the method description 
   * in {@link TokenizerProperties}.
   *
   * @param   pattern     the regular expression to be added
   * @param   companion   information object associated with this keyword
   * @param   flags       modification flags 
   * @throws  IllegalArgumentException when <code>null</code> or an empty pattern
   *          is passed
   * @see     #removePattern
   * @see     #addPattern(String)
   * @see     #addPattern(String, Object)
   */
  public void addPattern(String pattern, Object companion, int flags)
    throws IllegalArgumentException
  {
    // check parameter
    checkArgument(pattern, "Pattern");
    
    // normalize flags
    flags = normalizeFlags(flags);
    
    // construct the pattern
    PatternMatcher    data = null;
    TokenizerProperty prop = new TokenizerProperty(Token.PATTERN, new String[] { pattern }, companion, flags);
    
    try {
      data = new PatternMatcher(prop);
    } catch (Throwable ex) {
      throw new ExtIllegalArgumentException(ex, "Pattern matching is not available (use JDK 1.4 or above).");
    }
                                                      
    // Register pattern. First search for existing one
    synchronized(_patterns) {
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher       oldData = (PatternMatcher)_patterns.get(index);
        TokenizerProperty oldProp = oldData.getProperty();
       
        if (oldProp.getImages()[0].equals(pattern)) {
          _patterns.set(index, data);
          if ( ! oldProp.equals(prop)) {
            notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, data.getProperty(), oldProp));
          }
          return;
        }
      }
                                                      
      // not found -> register new pattern
      _patterns.add(data);
      notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_ADDED, data.getProperty()));
    }
  }
  
  /**
   * Removing a pattern. See the method description in {@link TokenizerProperties}.
   *
   * @param   pattern     the regular expression to be removed
   * @throws  IllegalArgumentException when <code>null</code> or an empty string 
   *          is passed
   */  
  public void removePattern(String pattern) throws IllegalArgumentException {
    // check parameter
    checkArgument(pattern, "Pattern");

    // remove it
    synchronized(_patterns) {
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher    data = (PatternMatcher)_patterns.get(index);
        TokenizerProperty prop = data.getProperty();

        if (prop.getImages()[0].equals(pattern)) {
          _patterns.remove(index);
          notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, prop));
          break;
        }
      }
    }
  }
  
  /**
   * Retrieving the information associated with a given pattern. See the method 
   * description in {@link TokenizerProperties}.
   *
   * @param   pattern     the regular expression to be removed
   * @return  the associated information or <code>null</code>
   * @throws  IllegalArgumentException when <code>null</code> or an emtpy pattern
   *          is passed
   */
  public Object getPatternCompanion(String pattern) throws IllegalArgumentException {
    TokenizerProperty prop = getPattern(pattern);
    
    if (prop != null) {
      return prop.getCompanion();
    } else {
      return null;
    }
  }
  
  /**
   * Checks if the given pattern is known to the parser.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param   pattern     the regular expression to be looked for
   * @return  <code>true</code> if the pattern is registered, 
   *          <code>false</code> otherwise
   */
  public boolean patternExists(String pattern) {
    try {
      return getPattern(pattern) != null;
    } catch (IllegalArgumentException ex) {
      return false;
    }
  }
  
  /**
   * Get the full description of a string property starting with the given 
   * prefix. The method returns <code>null</code> if the passed <code>start</code>
   * parameter cannot be mapped to a known string description ({@link #stringExists}
   * would return <code>false</code>). 
   *
   * @param   pattern   the regular expression to be looked for
   * @return  the full pattern description or <code>null</code>
   * @throws  IllegalArgumentException when <code>null</code> or an emtpy pattern 
   *          is passed
   */
  public TokenizerProperty getPattern(String pattern) throws IllegalArgumentException {
    // check parameter
    checkArgument(pattern, "Pattern");

    // fetch the pattern property
    synchronized(_patterns) {
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher    data = (PatternMatcher)_patterns.get(index);
        TokenizerProperty prop = data.getProperty();

        if (prop.getImages()[0].equals(pattern)) {
          return prop;
        }
      }
      return null;
    }
  }

  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects. Each <code>TokenizerProperty</code> object contains a pattern and 
   * its companion if such an associated object exists.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getPatterns() {
    return new PatternIterator(this);
  }
  

  /**
   * Registering a {@link TokenizerProperty}.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param   property   property to register
   * @throws  IllegalArgumentException when <code>null</code>, an incomplete or 
   *          otherwise unusable property is passed
   */
  public void addProperty(TokenizerProperty property) throws IllegalArgumentException {
    // check the parameter
    checkPropertyArgument(property);
    
    // add property according to type
    String[] images = property.getImages();
    
    switch (property.getType()) {
    case Token.STRING:
    case Token.LINE_COMMENT:
    case Token.BLOCK_COMMENT:
    case Token.SPECIAL_SEQUENCE:
      addSpecialSequence(property);
      break;

    case Token.KEYWORD:
      addKeyword(images[0], property.getCompanion(), property.getFlags());
      break;

    case Token.PATTERN:
      addPattern(images[0], property.getCompanion(), property.getFlags());
      break;

    case Token.WHITESPACE:
    case Token.SEPARATOR:
    default:
      throw new ExtIllegalArgumentException("Unsupported property type {0}. (Leading) image \"{1}\".", 
                                            new Object[] { new Integer(property.getType()), images[0] } );
    }
  }
  
  /**
   * Deregistering a {@link TokenizerProperty} from the store.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param   property    property to register
   * @throws  IllegalArgumentException when <code>null</code>, an incomplete or 
   *          otherwise unusable property is passed
   */  
  public void removeProperty(TokenizerProperty property) throws IllegalArgumentException {
    // check the parameter
    checkPropertyArgument(property);
    
    // removing property according to type
    String[] images = property.getImages();
    
    switch (property.getType()) {
    case Token.LINE_COMMENT:
      removeLineComment(images[0]);
      break;

    case Token.BLOCK_COMMENT:
      removeBlockComment(images[0]);
      break;

    case Token.STRING:
      removeString(images[0]);
      break;

    case Token.KEYWORD:
      removeKeyword(images[0]);
      break;

    case Token.SPECIAL_SEQUENCE:
      removeSpecialSequence(images[0]);
      break;

    case Token.PATTERN:
      removePattern(images[0]);
      break;

    case Token.WHITESPACE:
    case Token.SEPARATOR:
    default:
      throw new ExtIllegalArgumentException("Unsupported property type {0}. (Leading) image \"{1}\".", 
                                            new Object[] { new Integer(property.getType()), images[0] } );
    }
  }
  
  /**
   * This method returns an {@link java.util.Iterator} of {@link TokenizerProperty}
   * objects.
   * See the method description in {@link TokenizerProperties}.
   *
   * @return enumeration of {@link TokenizerProperty} objects
   */  
  public Iterator getProperties() {
    return new FullIterator(this);
  }
  
  /**
   * Checks if the given {@link TokenizerProperty} is known to this <code>TokenizerProperties</code>
   * instance. 
   * See the method description in {@link TokenizerProperties}.
   *
   * @param   property  the property to search
   * @return <code>true</code> if the property is known,
   *        <code>false</code> otherwise
   */  
  public boolean propertyExists(TokenizerProperty property) {
    // trivial case of null
    if (property == null || property.getImages() == null || property.getImages()[0] == null) {
      return false;
    }
    
    // which type ?
    String image = property.getImages()[0];
    
    switch (property.getType()) {
    case Token.LINE_COMMENT:
      return lineCommentExists(property.getImages()[0]);
    case Token.BLOCK_COMMENT:
      return blockCommentExists(property.getImages()[0]);
    case Token.KEYWORD:
      return keywordExists(property.getImages()[0]);
    case Token.PATTERN:
      return patternExists(property.getImages()[0]);
    default:
      return specialSequenceExists(property.getImages()[0]);
    }
  }

  
  /**
   * Registering a new {@link TokenizerPropertyListener}.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param listener  the new {@link TokenizerPropertyListener}
   * @see #removeTokenizerPropertyListener
   */
  public void addTokenizerPropertyListener(TokenizerPropertyListener listener) {
    if (listener != null) {
      synchronized(_listeners) {
        _listeners.add(listener);
      }
    }
  }
  
  
  /**
   * Removing a listener from the list of registered {@link TokenizerPropertyListener}
   * instances.
   * See the method description in {@link TokenizerProperties}.
   *
   * @param listener  the {@link TokenizerPropertyListener} to deregister
   * @see #addTokenizerPropertyListener
   */
  public void removeTokenizerPropertyListener(TokenizerPropertyListener listener) {
    if (listener != null) {
      synchronized(_listeners) {
        _listeners.remove(listener);
      }
    }
  }
 
  
  //---------------------------------------------------------------------------
  // Methods of the DataMapper interface
  //
  
  /**
   * Setting the backing {@link TokenizerProperties} instance this <code>DataMapper</code> 
   * is working with. Usually, the <code>DataMapper</code>
   * interface is implemented by <code>TokenizerProperties</code> implementations,
   * too. Otherwise the {@link Tokenizer} using the <code>TokenizerProperties</code>, 
   * will construct a default <code>DataMapper</code> an propagate the 
   * <code>TokenizerProperties</code> instance by calling this method.
   *<br>
   * The method should throw an {@link java.lang.UnsupportedOperationException}
   * if this <code>DataMapper</code> is an extension to an <code>TokenizerProperties</code>
   * implementation.
   *
   * @param   props   the {@link de.susebox.jtopas.TokenizerProperties}
   * @throws  UnsupportedOperationException is this is a <code>DataMapper</code>
   *          implemented by a {@link de.susebox.jtopas.TokenizerProperties}
   *          implementation
   * @throws  NullPointerException  if no {@link TokenizerProperties} are given
   */
  public void setTokenizerProperties(TokenizerProperties props) 
    throws UnsupportedOperationException, NullPointerException
  {
    throw new ExtUnsupportedOperationException(
                  "Class {0} already defines the {1} interface.",
                  new Object[] { StandardTokenizerProperties.class.getName(), 
                                 DataMapper.class.getName() } );
  }

  /**
   * The method retrieves the backing {@link de.susebox.jtopas.TokenizerProperties}
   * instance, this <code>DataMapper</code> is working on. For implementations
   * of the <code>TokenizerProperties</code> interface that also implement the
   * <code>DataMapper</code> interface, this method returns the instance itself
   * it is called on.
   *<br>
   * Otherwise the method returns the <code>TokenizerProperties</code> instance 
   * passed through the last call to {@link #setTokenizerProperties} or <code>null</code>
   * if no such call has taken place so far.
   *
   * @return the backing {@link de.susebox.jtopas.TokenizerProperties} or <code>null</code>
   */
  public TokenizerProperties getTokenizerProperties() {
    return this;
  }

  /**
   * This method checks if the character is a whitespace. Implement Your own
   * code for situations where this default implementation is not fast enough
   * or otherwise not really good.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a whitespace,
   *         <code>false</code> otherwise
   */
  public boolean isWhitespace(char testChar) {
    synchronized(_syncWhitespaces) {
      if (_defaultWhitespaces) {
        switch (testChar) {
        case ' ':
        case '\n':
        case '\r':
        case '\t':
          return true;
        default:
          return false;
        }
      } else {
        return (   indexInSet          (testChar, _whitespacesCase)   >= 0 
                || indexInSetIgnoreCase(testChar, _whitespacesNoCase) >= 0);
      }
    }
  }
      
 
  /**
   * This method detects the number of whitespace characters the data range given
   * through the {@link DataProvider} parameter starts with.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  number of whitespace characters starting from the given offset
   * @throws  TokenizerException failure while reading data from the input stream
   * @throws  NullPointerException  if no {@link DataProvider} is given
   * @see     de.susebox.jtopas.spi.DataProvider
   */
  public int countLeadingWhitespaces(DataProvider dataProvider) throws NullPointerException {
    char[]  data     = dataProvider.getData();
    int     startPos = dataProvider.getStartPosition();
    int     maxChars = dataProvider.getLength();
    int     len      = 0;
    
    while (len < maxChars) {
      if ( ! isWhitespace(data[startPos + len])) {
        break;
      }
      len++;
    }
    return len;
  }
  
 
  /** 
   * If a {@link Tokenizer} performs line counting, it is often nessecary to
   * know if newline characters is considered to be a whitespace. See {@link WhitespaceHandler}
   * for details.
   *
   * @return  <code>true</code> if newline characters are in the current whitespace set,
   *          <code>false</code> otherwise
   *
   */
  public boolean newlineIsWhitespace() {
    synchronized(_syncWhitespaces) {
      return _newlineIsWhitespace;
    }
  }  
  

  /**
   * This method checks the given character if it is a separator.
   *
   * @param testChar  check this character
   * @return <code>true</code> if the given character is a separator,
   *         <code>false</code> otherwise
   */
  public boolean isSeparator(char testChar) {
    synchronized(_syncSeparators) {
      return   indexInSet          (testChar, _separatorsCase)   >= 0 
            || indexInSetIgnoreCase(testChar, _separatorsNoCase) >= 0;
    }
  }

  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if special sequence checking must be performed at all. 
   * If the method returns <code>false</code> time-consuming preparations can be 
   * skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasSequenceCommentOrString() {
    synchronized(_sequences) {
      return (_sequences[0] != null || _sequences[1] != null);
    }
  }
  
  /**
   * This method checks if a given range of data starts with a special sequence,
   * a comment or a string. These three types of token are testet together since
   * both comment and string prefixes are ordinary special sequences. Only the 
   * actions preformed <strong>after</strong> a string or comment has been detected,
   * are different.
   *<br>
   * The method returns <code>null</code> if no special sequence, comment or string 
   * could matches the the leading part of the data range given through the
   * {@link DataProvider}.
   *<br>
   * In cases of strings or comments, the return value contains the description
   * for the introducing character sequence, <strong>NOT</strong> the whole
   * string or comment. The reading of the rest of the string or comment is done
   * by the calling {@link de.susebox.jtopas.Tokenizer}.
   *
   * @param   dataProvider  the source to get the data range from
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a special sequence, 
   *          comment or string could be detected, <code>null</code> otherwise
   * @throws  TokenizerException failure while reading more data
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty startsWithSequenceCommentOrString(DataProvider dataProvider) 
    throws TokenizerException, NullPointerException
  {
    // we need the longest possible match
    TokenizerProperty prop = null;

    synchronized(_sequences) {
      for (int pos = 0; pos < _sequences.length; ++pos) {
        if (_sequences[pos] != null) {
          TokenizerProperty p = _sequences[pos].startsWithSequenceCommentOrString(dataProvider);

          if (   p != null 
              && (   prop == null
                  || p.getImages()[0].length() > prop.getImages()[0].length())) {
            prop = p;
          }
        }
      }
    }
    return prop;
  }

  /**
   * This method returns the length of the longest special sequence, comment or
   * string prefix that is known to this <code>SequenceHandler</code>. When
   * calling {@link #startsWithSequenceCommentOrString}, the passed {@link DataProvider}
   * parameter will supply at least this number of characters (see {@link DataProvider#getLength}).
   * If less characters are provided, EOF is reached.
   *
   * @return  the number of characters needed in the worst case to identify a 
   *          special sequence
   */
  public int getSequenceMaxLength() {
    return _sequenceMaxLength;
  }

  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if keyword matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasKeywords() {
    synchronized(_keywords) {
      return (_keywords[0] != null || _keywords[1] != null);
    }
  }
  
  /**
   * This method checks if the character range given through the 
   * {@link DataProvider} comprises a keyword.
   *
   * @param   dataProvider  the source to get the data from, that are checked
   * @return  a {@link de.susebox.jtopas.TokenizerProperty} if a keyword could be 
   *          found, <code>null</code> otherwise
   * @throws  TokenizerException failure while reading more data
   * @throws  NullPointerException  if no {@link DataProvider} is given
   */
  public TokenizerProperty isKeyword(DataProvider dataProvider)
    throws TokenizerException, NullPointerException
  {
    TokenizerProperty prop = null;
    
    synchronized(_keywords) {
      if (_keywords[0] != null || _keywords[1] != null) {
        String  keyword  = new String(dataProvider.getData(), dataProvider.getStartPosition(), dataProvider.getLength());

        if (_keywords[0] != null) {
          prop = (TokenizerProperty)_keywords[0].get(keyword);
        }
        if (prop == null && _keywords[1] != null) {
          keyword = keyword.toUpperCase();
          prop    = (TokenizerProperty)_keywords[1].get(keyword);
        }
      }
    }
    return prop;
  }
  
  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if pattern matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasPattern() {
    synchronized(_patterns) {
      return (_patterns.size() > 0);
    }
  }
    
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern.
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
    synchronized(_patterns) {
      int               longestMatch = 0;
      TokenizerProperty matchingProp = null;
      
      // only get the string if pattern are available
      for (int index = 0; index < _patterns.size(); ++index) {
        PatternMatcher  data          = (PatternMatcher)_patterns.get(index);
        boolean         isFreePattern = (data.getProperty().getFlags() & TokenizerProperties.F_FREE_PATTERN) != 0;

        if ( ! freePatternOnly || isFreePattern) {
          TokenizerProperty prop = data.matches(dataProvider, freePatternOnly, lengthOfMatch);

          if (prop != null) {
            if (longestMatch < lengthOfMatch[0]) {
              matchingProp = prop;
              longestMatch = lengthOfMatch[0];
            }
          }
        }
      }
      
      // return the best result
      lengthOfMatch[0] = longestMatch;
      return matchingProp;
    } 
  }

  
  //---------------------------------------------------------------------------
  // Implementation
  //

  /**
   * Normalize flags. This is nessecary for the case-sensitivity flags
   * {@link TokenizerProperties#F_CASE} and {@link TokenizerProperties#F_NO_CASE}.
   * If neither <code>F_CASE</code> nor <code>F_NO_CASE</code> is set, <code>F_CASE</code>
   * is assumed. If both flags are set, <code>F_CASE</code> takes preceedence.
   *
   * @param   flags   not yet normalized flags
   * @return  the normalized flags
   */
  public int normalizeFlags(int flags) {
    if ((flags & (F_CASE | F_NO_CASE)) == 0) {
      // none set: F_CASE is the default
      flags |= F_CASE;
    } else if ((flags & F_CASE) != 0) {
      // perhaps both set: F_CASE weights more
      flags &= ~F_NO_CASE;
    }
    return flags;
  }
    
  /**
   * Checking a string parameter on null or emptiness. The method encapsulates
   * commonly used code (see {@link #addKeyword} or {@link #addSpecialSequence}
   * for example).
   *
   * @param   arg   the parameter to check
   * @param   name  a name for the <code>arg</code> parameter
   * @throws  IllegalArgumentException if the given <code>arg</code> is null or empty
   */
  private void checkArgument(String arg, String name) throws IllegalArgumentException {
    if (arg == null) {
      throw new ExtIllegalArgumentException("{0} is null.", new Object[] { name } );
    } else if (arg.length() <= 0) {
      throw new ExtIllegalArgumentException("{0} is empty.", new Object[] { name } );
    }
  }
  
  /**
   * Checking a {@link TokenizerProperty} parameter on null or missing nessecary
   * values. The method encapsulates commonly used code (see {@link #addProperty} 
   * and {@link #removeProperty}).
   *
   * @param   property    the parameter to check
   * @throws  IllegalArgumentException if the given <code>arg</code> is null or empty
   */
  private void checkPropertyArgument(TokenizerProperty property) throws IllegalArgumentException {
    // check the parameter
    if (property == null) {
      throw new ExtIllegalArgumentException("Property is null.", null );
    } else if (property.getImages() == null) {
      throw new ExtIllegalArgumentException("No image(s) given in property.", null );
    } else if (property.getImages()[0] == null) {
      throw new ExtIllegalArgumentException("No (leading) image given in property.", null );
    }
  }

  /**
   * The method fires the nessecary events when whitespace or separator sets
   * change.
   *
   * @param type          token type
   * @param newValue      the newly set value 
   * @param oldValue      the old value with case-sensitive handling
   * @param removedValue  the removed property
   */
  private void handleEvent(
    int     type, 
    String  newValue, 
    String  oldValue, 
    String  removedValue 
  ) 
  {
    if (removedValue != null && removedValue.length() > 0) {
      notifyListeners(
        new TokenizerPropertyEvent(
              TokenizerPropertyEvent.PROPERTY_REMOVED,
              new TokenizerProperty(type, new String[] { removedValue } )));
    }
    if (newValue != null && newValue.length() > 0) {
      if (oldValue == null) {
        notifyListeners(
          new TokenizerPropertyEvent(
                TokenizerPropertyEvent.PROPERTY_ADDED,
                new TokenizerProperty(type, new String[] { newValue } )));
      } else if ( ! oldValue.equals(newValue)) {
        notifyListeners(
          new TokenizerPropertyEvent(
                TokenizerPropertyEvent.PROPERTY_MODIFIED,
                new TokenizerProperty(type, new String[] { newValue } ),
                new TokenizerProperty(type, new String[] { oldValue } )));
      }
    } else if (oldValue != null && oldValue.length() > 0) {
      notifyListeners(
        new TokenizerPropertyEvent(
              TokenizerPropertyEvent.PROPERTY_REMOVED,
              new TokenizerProperty(type, new String[] { oldValue } )));
    }
  }
  
  /**
   * This method creates the sorted arrays to store the case-sensitive and
   * -insensitive special sequences, comments, strings etc.
   *
   * @param   property  the description of the new sequence
   * @throws  IllegalArgumentException if <code>null</code> or an incomplete property
   *          is passed
   */
  protected void addSpecialSequence(TokenizerProperty property) throws IllegalArgumentException {
    // check the given property
    checkPropertyArgument(property);
    
    // check various special cases
    String[] images = property.getImages();
      
    switch (property.getType()) {
    case Token.STRING:
    case Token.BLOCK_COMMENT:
      checkArgument((images.length < 2) ? null : images[1], "End sequence");
      break;
    }
    
    // add new sequence
    synchronized(_sequences) {
      int arrayIdx;
      int flags = property.getFlags();

      if ((flags & F_NO_CASE) == 0) {
        if (_sequences[0] == null) {
          _sequences[0] = new SequenceStore(flags);
        }
        arrayIdx = 0;
      } else {
        if (_sequences[1] == null) {
          _sequences[1] = new SequenceStore(flags);
        }
        arrayIdx = 1;
      }
      
      // add / replace property
      TokenizerProperty oldProp = _sequences[arrayIdx].addSpecialSequence(property);
      
      // notify listeners
      if (oldProp == null) {
        notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_ADDED, property));
      } else if ( ! oldProp.equals(property)) {
        notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_MODIFIED, property, oldProp));
      }
    }
  }
  
  
  /**
   * Search a special sequence for the getter methods.
   *
   * @param   specSeq   (starting) sequence to be found
   * @return  the {@link TokenizerProperty} of the sequence or <code>null</code>
   */
  protected TokenizerProperty searchSequence(String specSeq) {
    synchronized(_sequences) {
      for (int pos = 0; pos < _sequences.length; ++pos) {
        TokenizerProperty prop;

        if (_sequences[pos] != null && (prop = _sequences[pos].getSpecialSequence(specSeq)) != null) {
          return prop;
        }
      } 
      return null;
    }
  }
  
  
  /**
   * A given character is searched in the given character set ignoring the case.
   *
   * @return  index in the given character set where the given character
   *          was found or -1 when not ound
   * @see #indexInSet
   */
  protected int indexInSetIgnoreCase(char ch, String set) {
    if (set != null && set.length() > 0) {
      return indexInSet(Character.toUpperCase(ch), set);
    } else {
      return -1;
    }
  }
  
  
  /**
   * A given character is searched in the given character set. This set may
   * contain ranges, for example "a-z" for all lowercase alpha characters. To use
   * the minus sign itself, escape it by "\-".
   *
   * @return  index in the given character set where the given character
   *          was found or -1 when not ound
   */
  protected int indexInSet(char ch, String set) {
    int  len = (set != null) ? set.length() : 0;
    char start, end, setChar;
    
    for (int ii = 0; ii < len; ++ii)  {
      switch (setChar = set.charAt(ii)) {
      case '-':
        start = (ii > 0) ? set.charAt(ii - 1) : 0;
        end   = (ii < len - 1) ? set.charAt(ii + 1) : 0xFFFF;
        if (ch >= start && ch <= end) {
          return ii;
        }
        ii += 2; 
        break;
        
      case '\\':
        setChar = (ii + 1 >= len) ? 0 : set.charAt(ii + 1);
        ii++;
        /* no break */
        
      default:
        if (ch == setChar) {
          return ii;
        }
      }
    }
    
    // not found
    return -1;
  }
  
  
  /**
   * Notifying the registered listeners about a change in the properties. Listeners
   * are called in the order of their registration (see {@link #addTokenizerPropertyListener}).
   *
   * @param event   the {@link TokenizerPropertyEvent} to communicate to the listeners
   */
  protected void notifyListeners(TokenizerPropertyEvent event) {
    synchronized(_listeners) {
      for (int index = 0; index < _listeners.size(); index++) {
        TokenizerPropertyListener listener = (TokenizerPropertyListener)_listeners.get(index);

        synchronized(listener) {
          listener.propertyChanged(event);
        }
      }
    }
  }
  
  
  //---------------------------------------------------------------------------
  // Members
  //
  
  /**
   * overall tokenizer flags.
   */
  protected int _flags = 0;
  
  /**
   * current whitespace characters including character ranges.
   */
  protected String _whitespacesCase = DEFAULT_WHITESPACES;
  
  /**
   * current whitespace characters including character ranges. Case is ignored.
   */
  protected String _whitespacesNoCase = "";
  
  /**
   * current separator characters including character ranges.
   */
  protected String _separatorsCase = DEFAULT_SEPARATORS;
  
  /**
   * current separator characters including character ranges. Case is ignored.
   */
  protected String _separatorsNoCase = "";
  
  /**
   * The first element is the {@link de.susebox.jtopas.impl.SequenceStore} for 
   * the case-sensitive sequences, the second is for the case-insensitive ones.
   */
  protected SequenceStore[] _sequences = new SequenceStore[2];
  
  /**
   * Like the array {@link #_sequences} this two-element Array contains two
   * {@link java.util.HashMap}, the first for the case-sensitive keywords, the
   * second for the case-insensitive ones.
   */
  protected HashMap[] _keywords = new HashMap[2];
  
  /**
   * This array contains the patterns
   */
  protected ArrayList _patterns = new ArrayList();
  
  /**
   * this flag speeds up the line and column counting
   */
  protected boolean _newlineIsWhitespace  = false;
  
  /**
   * For fast whitespace scans, check the common whitespaces first
   */
  private boolean _defaultWhitespaces = false;
  
  /**
   * maximum length of special sequences
   */
  private int _sequenceMaxLength = 0;
  
  /**
   * List of {@link TokenizerPropertyListener} instances.
   */
  private ArrayList _listeners = new ArrayList();
  
  /**
   * Which regular expression parser to use
   */
  private Class _patternClass = null;

  /**
   * A buffer used for pattern matching
   */
  private StringBuffer _foundMatch = new StringBuffer();
      
  /**
   * Synchronization object for whitespaces
   */
  private Object _syncWhitespaces = new Object();
  
  /**
   * Synchronization object for separators
   */
  private Object _syncSeparators = new Object();
}



//---------------------------------------------------------------------------
// inner classes
//

/**
 * Instances of this inner class are returned when a call to 
 * {@link TokenizerProperties#getProperties}.
 * Each element of the enumeration contains a {@link TokenizerProperty} element.
 */
final class FullIterator implements Iterator {
  
  /**
   * constructor taking the calling {@link TokenizerProperties} object to retrieve
   * the members holding {@link TokenizerProperty} elements which are iterated by 
   * this <code>FullIterator</code> instance.
   *
   * @param caseSensitiveMap  map with properties where case matters
   * @param caseSensitiveMap  map with properties where case doesn't matter
   */
  public FullIterator(StandardTokenizerProperties parent) {
    _parent = parent;
    
    // create list of iterators
    _iterators    = new Object[3];
    synchronized(_parent._keywords) {
      _iterators[0] = new MapIterator(_parent, _parent._keywords[0], _parent._keywords[1]);
    }
    _iterators[1] = new PatternIterator(_parent);
    _iterators[2] = new SpecialSequencesIterator(parent, 0);
    _currIndex    = 0;
  }

  /**
   * Test wether there is another element in the iterated set or not. See
   * {@link java.util.Iterator} for details.
   *
   * @return <code>true</code>if another call to {@link #next} will return an object,
   *        <code>false</code> otherwise
   */
  public boolean hasNext() {
    synchronized(this) {
      while (_currIndex < _iterators.length) {
        Iterator iter = (Iterator)_iterators[_currIndex];

        if (iter.hasNext()) {
          return true;
        }
        _currIndex++;
      }
      return false;
    }
  }
  
  /**
   * Retrieve the next element in the iterated set. See {@link java.util.Iterator} 
   * for details.
   *
   * @return the next element or <code>null</code> if there is none
   */
  public Object next() {
    if (hasNext()) {
      synchronized(this) {
        Iterator iter = (Iterator)_iterators[_currIndex];
        return iter.next();
      }
    } else {
      return null;
    }
  }
  
  /**
   * Retrieve the next element in the iterated set. See {@link java.util.Iterator} 
   * for details.
   *
   * @return the next element or <code>null</code> if there is none
   */
  public void remove() {
    if (_currIndex < _iterators.length) {
      Iterator iter = (Iterator)_iterators[_currIndex];
      iter.remove();
    }
  }
  
  
  // members
  private StandardTokenizerProperties _parent     = null;
  private Object[]                    _iterators  = null;
  private int                         _currIndex  = -1;
}

/**
 * Instances of this inner class are returned when a call to {@link TokenizerProperties#getKeywords}
 * or {@link TokenizerProperties#getPatterns}.
 * Each element of the enumeration contains a {@link TokenizerProperty} element,
 * that in turn has the keyword or a pattern with its companion
 */
final class MapIterator implements Iterator {

  /**
   * constructor taking the a case-sensitive and a case-insensitive {@link java.util.Map}
   * which are iterated by this <code>MapIterator</code> instance.
   *
   * @param caseSensitiveMap  map with properties where case matters
   * @param caseSensitiveMap  map with properties where case doesn't matter
   */
  public MapIterator(StandardTokenizerProperties parent, Map caseSensitiveMap, Map caseInsensitiveMap) {
    synchronized(this) {
      _parent = parent;
      if (caseSensitiveMap != null) {
        _iterators[0] = caseSensitiveMap.values().iterator();
      }
      if (caseInsensitiveMap != null) {
        _iterators[1] = caseInsensitiveMap.values().iterator();
      }
    }
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    // check the current array
    synchronized(_iterators) {
      if (_iterators[0] != null) {
        if (_iterators[0].hasNext()) {
          return true;
        } else {
          _iterators[0] = null;
        }
      }
      if (_iterators[1] != null) {
        if (_iterators[1].hasNext()) {
          return true;
        } else {
          _iterators[1] = null;
        }
      }
      return false;
    }
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration. 
   *
   * @return the next keyword as a <code>TokenizerProperty</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() {
    if ( ! hasNext()) {
      throw new NoSuchElementException();
    }
    
    synchronized(this) {
      if (_iterators[0] != null) {
        _currentData = (TokenizerProperty)_iterators[0].next();
      } else {
        _currentData = (TokenizerProperty)_iterators[1].next();
      }
      return _currentData;
    }
  }
  
  /**
   * This method is similar to {@link Tokenizer#removeKeyword}.
   *
   * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
   *          <code>remove</code> has been called already after the last <code>next</code>.
   */
  public void remove() {
    synchronized(this) {
      // if current element is not set
      if (_currentData == null) {
        throw new IllegalStateException();
      }
    
      if (_iterators[0] != null) {
        _iterators[0].remove();
      } else {
        _iterators[1].remove();
      }
      _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentData));
      _currentData = null;
    }
  }

  // members
  private StandardTokenizerProperties _parent     = null;
  private Iterator[]                  _iterators  = new Iterator[2];
  private TokenizerProperty           _currentData   = null;
}



/**
 * Iterator for comments, strings and special sequences.
 * Instances of this inner class are returned when a call to one of the methods
 *<ul><li>
 *    {@link #getBlockComments}
 *</li><li>
 *    {@link #getLineComments}
 *</li><li>
 *    {@link #getStrings}
 *</li><li>
 *    {@link #getSpecialSequences}
 *</li></ul>
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
  public SpecialSequencesIterator(StandardTokenizerProperties parent, int type) {
    _type   = type;
    _parent = parent;
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    synchronized(this) {
      if (_currentIterator != null && _currentIterator.hasNext()) {
        return true;
      }

      while (_parent._sequences != null && ++_currentIndex < _parent._sequences.length) {
        if (_parent._sequences[_currentIndex] != null) {
          _currentIterator = _parent._sequences[_currentIndex].getSpecialSequences(_type);
          if (_currentIterator.hasNext()) {
            return true;
          }
        }
      }
      return false;
    }
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration.
   *
   * @return a {@link TokenizerProperty} of the desired type or <code>null</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() throws NoSuchElementException {
    synchronized(this) {
      if (! hasNext()) {
        throw new NoSuchElementException();
      }
      _currentElement = (TokenizerProperty)_currentIterator.next();
      return _currentElement;
    }
  }
  
  /**
   * Remove the current special sequence entry from the collection. This is an
   * alternative to {@link Tokenizer#removeSpecialSequence}.
   *
   * @throws  IllegalStateExcpetion if {@link #next} has not been called before or
   *          <code>remove</code> has been called already after the last <code>next</code>.
   */
  public void remove() throws IllegalStateException {
    synchronized(this) {
      // if current element is not set
      if (_currentElement == null) {
        throw new IllegalStateException();
      }
    
      // remove current element
      try {
        _currentIterator.remove();
        _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentElement));
        _currentElement = null;
      } catch (Exception ex) {
        throw new ExtRuntimeException(ex, "While trying to remove current element of a SpecialSequencesIterator.");
      }
    }
  }


  // members
  private StandardTokenizerProperties _parent           = null;
  private TokenizerProperty           _currentElement   = null;
  private Iterator                    _currentIterator  = null;
  private int                         _currentIndex     = -1;
  private int                         _type             = Token.UNKNOWN;
}


/**
 * An {@link java.util.Iterator} for pattern.
 */
final class PatternIterator implements Iterator {
  /**
   * constructor taking the calling {@link TokenizerProperties} object.
   *
   * @param parent  the caller
   */
  public PatternIterator(StandardTokenizerProperties parent) {
    _parent   = parent;
    synchronized(parent._patterns) {
      _iterator = parent._patterns.iterator();
    }
  }

  /**
   * the well known method from the {@link java.util.Iterator} interface.
   *
   * @return <code>true</code> if there are more {@link TokenizerProperty}
   *         elements, <code>false</code> otherwise
   */
  public boolean hasNext() {
    return _iterator.hasNext();
  }

  /**
   * Retrieve the next {@link TokenizerProperty} in this enumeration. 
   *
   * @return  the next keyword as a <code>TokenizerProperty</code>
   * @throws NoSuchElementException if there is no more element in this iterator
   */
  public Object next() throws NoSuchElementException {
    synchronized(this) {
      _currentData = (PatternMatcher)_iterator.next();
      return _currentData.getProperty();
    }
  }
  
  /**
   * This method is similar to {@link Tokenizer#removeKeyword}
   */
  public void remove() {
    synchronized(this) {
      _iterator.remove();
      _parent.notifyListeners(new TokenizerPropertyEvent(TokenizerPropertyEvent.PROPERTY_REMOVED, _currentData.getProperty()));
    }
  }

  // members
  private StandardTokenizerProperties _parent = null;
  private Iterator                    _iterator = null;
  private PatternMatcher              _currentData = null;
}

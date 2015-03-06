/*
 * PatternHandler.java: Interface for pattern-aware tokenizers.
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

package de.susebox.jtopas.spi;

//-----------------------------------------------------------------------------
// Imports
//
import de.susebox.jtopas.TokenizerProperty;
import de.susebox.jtopas.TokenizerException;


//-----------------------------------------------------------------------------
// Interface PatternHandler
//

/**<p>
 * This interface must be implemented by classes that should be used as a 
 * pattern handler for a {@link de.susebox.jtopas.Tokenizer}. Pattern are usually
 * regular expressions that are applied on token images to check if that image 
 * matches the pattern.
 *</p>
 *
 * @see     de.susebox.jtopas.Tokenizer
 * @see     de.susebox.jtopas.TokenizerProperties
 * @see     de.susebox.jtopas.spi.DataMapper
 * @author  Heiko Blau
 */
public interface PatternHandler {
  
  /**
   * This method can be used by a {@link de.susebox.jtopas.Tokenizer} implementation 
   * for a fast detection if pattern matching must be performed at all. If the method
   * returns <code>false</code> time-consuming preparations can be skipped.
   *
   * @return  <code>true</code> if there actually are pattern that can be tested
   *          for a match, <code>false</code> otherwise.
   */
  public boolean hasPattern();
  
  /**
   * This method checks if the start of a character range given through the 
   * {@link DataProvider} matches a pattern. An implementation should use
   * a {@link de.susebox.jtopas.TokenizerException} to report problems.
   *<br>
   * The method returns <code>null</code> if the beginning of the character range
   * doesn't match a pattern knwon to the <code>PatternHandler</code>. Otherwise
   * it returns a valid {@link de.susebox.jtopas.TokenizerProperty} instance,
   * generally of type {@link de.susebox.jtopas.Token#PATTERN}. It places the
   * length of the match into the first element of the given array 
   * <code>lengthOfMatch</code> (being one or the rarely used pseudo-out parameter
   * in Java). 
   *<br>
   * The pattern check is repeated if the method returns a match that is exactly
   * as long as the given data range and more data is available. Since it is 
   * probably a rare case, that where are not enough data to find a complete or 
   * no match, the overhead of a repeated check on part of the data is neglected.
   *<br>
   * If a pattern handler has more than one pattern that could be applied to the
   * given data, it should return the longest possible match.
   *<br>
   * The flag <code>freePatternOnly</code> is set, if only pattern are of
   * interest that can occur anywhere in the data source. A number in a programming
   * language for instance is <strong>NOT</strong> a free pattern, since it must
   * be enclosed in whitespaces, separators, comments or other special sequences.
   * A block comment described as a regular expression, is a free pattern since
   * it is can occur anywhere, for instance between two normal token.
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
    throws TokenizerException, NullPointerException;
}

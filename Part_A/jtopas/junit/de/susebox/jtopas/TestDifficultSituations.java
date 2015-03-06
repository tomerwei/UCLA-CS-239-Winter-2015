/*
 * TestDifficultSituations.java: JUnit test for a Tokenizer
 *
 * Copyright (C) 2002 Heiko Blau
 *
 * This file belongs to the JTopas test suite.
 * The JTopas test suite is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; either version 2.1 of the License, or (at your option) 
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along 
 * with the JTopas test suite. If not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330, 
 *   Boston, MA 02111-1307 
 *   USA
 *
 * or check the Internet: http://www.fsf.org
 *
 * The JTopas test suite uses the test framework JUnit by Kent Beck and Erich Gamma.
 * You should have received a copy of their JUnit licence agreement along with 
 * the JTopas test suite.
 *
 * We do NOT provide the JUnit archive junit.jar nessecary to compile and run 
 * our tests, since we assume, that You  either have it already or would like 
 * to get the current release Yourself. 
 * Please visit either:
 *   http://sourceforge.net/projects/junit
 * or
 *   http://junit.org
 * to obtain JUnit.
 *
 * Contact:
 *   email: heiko@susebox.de 
 */

package de.susebox.jtopas;

//-----------------------------------------------------------------------------
// Imports
//
import java.io.Reader;
import java.io.StringReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import de.susebox.TestUtilities;


//-----------------------------------------------------------------------------
// Class TestDifficultSituations
//

/**<p>
 * The class contains a number of test cases that are supposed to be difficult
 * to handle for a {@link Tokenizer}, e.g. EOF conditions inside strings etc.
 *</p>
 *
 * @see     Tokenizer
 * @see     StandardTokenizer
 * @see     StandardTokenizerProperties
 * @author  Heiko Blau
 */
public class TestDifficultSituations extends TestCase {
  
  //---------------------------------------------------------------------------
  // properties
  //

  
  //---------------------------------------------------------------------------
  // main method
  //
  
  /**
   * call this method to invoke the tests
   */
  public static void main(String[] args) {
    String[]   tests = { TestDifficultSituations.class.getName() };

    TestUtilities.run(tests, args);
  }
  

  //---------------------------------------------------------------------------
  // suite method
  //
  
  /**
   * Implementation of the JUnit method <code>suite</code>. For each set of test
   * properties one or more tests are instantiated.
   *
   * @return a test suite
   */
  public static Test suite() {
    TestSuite   suite = new TestSuite(TestDifficultSituations.class.getName());
    
    suite.addTest(new TestDifficultSituations("testEmptySource"));
    suite.addTest(new TestDifficultSituations("testSmallSource"));
    suite.addTest(new TestDifficultSituations("testSimilarSpecialSequences"));
    suite.addTest(new TestDifficultSituations("testNonASCIICharacters"));
    suite.addTest(new TestDifficultSituations("testEOFInLineComment"));
    suite.addTest(new TestDifficultSituations("testEOFInBlockComment"));
    suite.addTest(new TestDifficultSituations("testEOFInString"));
    suite.addTest(new TestDifficultSituations("testStringEscapes1"));
    suite.addTest(new TestDifficultSituations("testStringEscapes2"));
    suite.addTest(new TestDifficultSituations("testNestedComments"));
    suite.addTest(new TestDifficultSituations("testReaderSwitching"));
    suite.addTest(new TestDifficultSituations("testDOSEOL"));
    suite.addTest(new TestDifficultSituations("testMACEOL"));
    suite.addTest(new TestDifficultSituations("testSpecialCalls"));
    suite.addTest(new TestDifficultSituations("testLineCounting"));
    return suite;
  }
  
  
  //---------------------------------------------------------------------------
  // Constructor
  //
  
  /**
   * Default constructor. Standard input {@link java.lang.System#in} is used
   * to construct the input stream reader.
   */  
  public TestDifficultSituations(String test) {
    super(test);
  }

  
  //---------------------------------------------------------------------------
  // Fixture setup and release
  //
  
  /**
   * Sets up the fixture, for example, open a network connection.
   * This method is called before a test is executed.
   */
  protected void setUp() throws Exception {}

  
  /**
   * Tears down the fixture, for example, close a network connection.
   * This method is called after a test is executed.
   */
  protected void tearDown() throws Exception {}
  
  
  //---------------------------------------------------------------------------
  // test cases
  //

  // various constants
  private static final String PLUS              = "+";
  private static final String DOUBLE_PLUS       = "++";
  private static final String TRIPLE_PLUS       = "+++";
  private static final String PLUS_EQUAL        = "+=";
  private static final String PLUS_MINUS        = "+-";
  private static final String HTML_OPEN         = "<";
  private static final String HTML_COMMENT1     = "<!";
  private static final String HTML_COMMENT2     = "<!--";
  private static final String HTML_HEAD         = "<head>";
  private static final String HTML_HEADER       = "<h>";
  private static final String HTML_HT           = "<ht>";
  private static final String HTML_CLOSE        = ">";
  private static final String MINUS             = "-";
  private static final String DOUBLE_MINUS      = "--";
  private static final String HTML_COMMENT_END  = "-->";
  private static final String HTML_HEAD_END     = "</head>";
  private static final String HTML_HEADER_END   = "</h>";
  private static final String SHIFT_LEFT        = "<<";
  private static final String SHIFT_RIGHT       = ">>";
  private static final String COLON             = ".";
  private static final String EURO              = "§";
  private static final String DOUBLE_EURO       = "§§";
  private static final String EUROURO           = "§uro";
  private static final String AE                = "Ê";
  private static final String OERE              = "¯";
  private static final String BUG               = "";
  private static final String DOUBLE_BUG        = "";
  
  /**
   * Test similar special sequences.
   */
  public void testSimilarSpecialSequences() throws Throwable {
    Reader reader = new StringReader( "lots+of++special+=sequences+in+++a+-row\n"
                                    + "with <HEAD>HTML-tags-in-between</head>\n"
                                    + "like <h>headings</h><open and close> tags\n"
                                    + "and <!even--comments-->+<!--in<ht>many+=forms-->>\n"
                                    + "some<<as>>operators.\n"
                                    + "+++++<<<>>>.\n"
                                    );
    String[] expectedToken = {
      PLUS, DOUBLE_PLUS, PLUS_EQUAL, PLUS, TRIPLE_PLUS, PLUS_MINUS,     // "lots+of++special+=sequences+in+++a+-row\n"
      HTML_HEAD, MINUS, MINUS, MINUS, HTML_HEAD_END,                    // "with <HEAD>HTML-tags-in-between</head>\n"
      HTML_HEADER, HTML_HEADER_END, HTML_OPEN, HTML_CLOSE,              // "like <h>headings</h><open and close> tags\n"
      HTML_COMMENT1, DOUBLE_MINUS, HTML_COMMENT_END, PLUS, 
      HTML_COMMENT2, HTML_HT, PLUS_EQUAL, HTML_COMMENT_END, HTML_CLOSE, // "and <!even--comments-->+<!--in<ht>many+=forms-->>\n"
      SHIFT_LEFT, SHIFT_RIGHT, COLON,                                   // "some<<as>>operators."
      TRIPLE_PLUS, DOUBLE_PLUS, SHIFT_LEFT, HTML_OPEN, SHIFT_RIGHT, 
      HTML_CLOSE, COLON                                                 // "+++++<<<>>>.\n"
    };
    
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);

    try {
      props.addSpecialSequence(COLON,             COLON);
      props.addSpecialSequence(PLUS,              PLUS);
      props.addSpecialSequence(DOUBLE_PLUS,       DOUBLE_PLUS);
      props.addSpecialSequence(TRIPLE_PLUS,       TRIPLE_PLUS);
      props.addSpecialSequence(PLUS_EQUAL,        PLUS_EQUAL);
      props.addSpecialSequence(PLUS_MINUS,        PLUS_MINUS);
      props.addSpecialSequence(SHIFT_LEFT,        SHIFT_LEFT);
      props.addSpecialSequence(HTML_OPEN,         HTML_OPEN,        TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_COMMENT1,     HTML_COMMENT1,    TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_COMMENT2,     HTML_COMMENT2,    TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_HEAD,         HTML_HEAD,        TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_HEADER,       HTML_HEADER,      TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_HT,           HTML_HT,          TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_CLOSE,        HTML_CLOSE,       TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(SHIFT_RIGHT,       SHIFT_RIGHT);
      props.addSpecialSequence(MINUS,             MINUS);
      props.addSpecialSequence(DOUBLE_MINUS,      DOUBLE_MINUS);
      props.addSpecialSequence(HTML_COMMENT_END,  HTML_COMMENT_END, TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_HEAD_END,     HTML_HEAD_END,    TokenizerProperties.F_NO_CASE);
      props.addSpecialSequence(HTML_HEADER_END,   HTML_HEADER_END,  TokenizerProperties.F_NO_CASE);
      tokenizer.setSource(reader);

      // start tokenizing
      int index = 0;

      while (tokenizer.hasMoreToken()) {
        Token   token = tokenizer.nextToken();
        boolean isOK;

        switch (token.getType()) {
        case Token.NORMAL:
          System.out.println(token.getImage());
          break;
        case Token.SPECIAL_SEQUENCE:
          if ((props.getSpecialSequence(token.getImage()).getFlags() & TokenizerProperties.F_NO_CASE) != 0) {
            isOK = expectedToken[index].equalsIgnoreCase(token.getImage());
          } else {
            isOK = expectedToken[index].equals(token.getImage());
          }
          assertTrue("Index " + index + ": expected†\"" + expectedToken[index] + "\", got \"" + token.getImage() + "\".", isOK);
          index++;
          break;
        }
      }
    } finally {
      tokenizer.close();
    }
  }

  
  /**
   * Test similar special sequences.
   */
  public void testNonASCIICharacters() throws Throwable {
    Reader reader = new StringReader( "1§ is an Ê to much. Or¯takethis: §§ or §uro and .");
    
    String[] expectedToken = {
      EURO, AE, OERE, BUG, DOUBLE_EURO, EUROURO, DOUBLE_BUG
    };
    
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);

    try {
      props.addSpecialSequence(EURO,          EURO);
      props.addSpecialSequence(DOUBLE_EURO,   DOUBLE_EURO);
      props.addSpecialSequence(EUROURO,       EUROURO);
      props.addSpecialSequence(AE,            AE);
      props.addSpecialSequence(OERE,          OERE);
      props.addSpecialSequence(BUG,           BUG);
      props.addSpecialSequence(DOUBLE_BUG,    DOUBLE_BUG);
      tokenizer.setSource(reader);

      // start tokenizing
      int index = 0;

      while (tokenizer.hasMoreToken()) {
        Token   token = tokenizer.nextToken();
        boolean isOK;

        switch (token.getType()) {
        case Token.NORMAL:
          System.out.println(token.getImage());
          break;
        case Token.SPECIAL_SEQUENCE:
          assertTrue( "Index " + index + ": expected†\"" + expectedToken[index] + "\", got \"" + token.getImage() + "\".", 
                      expectedToken[index].equals(token.getImage()));
          index++;
          break;
        }
      }
    } finally {
      tokenizer.close();
    }
  }

  
  /**
   * Test the case of an completely empty data source. This is always a good
   * candidate for failures :-)
   */
  public void testEmptySource() throws Throwable {
    Reader              reader    = new StringReader("");
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES);
      props.addLineComment("//");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
      assertTrue( ! tokenizer.hasMoreToken());
    } finally {
      tokenizer.close();
    }
  }

  
  /**
   * Test small sources.
   */
  public void testSmallSource() throws Throwable {
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Reader              reader;
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES);
      props.addLineComment("//");
      props.addSpecialSequence(PLUS,          PLUS);
      props.addSpecialSequence(DOUBLE_PLUS,   DOUBLE_PLUS);
      props.addSpecialSequence(MINUS,         MINUS);
      props.addSpecialSequence(DOUBLE_MINUS,  DOUBLE_MINUS);

      // a single character
      reader = new StringReader("A");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.NORMAL);
      assertTrue(token.getImage().equals("A"));
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
      assertTrue( ! tokenizer.hasMoreToken());

      // a single special sequence
      reader = new StringReader("++");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.SPECIAL_SEQUENCE);
      assertTrue(token.getCompanion() == DOUBLE_PLUS);
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
      assertTrue( ! tokenizer.hasMoreToken());

      // an empty line comment
      reader = new StringReader("//");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.LINE_COMMENT);
      assertTrue(token.getImage().equals("//"));
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
      assertTrue( ! tokenizer.hasMoreToken());

    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  
  /**
   * Test the case, when a line comment is not terminated by a newline character.
   * This happens when the last line of a file is a line comment without a 
   * newline on its end.
   * This is a rather common situation.
   */
  public void testEOFInLineComment() throws Throwable {
    Reader              reader    = new StringReader("// end of file occurs in line comment.");
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES);
      props.addLineComment("//");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.LINE_COMMENT);
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Test the case, when a block comment is not terminated. That means EOF
   * occurs unexpectedly in a block comment.
   */
  public void testEOFInBlockComment() throws Throwable {
    Reader              reader    = new StringReader("/* end of file occurs\nin a block comment.");
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES);
      props.addBlockComment("/*", "*/");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.BLOCK_COMMENT);
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Test the case, when a block comment is not terminated. That means EOF
   * occurs unexpectedly in a block comment.
   */
  public void testEOFInString() throws Throwable {
    Reader              reader    = new StringReader("-- end of file in String\n\"Thats the string, but rather unterminated |-(");
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.addLineComment("--");
      props.addString("\"", "\"", "\"");
      tokenizer.setSource(reader);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.STRING);
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }
  
  /**
   * Test various calls to methods with a special contract.
   */
  public void testSpecialCalls() throws Throwable {
    Reader              reader    = new StringReader("A simple text");
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token     = null;

    try {
      tokenizer.setSource(reader);

      try {
        tokenizer.currentToken();
        assertTrue("Tokenizer should have thrown an exception here.", false);
      } catch (TokenizerException ex) {};
      try {
        tokenizer.currentImage();
        assertTrue("Tokenizer should have thrown an exception here.", false);
      } catch (TokenizerException ex) {};

      while (tokenizer.hasMoreToken()) {
        Token newToken = tokenizer.nextToken();
        assertTrue( ! tokenizer.currentToken().equals(token));
        assertTrue(tokenizer.currentToken() != null);
        assertTrue(tokenizer.currentToken().equals(newToken));
        assertTrue(tokenizer.currentToken().equals(tokenizer.currentToken()));
        if (newToken.getType() != Token.EOF) {
          assertTrue(tokenizer.currentImage() != null);
          assertTrue(tokenizer.currentImage().equals(tokenizer.currentImage()));
        } else {
          assertTrue( ! tokenizer.hasMoreToken());
        }
        token = newToken;
      }
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }
  
  /**
   * Test various situations of string escapes, if the escape character is the
   * backslash (not equal to the string character).
   * This test takes a number of lines each with a string including escapes in
   * it. It passes if the right number of strings is returned and also the line
   * counting is ok.
   */
  public void testStringEscapes1() throws Throwable {
    Reader reader = new StringReader(
      "\"String escape \\\" in the middle\"\n"
    + "\"String escape on end \\\"\"\n"
    + "\"\\\" String escape on begin\"\n"
    + "\"Two string escapes \\\"\\\" after each other\"\n"
    + "\"Two string escapes on end \\\"\\\"\"\n");
    
    int                 lines     = 5;
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES | TokenizerProperties.F_COUNT_LINES);
      props.addString("\"", "\"", "\\");
      tokenizer.setSource(reader);

      for (int line = 0; line < lines; ++line) {
        assertTrue("(1) No more token at line " + line, tokenizer.hasMoreToken());
        token = tokenizer.nextToken();
        assertTrue("String not recognized at line " + line, token.getType() == Token.STRING);
        assertTrue("(2) No more token at line " + line, tokenizer.hasMoreToken());
        token = tokenizer.nextToken();
        assertTrue("Newline not recognized as whitespace at line " + line, token.getType() == Token.WHITESPACE);
      }
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Test various situations of string escapes, if the escape character is equal
   * to the string character).
   * This test takes a number of lines each with a string including escapes in
   * it. It passes if the right number of strings is returned and also the line
   * counting is ok.
   */
  public void testStringEscapes2() throws Throwable {
    Reader reader = new StringReader(
      "'String escape '' in the middle'\n"
    + "'String escape on end '''\n"
    + "''' String escape on begin'\n"
    + "'Two string escapes '''' after each other'\n"
    + "'Two string escapes on end '''''\n");
    
    int       lines     = 5;
    
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token     token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES | TokenizerProperties.F_COUNT_LINES);
      props.addString("'", "'", "'");
      tokenizer.setSource(reader);

      for (int line = 0; line < lines; ++line) {
        assertTrue("(1) No more token at line " + line, tokenizer.hasMoreToken());
        token = tokenizer.nextToken();
        assertTrue("String not recognized at line " + line, token.getType() == Token.STRING);
        assertTrue("(2) No more token at line " + line, tokenizer.hasMoreToken());
        token = tokenizer.nextToken();
        assertTrue("Newline not recognized as whitespace at line " + line, token.getType() == Token.WHITESPACE);
      }
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Test nested comments.
   */
  public void testNestedComments() throws Throwable {
    Reader reader = new StringReader(
      "// line comment including // line comment sequence\n"
    + "/* block comment with\n"
    + "  /* a nested block\n"
    + "     comment\n"
    + "  */\n"
    + "  normal token or not ?\n" 
    + "*/\n"
    + "// line comment with /* block comment */\n"
    + "'a string with // line comment'\n"
    + "'a string with /* block comment */'\n");
    
    int                 lines     = 10;
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES 
                        | TokenizerProperties.F_COUNT_LINES
                        | TokenizerProperties.F_ALLOW_NESTED_COMMENTS);
      props.addLineComment(TokenizerProperties.DEFAULT_LINE_COMMENT);
      props.addBlockComment(TokenizerProperties.DEFAULT_BLOCK_COMMENT_START, TokenizerProperties.DEFAULT_BLOCK_COMMENT_END);
      props.addString("'", "'", "'");
      tokenizer.setSource(reader);

      // first line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(1) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(2) wrong start position  " + token.getStartPosition(), token.getStartPosition() == 0);
      assertTrue("(3) wrong start line " + token.getStartLine(), token.getStartLine() == 0);
      assertTrue("(4) wrong start column" + token.getStartColumn(), token.getStartColumn() == 0);
      assertTrue("(5) wrong end line " + token.getEndLine(), token.getEndLine() == token.getStartLine() + 1);
      assertTrue("(6) wrong end column" + token.getEndColumn(), token.getEndColumn() == 0);

      // block comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(10) block comment not recognized", token.getType() == Token.BLOCK_COMMENT);
      assertTrue("(11) wrong start line " + token.getStartLine(), token.getStartLine() == 1);
      assertTrue("(12) wrong start column" + token.getStartColumn(), token.getStartColumn() == 0);
      assertTrue("(13) wrong end line " + token.getEndLine(), token.getEndLine() == token.getStartLine() + 5);
      assertTrue("(14) wrong end column" + token.getEndColumn(), token.getEndColumn() == 2);
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(15) newline behind block comment not recognized as whitespace", token.getType() == Token.WHITESPACE);
      assertTrue("(16) newline behind block comment not recognized as literal", tokenizer.currentImage().equals("\n"));

      // second line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(21) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(22) wrong start line " + token.getStartLine(), token.getStartLine() == 7);
      assertTrue("(23) wrong end line " + token.getEndLine(), token.getEndLine() == token.getStartLine() + 1);

      // string 1
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(31) string not recognized", token.getType() == Token.STRING);
      assertTrue("(32) wrong start line " + token.getStartLine(), token.getStartLine() == 8);
      assertTrue("(33) wrong start column" + token.getStartColumn(), token.getStartColumn() == 0);
      assertTrue("(34) wrong end line " + token.getEndLine(), token.getEndLine() == token.getStartLine());
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(35) newline behind string not recognized as whitespace", token.getType() == Token.WHITESPACE);
      assertTrue("(36) newline behind string not recognized as literal", tokenizer.currentImage().equals("\n"));

      // string 2
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(41) string not recognized", token.getType() == Token.STRING);
      assertTrue("(42) wrong start line " + token.getStartLine(), token.getStartLine() == 9);
      assertTrue("(43) wrong start column" + token.getStartColumn(), token.getStartColumn() == 0);
      assertTrue("(44) wrong end line " + token.getEndLine(), token.getEndLine() == token.getStartLine());
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(45) newline behind string not recognized as whitespace", token.getType() == Token.WHITESPACE);
      assertTrue("(46) newline behind string not recognized as literal", tokenizer.currentImage().equals("\n"));

      // EOF should be reached here
      token = tokenizer.nextToken();
      assertTrue(token.getType() == Token.EOF);

    } finally {
      // Cleanup
      tokenizer.close();
    }
  }
  
  
  /**
   * Test reader switching
   */
  public void testReaderSwitching() throws Throwable {
    Reader reader1 = new StringReader("0/2 4/6 8/10");
    Reader reader2 = new StringReader("0/2 4/6 8/10");
    Reader reader3 = new StringReader("0/2 4/6 8/10");
    Reader[] readers = { reader1, reader2, reader3 };
    
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      for (int readerIdx = 0; readerIdx < readers.length; ++readerIdx) {
        tokenizer.setSource(readers[readerIdx]);
        for (int ii = 0; ii <= 8; ii += 4) {
          assertTrue(tokenizer.hasMoreToken());
          token = tokenizer.nextToken();
          assertTrue("Wrong start position " + token.getStartPosition(), token.getStartPosition() == ii);
          assertTrue("Wrong type " + token.getType(), token.getType() == Token.NORMAL);
          assertTrue("Token not recognized as literal", tokenizer.currentImage().equals(Integer.toString(ii)));
          assertTrue(tokenizer.hasMoreToken());
          token = tokenizer.nextToken();
          assertTrue("Wrong start position " + token.getStartPosition(), token.getStartPosition() == ii + 1);
          assertTrue("Wrong type " + token.getType(), token.getType() == Token.SEPARATOR);
          assertTrue("Separator not recognized as literal", tokenizer.currentImage().equals("/"));
          assertTrue(tokenizer.hasMoreToken());
          token = tokenizer.nextToken();
          assertTrue("Wrong start position " + token.getStartPosition(), token.getStartPosition() == ii + 2);
          assertTrue("Wrong type " + token.getType(), token.getType() == Token.NORMAL);
          assertTrue("Token not recognized as literal", tokenizer.currentImage().equals(Integer.toString(ii + 2)));
        }
      }
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }


  /**
   * Line counting and line comments in DOS files
   */
  public void testDOSEOL() throws Throwable {
    Reader reader = new StringReader(
      "// line comment with DOS line ending\r\n"
    + "void main(int argc)\r\n"
    + "{\r\n"
    + "  // another line comment\r\n"
    + "  /* a block comment\r\n"
    + "     with more than one line\r\n" 
    + "  */\r\n"
    + "}\r\n");
    
    int       lines     = 8;
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token     token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES | TokenizerProperties.F_COUNT_LINES);
      props.addLineComment(TokenizerProperties.DEFAULT_LINE_COMMENT);
      props.addBlockComment(TokenizerProperties.DEFAULT_BLOCK_COMMENT_START, TokenizerProperties.DEFAULT_BLOCK_COMMENT_END);
      props.addString("\"", "\"", "\\");
      tokenizer.setSource(reader);

      // zero line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(1) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(2) start line wrong", token.getStartLine() == 0);
      assertTrue("(3) start column wrong", token.getStartColumn() == 0);
      assertTrue("(4) end line wrong", token.getEndLine() == 1);
      assertTrue("(5) end column wrong", token.getEndColumn() == 0);

      // first line: void
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(10) token \"void\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("void"));
      assertTrue("(11) start line wrong", token.getStartLine() == 1);
      assertTrue("(12) start column wrong", token.getStartColumn() == 0);
      assertTrue("(13) end line wrong", token.getEndLine() == 1);
      assertTrue("(14) end column wrong", token.getEndColumn() == 4);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(15) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // first line: main
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(20) token \"main\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("main"));
      assertTrue("(21) start line wrong", token.getStartLine() == 1);
      assertTrue("(22) start column wrong", token.getStartColumn() == 5);
      assertTrue("(23) end line wrong", token.getEndLine() == 1);
      assertTrue("(24) end column wrong", token.getEndColumn() == 9);

      // first line: (
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(30) token \"(\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("("));
      assertTrue("(31) start line wrong", token.getStartLine() == 1);
      assertTrue("(32) start column wrong", token.getStartColumn() == 9);
      assertTrue("(33) end line wrong", token.getEndLine() == 1);
      assertTrue("(34) end column wrong", token.getEndColumn() == 10);

      // first line: int
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(40) token \"int\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("int"));
      assertTrue("(41) start line wrong", token.getStartLine() == 1);
      assertTrue("(42) start column wrong", token.getStartColumn() == 10);
      assertTrue("(43) end line wrong", token.getEndLine() == 1);
      assertTrue("(44) end column wrong", token.getEndColumn() == 13);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(45) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // first line: argc
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(50) token \"argc\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("argc"));
      assertTrue("(51) start line wrong", token.getStartLine() == 1);
      assertTrue("(52) start column wrong", token.getStartColumn() == 14);
      assertTrue("(53) end line wrong", token.getEndLine() == 1);
      assertTrue("(54) end column wrong", token.getEndColumn() == 18);

      // first line: )
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(60) token \")\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals(")"));
      assertTrue("(61) start line wrong", token.getStartLine() == 1);
      assertTrue("(62) start column wrong", token.getStartColumn() == 18);
      assertTrue("(63) end line wrong", token.getEndLine() == 1);
      assertTrue("(64) end column wrong", token.getEndColumn() == 19);

      // first line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(60) token \"\\r\\n\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r\n"));
      assertTrue("(61) start line wrong", token.getStartLine() == 1);
      assertTrue("(62) start column wrong", token.getStartColumn() == 19);
      assertTrue("(63) end line wrong", token.getEndLine() == 2);
      assertTrue("(64) end column wrong", token.getEndColumn() == 0);
      assertTrue("(65) wrong length", token.getLength() == 2);

      // second line: {
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(70) token \"{\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("{"));
      assertTrue("(71) start line wrong", token.getStartLine() == 2);
      assertTrue("(72) start column wrong", token.getStartColumn() == 0);
      assertTrue("(73) end line wrong", token.getEndLine() == 2);
      assertTrue("(74) end column wrong", token.getEndColumn() == 1);

      // second/third line: EOL + whitespaces
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(80) token \"\\r\\n  \" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r\n  "));
      assertTrue("(81) start line wrong", token.getStartLine() == 2);
      assertTrue("(82) start column wrong", token.getStartColumn() == 1);
      assertTrue("(83) end line wrong", token.getEndLine() == 3);
      assertTrue("(84) end column wrong", token.getEndColumn() == 2);
      assertTrue("(85) wrong length", token.getLength() == 4);

      // third line: line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(91) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(92) start line wrong", token.getStartLine() == 3);
      assertTrue("(93) start column wrong", token.getStartColumn() == 2);
      assertTrue("(94) end line wrong", token.getEndLine() == 4);
      assertTrue("(95) end column wrong", token.getEndColumn() == 0);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(96) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // forth line: block comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(101) block comment not recognized", token.getType() == Token.BLOCK_COMMENT);
      assertTrue("(102) start line wrong", token.getStartLine() == 4);
      assertTrue("(103) start column wrong", token.getStartColumn() == 2);
      assertTrue("(104) end line wrong", token.getEndLine() == 6);
      assertTrue("(105) end column wrong", token.getEndColumn() == 4);

      // 6th line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(110) token \"\\r\\n\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r\n"));
      assertTrue("(111) start line wrong", token.getStartLine() == 6);
      assertTrue("(112) start column wrong", token.getStartColumn() == 4);
      assertTrue("(113) end line wrong", token.getEndLine() == 7);
      assertTrue("(114) end column wrong", token.getEndColumn() == 0);
      assertTrue("(115) wrong length", token.getLength() == 2);

      // 7th line: }
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(120) token \"}\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("}"));
      assertTrue("(121) start line wrong", token.getStartLine() == 7);
      assertTrue("(122) start column wrong", token.getStartColumn() == 0);
      assertTrue("(123) end line wrong", token.getEndLine() == 7);
      assertTrue("(124) end column wrong", token.getEndColumn() == 1);

      // 7th line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(130) token \"\\r\\n\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r\n"));
      assertTrue("(131) start line wrong", token.getStartLine() == 7);
      assertTrue("(132) start column wrong", token.getStartColumn() == 1);
      assertTrue("(133) end line wrong", token.getEndLine() == 8);
      assertTrue("(134) end column wrong", token.getEndColumn() == 0);
      assertTrue("(135) wrong length", token.getLength() == 2);
      
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Line counting and line comments in MAC files
   */
  public void testMACEOL() throws Throwable {
    Reader reader = new StringReader(
      "// line comment with DOS line ending\r"
    + "void main(int argc)\r"
    + "{\r"
    + "  // another line comment\r"
    + "  /* a block comment\r"
    + "     with more than one line\r" 
    + "  */\r"
    + "}\r");
    
    int                 lines     = 8;
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES | TokenizerProperties.F_COUNT_LINES);
      props.addLineComment(TokenizerProperties.DEFAULT_LINE_COMMENT);
      props.addBlockComment(TokenizerProperties.DEFAULT_BLOCK_COMMENT_START, TokenizerProperties.DEFAULT_BLOCK_COMMENT_END);
      props.addString("\"", "\"", "\\");
      tokenizer.setSource(reader);

      // zero line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(1) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(2) start line wrong", token.getStartLine() == 0);
      assertTrue("(3) start column wrong", token.getStartColumn() == 0);
      assertTrue("(4) end line wrong", token.getEndLine() == 1);
      assertTrue("(5) end column wrong", token.getEndColumn() == 0);

      // first line: void
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(10) token \"void\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("void"));
      assertTrue("(11) start line wrong", token.getStartLine() == 1);
      assertTrue("(12) start column wrong", token.getStartColumn() == 0);
      assertTrue("(13) end line wrong", token.getEndLine() == 1);
      assertTrue("(14) end column wrong", token.getEndColumn() == 4);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(15) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // first line: main
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(20) token \"main\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("main"));
      assertTrue("(21) start line wrong", token.getStartLine() == 1);
      assertTrue("(22) start column wrong", token.getStartColumn() == 5);
      assertTrue("(23) end line wrong", token.getEndLine() == 1);
      assertTrue("(24) end column wrong", token.getEndColumn() == 9);

      // first line: (
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(30) token \"(\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("("));
      assertTrue("(31) start line wrong", token.getStartLine() == 1);
      assertTrue("(32) start column wrong", token.getStartColumn() == 9);
      assertTrue("(33) end line wrong", token.getEndLine() == 1);
      assertTrue("(34) end column wrong", token.getEndColumn() == 10);

      // first line: int
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(40) token \"int\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("int"));
      assertTrue("(41) start line wrong", token.getStartLine() == 1);
      assertTrue("(42) start column wrong", token.getStartColumn() == 10);
      assertTrue("(43) end line wrong", token.getEndLine() == 1);
      assertTrue("(44) end column wrong", token.getEndColumn() == 13);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(45) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // first line: argc
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(50) token \"argc\" not recognized.", token.getType() == Token.NORMAL && token.getImage().equals("argc"));
      assertTrue("(51) start line wrong", token.getStartLine() == 1);
      assertTrue("(52) start column wrong", token.getStartColumn() == 14);
      assertTrue("(53) end line wrong", token.getEndLine() == 1);
      assertTrue("(54) end column wrong", token.getEndColumn() == 18);

      // first line: )
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(60) token \")\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals(")"));
      assertTrue("(61) start line wrong", token.getStartLine() == 1);
      assertTrue("(62) start column wrong", token.getStartColumn() == 18);
      assertTrue("(63) end line wrong", token.getEndLine() == 1);
      assertTrue("(64) end column wrong", token.getEndColumn() == 19);

      // first line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(60) token \"\\r\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r"));
      assertTrue("(61) start line wrong", token.getStartLine() == 1);
      assertTrue("(62) start column wrong", token.getStartColumn() == 19);
      assertTrue("(63) end line wrong", token.getEndLine() == 2);
      assertTrue("(64) end column wrong", token.getEndColumn() == 0);
      assertTrue("(65) wrong length", token.getLength() == 1);

      // second line: {
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(70) token \"{\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("{"));
      assertTrue("(71) start line wrong", token.getStartLine() == 2);
      assertTrue("(72) start column wrong", token.getStartColumn() == 0);
      assertTrue("(73) end line wrong", token.getEndLine() == 2);
      assertTrue("(74) end column wrong", token.getEndColumn() == 1);

      // second/third line: EOL + whitespaces
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(80) token \"\\r  \" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r  "));
      assertTrue("(81) start line wrong", token.getStartLine() == 2);
      assertTrue("(82) start column wrong", token.getStartColumn() == 1);
      assertTrue("(83) end line wrong", token.getEndLine() == 3);
      assertTrue("(84) end column wrong", token.getEndColumn() == 2);
      assertTrue("(85) wrong length", token.getLength() == 3);

      // third line: line comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(91) line comment not recognized", token.getType() == Token.LINE_COMMENT);
      assertTrue("(92) start line wrong", token.getStartLine() == 3);
      assertTrue("(93) start column wrong", token.getStartColumn() == 2);
      assertTrue("(94) end line wrong", token.getEndLine() == 4);
      assertTrue("(95) end column wrong", token.getEndColumn() == 0);

      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(96) whitespace not recognized", token.getType() == Token.WHITESPACE);

      // forth line: block comment
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(101) block comment not recognized", token.getType() == Token.BLOCK_COMMENT);
      assertTrue("(102) start line wrong", token.getStartLine() == 4);
      assertTrue("(103) start column wrong", token.getStartColumn() == 2);
      assertTrue("(104) end line wrong", token.getEndLine() == 6);
      assertTrue("(105) end column wrong", token.getEndColumn() == 4);

      // 6th line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(110) token \"\\r\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r"));
      assertTrue("(111) start line wrong", token.getStartLine() == 6);
      assertTrue("(112) start column wrong", token.getStartColumn() == 4);
      assertTrue("(113) end line wrong", token.getEndLine() == 7);
      assertTrue("(114) end column wrong", token.getEndColumn() == 0);
      assertTrue("(115) wrong length", token.getLength() == 1);

      // 7th line: }
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(120) token \"}\" not recognized.", token.getType() == Token.SEPARATOR && token.getImage().equals("}"));
      assertTrue("(121) start line wrong", token.getStartLine() == 7);
      assertTrue("(122) start column wrong", token.getStartColumn() == 0);
      assertTrue("(123) end line wrong", token.getEndLine() == 7);
      assertTrue("(124) end column wrong", token.getEndColumn() == 1);

      // 7th line: EOL
      assertTrue(tokenizer.hasMoreToken());
      token = tokenizer.nextToken();
      assertTrue("(130) token \"\\r\" not recognized.", token.getType() == Token.WHITESPACE && token.getImage().equals("\r"));
      assertTrue("(131) start line wrong", token.getStartLine() == 7);
      assertTrue("(132) start column wrong", token.getStartColumn() == 1);
      assertTrue("(133) end line wrong", token.getEndLine() == 8);
      assertTrue("(134) end column wrong", token.getEndColumn() == 0);
      assertTrue("(135) wrong length", token.getLength() == 1);

    } finally {
      // Cleanup
      tokenizer.close();
    }
  }

  /**
   * Line counting with setReadPosition
   */
  public void testLineCounting() throws Throwable {
    Reader reader = new StringReader(
      "01234 67 9\r\n"
    + "0 2 4 6 8\r"
    + " 1 3 5 7 9\n"
    + "01 34 67 9\n"
    + "/* block comment\n"
    + "   in three lines\r\n"
    + "*/\n"
    + "// line comment 1\r"
    + "// line comment 2\r\n"
    + "// line comment 3\n"
    + "abc // line comment 1\r"
    + "01 34 67 // line comment 2\r\n"
    + "/* block comment */ // line comment 3\n");
    
    int[] expectedLines = {
      0, 0, 0,
      1, 1, 1, 1, 1,
      2, 2, 2, 2, 2,
      3, 3, 3, 3,
      4,
      7,
      8,
      9,
      10, 10,
      11, 11, 11, 11,
      12, 12
    };
    int[] expectedColumns = {
      0, 6, 9,
      0, 2, 4, 6, 8,
      1, 3, 5, 7, 9,
      0, 3, 6, 9,
      0,
      0,
      0,
      0,
      0, 4,
      0, 3, 6, 9,
      0, 20
    };
    
    TokenizerProperties props     = new StandardTokenizerProperties();
    StandardTokenizer   tokenizer = new StandardTokenizer(props);
    Token               token1;
    Token               token2;
    int                 line      = 0;
    int                 column    = 0;
    int                 index     = 0;

    try {
      props.setParseFlags(TokenizerProperties.F_RETURN_WHITESPACES | TokenizerProperties.F_COUNT_LINES);
      props.addLineComment(TokenizerProperties.DEFAULT_LINE_COMMENT);
      props.addBlockComment(TokenizerProperties.DEFAULT_BLOCK_COMMENT_START, TokenizerProperties.DEFAULT_BLOCK_COMMENT_END);
      tokenizer.setSource(reader);

      while (tokenizer.hasMoreToken()) {
        token1 = tokenizer.nextToken();
        assertTrue("Wrong line/column " + token1.getStartLine() + "/" + token1.getStartColumn(),
                   token1.getStartLine() == line && token1.getStartColumn() == column);

        tokenizer.setReadPositionRelative(-token1.getLength());
        token2 = tokenizer.nextToken();
        assertTrue("Wrong line/column " + token2.getStartLine() + "/" + token2.getStartColumn(),
                   token2.getStartLine() == line && token2.getStartColumn() == column);

        assertTrue("Token mismatch:\n  " + token1 + "\n  " + token2, token1.equals(token2));

        line    = token1.getEndLine();
        column  = token1.getEndColumn();

        // cross check the line and columns
        if (token1.getType() != Token.WHITESPACE && token1.getType() != Token.EOF) {
          assertTrue("Expected line " + expectedLines[index] + ", found " + token1.getStartLine(),
                      token1.getStartLine() == expectedLines[index]);
          assertTrue("Expected column " + expectedColumns[index] + ", found " + token1.getStartColumn(),
                      token1.getStartColumn() == expectedColumns[index]);
          index++;
        }
      }
    } finally {
      // Cleanup
      tokenizer.close();
    }
  }
}  


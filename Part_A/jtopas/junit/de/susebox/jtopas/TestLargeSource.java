/*
 * TestLargeSource.java: JUnit test for the StandardTokenizer
 *
 * Copyright (C) 2003 Heiko Blau
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
import java.util.Iterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import de.susebox.jtopas.Token;
import de.susebox.jtopas.Tokenizer;
import de.susebox.jtopas.StandardTokenizer;
import de.susebox.jtopas.StandardTokenizerProperties;
import de.susebox.jtopas.TokenizerProperty;
import de.susebox.jtopas.TokenizerException;

import de.susebox.TestUtilities;


//-----------------------------------------------------------------------------
// Class TestLargeSource
//

/**<p>
 * This test suite generates a huge file with a common mix of comments, special
 * sequences, keywords, separators etc. It is mainly designed for profiling
 * sessions to find the hot spots.
 *</p>
 *
 * @author  Heiko Blau
 */
public class TestLargeSource extends TestCase {
  
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
    String[]   tests = { TestLargeSource.class.getName() };
    
    TestUtilities.run(tests, args);
  }
  

  //---------------------------------------------------------------------------
  // suite method
  //
  
  /**
   * Implementation of the JUnit method <code>suite</code>. This one creates
   * a large source file java style.
   *
   * @return a test suite
   */
  public static Test suite() {
    // Construct the test suite
    TestSuite suite = new TestSuite(TestLargeSource.class.getName());
    
    suite.addTest(new TestLargeSource("parseSource"));
    suite.addTest(new TestLargeSource("parseSourceWithoutPattern"));
    suite.addTest(new TestLargeSource("parseSourceWithoutPatternAndKeyword"));
    return suite;
  }
  
  
  //---------------------------------------------------------------------------
  // Constructor
  //
  
  /**
   * Default constructor.
   */  
  public TestLargeSource(String test) {
    super(test);
  }

  
  //---------------------------------------------------------------------------
  // Fixture setup and release
  //
  
  /**
   * Sets up the fixture, for example, open a network connection.
   * This method is called before a test is executed.
   */
  protected void setUp() throws Exception {
    // create a temporary file that is removed on exit
    OutputStreamWriter  writer;
    
    _smallFile = File.createTempFile("jtopas", null); 
    _smallFile.deleteOnExit();
    _largeFile = File.createTempFile("jtopas", null); 
    _largeFile.deleteOnExit();
    
    writer = new OutputStreamWriter(new FileOutputStream(_smallFile));
    for (int count = 0; count < SMALL_LOOPS; ++count) {
      writer.write(CODE_PIECE);
    }
    writer.close();
    
    writer = new OutputStreamWriter(new FileOutputStream(_largeFile));
    for (int count = 0; count < SMALL_LOOPS * LARGE_SMALL_RATIO; ++count) {
      writer.write(CODE_PIECE);
    }
    writer.close();
      
    // initializing the tokenizer properties
    _properties = new StandardTokenizerProperties(TokenizerProperties.F_RETURN_WHITESPACES
                                                | TokenizerProperties.F_TOKEN_POS_ONLY
                                                | TokenizerProperties.F_COUNT_LINES
                                                | TokenizerProperties.F_CASE);

    for (int index = 0; index < _javaProperties.length; ++index) {
      _properties.addProperty(_javaProperties[index]);
    }
  }

  
  /**
   * Tears down the fixture, for example, close a network connection.
   * This method is called after a test is executed.
   */
  protected void tearDown() throws Exception {}
  
  
  //---------------------------------------------------------------------------
  // test cases
  //
  
  
  /**
   * Tokenizing a large source
   */
  public void parseSource() throws Throwable {
    StandardTokenizer tokenizer = new StandardTokenizer(_properties);
    
    try {
      System.out.println("parseSource: Tokenizing small file " + _smallFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_smallFile)));
      long smallTime = tokenize(tokenizer);

      System.out.println("parseSource: Tokenizing large file " + _largeFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_largeFile)));
      long largeTime = tokenize(tokenizer);

      compareTime(smallTime, largeTime);
    } finally {
      tokenizer.close();
    }
  }

  
  /**
   * Tokenizing a large source without pattern matching
   */
  public void parseSourceWithoutPattern() throws Throwable {
    StandardTokenizer tokenizer = new StandardTokenizer(_properties);
    
    try {
      Iterator iter = _properties.getPatterns();

      while (iter.hasNext()) {
        iter.next();
        iter.remove();
      }

      System.out.println("parseSourceWithoutPattern: Tokenizing small file " + _smallFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_smallFile)));
      long smallTime = tokenize(tokenizer);

      System.out.println("parseSourceWithoutPattern: Tokenizing large file " + _largeFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_largeFile)));
      long largeTime = tokenize(tokenizer);

      compareTime(smallTime, largeTime);
    } finally {
      tokenizer.close();
    }
  }

  
  /**
   * Tokenizing a large source without pattern matching
   */
  public void parseSourceWithoutPatternAndKeyword() throws Throwable {
    StandardTokenizer tokenizer = new StandardTokenizer(_properties);
    
    try {
      Iterator iter = _properties.getKeywords();
    
      while (iter.hasNext()) {
        iter.next();
        iter.remove();
      }
      iter = _properties.getPatterns();
      while (iter.hasNext()) {
        iter.next();
        iter.remove();
      }

      System.out.println("parseSourceWithoutPatternAndKeyword: Tokenizing small file " + _smallFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_smallFile)));
      long smallTime = tokenize(tokenizer);

      System.out.println("parseSourceWithoutPatternAndKeyword: Tokenizing large file " + _largeFile.getPath());
      tokenizer.setSource(new InputStreamReader(new FileInputStream(_largeFile)));
      long largeTime = tokenize(tokenizer);

      compareTime(smallTime, largeTime);
    } finally {
      tokenizer.close();
    }
  }

  
  //---------------------------------------------------------------------------
  // implementation
  //
  
  /**
   * Inner method for tokenizing
   */
  private long tokenize(Tokenizer tokenizer) throws Throwable {
    long    start       = System.currentTimeMillis();
    long    diffTime    = 0;
    int     index       = 0;
    boolean hasPattern  = tokenizer.getTokenizerProperties().getPatterns().hasNext();
    boolean hasKeywords = tokenizer.getTokenizerProperties().getKeywords().hasNext();
    int[]   expected    = hasPattern ? EXPECTED_TOKEN 
                                     : hasKeywords ? EXPECTED_TOKEN_WITHOUT_PATTERN 
                                                   : EXPECTED_TOKEN_WITHOUT_PATTERN_AND_KEYWORDS;
    
    // tokenizer loop
    while (tokenizer.hasMoreToken()) {
      Token token = tokenizer.nextToken();
      int   type  = token.getType();
      
      switch (type) {
      case Token.WHITESPACE:
      case Token.EOF:
        break;
      default:
        // use if instead of assertTrue alone for performance reasons (no string
        // concatenation nessecary)
        // System.out.println(tokenizer.currentImage());
        if (expected[index] != type) {
          assertTrue("Line/Column " + token.getStartLine() + "/" + token.getStartColumn() + ": Expected " + Token.getTypeName(expected[index]) + ", got " + Token.getTypeName(type) + ".\n" + tokenizer.currentImage(), false);
        }
        if (++index >= expected.length) {
          diffTime = System.currentTimeMillis() - start;
          System.out.println("Next " + index + " token processed after " + diffTime + " milliseconds.");
          index = 0;
        }
      }
    }

    // ready
    diffTime = System.currentTimeMillis() - start;
    System.out.println("  Finished after " + diffTime + " milliseconds.");
    return diffTime;
  }
  
  
  //---------------------------------------------------------------------------
  // utility methods
  //
  
  /**
   * Compare the time for the small to the time for the large file
   *
   * @param smallTime   time to parse the small file
   * @param largeTime   time to parse the large file
   */
  private void compareTime(double smallTime, double largeTime) {
    if (largeTime / (smallTime + 1) < 1.2 * LARGE_SMALL_RATIO) {
      System.out.println("Tokenizer too slow. Time for small / large file " 
                        + smallTime + "/" + largeTime + "ms. Exceeding limit ratio of " 
                        + LARGE_SMALL_RATIO + ".");
    }
  }
  
  
  //---------------------------------------------------------------------------
  // class constants
  //
  
  // piece of code the temporary large file consists of
  private static final String CODE_PIECE = 
    "/**\n"
  + " * A Java-like code example with lots of comments, strings, special\n"
  + " * sequences etc.\n"
  + " *<br>\n"
  + " * Even some HTML tags like in real javadoc comments are present :-)\n"
  + " * This piece of code is multiplied into a temporary file to get a really\n"
  + " * huge source file (nothing that should happen in real life).\n"
  + " */\n"
  + "\n"
  + "// package declaration\n"
  + "package ours.my.subpackage;\n"
  + "\n"
  + "// imports\n"
  + "import java.util.*;\n"
  + "import java.io.InputStream;\n"
  + "import java.io.InputStreamReader;\n"
  + "import java.io.OutputStream;\n"
  + "import java.io.OutputStreamWriter;\n"
  + "import java.net.URL;\n"
  + "import java.net.URI;\n"
  + "import javax.swing.*;\n"
  + "\n"
  + "// class declaration\n"
  + "\n"
  + "/**\n"
  + " * An example Java class probably not even syntactically ok.\n"
  + " *\n"
  + " * @see    OtherClass\n"
  + " * @see    java.io.File\n"
  + " * @author me\n"
  + " */\n"
  + "public class MyTestClass implements Serializable {\n"
  + "\n"
  + "  /**\n"
  + "   * The usual main method.\n"
  + "   *\n"
  + "   * @param args the command line options and arguments\n"
  + "   */\n"
  + "   public static void main(String[] args) {\n"
  + "     // create the argument store\n"
  + "     argStore = new ArrayList(32);\n"
  + "\n"
  + "     // wich GUI should be used?\n"
  + "     if (args != null && args.length > 0) {\n"
  + "       if (args[0].equals(\"swingui\")) {\n"
  + "         new junit.swingui.TestRunner().main(tests);\n"
  + "       } else if (args[0].equals(\"awtui\")) {\n"
  + "         new junit.awtui.TestRunner().main(tests);\n"
  + "       } else {\n"
  + "         new junit.textui.TestRunner().main(tests);\n"
  + "       }\n"
  + "     } else {\n"
  + "       new junit.textui.TestRunner().main(tests);\n"
  + "     }\n"
  + "\n"
  + "     // get all the other command line arguments\n"
  + "     double doubleValue = 0.0;\n"
  + "     int    intValue    = 0;\n"
  + "     String stringValue = null;\n"
  + "\n"
  + "     for (int index = 1; args != null && index < args.length; ++index) {\n"
  + "       if (args[index].charAt(0) == '-') {\n"
  + "         // options\n"
  + "         switch (args[index].charAt(1)) {\n"
  + "         case 'd':\n"
  + "           doubleValue = Double.valueOf(args[index].substring(2)).doubleValue();\n"
  + "           break;\n"
  + "         case 's':\n"
  + "           stringValue = args[index].substring(2);\n"
  + "           break;\n"
  + "         case 'i':\n"
  + "           intValue = Integer.valueOf(args[index].substring(2)).intValue();\n"
  + "           break;\n"
  + "         default:\n"
  + "           stringValue = \"\";\n"
  + "           doubleValue = 0.0;\n"
  + "           intValue    = 0;\n"
  + "         }\n"
  + "\n"
  + "       } else {\n"
  + "         // normal arguments\n"
  + "         if ( ! argStore.contains(args[index])) {\n"
  + "           argStore.add(args[index]);\n"
  + "         } else {\n"
  + "           System.out.println(\"Duplicate element \\\"\" + args[index] + \"\\\".\");\n"
  + "           /* perhaps better use Environment.getEnvironment(this).out().println() */\n"
  + "         }\n"
  + "       }\n"
  + "     }\n"
  + "   }\n"
  + "\n"
  + "  /**\n"
  + "   * The argument store.\n"
  + "   */\n"
  + "   private ArrayList argStore = null;\n"
  + "}\n"
  + "\n"
  + "\n";
  
  // expected token types (without whitespaces)
  private static final int EXPECTED_TOKEN[] = {
    Token.BLOCK_COMMENT,
    Token.LINE_COMMENT,     // "// package declaration\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "package ours.my.subpackage;\n"
    Token.LINE_COMMENT,     // "// imports\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import java.util.*;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStream;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStreamReader;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStream;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStreamWriter;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URL;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URI;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import javax.swing.*;\n"
    Token.LINE_COMMENT,     // "// class declaration\n"
    Token.BLOCK_COMMENT,
    Token.KEYWORD, Token.KEYWORD, Token.NORMAL, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "public class MyTestClass implementes Serializable {\n"
    Token.BLOCK_COMMENT,
    Token.KEYWORD, Token.KEYWORD, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "   public static void main(String[] args) {\n"
    Token.LINE_COMMENT,     // "// create the argument store\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     argStore = new ArrayList(32);\n"
    Token.LINE_COMMENT,     // " // wich GUI should be used?\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     if (args != null && args.length > 0) {\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       if (args[0].equals(\"swingui\")) {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.swingui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       } else if (args[0].equals(\"awtui\")) {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.awtui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,  // "       } else {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,  // "     } else {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "       new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.LINE_COMMENT,     // "     // get all the other command line arguments\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE,   // "     double doubleValue = 0.0;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE,   // "     int    intValue    = 0;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,   // "     String stringValue = null;\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, //"     for (int index = 1; args != null && index < args.length; ++index) {\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   //  "       if (args[index].charAt(0) == '-') {\n"
    Token.LINE_COMMENT,     // "         // options\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "         switch (args[index].charAt(1)) {\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 'd':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           doubleValue = Double.valueOf(args[index].substring(2)).doubleValue();\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 's':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "           stringValue = args[index].substring(2);\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 'i':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           intValue = Integer.valueOf(args[index].substring(2)).intValue();\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.SEPARATOR, // "         default:\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE,   // "           stringValue = \"\";\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE,  // "           doubleValue = 0.0;\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.PATTERN, Token.SPECIAL_SEQUENCE,  // "           intValue    = 0;\n"
    Token.SPECIAL_SEQUENCE, // "         }\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "       } else {\n"
    Token.LINE_COMMENT,     // "         // normal arguments\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         if ( ! argStore.contains(args[index])) {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           argStore.add(args[index]);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "         } else {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           System.out.println(\"Duplicate element \\\" + args[index] + \"\\\".\");\n"
    Token.BLOCK_COMMENT,      // "           /* perhaps better use Environment.getEnvironment(this).out().println() */\n"
    Token.SPECIAL_SEQUENCE,   // "         }\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.SPECIAL_SEQUENCE,   // "   }\n"
    Token.BLOCK_COMMENT, 
    Token.KEYWORD, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "   private ArrayList argStore = null;\n"
    Token.SPECIAL_SEQUENCE    // "}\n"
  };
  
  // expected token types (without whitespaces) when no pattern matching is performed
  private static final int EXPECTED_TOKEN_WITHOUT_PATTERN[] = {
    Token.BLOCK_COMMENT,
    Token.LINE_COMMENT,     // "// package declaration\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "package ours.my.subpackage;\n"
    Token.LINE_COMMENT,     // "// imports\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import java.util.*;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStream;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStreamReader;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStream;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStreamWriter;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URL;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URI;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import javax.swing.*;\n"
    Token.LINE_COMMENT,     // "// class declaration\n"
    Token.BLOCK_COMMENT,
    Token.KEYWORD, Token.KEYWORD, Token.NORMAL, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "public class MyTestClass implementes Serializable {\n"
    Token.BLOCK_COMMENT,
    Token.KEYWORD, Token.KEYWORD, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "   public static void main(String[] args) {\n"
    Token.LINE_COMMENT,     // "// create the argument store\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     argStore = new ArrayList(32);\n"
    Token.LINE_COMMENT,     // " // wich GUI should be used?\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     if (args != null && args.length > 0) {\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       if (args[0].equals(\"swingui\")) {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.swingui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       } else if (args[0].equals(\"awtui\")) {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.awtui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,  // "       } else {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,  // "     } else {\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "       new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.LINE_COMMENT,     // "     // get all the other command line arguments\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,   // "     double doubleValue = 0.0;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,   // "     int    intValue    = 0;\n"
    Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE,   // "     String stringValue = null;\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, //"     for (int index = 1; args != null && index < args.length; ++index) {\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   //  "       if (args[index].charAt(0) == '-') {\n"
    Token.LINE_COMMENT,     // "         // options\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "         switch (args[index].charAt(1)) {\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 'd':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           doubleValue = Double.valueOf(args[index].substring(2)).doubleValue();\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 's':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "           stringValue = args[index].substring(2);\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.STRING, Token.SEPARATOR, // "         case 'i':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           intValue = Integer.valueOf(args[index].substring(2)).intValue();\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.KEYWORD, Token.SEPARATOR, // "         default:\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE,   // "           stringValue = \"\";\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "           doubleValue = 0.0;\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "           intValue    = 0;\n"
    Token.SPECIAL_SEQUENCE, // "         }\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "       } else {\n"
    Token.LINE_COMMENT,     // "         // normal arguments\n"
    Token.KEYWORD, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         if ( ! argStore.contains(args[index])) {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           argStore.add(args[index]);\n"
    Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "         } else {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           System.out.println(\"Duplicate element \\\" + args[index] + \"\\\".\");\n"
    Token.BLOCK_COMMENT,      // "           /* perhaps better use Environment.getEnvironment(this).out().println() */\n"
    Token.SPECIAL_SEQUENCE,   // "         }\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.SPECIAL_SEQUENCE,   // "   }\n"
    Token.BLOCK_COMMENT, 
    Token.KEYWORD, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.KEYWORD, Token.SPECIAL_SEQUENCE, // "   private ArrayList argStore = null;\n"
    Token.SPECIAL_SEQUENCE    // "}\n"
  };
  
  // expected token types (without whitespaces) when no pattern matching is performed
  private static final int EXPECTED_TOKEN_WITHOUT_PATTERN_AND_KEYWORDS[] = {
    Token.BLOCK_COMMENT,
    Token.LINE_COMMENT,     // "// package declaration\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "package ours.my.subpackage;\n"
    Token.LINE_COMMENT,     // "// imports\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import java.util.*;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStream;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.InputStreamReader;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStream;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.io.OutputStreamWriter;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URL;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "import java.net.URI;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "import javax.swing.*;\n"
    Token.LINE_COMMENT,     // "// class declaration\n"
    Token.BLOCK_COMMENT,
    Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "public class MyTestClass implementes Serializable {\n"
    Token.BLOCK_COMMENT,
    Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "   public static void main(String[] args) {\n"
    Token.LINE_COMMENT,     // "// create the argument store\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     argStore = new ArrayList(32);\n"
    Token.LINE_COMMENT,     // " // wich GUI should be used?\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "     if (args != null && args.length > 0) {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       if (args[0].equals(\"swingui\")) {\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.swingui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "       } else if (args[0].equals(\"awtui\")) {\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.awtui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "       } else {\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "     } else {\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "       new junit.textui.TestRunner().main(tests);\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.LINE_COMMENT,     // "     // get all the other command line arguments\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,   // "     double doubleValue = 0.0;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,   // "     int    intValue    = 0;\n"
    Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,   // "     String stringValue = null;\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, //"     for (int index = 1; args != null && index < args.length; ++index) {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   //  "       if (args[index].charAt(0) == '-') {\n"
    Token.LINE_COMMENT,     // "         // options\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "         switch (args[index].charAt(1)) {\n"
    Token.NORMAL, Token.STRING, Token.SEPARATOR, // "         case 'd':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           doubleValue = Double.valueOf(args[index].substring(2)).doubleValue();\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.NORMAL, Token.STRING, Token.SEPARATOR, // "         case 's':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "           stringValue = args[index].substring(2);\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.NORMAL, Token.STRING, Token.SEPARATOR, // "         case 'i':\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, // "           intValue = Integer.valueOf(args[index].substring(2)).intValue();\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE,        // "           break;\n"
    Token.NORMAL, Token.SEPARATOR, // "         default:\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE,   // "           stringValue = \"\";\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "           doubleValue = 0.0;\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE,  // "           intValue    = 0;\n"
    Token.SPECIAL_SEQUENCE, // "         }\n"
    Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, // "       } else {\n"
    Token.LINE_COMMENT,     // "         // normal arguments\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,  // "         if ( ! argStore.contains(args[index])) {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           argStore.add(args[index]);\n"
    Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, // "         } else {\n"
    Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE, Token.STRING, Token.SPECIAL_SEQUENCE, Token.SPECIAL_SEQUENCE,   // "           System.out.println(\"Duplicate element \\\" + args[index] + \"\\\".\");\n"
    Token.BLOCK_COMMENT,      // "           /* perhaps better use Environment.getEnvironment(this).out().println() */\n"
    Token.SPECIAL_SEQUENCE,   // "         }\n"
    Token.SPECIAL_SEQUENCE,   // "       }\n"
    Token.SPECIAL_SEQUENCE,   // "     }\n"
    Token.SPECIAL_SEQUENCE,   // "   }\n"
    Token.BLOCK_COMMENT, 
    Token.NORMAL, Token.NORMAL, Token.NORMAL, Token.SPECIAL_SEQUENCE, Token.NORMAL, Token.SPECIAL_SEQUENCE, // "   private ArrayList argStore = null;\n"
    Token.SPECIAL_SEQUENCE    // "}\n"
  };
  
  // Tokenizer properties
  private static final Object JAVADOC_COMPANION         = new Object();
  private static final Object BLOCK_COMMENT_COMPANION   = new Object();
  private static final Object LINE_COMMENT_COMPANION    = new Object();
  private static final Object STRING_COMPANION          = new Object();
  private static final Object CHAR_COMPANION            = new Object();
  private static final Object BRACE_CLOSE_COMPANION     = new Object();
  private static final Object COLON_COMPANION           = new Object();
  private static final Object SEMICOLON_COMPANION       = new Object();
  private static final Object STAR_COMPANION            = new Object();
  
  // Properties
  static final TokenizerProperty[] _javaProperties = {
    new TokenizerProperty(Token.BLOCK_COMMENT, new String[] { "/**", "*/" },      JAVADOC_COMPANION),
    new TokenizerProperty(Token.BLOCK_COMMENT, new String[] { "/*", "*/" },       BLOCK_COMMENT_COMPANION),
    new TokenizerProperty(Token.LINE_COMMENT,  new String[] { "//" },             LINE_COMMENT_COMPANION),
    new TokenizerProperty(Token.STRING,        new String[] { "\"", "\"", "\\" }, STRING_COMPANION),
    new TokenizerProperty(Token.STRING,        new String[] { "'", "'", "\\" },   CHAR_COMPANION),
    new TokenizerProperty(Token.PATTERN,       new String[] { "[+\\-]?[0-9]+\\.?[0-9]*" } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "public"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "protected" } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "private"   } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "class"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "final"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "static"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "interface"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "extends"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "implements"   } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "synchronized" } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "null"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "this"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "super"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "new"       } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "void"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "byte"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "char"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "short"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "int"       } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "long"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "double"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "float"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "String"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "throws"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "static"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "import"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "package"   } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "if"        } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "else"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "for"       } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "while"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "switch"    } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "case"      } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "break"     } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "default"   } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "continue"  } ),
    new TokenizerProperty(Token.KEYWORD, new String[] { "goto"      } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "."   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ";"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ","   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "="   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "=="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "!="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ">"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "<"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ">="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "<="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "+="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "-="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "*="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "/="  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ">>=" } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "<<=" } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "+"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "-"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "*"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "/"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "++"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "--"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ">>"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "<<"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ">>>" } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "&"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "|"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "^"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "&&"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "||"  } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "!"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "{"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "}"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "("   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { ")"   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "["   } ),
    new TokenizerProperty(Token.SPECIAL_SEQUENCE, new String[] { "]"   } )
  };
  
  // ratio between small and large file
  private static final int    SMALL_LOOPS       = 10;
  private static final double LARGE_SMALL_RATIO = 100.0;
  
    
  //---------------------------------------------------------------------------
  // Members
  //
  private TokenizerProperties _properties = null;

  //---------------------------------------------------------------------------
  // class members
  //
  private static File _smallFile = null;
  private static File _largeFile = null;
}


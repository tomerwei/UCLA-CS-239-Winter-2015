/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000,2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Ant" and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

/*
 * This package is based on the work done by Timothy Gerard Endres
 * (time@ice.com) to whom the Ant project is very grateful for his great code.
 */

package org.apache.tools.tar;

/**
 * This class provides static utility methods to work with byte streams.
 *
 * @author Timothy Gerard Endres <a href="mailto:time@ice.com">time@ice.com</a>
 * @author Stefano Mazzocchi <a href="mailto:stefano@apache.org">stefano@apache.org</a>
 */
public class TarUtils {

    /**
     * Parse an octal string from a header buffer. This is used for the
     * file permission mode value.
     *
     * @param header The header buffer from which to parse.
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The long value of the octal string.
     */
    public static long parseOctal(byte[] header, int offset, int length) {
        long    result = 0;
        boolean stillPadding = true;
        int     end = offset + length;

        for (int i = offset; i < end; ++i) {
            if (header[i] == 0) {
                break;
            }

            if (header[i] == (byte) ' ' || header[i] == '0') {
                if (stillPadding) {
                    continue;
                }

                if (header[i] == (byte) ' ') {
                    break;
                }
            }

            stillPadding = false;
            result = (result << 3) + (header[i] - '0');
        }

        return result;
    }

    /**
     * Parse an entry name from a header buffer.
     *
     * @param header The header buffer from which to parse.
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The header's entry name.
     */
    public static StringBuffer parseName(byte[] header, int offset, int length) {
        StringBuffer result = new StringBuffer(length);
        int          end = offset + length;

        for (int i = offset; i < end; ++i) {
            if (header[i] == 0) {
                break;
            }

            result.append((char) header[i]);
        }

        return result;
    }

    /**
     * Determine the number of bytes in an entry name.
     *
     * @param name The header name from which to parse.
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The number of bytes in a header's entry name.
     */
    public static int getNameBytes(StringBuffer name, byte[] buf, int offset, int length) {
        int i;

        for (i = 0; i < length && i < name.length(); ++i) {
            buf[offset + i] = (byte) name.charAt(i);
        }

        for (; i < length; ++i) {
            buf[offset + i] = 0;
        }

        return offset + length;
    }

    /**
     * Parse an octal integer from a header buffer.
     *
     * @param value The header value
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The integer value of the octal bytes.
     */
    public static int getOctalBytes(long value, byte[] buf, int offset, int length) {
        int    idx = length - 1;

        buf[offset + idx] = 0;
        --idx;
        buf[offset + idx] = (byte) ' ';
        --idx;

        if (value == 0) {
            buf[offset + idx] = (byte) '0';
            --idx;
        } else {
            for (long val = value; idx >= 0 && val > 0; --idx) {
                buf[offset + idx] = (byte) ((byte) '0' + (byte) (val & 7));
                val = val >> 3;
            }
        }

        for (; idx >= 0; --idx) {
            buf[offset + idx] = (byte) ' ';
        }

        return offset + length;
    }

    /**
     * Parse an octal long integer from a header buffer.
     *
     * @param value The header value
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The long value of the octal bytes.
     */
    public static int getLongOctalBytes(long value, byte[] buf, int offset, int length) {
        byte[] temp = new byte[length + 1];

        getOctalBytes(value, temp, 0, length + 1);
        System.arraycopy(temp, 0, buf, offset, length);

        return offset + length;
    }

    /**
     * Parse the checksum octal integer from a header buffer.
     *
     * @param value The header value
     * @param offset The offset into the buffer from which to parse.
     * @param length The number of header bytes to parse.
     * @return The integer value of the entry's checksum.
     */
    public static int getCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
        getOctalBytes(value, buf, offset, length);

        buf[offset + length - 1] = (byte) ' ';
        buf[offset + length - 2] = 0;

        return offset + length;
    }

    /**
     * Compute the checksum of a tar entry header.
     *
     * @param buf The tar entry's header buffer.
     * @return The computed checksum.
     */
    public static long computeCheckSum(byte[] buf) {
        long sum = 0;

        for (int i = 0; i < buf.length; ++i) {
            sum += 255 & buf[i];
        }

        return sum;
    }
}

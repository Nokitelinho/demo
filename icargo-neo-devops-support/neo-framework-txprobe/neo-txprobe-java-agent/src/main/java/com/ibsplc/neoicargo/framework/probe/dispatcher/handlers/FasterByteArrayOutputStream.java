/*
 * @(#)ByteArrayOutputStream.java	1.49 04/05/18
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.dispatcher.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * An unsynchronized version of the ByteArrayOutputStream. Also the intial
 * size is set to 500 bytes.<p>
 * Moreover the actual byte array buffer is returned by method <tt>toByteArray</tt>
 * instead of a copy.
 * @author A-1456
 *
 */
class FasterByteArrayOutputStream extends OutputStream {

	/**
	 * The buffer where data is stored.
	 */
	protected byte buf[];

	/**
	 * The number of valid bytes in the buffer.
	 */
	protected int count;

	/**
	 * Creates a new byte array output stream. The buffer capacity is initially
	 * 500 bytes, though its size increases if necessary.
	 */
	public FasterByteArrayOutputStream() {
		this(500);
	}

	/**
	 * Creates a new byte array output stream, with a buffer capacity of the
	 * specified size, in bytes.
	 * 
	 * @param size
	 *            the initial size.
	 * @exception IllegalArgumentException
	 *                if size is negative.
	 */
	public FasterByteArrayOutputStream(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Negative initial size: " + size);
		}
		buf = new byte[size];
	}

	/**
	 * Writes the specified byte to this byte array output stream.
	 * 
	 * @param b
	 *            the byte to be written.
	 */
	public void write(int b) {
		int newcount = count + 1;
		if (newcount > buf.length) {
			byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
			System.arraycopy(buf, 0, newbuf, 0, count);
			buf = newbuf;
		}
		buf[count] = (byte) b;
		count = newcount;
	}

	/**
	 * Writes <code>len</code> bytes from the specified byte array starting at
	 * offset <code>off</code> to this byte array output stream.
	 * 
	 * @param b
	 *            the data.
	 * @param off
	 *            the start offset in the data.
	 * @param len
	 *            the number of bytes to write.
	 */
	public void write(byte b[], int off, int len) {
		if ((off < 0) || (off > b.length) || (len < 0)
				|| ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		int newcount = count + len;
		if (newcount > buf.length) {
			byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
			System.arraycopy(buf, 0, newbuf, 0, count);
			buf = newbuf;
		}
		System.arraycopy(b, off, buf, count, len);
		count = newcount;
	}

	/**
	 * Writes the complete contents of this byte array output stream to the
	 * specified output stream argument, as if by calling the output stream's
	 * write method using <code>out.write(buf, 0, count)</code>.
	 * 
	 * @param out
	 *            the output stream to which to write the data.
	 * @exception IOException
	 *                if an I/O error occurs.
	 */
	public void writeTo(OutputStream out) throws IOException {
		out.write(buf, 0, count);
	}

	/**
	 * Resets the <code>count</code> field of this byte array output stream to
	 * zero, so that all currently accumulated output in the output stream is
	 * discarded. The output stream can be used again, reusing the already
	 * allocated buffer space.
	 * 
	 * @see java.io.ByteArrayInputStream
	 */
	public void reset() {
		count = 0;
	}

	
	public byte toByteArray()[] {
		return buf;
	}

	/**
	 * Returns the current size of the buffer.
	 * 
	 * @return the value of the <code>count</code> field, which is the number
	 *         of valid bytes in this output stream.
	 * @see java.io.ByteArrayOutputStream
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns the internal byte array
	 * @return
	 */
	public byte[] byteArray() {
		return buf;
	}

	/**
	 * Converts the buffer's contents into a string, translating bytes into
	 * characters according to the platform's default character encoding.
	 * 
	 * @return String translated from the buffer's contents.
	 * @since JDK1.1
	 */
	public String toString() {
		return new String(buf, 0, count,Charset.defaultCharset());
	}

	/**
	 * Converts the buffer's contents into a string, translating bytes into
	 * characters according to the specified character encoding.
	 * 
	 * @param enc
	 *            a character-encoding name.
	 * @return String translated from the buffer's contents.
	 * @throws UnsupportedEncodingException
	 *             If the named encoding is not supported.
	 * @since JDK1.1
	 */
	public String toString(String enc) throws UnsupportedEncodingException {
		return new String(buf, 0, count, enc);
	}

	/**
	 * Creates a newly allocated string. Its size is the current size of the
	 * output stream and the valid contents of the buffer have been copied into
	 * it. Each character <i>c</i> in the resulting string is constructed from
	 * the corresponding element <i>b</i> in the byte array such that:
	 * <blockquote>
	 * 
	 * <pre>
	 * c == (char) (((hibyte &amp; 0xff) &lt;&lt; 8) | (b &amp; 0xff))
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @deprecated This method does not properly convert bytes into characters.
	 *             As of JDK&nbsp;1.1, the preferred way to do this is via the
	 *             <code>toString(String enc)</code> method, which takes an
	 *             encoding-name argument, or the <code>toString()</code>
	 *             method, which uses the platform's default character encoding.
	 * 
	 * @param hibyte
	 *            the high byte of each resulting Unicode character.
	 * @return the current contents of the output stream, as a string.
	 * @see java.io.ByteArrayOutputStream#size()
	 * @see java.io.ByteArrayOutputStream#toString(String)
	 * @see java.io.ByteArrayOutputStream#toString()
	 */
	@Deprecated
	public String toString(int hibyte) {
		return new String(buf, hibyte, 0, count);
	}

	/**
	 * Closing a <tt>ByteArrayOutputStream</tt> has no effect. The methods in
	 * this class can be called after the stream has been closed without
	 * generating an <tt>IOException</tt>.
	 * <p>
	 * 
	 */
	public void close() throws IOException {
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
}

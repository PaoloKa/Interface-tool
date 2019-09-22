// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BZip2Compressor.java

package com.alex.util.bzip2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.apache.CBZip2OutputStream;

public class BZip2Compressor {

	public static final byte[] compress(byte[] data) {
		ByteArrayOutputStream compressedBytes = new ByteArrayOutputStream();
		try {
			CBZip2OutputStream out = new CBZip2OutputStream(compressedBytes);
			out.write(data);
			out.close();
			return compressedBytes.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

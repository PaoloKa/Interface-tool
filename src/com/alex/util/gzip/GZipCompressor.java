// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GZipCompressor.java

package com.alex.util.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipCompressor {

	public GZipCompressor() {
	}

	public static final byte[] compress(byte data[]) {
		ByteArrayOutputStream compressedBytes = new ByteArrayOutputStream();
		try {
			GZIPOutputStream out = new GZIPOutputStream(compressedBytes);
			out.write(data);
			out.finish();
			out.close();
			return compressedBytes.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

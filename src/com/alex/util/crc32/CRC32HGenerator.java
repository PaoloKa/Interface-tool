// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CRC32HGenerator.java

package com.alex.util.crc32;

import java.util.zip.CRC32;

public final class CRC32HGenerator {

	public static final CRC32 CRC32Instance = new CRC32();

	public static int getHash(byte[] data) {
		return getHash(data, 0, data.length);
	}

	public static int getHash(byte[] data, int offset, int length) {
		synchronized (CRC32Instance) {
			CRC32Instance.update(data, offset, length);
			int hash = (int) CRC32Instance.getValue();
			CRC32Instance.reset();
			return hash;
		}
	}

	private CRC32HGenerator() {

	}

}

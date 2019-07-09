package com.rs.cache.lzma;

import com.alex.io.InputStream;

//import SevenZip.Compression.LZMA.Decoder;

/**
 * @author _jordan <citellumrsps@gmail.com>
 */
public final class LZMADecompressor {

	/**
	 * Starts the decompression for LZMA.
	 * 
	 * @param buffer The buffer.
	 * @param length The compression length.
	 */
	public static void decompressLZMA(InputStream buffer, int length) {
	/*	try {
			ByteArrayInputStream input = new ByteArrayInputStream(buffer.getBuffer());
			input.skip(buffer.getOffset());
		//	decompress(input, length);
		} 
	}*/
	}
}

	/**
	 * Decompresses data with the LZMA algorithm.
	 * 
	 * @param input The created input stream wrapped around the initial buffer.
	 * @param length The compression length.
	 * @throws IOException
	 */
	//private static void decompress(ByteArrayInputStream input, int length) throws IOException {
		/*Decoder decoder = new Decoder();
		byte[] properties = new byte[5];
		if (input.read(properties, 0, 5) != 5) {
			throw new IOException("LZMA: Bad input.");
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream(length);
		synchronized (decoder) {
			if (!decoder.SetDecoderProperties(properties)) {
				throw new IOException("LZMA: Bad properties.");
			}
			decoder.Code(input, output, length);
		}
	}*/

//}

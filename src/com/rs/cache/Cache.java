package com.rs.cache;

import java.io.IOException;
import java.math.BigInteger;

import com.alex.io.OutputStream;
import com.alex.store.Store;
import com.alex.util.whirlpool.Whirlpool;
import com.alex.utils.Utils;

import GUI.InterfaceGui;
import properties.PropertyValues;

public final class Cache {

	public static Store STORE;

	private Cache() {

	}

	public static String path = "C:\\Users\\paolo\\Dropbox\\Zaria 667\\data\\cache/";
	
	public static void init() throws IOException {
		STORE = new Store(PropertyValues.cache_path);
	}

	public static final byte[] generateUkeysFile() {
		OutputStream stream = new OutputStream();
		stream.writeByte(STORE.getIndexes().length);
		for (int index = 0; index < STORE.getIndexes().length; index++) {
			if (STORE.getIndexes()[index] == null) {
				stream.writeInt(0);
				stream.writeInt(0);
				stream.writeBytes(new byte[64]);
				continue;
			}
			stream.writeInt(STORE.getIndexes()[index].getCRC());
			stream.writeInt(STORE.getIndexes()[index].getTable().getRevision());
			stream.writeBytes(STORE.getIndexes()[index].getWhirlpool());
		}
		byte[] archive = new byte[stream.getOffset()];
		stream.setOffset(0);
		stream.getBytes(archive, 0, archive.length);
		OutputStream hashStream = new OutputStream(65);
		hashStream.writeByte(0);
		hashStream.writeBytes(Whirlpool.getHash(archive, 0, archive.length));
		byte[] hash = new byte[hashStream.getOffset()];
		hashStream.setOffset(0);
		hashStream.getBytes(hash, 0, hash.length);
		hash = Utils.cryptRSA(hash, 
				new BigInteger("95776340111155337321344029627634" + "1788886261017915822452285867506979967134540193547165770775775581569761779944798377609896913564389" + "7487964729306417755551818756732765979333143142115320393191493385852685739642805226692650786060316" + "6705084302845740310178306001400777670591958466653637275131498866778592148380588481")
		, new BigInteger("119555331260995530494627322191654816613" + "15547661260381710307968992599540226345789589082914809341413534242080728782003241745842876349656560" + "597016393669681148550055350674397952146548980174697339290188558877746202316525248398843187741102181" + "6445058706597607453280166045122971960003629860919338852061972113876035333"));
		stream.writeBytes(hash);
		archive = new byte[stream.getOffset()];
		stream.setOffset(0);
		stream.getBytes(archive, 0, archive.length);
		return archive;
	}

	public static Store getStore() {
		return STORE;
	}

}

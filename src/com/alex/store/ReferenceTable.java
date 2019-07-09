// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ReferenceTable.java

package com.alex.store;

import java.util.Arrays;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;

// Referenced classes of package com.alex.store:
//            Archive, ArchiveReference, FileReference, MainFile

public final class ReferenceTable {

	protected ReferenceTable(Archive archive) {
		this.archive = archive;
		decodeHeader();
	}

	public void setKeys(int keys[]) {
		archive.setKeys(keys);
	}

	public int[] getKeys() {
		return archive.getKeys();
	}

	public void sortArchives() {
		Arrays.sort(validArchiveIds);
		needsArchivesSort = false;
	}

	public void addEmptyArchiveReference(int archiveId) {
		needsArchivesSort = true;
		int newValidArchiveIds[] = Arrays.copyOf(validArchiveIds, validArchiveIds.length + 1);
		newValidArchiveIds[newValidArchiveIds.length - 1] = archiveId;
		validArchiveIds = newValidArchiveIds;
		ArchiveReference reference;
		if (archives.length <= archiveId) {
			ArchiveReference newArchives[] = Arrays.copyOf(archives, archiveId + 1);
			reference = newArchives[archiveId] = new ArchiveReference();
			archives = newArchives;
		} else {
			reference = archives[archiveId] = new ArchiveReference();
		}
		reference.reset();
	}

	public void sortTable() {
		if (needsArchivesSort)
			sortArchives();
		for (int index = 0; index < validArchiveIds.length; index++) {
			ArchiveReference archive = archives[validArchiveIds[index]];
			if (archive.isNeedsFilesSort())
				archive.sortFiles();
		}

	}

	public Object[] encodeHeader(MainFile mainFile) {

		OutputStream stream = new OutputStream();
		try {
			int protocol = getProtocol();
			stream.writeByte(protocol);
			if (protocol >= 6)
				stream.writeInt(revision);
			stream.writeByte((named ? 1 : 0) | (usesWhirpool ? 2 : 0));

			if (protocol >= 7)
				stream.writeBigSmart(validArchiveIds.length);
			else
				stream.writeShort(validArchiveIds.length);

			for (int index = 0; index < validArchiveIds.length; index++) {
				int offset = validArchiveIds[index];
				if (index != 0)
					offset -= validArchiveIds[index - 1];

				if (protocol >= 7)
					stream.writeBigSmart(offset);
				else
					stream.writeShort(offset);
			}

			int pHash = this.archive.getHash();
			boolean flag4 = (pHash & 0x04) != 0;
			boolean flag8 = (pHash & 0x08) != 0;

			if (named) {
				for (int index = 0; index < validArchiveIds.length; index++)
					stream.writeInt(archives[validArchiveIds[index]].getNameHash());

			}

			for (int index = 0; index < validArchiveIds.length; index++)
				stream.writeInt(archives[validArchiveIds[index]].getCRC());

			/*
			 * Write the freaking hash? WTF IS THE HASH?
			 */
			if (flag8) {
				for (int index = 0; index < validArchiveIds.length; index++)
					stream.writeInt(archives[validArchiveIds[index]].getHash()); // HSAS
			}

			if (usesWhirpool) {
				for (int index = 0; index < validArchiveIds.length; index++)
					stream.writeBytes(archives[validArchiveIds[index]].getWhirpool());

			}

			if (flag4) {
				for (int index = 0; index < validArchiveIds.length; index++) {
					stream.writeInt(archives[validArchiveIds[index]].getCompressed());
					stream.writeInt(archives[validArchiveIds[index]].getUncompressed());
				}
			}

			for (int index = 0; index < validArchiveIds.length; index++)
				stream.writeInt(archives[validArchiveIds[index]].getRevision());

			for (int index = 0; index < validArchiveIds.length; index++) {
				int value = archives[validArchiveIds[index]].getValidFileIds().length;
				if (protocol >= 7)
					stream.writeBigSmart(value);
				else
					stream.writeShort(value);
			}

			for (int index = 0; index < validArchiveIds.length; index++) {
				ArchiveReference archive = archives[validArchiveIds[index]];
				for (int index2 = 0; index2 < archive.getValidFileIds().length; index2++) {
					int offset = archive.getValidFileIds()[index2];
					if (index2 != 0)
						offset -= archive.getValidFileIds()[index2 - 1];
					if (protocol >= 7)
						stream.writeBigSmart(offset);
					else
						stream.writeShort(offset);
				}

			}

			if (named) {
				for (int index = 0; index < validArchiveIds.length; index++) {
					ArchiveReference archive = archives[validArchiveIds[index]];
					for (int index2 = 0; index2 < archive.getValidFileIds().length; index2++)
						stream.writeInt(archive.getFiles()[archive.getValidFileIds()[index2]].getNameHash());

				}

			}
			byte data[] = new byte[stream.getOffset()];
			stream.setOffset(0);
			stream.getBytes(data, 0, data.length);
			return this.archive.editNoRevision(data, mainFile);
		} catch (Exception e) {
			// System.out.println("Unhandled exception: " + e);
		}
		return archives;
	}

	public int getProtocol() {
		if (archives.length > 65535)
			return 7;
		for (int index = 0; index < validArchiveIds.length; index++) {
			if (index > 0 && validArchiveIds[index] - validArchiveIds[index - 1] > 65535)
				return 7;
			if (archives[validArchiveIds[index]].getValidFileIds().length > 65535)
				return 7;
		}

		return revision != 0 ? 6 : 5;
	}

	public void setRevision(int revision) {
		updatedRevision = true;
		this.revision = revision;
	}

	public void updateRevision() {
		if (updatedRevision) {
			return;
		} else {
			revision++;
			updatedRevision = true;
			return;
		}
	}

	public void removeArchiveReference(int archiveId) {
		int newValidFileIds[] = new int[validArchiveIds.length - 1];
		int count = 0;
		int ai[];
		int j = (ai = validArchiveIds).length;
		for (int i = 0; i < j; i++) {
			int id = ai[i];
			if (id != archiveId)
				newValidFileIds[count++] = id;
		}

		validArchiveIds = newValidFileIds;
		archives[archiveId] = null;
		needsArchivesSort = true;
	}

	// public static final int flag4 = 0x04;

	private void decodeHeader() {

		// System.out.println("Archive Data: " + this.archive.getData());
		InputStream stream = new InputStream(this.archive.getData());

		int protocol = stream.readUnsignedByte();

		if (protocol < 5 || protocol > 7)
			throw new RuntimeException("INVALID PROTOCOL");
		if (protocol >= 6) {
			revision = stream.readInt();
		}
		this.archive.setHash(stream.readUnsignedByte());
		// int hash = stream.readUnsignedByte();
		named = (1 & this.archive.getHash()) != 0;
		usesWhirpool = (2 & this.archive.getHash()) != 0;
		boolean flag4 = (this.archive.getHash() & 4) != 0;
		boolean flag8 = (this.archive.getHash() & 8) != 0;

		int validArchivesCount = protocol >= 7 ? stream.readBigSmart() : stream.readUnsignedShort();
		validArchiveIds = new int[validArchivesCount];

		int lastArchiveId = 0;
		int biggestArchiveId = 0;

		for (int index = 0; index < validArchivesCount; index++) {
			int archiveId = lastArchiveId += protocol >= 7 ? stream.readBigSmart() : stream.readUnsignedShort();

			if (archiveId > biggestArchiveId)
				biggestArchiveId = archiveId;
			validArchiveIds[index] = archiveId;
		}

		archives = new ArchiveReference[biggestArchiveId + 1];
		for (int index = 0; index < validArchivesCount; index++)
			archives[validArchiveIds[index]] = new ArchiveReference();

		if (named) {
			for (int index = 0; index < validArchivesCount; index++)
				archives[validArchiveIds[index]].setNameHash(stream.readInt()); // identifier
		}

		if (NEW_PROTOCOL) {
			for (int index = 0; index < validArchivesCount; index++)
				archives[validArchiveIds[index]].setCrc(stream.readInt());
		}

		// FLAG8
		// BUFFER FLAG_UNKNOWN4

		if (flag8) {
			for (int i = 0; i < validArchivesCount; i++) {
				archives[validArchiveIds[i]].setHash(stream.readInt());
			}
		}

		// FLAG_WHIRLPOOL
		if (usesWhirpool) {
			for (int index = 0; index < validArchivesCount; index++) {
				byte[] whirpool = new byte[64];
				stream.getBytes(whirpool, 0, 64);
				archives[validArchiveIds[index]].setWhirpool(whirpool);
			}
		}
		if (flag4) {
			for (int i = 0; i < validArchivesCount; i++) {
				archives[validArchiveIds[i]].setCompressed(stream.readInt());
				archives[validArchiveIds[i]].setUncompressed(stream.readInt());// stream.readInt();
			}
		}

		if (!NEW_PROTOCOL) {
			for (int index = 0; index < validArchivesCount; index++)
				archives[validArchiveIds[index]].setCrc(stream.readInt());

		}

		for (int index = 0; index < validArchivesCount; index++)
			archives[validArchiveIds[index]].setRevision(stream.readInt());

		for (int index = 0; index < validArchivesCount; index++) {
			int size = protocol >= 7 ? stream.readBigSmart() : stream.readUnsignedShort();
			archives[validArchiveIds[index]].setValidFileIds(new int[size]);
		}

		for (int index = 0; index < validArchivesCount; index++) {
			int lastFileId = 0;
			int biggestFileId = 0;
			ArchiveReference archive = archives[validArchiveIds[index]];
			for (int index2 = 0; index2 < archive.getValidFileIds().length; index2++) {
				int fileId = lastFileId += protocol >= 7 ? stream.readBigSmart() : stream.readUnsignedShort();
				if (fileId > biggestFileId)
					biggestFileId = fileId;
				archive.getValidFileIds()[index2] = fileId;
			}
			archive.setFiles(new FileReference[biggestFileId + 1]);
			for (int index2 = 0; index2 < archive.getValidFileIds().length; index2++)
				archive.getFiles()[archive.getValidFileIds()[index2]] = new FileReference();
		}
		if (named) {
			for (int index = 0; index < validArchivesCount; index++) {
				ArchiveReference archive = archives[validArchiveIds[index]];
				for (int index2 = 0; index2 < archive.getValidFileIds().length; index2++)
					archive.getFiles()[archive.getValidFileIds()[index2]].setNameHash(stream.readInt());
			}
		}
	}

	public int getRevision() {
		return revision;
	}

	public ArchiveReference[] getArchives() {
		return archives;
	}

	public int[] getValidArchiveIds() {
		return validArchiveIds;
	}

	public boolean isNamed() {
		return named;
	}

	public boolean usesWhirpool() {
		return usesWhirpool;
	}

	public int getCompression() {
		return archive.getCompression();
	}

	public static boolean NEW_PROTOCOL = false;
	private Archive archive;
	private int revision;
	private boolean named;
	private boolean usesWhirpool;
	private ArchiveReference archives[];
	private int validArchiveIds[];
	private boolean updatedRevision;
	private boolean needsArchivesSort;

}

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArchiveReference.java

package com.alex.store;

import java.util.Arrays;

// Referenced classes of package com.alex.store:
//            FileReference

public class ArchiveReference {

	private int nameHash;
	private int hash;
	private int compressed;
	private int uncompressed;
	private byte[] whirpool;
	private int flags;
	private int crc;
	private int revision;
	private FileReference[] files;
	private int[] validFileIds;
	private boolean needsFilesSort;
	private boolean updatedRevision;

	public void updateRevision() {
		if (updatedRevision)
			return;
		revision++;
		updatedRevision = true;
	}

	public int getFlags() {

		return flags;
	}

	public void setFlags(int flags) {

		this.flags = flags;
	}

	public int getNameHash() {
		return nameHash;
	}

	public int getHash() {

		return hash;
	}

	public int getUncompressed() {
		return uncompressed;
	}

	public int getCompressed() {

		return compressed;
	}

	public void setNameHash(int nameHash) {
		this.nameHash = nameHash;
	}

	public void setHash(int hash) {
		this.hash = hash;
	}

	public void setUncompressed(int uncompressed) {
		this.uncompressed = uncompressed;
	}

	public void setCompressed(int compressed) {
		this.compressed = compressed;
	}

	public byte[] getWhirpool() {
		return whirpool;
	}

	public void setWhirpool(byte[] whirpool) {
		this.whirpool = whirpool;
	}

	public int getCRC() {
		return crc;
	}

	public void setCrc(int crc) {
		this.crc = crc;
	}

	public int getRevision() {
		return revision;
	}

	public FileReference[] getFiles() {
		return files;
	}

	public void setFiles(FileReference[] files) {
		this.files = files;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public int[] getValidFileIds() {
		return validFileIds;
	}

	public void setValidFileIds(int[] validFileIds) {
		this.validFileIds = validFileIds;
	}

	public boolean isNeedsFilesSort() {
		return needsFilesSort;
	}

	public void setNeedsFilesSort(boolean needsFilesSort) {
		this.needsFilesSort = needsFilesSort;
	}

	public void removeFileReference(int fileId) {
		int[] newValidFileIds = new int[validFileIds.length - 1];
		int count = 0;
		for (int id : validFileIds) {
			if (id == fileId)
				continue;
			newValidFileIds[count++] = id;
		}
		validFileIds = newValidFileIds;
		files[fileId] = null;
	}

	public void addEmptyFileReference(int fileId) {
		needsFilesSort = true;
		int[] newValidFileIds = Arrays.copyOf(validFileIds, validFileIds.length + 1);
		newValidFileIds[newValidFileIds.length - 1] = fileId;
		validFileIds = newValidFileIds;
		if (files.length <= fileId) {
			FileReference[] newFiles = Arrays.copyOf(files, fileId + 1);
			newFiles[fileId] = new FileReference();
			files = newFiles;
		} else
			files[fileId] = new FileReference();
	}

	public void sortFiles() {
		Arrays.sort(validFileIds);
		needsFilesSort = false;
	}

	public void reset() {
		whirpool = null;
		updatedRevision = true;
		revision = 0;
		nameHash = 0;
		crc = 0;
		files = new FileReference[0];
		validFileIds = new int[0];
		needsFilesSort = false;
	}

	public void copyHeader(ArchiveReference fromReference) {
		setCrc(fromReference.getCRC());
		setNameHash(fromReference.getNameHash());
		setWhirpool(fromReference.getWhirpool());
		int[] validFiles = fromReference.getValidFileIds();
		setValidFileIds(Arrays.copyOf(validFiles, validFiles.length));
		FileReference[] files = fromReference.getFiles();
		setFiles(Arrays.copyOf(files, files.length));
	}

}

package com.interfaces;

import com.alex.store.ReferenceTable;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ComponentDefinition;
import com.alex.store.Store;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InterfacePacker {

    public static final int INTERFACE_ARCHIVE = 3;

    public static void packComponent(int interfaceId,int fileId, ComponentDefinition component){
        Cache.STORE.getIndexes()[INTERFACE_ARCHIVE].putFile(interfaceId,  fileId, component.encode());
    }


    public static void packInterface(int archive, ComponentDefinition[] components){

    }


    public static void main(String args[]) throws FileNotFoundException, IOException {
            ReferenceTable.NEW_PROTOCOL = true;
            Store from_store = new Store("D:\\Atraxia\\server\\data\\cache_backup\\xd5\\cache\\");
            Store to_store = new Store("D:\\Atraxia\\server\\data\\cache\\");
            int fromIndexId = 3;
            int toIndexId = fromIndexId;
            int fromArchiveId = 1904;// example interfaceId
            int toArchiveId = fromArchiveId;
            if (from_store.getIndexes()[fromIndexId].archiveExists(fromArchiveId)) {
               boolean packed =  to_store.getIndexes()[toIndexId].putArchive(fromIndexId, fromArchiveId, toArchiveId, from_store, false, true);
                System.out.println(packed);
            }
            to_store.getIndexes()[toIndexId].rewriteTable();
    }
}


package GUI;

import com.rs.cache.Cache;
import com.rs.cache.loaders.ComponentDefinition;

public class InterfacePacker {

    public static final int INTERFACE_ARCHIVE = 3;

    public static void packComponent(int interfaceId,int fileId, ComponentDefinition component){
        Cache.STORE.getIndexes()[INTERFACE_ARCHIVE].putFile(interfaceId,  fileId, component.encode());
    }


    public static void packInterface(int archive, ComponentDefinition[] components){

    }
}


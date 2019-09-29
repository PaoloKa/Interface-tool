package com.interfaces.components.premade;

import com.rs.cache.Cache;
import com.rs.cache.loaders.ComponentDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public class PremadeComponentHandler {


    private static final HashMap<String, Class<? extends PremadeComponent>> cachedPremadeComponents = new HashMap<String, Class<? extends PremadeComponent>>();

    public static void addPremadeComponents(int interfaceId, PremadeComponent premadeComponent){
        int currentInterfaceSize = ComponentDefinition.getInterfaceDefinitionsComponentsSize(interfaceId);
       /* for (ComponentDefinition newComponent : premadeComponent.createAndGetComponents()) {
            Cache.STORE.getIndexes()[3].putFile(interfaceId, currentInterfaceSize, newComponent.encode());
            currentInterfaceSize++;
        }*/

    }


    public static void loadPremadeComponents(){

    }

}

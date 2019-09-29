package com.interfaces.components.premade;

import com.rs.cache.loaders.ComponentDefinition;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public abstract class PremadeComponent {

    /**
     * @return
     */
    public abstract ComponentDefinition[] createAndGetComponents(int interfaceId, int componentPosition);



    private int createHash(int componentPosition, int interfaceId){
        return componentPosition +(interfaceId << 16);
    }
}

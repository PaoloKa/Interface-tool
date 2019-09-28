package com.interfaces.components.premade;

import com.rs.cache.loaders.ComponentDefinition;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public abstract class PremadeComponent {

    /**
     *
     * @param interfaceId the id of the interface to pack to
     * @param interfaceSize the current length of the components of that specific interface
     * @return
     */
    public abstract ComponentDefinition[] createComponents(int interfaceId, int interfaceSize);


    protected int createHashForComponet(int interfaceId, int componentPosition){
        return componentPosition +(interfaceId << 16);

    }
}

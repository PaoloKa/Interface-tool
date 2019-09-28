package com.interfaces.components;

import com.rs.cache.loaders.ComponentDefinition;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public class ComponentFactory {

    /**
     * returns a empty component with only the the set
     * @param type
     * @return
     */
    public static ComponentDefinition createBaseComponent(int type){
        ComponentDefinition baseComponent = new ComponentDefinition();
        baseComponent.type = type;
        return baseComponent;
        }
  }

package com.interfaces.components;

import com.rs.cache.loaders.ComponentDefinition;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public class ComponentFactory {

    public enum CUSTOM_COMPONENT_TYPES { CLICKABLE_TEXT };

    /**
     * returns a empty component with only the the set
     * @param type
     * @return
     */
    public static ComponentDefinition createBaseTypeComponent(int type, int interfaceId, int componentPosition){
        ComponentDefinition baseComponent = new ComponentDefinition();
        baseComponent.type = type;
        baseComponent.ihash = componentPosition +(interfaceId << 16);
        return baseComponent;
    }

    public static ComponentDefinition  createCustomComponent(CUSTOM_COMPONENT_TYPES component_types,int interfaceId, int componentPosition){
        ComponentDefinition component = null;
        switch (component_types){
            case CLICKABLE_TEXT:
                component = createBaseTypeComponent(ComponentConstants.TEXT, interfaceId,componentPosition);
                component.text = "custom text";
                component.rightclickOptions = new String[5];
                component.optionMask = ComponentConstants.CLICK_MASK;
                component.rightclickOptions[0] = "Select";
                break;
        }
        return component;
    }
  }

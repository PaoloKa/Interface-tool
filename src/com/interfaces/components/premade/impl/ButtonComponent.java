package com.interfaces.components.premade.impl;

import com.interfaces.components.ComponentConstants;
import com.interfaces.components.ComponentFactory;
import com.interfaces.components.premade.PremadeComponent;
import com.rs.cache.loaders.ComponentDefinition;

/**
 * Interface tool
 * paolo 28/09/2019
 * #Shnek6969
 */
public class ButtonComponent extends PremadeComponent {


    public ComponentDefinition[] createAndGetComponents(int interfaceId, int componentPosition){
        ComponentDefinition[] components = new ComponentDefinition[2];
        components[0] = createContainer(interfaceId,componentPosition);
        components[1] = ComponentFactory.createCustomComponent(ComponentFactory.CUSTOM_COMPONENT_TYPES.CLICKABLE_TEXT, interfaceId,componentPosition);
        return components;
    }


    private ComponentDefinition createContainer(int interfaceId, int componentPosition){
        ComponentDefinition component = ComponentFactory.createBaseTypeComponent(ComponentConstants.CONTAINER,interfaceId,componentPosition);
        component.onLoadScript = new Object[]  {92,-2147483645};
        component.onMouseLeaveScript = new Object[]  {92,-2147483645};
        component.onMouseHoverScript = new Object[]  {94,-2147483645};
        return component;
    }



}

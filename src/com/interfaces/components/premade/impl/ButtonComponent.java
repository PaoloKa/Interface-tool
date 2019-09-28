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


    public ComponentDefinition[] createComponents(int interfaceId, int interfaceSize){
        ComponentDefinition[] components = new ComponentDefinition[2];
        components[0] = createContainer(interfaceId,interfaceSize);

        return components;
    }

    private ComponentDefinition createContainer(int interfaceId,int interfaceSize){
        ComponentDefinition component = ComponentFactory.createBaseComponent(ComponentConstants.CONTAINER);
        component.onLoadScript = new Object[]  {92,-2147483645};
        component.onMouseLeaveScript = new Object[]  {92,-2147483645};
        component.onMouseHoverScript = new Object[]  {94,-2147483645};
        component.ihash = createHashForComponet(interfaceId,interfaceSize + 0)
        return component;
    }



}

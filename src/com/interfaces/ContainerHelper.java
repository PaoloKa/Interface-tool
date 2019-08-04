package com.interfaces;

import com.rs.cache.loaders.ComponentDefinition;

import static com.interfaces.ComponentConstants.SCROLL_BAR_SCRIPT;

/**
 * Interface tool
 * paolo 04/08/2019
 * #Shnek6969
 */
public class ContainerHelper {

    /**
     * checks if the component has the scrollbar script
     * @param component
     * @return
     */
    public static boolean isScrollBar(ComponentDefinition component){
        if(component.onLoadScript!= null && Integer.parseInt(component.onLoadScript[0].toString()) == SCROLL_BAR_SCRIPT)
            return true;
        return false;
    }
}

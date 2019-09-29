package com.interfaces;

import com.interfaces.components.ComponentPosition;
import com.rs.cache.loaders.ComponentDefinition;

import java.util.ArrayList;

public class InterfaceUtils {
	
	/**
	 * checks if the component OR if it's inside a hidden parent.
	 * @param component
	 * @return
	 */
	public static boolean isHidden(ComponentDefinition component) {
		if(component.hidden)
			return true;
		ComponentDefinition parent = getParent(component.parentId);
		boolean hidden = false;
		while(parent != null) {
			if(parent.hidden) {
				hidden = true;
				break;
			}
			parent = getParent(parent.parentId);
		}
		return hidden;
	}
	/**
	 * returns the parent bases on it's hash
	 * @param parentHash
	 * @return
	 */
	public static ComponentDefinition getParent(int parentHash) {
		if(parentHash == -1)
			return null;
		int interfaceId = parentHash >> 16;
		int baseHash = interfaceId << 16;
		int componentId = parentHash - baseHash;
		return ComponentDefinition.getInterfaceComponent(interfaceId, componentId);
	}
	/**
	 * turns a string into a 'object array of script inputs'
	 * @param input
	 * @return
	 */
	public static Object[] getScriptArray(String input) {
		if(input == null || input == "" || input == " ")
			return null;
		String[] values = input.split(";");
		Object[] objs = new Object[values.length];
		try{
			Integer.parseInt(values[0]);
		} catch(Exception exp){ //return null if the first value isn't a number
			return null;
		}
		for(int i = 0; i < values.length; i++) {
			try{
				int x = Integer.parseInt(values[i]);
				objs[i] = x;
			} catch(Exception ex){
				objs[i] = values[i];
			}
		}
		return objs;		
	}

	/**
	 *
	 * @param input
	 * @return
	 */
	public static int[] getConfigArray(String input){
		if(input == null || input.isEmpty() || input.equalsIgnoreCase(" "))
			return null;
		String[] values = input.split(";");
		int[] intArray = new int[values.length];
		for(int i =0; i < values.length; i++){
			intArray[i] = Integer.parseInt(values[i]);
		}
		return intArray;

	}

	/**
	 * returns the drawing order of an interface
	 * @param interfaceId
	 * @return
	 */
	public static ArrayList<ComponentDefinition> getOrderedComps(int interfaceId) {
		ArrayList<ComponentDefinition> comps = new ArrayList();
		ArrayList<ComponentDefinition> containers = ComponentDefinition.getInterfaceContainers(interfaceId); //gets all the containers of an interface
		ComponentDefinition[] allComps = ComponentDefinition.getInterface(interfaceId);
		if (allComps == null)
			return null;
       /* for (ComponentDefinition c : allComps) {
            if (c == null)
                continue;
            if (c.parentId == -1)
                comps.add(c);
        }*/
		for (ComponentDefinition comp : containers) {
			if (!comps.contains(comp))
				comps.add(comp); //add container itself
			for (ComponentDefinition child : ComponentDefinition.getChildsByParent(interfaceId, comp.ihash))
				comps.add(child); //Add childs
		}
		/**
		 * adding all the comps who don't have a parent
		 */
		for (int i = 0; i < allComps.length; i++) {
			if (allComps[i] == null)
				continue;
			ComponentPosition.setValues(allComps[i]);
			boolean found = false;
			for (ComponentDefinition c : comps) {
				if (c.componentId == allComps[i].componentId)
					found = true;
			}
			if (!found)
				comps.add(allComps[i]);
		}
		return comps;

	}
}

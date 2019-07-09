package GUI;

import com.rs.cache.loaders.IComponentDefinitions;

public class InterfaceUtils {
	
	/**
	 * checks if the component OR if it's inside a hidden parent.
	 * @param component
	 * @return
	 */
	public static boolean isHidden(IComponentDefinitions component) {
		if(component.hidden)
			return true;
		IComponentDefinitions parent = getParent(component.parentId);
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
	public static IComponentDefinitions getParent(int parentHash) {
		if(parentHash == -1)
			return null;
		int interfaceId = parentHash >> 16;
		int baseHash = interfaceId << 16;
		int componentId = parentHash - baseHash;
		return IComponentDefinitions.getInterfaceComponent(interfaceId, componentId);
	}
	/**
	 * turns a string into a 'object array of script inputs'
	 * @param input
	 * @return
	 */
	public static Object[] getScriptArray(String input) {
		if(input == null || input == "")
			return null;
		String[] values = input.split(";");
		Object[] objs = new Object[values.length];
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

}

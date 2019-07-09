package com.interfaces;

/**
 * Shnek
 */
public enum ComponentType {

		CONTAINER(0),
		SPRITE(5),
		MODEL(6),
		TEXT(4),
		UNKNOWN(9),
		FIGURE(3);
		
		private int type;
		
		ComponentType(int type){
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
}

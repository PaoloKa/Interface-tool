package GUI;

import com.rs.cache.loaders.IComponentDefinitions;

public class ComponentPosition {
	
	/**
	 * sets the component on the right position
	 * @param component
	 * @param parentWidth
	 * @param parentHeight
	 * 
	 */
	public static void realPosition(IComponentDefinitions component, int parentWidth, int parentHeight) {
			/**
			 * x Type
			 */
			if (component.aspectXType == 0)
				component.positionX = component.basePositionX;
			else if (component.aspectXType == 1)
				component.positionX = ((component.basePositionX + (parentWidth - component.width) / 2));
			else if (2 == component.aspectXType)
				component.positionX = (parentWidth - component.width - component.basePositionX);
			else if (component.aspectXType == 3)
				component.positionX = (parentWidth * (component.basePositionX) >> 14);
			else if (component.aspectXType == 4)
				component.positionX = ((parentWidth - component.width) / 2 + (parentWidth * (component.basePositionX) >> 14));
			else
				component.positionX = (parentWidth - component.width - (component.basePositionX * parentWidth >> 14));
			/**
			 * Y type
			 */
			if (component.aspectYType == 0)
				component.positionY = component.basePositionY;
			else if (component.aspectYType == 1)
				component.positionY = ((component.basePositionY + (parentHeight - component.height) / 2));
			else if (component.aspectYType == 2)
				component.positionY = (parentHeight - component.height - component.basePositionY);
			else if (3 == component.aspectYType)
				component.positionY = (component.basePositionY * parentHeight >> 14);
			else if (component.aspectYType == 4)
				component.positionY = (((component.basePositionY * parentHeight >> 14) + (parentHeight - component.height) / 2));
			else
				component.positionY = (parentHeight - component.height - (component.basePositionY * parentHeight >> 14));
			
			/**
			 * checking if values aren't to big
			 */
			if (component.positionX < 0)
				component.positionX = 0;
		    if ((component.positionX + component.width) > parentWidth)
				component.positionX = (parentWidth - component.width);
			if (component.positionY < 0)
				component.positionY = 0;
			 if ((component.positionY + component.height) > parentHeight)
				component.positionY = (parentHeight - component.height);
		}
	
	/**
	 * gets the right scale of the component
	 * @param component
	 * @param width
	 * @param height
	 * @param bool
	 * @param i_3_
	 */
	public static void realAfmeting(IComponentDefinitions component, int width, int height) {
		/* width */
			if (component.aspectWidthType == 0)
				component.width = component.baseWidth;
			else if (1 == component.aspectWidthType)
				component.width = (width - component.baseWidth);
			else if (2 == component.aspectWidthType)
				component.width = (width * (component.baseWidth) >> 14);
			//height
			if (0 == component.aspectHeightType)
				component.height = component.baseHeight;
			else if (1 == component.aspectHeightType)
				component.height = (height - component.baseHeight);
			else if (component.aspectHeightType == 2)
				component.height = (height * (component.baseHeight) >> 14);
			
			if(component.type == 0){
				if (component.height < 5 && component.width < 5) {
					component.height = 5;
					component.width = 5;
				} else {
					if (component.height <= 0)
						component.height = 5;
					if (component.width <= 0)
						component.width = 5;
				}
			
			}
			
			/*
			if (component.aspectWidthType == 4)
				component.width = (component.anInt1158 * (component.height) / (component.anInt1242));
			if (component.aspectHeightType == 4)
				component.height = (component.width * (component.anInt1242) / (component.anInt1158));
			*/
	}
	
	public static void setValues(IComponentDefinitions component) {
		if(component == null)
			return;
		IComponentDefinitions parent = InterfaceUtils.getParent(component.parentId);
		int width;
		int height;
		if (null == parent) {
			width = 520;
			height =  339;
		} else {
			setValues(parent);
			width = parent.width   ;
			height = parent.height ;
		}
		realAfmeting(component,width,height);
		realPosition(component,width,height);
	}
}

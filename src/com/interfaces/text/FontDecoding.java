package com.interfaces.text;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rs.cache.loaders.ComponentDefinition;

public class FontDecoding {

	
	public static BufferedImage[] getTextArray(ComponentDefinition component){
		BufferedImage[] arr =  new BufferedImage[component.text.length()];
		int counter = 0;
		for(char c : component.text.toCharArray()){
			try {
				arr[counter] = ImageIO.read(new File("C:\\Users\\paolo\\Dropbox\\Interface tool\\data\\dump\\"+component.fontId+"_"+getCharIndex(c)+".png"));
			} catch (IOException e) {
				//e.printStackTrace();
			}
        	
			counter++;
		}
		return arr;
	}
	
	public static int getCharIndex(char c){
		if(Character.isDigit(c))
			return 48 + Character.getNumericValue(c);
		if(Character.isUpperCase(c))
			return 55 + Character.getNumericValue(c);
		if(Character.isLowerCase(c))
			return 87 +  Character.getNumericValue(c);
		return 0;
	}

}

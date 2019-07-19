package com.interfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rs.cache.loaders.ComponentDefinition;

public class FontDecoding {
	
	/**
	 * info start number = 48 = 0   57 = 9
	 * 65 = A
	 * 90 = Z
	 * 97 = a
	 * 122 = z
	 * @param text
	 * @return
	 */
	
	public static BufferedImage[] getTextArray(ComponentDefinition component){
		BufferedImage[] arr =  new BufferedImage[component.text.length()];
		int counter = 0;
		for(char c : component.text.toCharArray()){
			try {
				System.out.println(counter+ " char : "+c +"  index value: "+getCharIndex(c));
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

/**
* Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.rs.cache.Cache;

import GUI.InterfaceGui;
import properties.PropertyValues;

/**
 * @author Kyle Friz
 * @since Dec 30, 2015
 */
public class SpriteDumper {
	
	public static void main(String[] args) throws IOException {
		File directory = new File(PropertyValues.dump_path);			
		
		if (!directory.exists()) {
			directory.mkdirs();
		}
		com.rs.cache.Cache.init();
		int size = Cache.STORE.getIndexes()[8].getLastArchiveId();
			for (int i = 0; i < Cache.STORE.getIndexes()[8].getLastArchiveId(); i++) {
				if (Cache.STORE.getIndexes()[8].getFile(i) == null)
					continue;
				byte[] data = Cache.STORE.getIndexes()[8].getFile(i);
				if(data == null)
					continue;
				ByteBuffer buf = ByteBuffer.wrap(data);
				Sprite sprite = Sprite.decode(buf);
				
				for (int frame = 0; frame < sprite.size(); frame++) {
					File file = new File(PropertyValues.dump_path, i + "_" + frame + ".png");
					
				//	BufferedImage image = ImageUtils.createColoredBackground(ImageUtils.makeColorTransparent(sprite.getFrame(frame), Color.WHITE), (Color.WHITE));

					BufferedImage image = sprite.getFrame(frame);
					
					ImageIO.write(image, "png", file);
				}
				
				double progress = (double) (i + 1) / size * 100;
				
				System.out.printf("%d out of %d %.2f%s\n", (i + 1),size, progress, "%");	
				
			}

			

		//}
	}

}

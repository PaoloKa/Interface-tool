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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.logging.LogFactory;
import com.rs.cache.Cache;

import GUI.InterfaceGui;
import properties.PropertyValues;


public class SpriteDumper {

	private static final Logger logger = LogFactory.createLogger(SpriteDumper.class.getName());

	public static void dump() {
		File directory = new File(PropertyValues.dump_path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		int size = Cache.STORE.getIndexes()[8].getLastArchiveId();
			for (int i = 0; i < Cache.STORE.getIndexes()[8].getLastArchiveId(); i++) {
				if (Cache.STORE.getIndexes()[8].getFile(i) == null)
					continue;
				byte[] data = Cache.STORE.getIndexes()[8].getFile(i);
				if(data == null)
					continue;
				ByteBuffer buf = ByteBuffer.wrap(data);
				Sprite sprite = Sprite.decode(buf);
				if(sprite == null || sprite.size() == 0)
					continue;
				for (int frame = 0; frame < sprite.size(); frame++) {
					File file = new File(PropertyValues.dump_path, i + "_" + frame + ".png");
					BufferedImage image = sprite.getFrame(frame);
					try {
						ImageIO.write(image, "png", file);
					} catch (Exception e) {
						logger.log(Level.SEVERE,"Could not dump sprite "+i+" error ->"+e.getMessage());
						continue;
					}
				}
				
				double progress = (double) (i + 1) / size * 100;
				
				//System.out.printf("%d out of %d %.2f%s\n", (i + 1),size, progress, "%");
				logger.info((i + 1)+" out of "+size+" "+Math.round(progress)+"%");
				
			}

			

		//}
	}

}

package com.ericsson.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;

public class ImageUtil {
	static ImageUtil imageUtil = null;

	public static ImageUtil getInstance() {
		if (imageUtil == null) {
			imageUtil = new ImageUtil();
		}
		return imageUtil;
	}

	public Image convertStringToImage(String base64String) {
		// Convert String to byte[]
		byte[] imageBytes = Base64.decodeBase64(base64String);
		Image image = new ImageIcon(imageBytes).getImage();
		return image;
	}

	public void saveImage(java.awt.Image image, String fileName) {
		try {
			ImageIO.write(resizeImage(image), "jpg", new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage resizeImage(Image image) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		BufferedImage bimage = null;
		try {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null),
					image.getHeight(null), type);
			// Copy image to buffered image
			Graphics g = bimage.createGraphics().create();
			Graphics2D g2d = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHints(rh);
			// Paint the image onto the buffered image
			// imagem, Xinicial, YInicial, XFinal, YFinal,
			int realHeight = (int) (screenSize.getHeight() - (screenSize.getHeight()- bimage.getHeight()));
			int realWidth = (int) (screenSize.getWidth() - (screenSize.getWidth()- bimage.getWidth()));
			
			g2d.drawImage(image, 0, 0, bimage.getWidth(), bimage.getHeight(),
					0, 0, realWidth, realHeight, null);
			g2d.dispose();
			return bimage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
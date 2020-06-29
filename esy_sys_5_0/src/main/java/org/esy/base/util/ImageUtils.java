package org.esy.base.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import sun.misc.BASE64Encoder;

/**
 * 图片工具类 2014-03-28
 * 
 * @author duiqing.huang
 * 
 */
public class ImageUtils {

	/**
	 * 图片转base64
	 * 
	 * @param img
	 * @return
	 */
	public static String imgToBase64(byte[] img) {
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(img);
	}

	/**
	 * 图片转缩略图
	 * 
	 * @param img
	 *            图片
	 * @param width
	 * @param height
	 * @return
	 */
	public static byte[] imgToThumb(InputStream img, int width, int height) throws Exception {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(img).size(width, height).toOutputStream(baos);
		bytes = baos.toByteArray();
		img.close();
		baos.close();
		return bytes;
	}

	/**
	 * 图片转缩略图并返回base 64
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception 
	 */
	public static String imgForThumbToBase64(InputStream img, int width, int height) throws Exception {
		byte[] bytes = imgToThumb(img, width, height);
		try {
			img.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes == null ? "" : imgToBase64(bytes);
	}

	/**
	 * 图片转缩略图并返回base 64 自定义
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	public static String _imgForThumbToBase64(InputStream img, int width, int height) {
		byte[] bytes = null;
		try {
			bytes = resizeFix(ImageIO.read(img), width, height);
			img.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes == null ? "" : imgToBase64(bytes);
	}

	/**
	 * 图片加水印
	 * 
	 * @param img
	 *            图片
	 * @param os
	 *            加水印后的图片
	 * @param waterImagePath
	 *            水印图片
	 * @param positions
	 *            水印位置
	 * @param tran
	 *            水印透明度
	 * @throws IOException
	 */
	public static void imgWaterMark(InputStream img, OutputStream os, String waterImagePath, Positions positions,
			float tran) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(img);
		Thumbnails.of(bufferedImage).size(bufferedImage.getWidth(), bufferedImage.getHeight()).outputQuality(0.8f)
				.watermark(positions, ImageIO.read(new File(waterImagePath)), tran).toOutputStream(os);
		img.close();
	}

	public static byte[] resizeFix(BufferedImage srcImgBuff, int boxWidth, int boxHeight) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] btyes = null;
		try {
			int width = srcImgBuff.getWidth();
			int height = srcImgBuff.getHeight();
			if (width <= boxWidth && height <= boxHeight) {
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(srcImgBuff);
				btyes = out.toByteArray();
				out.close();
				return btyes;
			}
			int zoomWidth;
			int zoomHeight;
			if ((float) width / height > (float) boxWidth / boxHeight) {
				zoomWidth = boxWidth;
				zoomHeight = Math.round((float) boxWidth * height / width);
			} else {
				zoomWidth = Math.round((float) boxHeight * width / height);
				zoomHeight = boxHeight;
			}
			BufferedImage imgBuff = scaleImage(srcImgBuff, width, height, zoomWidth, zoomHeight);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(imgBuff);
			btyes = out.toByteArray();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return btyes;
	}

	private static BufferedImage scaleImage(BufferedImage srcImgBuff, int width, int height, int zoomWidth,
			int zoomHeight) {
		int[] colorArray = srcImgBuff.getRGB(0, 0, width, height, null, 0, width);
		BufferedImage outBuff = new BufferedImage(zoomWidth, zoomHeight, BufferedImage.TYPE_INT_RGB);
		// 宽缩小的倍数
		float wScale = (float) width / zoomWidth;
		int wScaleInt = (int) (wScale + 0.5);
		// 高缩小的倍数
		float hScale = (float) height / zoomHeight;
		int hScaleInt = (int) (hScale + 0.5);
		int area = wScaleInt * hScaleInt;
		int x0, x1, y0, y1;
		int color;
		long red, green, blue;
		int x, y, i, j;
		for (y = 0; y < zoomHeight; y++) {
			// 得到原图高的Y坐标
			y0 = (int) (y * hScale);
			y1 = y0 + hScaleInt;
			for (x = 0; x < zoomWidth; x++) {
				x0 = (int) (x * wScale);
				x1 = x0 + wScaleInt;
				red = green = blue = 0;
				for (i = x0; i < x1; i++) {
					for (j = y0; j < y1; j++) {
						color = colorArray[width * j + i];
						red += getRedValue(color);
						green += getGreenValue(color);
						blue += getBlueValue(color);
					}
				}
				outBuff.setRGB(x, y, comRGB((int) (red / area), (int) (green / area), (int) (blue / area)));
			}
		}
		return outBuff;
	}

	private static int getRedValue(int rgbValue) {
		return (rgbValue & 0x00ff0000) >> 16;
	}

	private static int getGreenValue(int rgbValue) {
		return (rgbValue & 0x0000ff00) >> 8;
	}

	private static int getBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}

	private static int comRGB(int redValue, int greenValue, int blueValue) {
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	public static ByteArrayOutputStream parse(InputStream in) throws Exception {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int ch;
		while ((ch = in.read()) != -1) {
			swapStream.write(ch);
		}
		return swapStream;
	}

	/*
	 * 获取图片文件的文件类型，JPG, PNG, GIF，如果非图片类型那么返回Undefined
	 * 
	 * @param srcFileName 图片全路明
	 * 
	 * @return 图片文件类型JPG, PNG, GIF 如果非图片则为Undefined
	 */
	public static String getImageType(InputStream imgFile) {
		byte[] b = new byte[10];
		int l = -1;
		try {
			l = imgFile.read(b);
			imgFile.close();
		} catch (Exception e) {
			return "Undefined";
		}
		if (l == 10) {
			byte b0 = b[0];
			byte b1 = b[1];
			byte b2 = b[2];
			byte b3 = b[3];
			byte b6 = b[6];
			byte b7 = b[7];
			byte b8 = b[8];
			byte b9 = b[9];
			if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
				return "gif";
			} else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
				return "png";
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
				return "jpg";
			} else {
				return "Undefined";
			}
		} else {
			return "Undefined";
		}
	}

}

package com.giimall.common.util;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.giimall.common.constant.CharsetConstant;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 二维码工具类
 *
 * @author wangLiuJing
 * Created on 2020/8/23
 */
public class QRCodeUtil {

	private static final String FORMAT_NAME_BMP = "bmp";

	private static final String FORMAT_NAME_GIF = "gif";

	private static final String FORMAT_NAME_JPEG = "jpeg";

	private static final String FORMAT_NAME_PNG = "png";

	private static final String FORMAT_NAME_WBMP = "wbmp";

	private static final int DEFAULT_WIDTH = 300;

	private static final int DEFAULT_HEIGHT = 300;

	private static final int DEFAULT_MARGIN = 2;

	/**
	 * 二维码图片的宽度
	 */
	private int width;
	/**
	 * 二维码图片的高度
	 */
	private int height;
	/**
	 * 二维码图片的边距
	 */
	private int margin;

	public QRCodeUtil() {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.margin = DEFAULT_MARGIN;
	}

	public QRCodeUtil(int width, int height, int margin) {
		this.width = width;
		this.height = height;
		this.margin = margin;
	}

	/**
	 * 根据二维码图谱路径获取二维码内容
	 * @author wangLiuJing
	 * Created on 2020/8/23
	 *
	 * @param filePath of type String
	 * @return String
	 */
	@SneakyThrows
	public static String getContentFromQRCode(String filePath) {
		BufferedImage image = ImageIO.read(new File(filePath));
		return getContent(image);
	}

	/**
	 * 根据二维码图片流获取二维码内容
	 * @author wangLiuJing
	 * Created on 2020/8/23
	 *
	 * @param inputStream of type InputStream
	 * @return String
	 */
	@SneakyThrows
	public static String getContentFromQRCode(InputStream inputStream) {
		BufferedImage image = ImageIO.read(inputStream);
		return getContent(image);
	}

	/**
	 * 生成二维码图片文件
	 * @author wangLiuJing
	 * Created on 2020/8/23
	 *
	 * @param filePath of type String
	 * @param content of type String
	 * @param formatName of type String
	 */
	@SneakyThrows
	public void createQRCodeToFile(String filePath, String content, String formatName) {
		/**
		 * 定义二维码的参数
		 */
		BitMatrix bitMatrix = getBitMatrix(content);
		Path file = new File(filePath).toPath();
		MatrixToImageWriter.writeToPath(bitMatrix, formatName, file);
	}

	/**
	 * 生成二维码文件流
	 * @author wangLiuJing
	 * Created on 2020/8/23
	 *
	 * @param content of type String
	 * @param formatName of type String
	 * @return ByteArrayOutputStream
	 */
	@SneakyThrows
	public ByteArrayOutputStream createQRCodeToStream(String content, String formatName) {
		BitMatrix bitMatrix = getBitMatrix(content);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, formatName, outputStream);
		return outputStream;
	}

	private BitMatrix getBitMatrix(String content) throws WriterException {
		/**
		 * 定义二维码的参数
		 */
		HashMap hints = new HashMap(3);
		// 指定字符编码为“utf-8”
		hints.put(EncodeHintType.CHARACTER_SET, CharsetConstant.CHARSET_UTF_8);
		// 指定二维码的纠错等级为中级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		// 设置图片的边距
		hints.put(EncodeHintType.MARGIN, margin);

		/**
		 * 生成二维码
		 */
		return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
	}


	@SneakyThrows
	private static String getContent(BufferedImage image) {
		MultiFormatReader formatReader = new MultiFormatReader();
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
		HashMap hints = new HashMap(1);
		hints.put(EncodeHintType.CHARACTER_SET, CharsetConstant.CHARSET_UTF_8);
		Result result = formatReader.decode(binaryBitmap, hints);
		return result.toString();
	}
}
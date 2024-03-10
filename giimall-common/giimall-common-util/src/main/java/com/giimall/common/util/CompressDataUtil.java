package com.giimall.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.*;

/**
 * 压缩数据工具类
 *
 * @author wangLiuJing
 * Created on 2019/9/18
 */
public class CompressDataUtil {

	/**
	 * Method gZip压缩
	 *
	 * @param data of type byte[]
	 * @return byte[]
	 * @author wangLiuJing
	 * Created on 2019/9/18
	 */
	public static byte[] gZip(byte[] data) {

		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
			gzip.write(data);
			gzip.finish();
			return bos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException("压缩数据异常");
		}
	}

	public static byte[] gZip(String data) {
		return gZip(data.getBytes());
	}

	public static byte[] gZip(Object data) {
		byte[] bytes = BeanUtil.objectToBytes(data);
		return gZip(bytes);
	}

	/**
	 * 压缩对象为Byte数组的Json字符串
	 * 由于压缩后的字节数组在序列化转码的过程中会造成数据丢失所以直接把压缩后的字节数组字节转换为json字符串
	 * 可以通过unGZipToObject(String byteJson)把对象还原
	 *
	 * @param data of type Object
	 * @return String
	 * @author wangLiuJing
	 * Created on 2019/9/19
	 */
	public static String gZipToByteJson(Object data) {
		byte[] bytes = BeanUtil.objectToBytes(data);
		return JsonUtil.objectToJson(gZip(bytes));
	}

	/**
	 * Method unGZip解压缩
	 *
	 * @param data of type byte[]
	 * @return byte[]
	 * @author wangLiuJing
	 * Created on 2019/9/18
	 */
	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
		try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
			 GZIPInputStream gzip = new GZIPInputStream(bis);
			 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			byte[] buf = new byte[1024];
			int num;
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			return baos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException("解压数据异常");
		}
	}

	public static String unGZipToString(byte[] data) {
		return new String(unGZip(data));
	}

	public static <T> T unGZipToObject(byte[] data) {
		return BeanUtil.bytesToObject(unGZip(data));
	}

	/**
	 * 解压byteJson字符串为对象
	 * 用于还原gZipToByteJson(Object data)该方法压缩后的对象
	 *
	 * @param byteJson of type String
	 * @return T
	 * @author wangLiuJing
	 * Created on 2019/9/19
	 */
	public static <T> T unGZipToObject(String byteJson) {
		byte[] bytes = JsonUtil.jsonToPojo(byteJson, byte[].class);
		return BeanUtil.bytesToObject(unGZip(bytes));
	}


	/**
	 * 压缩Zip
	 *
	 * @param data of type byte[]
	 * @return byte[]
	 * @author wangLiuJing
	 * Created on 2019/9/18
	 */
	public static byte[] zip(byte[] data) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 ZipOutputStream zip = new ZipOutputStream(bos)) {

			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			return bos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException("压缩数据异常");
		}
	}

	public static byte[] zip(String data) {
		return zip(data.getBytes());
	}

	public static byte[] zip(Object data) {
		return zip(BeanUtil.objectToBytes(data));
	}

	/**
	 * 解压缩
	 *
	 * @param data of type byte[]
	 * @return byte[]
	 * @author wangLiuJing
	 * Created on 2019/9/18
	 */
	public static byte[] unZip(byte[] data) {

		try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
			 ZipInputStream zip = new ZipInputStream(bis);
			 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num;
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
			}
			return baos.toByteArray();
		} catch (Exception ex) {
			throw new RuntimeException("解压数据异常");
		}
	}

	public static String unZipToString(byte[] data) {
		return new String(unZip(data));
	}

	public static <T> T unZipToObject(byte[] data) {
		return BeanUtil.bytesToObject(unZip(data));
	}
}

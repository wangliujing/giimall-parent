package com.giimall.common.util;

import com.giimall.common.constant.CharsetConstant;
import com.giimall.common.exception.CommonException;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Set;

/**
 * AES对称加密和解密
 *
 * @author wangLiuJing
 * Created on 2020/6/17
 */
public class AesEncryptUtil {

	/**
	 * 对称密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

	/** 密钥长度 */
	private static final Set<Integer> KEY_SIZE = Set.of(16, 24, 32);

	/**
	 * 加密
	 * 1.构造密钥生成器
	 * 2.根据ecnodeRules规则初始化密钥生成器
	 * 3.产生密钥
	 * 4.创建和初始化密码器
	 * 5.内容加密
	 * 6.返回字符串
	 *
	 * @param secretKey of type String 秘钥
	 * @param content   of type String 加密内容
	 * @return String
	 * @author wangLiuJing
	 * Created on 2020/6/17
	 */
	@SneakyThrows
	public static String encrypt(String secretKey, String content) {
		if(content == null) {
			return null;
		}
		checkSecretKey(secretKey);
		byte[] raw = secretKey.getBytes(CharsetConstant.CHARSET_UTF_8);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(content.getBytes(CharsetConstant.CHARSET_UTF_8));
		// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return Base64.getEncoder().encodeToString(encrypted);
	}

	private static void checkSecretKey(String secretKey) {
		if(StringUtil.isNotBlank(secretKey) && KEY_SIZE.contains(secretKey.length())) {
			return;
		}
		throw new CommonException("密钥长度必须为 16/24/32");
	}

	/**
	 * 解密
	 * @author wangLiuJing
	 * Created on 2020/6/17
	 *
	 * @param secretKey of type String  秘钥
	 * @param ciphertext of type String 密文
	 * @return String
	 */
	@SneakyThrows
	public static String decrypt(String secretKey, String ciphertext) {
		if(ciphertext == null) {
			return null;
		}
		checkSecretKey(secretKey);
		byte[] raw = secretKey.getBytes(CharsetConstant.CHARSET_UTF_8);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] encrypted = Base64.getDecoder().decode(ciphertext);
		byte[] original = cipher.doFinal(encrypted);
		String originalString = new String(original,CharsetConstant.CHARSET_UTF_8);
		return originalString;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String cKey = "fty;POIU1234++==1111111111123456";
		// 需要加密的字串
		String cSrc = "111";
		// 加密
		String enString = encrypt(cKey, cSrc);
		System.out.println("加密后的字串是：" + enString);

		// 解密
		String DeString = decrypt(cKey, enString);
		System.out.println("解密后的字串是：" + DeString);
	}


}
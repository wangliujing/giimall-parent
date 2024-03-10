package com.giimall.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * http请求相关工具类
 *
 * @author wangLiuJing
 * Created on 2019/9/29
 */
@Slf4j
public class HttpUtil {

	/**
	 * 获取请求体
	 * @author wangLiuJing
	 * Created on 2021/12/29
	 *
	 * @param servletRequest of type ServletRequest
	 * @return String
	 * @throws IOException when
	 */
	public static String getRequestBody(ServletRequest servletRequest) throws IOException {
		byte[] bytes = StreamUtils.copyToByteArray(servletRequest.getInputStream());
		return new String(bytes);
	}


	/**
	 * 路径按分割符分割
	 *
	 * @param uri
	 * @return {@link List}<{@link String}>
	 */
	public static List<String> getUriParts(String uri) {
		if(StringUtil.isBlank(uri)) {
			return null;
		}
		return Arrays.stream(uri.split("/|\\\\")).filter(part -> StringUtil.isNotBlank(part))
				.collect(Collectors.toList());
	}

}

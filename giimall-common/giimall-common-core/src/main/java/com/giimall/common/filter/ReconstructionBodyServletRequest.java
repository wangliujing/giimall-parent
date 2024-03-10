/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 重新构建请求体 请求对象
 *
 * @author wangLiuJing
 * @version Id: ReconstructionBodyServletRequest, v1.0.0 2022年05月14日 20:29 wangLiuJing Exp $ 
 */
public class ReconstructionBodyServletRequest extends HttpServletRequestWrapper {

	private byte[] reqBodyBytes;

	public ReconstructionBodyServletRequest(HttpServletRequest request, String requestBody) {
		super(request);
		reqBodyBytes = requestBody.getBytes();
	}


	@Override
	public ServletInputStream getInputStream() {
		return new ServletInputStreamWrapper(reqBodyBytes);
	}

	@Override
	public int getContentLength() {
		return reqBodyBytes.length;
	}

	@Override
	public long getContentLengthLong() {
		return reqBodyBytes.length;
	}

	/**
	 * 解决请求重新将请求体包装到HttpServletRequestWrapper中
	 *
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 */
	public class ServletInputStreamWrapper extends ServletInputStream {

		private byte[] data;
		private int idx = 0;

		public ServletInputStreamWrapper(byte[] data) {
			if (data == null) {
				data = new byte[0];
			}
			this.data = data;
		}

		@Override
		public int read() {
			// & 255 只保留低8位
			return this.idx == this.data.length ? -1 : this.data[this.idx++] & 255;
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener readListener) {

		}
	}
}

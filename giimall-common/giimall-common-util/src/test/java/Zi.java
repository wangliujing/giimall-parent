/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */

import com.giimall.common.util.ProxyFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: Zi, v1.0.0 2022年06月08日 19:16 wangLiuJing Exp $ 
 */
public class Zi extends ProxyTest {

	ProxyTest proxyTest;

	public void getA() {

		proxyTest.getA();
		super.getA();
	}

	public void getB() {
		proxyTest.getB();
	}

	public static void main(String[] args)  {
		byte[] abcs = Base64.getDecoder().decode("abc");

		System.out.println(new String(abcs));
	}
}

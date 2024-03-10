package com.giimall.common.util;

import java.net.URL;

/**
 * 系统工具类
 *
 * @author wangLiuJing
 * Created on 2021/10/20
 */
public class SystemUtil {

	/**
	 * 判断Class是否在jar包运行
	 *
	 * @return the runInJar (type boolean) of this FileUtil object.
	 * @author wangLiuJing
	 * Created on 2020/11/20
	 */
	public static boolean isRunInJar() {
		URL url = SystemUtil.class.getResource("");
		String protocol = url.getProtocol();
		if ("jar".equals(protocol)) {
			return true;
		}
		return false;
	}

	/**
	 * 系统是否运行在linux
	 * @author wangLiuJing
	 * Created on 2021/10/20
	 * @return the runInLinux (type boolean) of this SystemUtil object.
	 */
	public static boolean isRunInLinux(){
		String osName = System.getProperties().getProperty("os.name");
		if("Linux".equals(osName)){
			return true;
		}
		return false;
	}
}

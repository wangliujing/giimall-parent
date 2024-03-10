package com.giimall.common.util;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.exception.CommonException;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;


/**
 * IP地址工具类
 *
 * @author wangLiuJing
 * Created on 2019/11/14
 */
public class IpUtil {

	private static final String IP_UNKNOWN = "unknown";
	private static final int IP_LENGTH = 15;
	private static final String IP_LOCAL_ADDRESS = "127.0.0.1";
	private static final String IP_LOCALHOST_ADDRESS = "0:0:0:0:0:0:0:1";
	private static final int IP_SECTION_LENGTH = 4;
	/** 所有网段  */
	private static final String ALL_NETWORK_SEGMENT = "0.0.0.0/0";

	/***
	 * 获取请求的IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress;
		try {
			if (request == null) {
				return null;
			}
			ipAddress = request.getHeader("x-Forwarded-For");
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("X-Forwarded-For");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("X_FORWARDED_FOR");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (IP_LOCAL_ADDRESS.equals(ipAddress) || IP_LOCALHOST_ADDRESS.equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割 "***.***.***.***".length()
			if (ipAddress != null && ipAddress.length() >= IP_LENGTH) {
				if (IP_LOCAL_ADDRESS.equals(ipAddress) || IP_LOCALHOST_ADDRESS.equals(ipAddress)) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				} else if(ipAddress.indexOf(SymbolConstant.COMMA) > 0){
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(SymbolConstant.COMMA));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		return ipAddress;
	}

	@SneakyThrows
	public static String getLocalIpAddress(){
		InetAddress addr = InetAddress.getLocalHost();
		byte[] ipAddr = addr.getAddress();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				result.append(SymbolConstant.POINT);
			}
			result.append(ipAddr[i] & 0xFF) ;
		}
		return result.toString();
	}

	/**
	 * 判断IP是否在指定范围
	 *
	 * @param ipSection
	 * @param ip
	 * @return
	 */
	public static boolean ipIsValid(String ipSection, String ip) {
		if (ipSection == null) {
			throw new NullPointerException("IP段不能为空！");
		}
		if (ip == null) {
			throw new NullPointerException("IP不能为空！");
		}
		ipSection = ipSection.trim();
		ip = ip.trim();
		final String regxIp = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
		final String regxIpb = regxIp + "\\-" + regxIp;
		if (!ipSection.matches(regxIpb) || !ip.matches(regxIpb)) {
			return false;
		}
		int idx = ipSection.indexOf('-');
		String[] sips = ipSection.substring(0, idx).split("\\.");
		String[] sipe = ipSection.substring(idx + 1).split("\\.");
		String[] sipt = ip.split("\\.");
		long ips = 0L, ipe = 0L, ipt = 0L;
		for (int i = 0; i < IP_SECTION_LENGTH; ++i) {
			ips = ips << 8 | Integer.parseInt(sips[i]);
			ipe = ipe << 8 | Integer.parseInt(sipe[i]);
			ipt = ipt << 8 | Integer.parseInt(sipt[i]);
		}
		if (ips > ipe) {
			long t = ips;
			ips = ipe;
			ipe = t;
		}
		return ips <= ipt && ipt <= ipe;
	}

	/**
	 * 判断IP地址的合法性
	 *
	 * @param text
	 * @return
	 */
	public static boolean ipCheck(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			return text.matches(regex);
		}
		return false;
	}

	/**
	 * 判断是否是CIDR格式（xxx.xxx.xxx.xxx/xx）的ip匹配串
	 *
	 * @param ipStr of type String
	 * @return boolean
	 * @author zhanghao
	 * Created on 2021/4/14
	 */
	public static boolean ipCidrCheck(String ipStr) {
		if (ipStr == null) {
			return false;
		}
		return ipStr.matches("^(?:(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.)" +
				"{3}(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\/([1-9]|[1-2]\\d|3[0-2])$");
	}

	/**
	 * 拼端口
	 * @author wangLiuJing
	 * Created on 2020/1/6
	 *
	 * @param ip of type String
	 * @param port of type int
	 * @param timeout of type int
	 * @return booleans
	 */
	public static boolean pingHost(String ip, int port, int timeout){
		Socket socket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(ip, port);
		try {
			socket.connect(socketAddress, timeout);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是相同的ip
	 * @author wangLiuJing
	 * Created on 2021/12/6
	 *
	 * @param originalIp of type String
	 * @param targetIp of type String
	 * @return boolean
	 */
	public static boolean isSameIp(String originalIp, String targetIp) {
		if(!(ipCheck(originalIp) && ipCheck(targetIp))){
			throw new CommonException("originalIp与targetIp格式必须符合 xxx.xxx.xxx.xxx 格式 ");
		}
		return originalIp.trim().equals(targetIp.trim());
	}

	/**
	 * 校验ip是否符合cidrExpression网段
	 * @author wangLiuJing
	 * Created on 2021/11/10
	 *
	 * @param cidrExpression of type String 网段表达式
	 * @param ip of type String 待验证ip
	 * @return boolean
	 */
	public static boolean cidrIpMatch(String cidrExpression, String ip) {
		if (cidrExpression == null) {
			return false;
		}
		// 如果是所有网段统一返回true
		if(ALL_NETWORK_SEGMENT.equals(cidrExpression)){
			return true;
		}
		if(!ipCidrCheck(cidrExpression)){
			throw new CommonException("cidrExpression必须是CIDR格式 xxx.xxx.xxx.xxx/xx ");
		}
		// 将CIDR前的ip段转成二进制，再进行右移匹配
		String[] split = cidrExpression.trim().split("/");
		// 截取"/"右侧的数字作为右移位数
		int shiftNum = 32 - Integer.parseInt(split[1]);
		// 将匹配ip和原ip无符号位移，再进行比较
		return ipStr2Int(split[0]) >>> shiftNum == ipStr2Int(ip.trim()) >>> shiftNum;
	}


	/**
	 * 把xxx.xxx.xxx.xxx形式的ip转换成32位的二进制对应的整数
	 *
	 * @param ip of type String
	 * @return int
	 * @author zhanghao
	 * Created on 2021/4/15
	 */
	public static int ipStr2Int(String ip) {
		if (ipCheck(ip)) {
			int ipInt = 0;
			String[] split = ip.split("\\.");
			for (String s : split) {
				ipInt = ipInt << 8 | Integer.parseInt(s);
			}
			return ipInt;
		}
		throw new CommonException(ip + "不满足ip格式xxx.xxx.xxx.xxx");
	}
}

package com.giimall.common.util;

import lombok.SneakyThrows;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 封装了XML转换成object，object转换成XML的代码
 *
 * @author wangLiuJing
 * Created on 2019/12/30
 */
public class XmlUtil {

	/**
	 * 将对象直接转换成String类型的 XML输出
	 * @author wangLiuJing
	 * Created on 2019/12/30
	 *
	 * @param obj of type Object
	 * @param removeMsgHead of type boolean  是否去除xml报文头
	 * @return String
	 */
	@SneakyThrows
	public static String convertToXml(Object obj, boolean removeMsgHead) {
		// 创建输出流
		StringWriter sw = new StringWriter();
		// 利用jdk中自带的转换类实现
		JAXBContext context = JAXBContext.newInstance(obj.getClass());

		Marshaller marshaller = context.createMarshaller();
		// 格式化xml输出的格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, removeMsgHead);
		// 将对象转换成输出流形式的xml
		marshaller.marshal(obj, sw);
		return sw.toString();
	}

	/**
	 * 将对象根据路径转换成xml文件
	 * @author wangLiuJing
	 * Created on 2019/12/30
	 *
	 * @param obj of type Object
	 * @param path of type String
	 */
	@SneakyThrows
	public static void convertToXml(Object obj, String path) {
		// 利用jdk中自带的转换类实现
		JAXBContext context = JAXBContext.newInstance(obj.getClass());

		Marshaller marshaller = context.createMarshaller();
		// 格式化xml输出的格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		// 将对象转换成输出流形式的xml
		marshaller.marshal(obj, new FileWriter(path));
	}

	/**
	 * 将String类型的xml转换成对象
	 * @author wangLiuJing
	 * Created on 2019/12/30
	 *
	 * @param clazz of type Class
	 * @param xmlStr of type String
	 * @return Object
	 */
	@SneakyThrows
	public static <T> T convertXmlStrToObject(Class<T> clazz, String xmlStr) {
		JAXBContext context = JAXBContext.newInstance(clazz);
		// 进行将Xml转成对象的核心接口
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return  (T) unmarshaller.unmarshal(new StringReader(xmlStr));
	}

	/**
	 * 将file类型的xml转换成对象
	 * @author wangLiuJing
	 * Created on 2019/12/30
	 *
	 * @param clazz of type Class
	 * @param xmlPath of type String
	 * @return T
	 */
	@SneakyThrows
	public static <T> T convertXmlFileToObject(Class<T> clazz, String xmlPath) {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new FileReader(xmlPath));
	}

	/**
	 * 将文本类型的xml转换成Document对象
	 * @author zhanghao
	 * Created on 2020/9/2
	 *
	 * @param text of type String
	 * @return Document
	 */
	@SneakyThrows
	public static Document parseTextToDocument(String text) {
		return DocumentHelper.parseText(text);
	}
}

package com.giimall.common.util;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class IkAnalyzerUtil {

	/**
	 * Method analyze ...
	 * @author wangLiuJing
	 * Created on 2020/9/16
	 *
	 * @param target of type String
	 * @param useSmart of type boolean false 是细粒度分词  true 是粗粒度分词
	 * @return List<String>
	 */
	@SneakyThrows
	public static List<String> analyze(String target, boolean useSmart) {
		if (StringUtil.isEmpty(target)) {
			return Lists.newArrayList();
		}
		List<String> result = new ArrayList<>();
		StringReader sr = new StringReader(target);
		// 关闭智能分词 (对分词的精度影响较大)
		IKSegmenter ik = new IKSegmenter(sr, useSmart);
		Lexeme lex;
		while ((lex = ik.next()) != null) {
			String lexemeText = lex.getLexemeText();
			result.add(lexemeText);
		}
		return result;
	}
}

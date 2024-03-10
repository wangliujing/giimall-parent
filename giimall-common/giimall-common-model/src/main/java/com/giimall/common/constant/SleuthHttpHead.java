/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */
package com.giimall.common.constant;

/**
 * 链路追踪请求头
 *
 * @author wangLiuJing
 * @version Id: SleuthHttpHead, v1.0.0 2022年05月25日 17:55 wangLiuJing Exp $ 
 */
public class SleuthHttpHead {

	public static final String TRACE_ID = "X-B3-TraceId";

	public static final String SPAN_ID = "X-B3-SpanId";

	public static final String PARENT_SPAN_ID = "X-B3-ParentSpanId";
	/**是否被抽样为输出的标志    1为需要被输出    0为不需要被输出*/
	public static final String SAMPLED = "X-B3-Sampled";
}

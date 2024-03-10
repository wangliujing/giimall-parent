/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: Sub, v1.0.0 2022年04月11日 23:26 wangLiuJing Exp $ 
 */
public class Sub extends Student {

	public void test() {
		super.test();
	}

	@Override
	public void a() {
		System.out.println("我是子类");
	}

	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.test();
	}
}

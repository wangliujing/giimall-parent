/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: ProxyTest, v1.0.0 2022年06月07日 18:11 wangLiuJing Exp $ 
 */
public class ProxyTest {

	public void getA(){
		System.out.println("执行A");
		getB();
	}

	public void getB(){
		System.out.println("执行B");
	}

}

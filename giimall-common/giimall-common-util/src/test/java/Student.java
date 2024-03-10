/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */

import lombok.Data;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: Student, v1.0.0 2022年07月25日 15:31 wangLiuJing Exp $ 
 */
@Data
public class Student {
	private Long id;
	private String name;
	private int age;
	private Course[] courseList;

	@Data
	public static class Course {
		private String name;
		private Long id;
	}
}

/**
 * @company 杭州吉喵云科技有限公司(www.gillmall.com)
 * @copyright Copyright (c) 2012-2022
 */

import com.giimall.common.util.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * 类描述与备注 
 *
 * @author wangLiuJing
 * @version Id: Test, v1.0.0 2022年04月24日 15:04 wangLiuJing Exp $ 
 */
public class Test {

	public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		Student student = new Student();
		student.setId(1L);
		student.setName("张三");
		student.setAge(1);
		Student.Course[] courseList = new Student.Course[2];
		Student.Course course = new Student.Course();
		course.setId(1L);
		course.setName("语文");
		courseList[0] = course;

		course = new Student.Course();
		course.setId(2L);
		course.setName("英语");
		courseList[1] = course;
		student.setCourseList(courseList);


		Student student1 = new Student();
		student1.setId(1L);
		student1.setName("张三");
		student1.setAge(1);
		courseList = new Student.Course[2];

		course = new Student.Course();
		course.setId(1L);
		course.setName("语文");
		courseList[0] = course;

		course = new Student.Course();
		course.setId(2L);
		course.setName("英语1");
		courseList[1] = course;

		student1.setCourseList(courseList);
		System.out.println(BeanUtil.isSameValue(student, student1, false, "courseList"));
	}

}

package com.giimall.common.model.tree;

import java.io.Serializable;

/**
 * 树节点对象，必须实现这个interface
 *
 * @author wangLiuJing
 * Created on 2019/9/24
 */
public interface Nodeable<T> extends Serializable {

	/**
	 * 获取ID
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 * @return the id (type T) of this Nodeable object.
	 */
	T getId();

	/**
	 * 设置ID
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 *
	 * @param id of type T
	 */
	void setId(T id);

	/**
	 * 获取父ID
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 * @return the pid (type T) of this Nodeable object.
	 */
	T getPid();

	/**
	 * 设置父ID
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 *
	 * @param id of type T
	 */
	void setPid(T id);

	/**
	 * 是否禁用
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 * @return the ifDisabled (type Boolean) of this Nodeable object.
	 */
	Boolean getIfDisabled();

	/**
	 * 设置是否禁用
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 *
	 * @param ifDisabled of type Boolean
	 */
	void setIfDisabled(Boolean ifDisabled);

	/**
	 * 获取名称
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 * @return the name (type String) of this Nodeable object.
	 */
	String getName();



	/**
	 * 设置名称
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 *
	 * @param name of type String
	 */
	void setName(String name);

	/**
	 * 是否是叶子节点
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 * @return the ifLeafNode (type Boolean) of this Nodeable object.
	 */
	Boolean getIfLeafNode();


	/**
	 * 设置是否是叶子节点.
	 * @author wangLiuJing
	 * Created on 2022/1/5
	 *
	 * @param ifLeafNode of type Boolean
	 */
	void setIfLeafNode(Boolean ifLeafNode);

}

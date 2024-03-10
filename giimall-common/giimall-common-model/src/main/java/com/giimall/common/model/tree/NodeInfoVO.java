package com.giimall.common.model.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 树形结构页面展示实体类
 *
 * @author wangLiuJing
 * Created on 2019/9/24
 */
@Data
public class NodeInfoVO<T extends Nodeable<E>, E> implements Serializable {

	private E id;

	private E pid;

	private String name;

	private Boolean ifDisabled;

	private Boolean ifLeafNode;

	private T entity;

	private Map<String, Object> attributeMap;

	private List<NodeInfoVO<T, E>> children;
}

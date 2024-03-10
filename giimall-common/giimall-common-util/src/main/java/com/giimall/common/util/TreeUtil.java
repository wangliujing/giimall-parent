package com.giimall.common.util;


import com.giimall.common.model.tree.NodeInfoVO;
import com.giimall.common.model.tree.Nodeable;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 树形结构工具类，使用该工具类实体对象必须实现Nodeable接口
 *
 * @author wangLiuJing
 * Created on 2019/9/24
 */

public class TreeUtil {

	/**
	 * 转换平行结构数据为树形结构
	 * Method treeNodeInfo ...
	 *
	 * @param list of type List<? extends Nodeable>
	 * @param rootPidFlag of type T
	 * @return List<NodeInfoVO < Nodeable, T>>
	 * @author wangLiuJing
	 * Created on 2019/9/24
	 */
	@SneakyThrows
	public static <T> List<NodeInfoVO<Nodeable<T>, T>> treeNodeInfo(List<? extends Nodeable<T>> list, T rootPidFlag,
																	String... attributeNames) {
		Iterator<? extends Nodeable<T>> iterator = list.iterator();
		List<NodeInfoVO<Nodeable<T>, T>> result = new ArrayList<>();
		while (iterator.hasNext()) {
			Nodeable<T> nodeable = iterator.next();
			if (Objects.equals(nodeable.getPid(), rootPidFlag)) {
				NodeInfoVO<Nodeable<T>, T> nodeInfoVO = new NodeInfoVO();
				nodeInfoVO.setId(nodeable.getId());
				nodeInfoVO.setPid(nodeable.getPid());
				nodeInfoVO.setName(nodeable.getName());
				nodeInfoVO.setIfDisabled(nodeable.getIfDisabled());
				nodeInfoVO.setIfLeafNode(nodeable.getIfLeafNode());
				if (attributeNames != null) {
					Map<String, Object> attributeMap = new HashMap<>(10);
					Class<? extends Nodeable> attributeClazz = nodeable.getClass();
					for (String attributeName : attributeNames) {
						Field field = BeanUtil.getDeclaredField(attributeClazz, attributeName);
						field.setAccessible(true);
						attributeMap.put(attributeName, field.get(nodeable));
					}
					nodeInfoVO.setAttributeMap(attributeMap);
				}
				nodeInfoVO.setEntity(nodeable);
				// 如果不是叶子节点则继续递归查询
				if (!Boolean.TRUE.equals(nodeable.getIfLeafNode())) {
					nodeInfoVO.setChildren(treeNodeInfo(list, nodeInfoVO.getId(), attributeNames));
				}
				result.add(nodeInfoVO);
			}
		}
		// 若没有叶子节点属性，则会返回一个空的list，此时将空数组变成null返回，防止子节点中插入空数组
		return result.isEmpty() ? null : result;
	}


	/**
	 * 清除树形结构entity属性
	 *
	 * @param list of type List<NodeInfoVO<Nodeable, T>>
	 * @return List<NodeInfoVO   <Nodeable, T>>
	 * @author wangLiuJing
	 * Created on 2019/9/25
	 */
	public static  <T> List<NodeInfoVO<Nodeable<T>, T>> clearNodeInfoEntity(List<NodeInfoVO<Nodeable<T>, T>> list) {
		if (list == null || list.isEmpty()) {
			 return null;
		}
		Iterator<NodeInfoVO<Nodeable<T>, T>> iterator = list.iterator();
		while (iterator.hasNext()) {
			NodeInfoVO<Nodeable<T>, T> next = iterator.next();
			next.setEntity(null);
			if (!Boolean.TRUE.equals(next.getIfLeafNode())) {
				clearNodeInfoEntity(next.getChildren());
			}
		}
		return list;
	}

	/**
	 * 根据父id递归获取子列表
	 *
	 * @param list of type List<? extends Nodeable>
	 * @return List<? extendsNodeable>
	 * @author wangLiuJing
	 * Created on 2019/9/24
	 */
	public static <T> List<Nodeable<T>> getChildNodeList(List<? extends Nodeable<T>> list, T pid) {
		List<Nodeable<T>> childList = new ArrayList<>();
		Iterator<? extends Nodeable<T>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Nodeable<T> nodeable = iterator.next();
			if (Objects.equals(nodeable.getPid(), pid)) {
				childList.add(nodeable);
				// 如果不是叶子节点则继续递归查询
				if (!nodeable.getIfLeafNode()) {
					childList.addAll(getChildNodeList(list, nodeable.getId()));
				}
			}
		}
		return childList;
	}

	/**
	 * 根据父id递归获取子列表(包含节点本身)
	 *
	 * @param list of type List<? extends Nodeable>
	 * @return List<?   extends   Nodeable   < T>>
	 * @author wangLiuJing
	 * Created on 2019/9/24
	 */
	public static <T> List<Nodeable<T>> getChildNodeListIncludSelf(List<? extends Nodeable<T>> list, T pid) {
		List<Nodeable<T>> childList = new ArrayList<>();
		Iterator<? extends Nodeable<T>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Nodeable<T> nodeable = iterator.next();
			// 添加节点本身
			if (Objects.equals(nodeable.getId(), pid)) {
				childList.add(nodeable);
			}
			if (Objects.equals(nodeable.getPid(), pid)) {
				childList.add(nodeable);
				// 如果不是叶子节点则继续递归查询
				if (!nodeable.getIfLeafNode()) {
					childList.addAll(getChildNodeList(list, nodeable.getId()));
				}
			}
		}
		return childList;
	}


	/**
	 * 从缓存根据父id递归获取子列表
	 *
	 * @param list of type List<? extends Nodeable>
	 * @return List<?extendsNodeable < T>>
	 * @author wangLiuJing
	 * Created on 2019/9/24
	 */
	public static <T> List<Nodeable<T>> getChildNodeListFormCach(List<NodeInfoVO<Nodeable<T>, T>> list, T pid) {
		List<Nodeable<T>> childList = new ArrayList<>();
		Iterator<NodeInfoVO<Nodeable<T>, T>> rootIterator = list.iterator();
		while (rootIterator.hasNext()) {
			NodeInfoVO<Nodeable<T>, T> next = rootIterator.next();
			if (Objects.equals(next.getPid(), pid)) {
				childList.add(next.getEntity());
				// 如果不是叶子节点则继续递归查询
				if (! next.getIfLeafNode()) {
					childList.addAll(getChildNodeListFormCach(next.getChildren()));
				}
			} else {
				// 如果不是叶子节点则继续递归查询
				if (! next.getIfLeafNode()) {
					getChildNodeListFormCach(next.getChildren(), pid);
				}
			}
		}
		return childList;
	}


	/**
	 * 从缓存根据父id递归获取子列表(包含节点本身)
	 *
	 * @param list of type List<? extends Nodeable>
	 * @return List<? extends Nodeable < T>>
	 * @author wangLiuJing
	 * Created on 2019/9/24
	 */
	public static <T> List<Nodeable<T>> getChildNodeListFormCachIncludSelf(List<NodeInfoVO<Nodeable<T>, T>> list, T pid) {
		List<Nodeable<T>> childList = new ArrayList<>();
		Iterator<NodeInfoVO<Nodeable<T>, T>> rootIterator = list.iterator();
		while (rootIterator.hasNext()) {
			NodeInfoVO<Nodeable<T>, T> next = rootIterator.next();
			// 加入节点本身
			if (Objects.equals(next.getId(), pid)) {
				childList.add(next.getEntity());
			}
			if (Objects.equals(next.getPid(), pid)) {
				childList.add(next.getEntity());
				// 如果不是叶子节点则继续递归查询
				if (!next.getIfLeafNode()) {
					childList.addAll(getChildNodeListFormCach(next.getChildren()));
				}
			} else {
				// 如果不是叶子节点则继续递归查询
				if (!next.getIfLeafNode()) {
					List<Nodeable<T>> childNodeListFormCachIncludSelf = getChildNodeListFormCachIncludSelf(next.getChildren(), pid);
					childList.addAll(childNodeListFormCachIncludSelf);
				}
			}
		}
		return childList;
	}

	private static <T> List<Nodeable<T>> getChildNodeListFormCach(List<NodeInfoVO<Nodeable<T>, T>> childNodeInfoList) {
		List<Nodeable<T>> childList = new ArrayList<>();
		Iterator<NodeInfoVO<Nodeable<T>, T>> iterator = childNodeInfoList.iterator();
		while (iterator.hasNext()) {
			NodeInfoVO<Nodeable<T>, T> next = iterator.next();
			childList.add(next.getEntity());
			// 如果不是叶子节点则继续递归查询
			if (!next.getIfLeafNode()) {
				childList.addAll(getChildNodeListFormCach(next.getChildren()));
			}
		}
		return childList;
	}

	public static <T> List<T> getNodeableIds(List<Nodeable<T>> list) {
		List<T> listIds = new ArrayList<>();
		for (Nodeable<T> nodeable : list) {
			listIds.add(nodeable.getId());
		}
		return listIds;
	}

	/**
	 * 获取当前节点的所有父节点，
	 * @author zhanghao
	 * Created on 2020/7/2
	 *
	 * @param tree of type List<NodeInfoVO<Nodeable, T>>
	 * @param nodeId of type T
	 * @return List<Nodeable < T>>  子节点在列表前，父节点在后
	 */
	public static <T> List<Nodeable<T>> getParentNode(List<NodeInfoVO<Nodeable<T>, T>> tree, T nodeId) {
		for (NodeInfoVO<Nodeable<T>, T> nodeInfoVO : tree) {
			// 若当前树节点是目标节点，结果列表中添加节点并返回
			if (nodeInfoVO.getId().equals(nodeId)) {
				List<Nodeable<T>> resultList = new ArrayList<>(10);
				resultList.add(nodeInfoVO.getEntity());
				return resultList;
			}
			// 若该节点不是目标节点，则递归调用该节点子树，若子树查询到了节点，即parentNodes不为空，则把子树的父节点添加到结果列表尾部
			if (nodeInfoVO.getChildren() != null) {
				List<Nodeable<T>> parentNodes = getParentNode(nodeInfoVO.getChildren(), nodeId);
				if (parentNodes != null && !parentNodes.isEmpty()) {
					parentNodes.add(nodeInfoVO.getEntity());
					return parentNodes;
				}
			}
		}
		return null;
	}

	/**
	 * 根据节点ID，截取树形结构
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 *
	 * @param list of type List<NodeInfoVO<Nodeable, T>>
	 * @param nodeId of type T
	 * @return NodeInfoVO<Nodeable < T>, T>
	 */
	public static <T> NodeInfoVO<Nodeable<T>, T> getNodeableTreeByIdFromCache(List<NodeInfoVO<Nodeable<T>, T>> list, T nodeId) {
		for (NodeInfoVO<Nodeable<T>, T> nodeableTNodeInfoVO : list) {
			if (nodeableTNodeInfoVO.getId().equals(nodeId)) {
				return nodeableTNodeInfoVO;
			}
			if (!nodeableTNodeInfoVO.getIfLeafNode()) {
				List<NodeInfoVO<Nodeable<T>, T>> children = nodeableTNodeInfoVO.getChildren();
				if (children != null && !list.isEmpty()) {
					NodeInfoVO<Nodeable<T>, T> nodeableTreeByIdFromCache = getNodeableTreeByIdFromCache(children, nodeId);
					if(nodeableTreeByIdFromCache != null){
						return nodeableTreeByIdFromCache;
					}
				}
			}
		}
		return null;
	}


	/**
	 * 从树形结构过滤掉禁用属性的节点
	 * @author wangLiuJing
	 * Created on 2020/10/9
	 *
	 * @param list of type List<NodeInfoVO<Nodeable, Object>>
	 * @return List<NodeInfoVO < Nodeable < T>, T>>
	 */
	public static <T> List<NodeInfoVO<Nodeable<T>, T>> getNodeableTreeUnDisabled(List<NodeInfoVO<Nodeable<T>, T>> list) {

		Iterator<NodeInfoVO<Nodeable<T>, T>> iterator = list.iterator();
		while (iterator.hasNext()){
			NodeInfoVO<Nodeable<T>, T> next = iterator.next();
			Boolean ifDisabled = next.getIfDisabled();
			if(ifDisabled){
				iterator.remove();
				continue;
			}
			if(!next.getIfLeafNode()){
				getNodeableTreeUnDisabled(next.getChildren());
			}
		}
		return list;
	}

}

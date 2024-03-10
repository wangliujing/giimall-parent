package com.giimall.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.giimall.common.model.resultcode.IResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用于各个微服务返回分页对象
 *
 * @author wangLiuJing
 * Created on 2019/9/19
 */
@Data
@NoArgsConstructor
public class PaginationResult<T> extends StandardResult {

	/**
	 * 响应数据体
	 */
	private Collection<T> data;
	/**
	 * 总数据量
	 */
	private long total;

	/**
	 * 总页数
	 */
	private long pages;

	private PaginationResult(boolean state, String msg, Collection<T> data, int code, long total, long pages) {
		super(state, msg, null, code);
		this.data = data;
		this.total = total;
		this.pages = pages;
	}

	private PaginationResult(IResultCode resultCode, Collection<T> data, long total, long pages) {
		super(resultCode, null);
		this.data = data;
		this.total = total;
		this.pages = pages;
	}

	public static <T> PaginationResult<T> resultCode(IResultCode resultCode, IPage<T> page) {
		if(page == null) {
			return new PaginationResult(resultCode, new ArrayList<>(), 0, 0);
		}
		return new PaginationResult(resultCode, page.getRecords(), page.getTotal(), page.getPages());
	}

	public static <T> PaginationResult<T> resultCode(IResultCode resultCode, Collection<T> data, long total, long pages) {
		return new PaginationResult(resultCode, data, total, pages);
	}

	public static <T> PaginationResult<T> resultCode(boolean state, String msg, Collection<T> data, int code,
													 long total, long pages) {
		return new PaginationResult(state, msg, data, code, total, pages);
	}
}

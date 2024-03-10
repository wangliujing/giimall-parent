package com.giimall.common.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页请求
 *
 * @author wangLiuJing
 * @date 2022/03/15
 */
@Data
public class PageRequest implements Serializable {
    /**
     * 当前页码
     */
    @NotNull(message = "页码不能为空")
    protected Integer pageNum;
    /**
     * 每页数量
     */
    @NotNull(message = "页长不能为空")
    protected Integer pageSize;

    @JsonIgnore
    public IPage getPage() {
        return new Page(pageNum, pageSize);
    }
}

package com.giimall.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * id传输类
 * @author wangLiuJing
 * @date 2022/03/15
 */
@Data
public class IdDTO implements Serializable {

	@NotNull(message = "主键ID不能为空")
	protected Long id;
}

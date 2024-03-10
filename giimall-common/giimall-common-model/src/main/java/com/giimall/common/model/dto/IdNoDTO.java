package com.giimall.common.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: ID编号传输类.
 * @Author: gufengrong
 * @Date: 2022/5/10 17:46
 */
@Data
public class IdNoDTO implements Serializable {

  @NotBlank(message = "业务编号不能为空")
  protected String idNo;
}

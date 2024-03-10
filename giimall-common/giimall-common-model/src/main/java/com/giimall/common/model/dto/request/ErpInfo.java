package com.giimall.common.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Erp 信息
 */
@Data
public class ErpInfo {

    private Long erpId;

    @JsonProperty("isOwner")
    private boolean isOwner;
}

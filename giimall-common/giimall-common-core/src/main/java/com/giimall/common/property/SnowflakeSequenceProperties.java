package com.giimall.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 雪花算法配置类
 *
 * @author wangLiuJing
 * Created on 2019/12/26
 */
@Data
@ConfigurationProperties(prefix = "id.snowflake")
@RefreshScope
public class SnowflakeSequenceProperties {

	private Long dataCenterId;
}

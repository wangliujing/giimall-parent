package com.giimall.common.initialize;

import com.giimall.common.constant.SymbolConstant;
import com.giimall.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.annotation.Order;

import java.io.File;

/**
 * 主要用于解决上传文件linux临时目录缺失问题，如果临时目录不存在会自动创建
 *
 * @author wangLiuJing
 * Created on 2021/11/29
 */
@Order(1)
@Slf4j
public class TmpDirectryCreate implements CommandLineRunner {

    private MultipartProperties multipartProperties;

    public TmpDirectryCreate(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        // 获取临时文件目录配置
        String location = multipartProperties.getLocation();
        if (StringUtil.isNotBlank(location)) {
            // 判断系统环境是否是Linux
            String osName = System.getProperty("os.name");
            if (StringUtil.isNotBlank(osName) && osName.toLowerCase().contains("linux")) {
                // 创建临时文件目录
                log.info("环境系统为{}，开始创建临时文件目录{}");
                File file = new File(location.endsWith(SymbolConstant.SLASH) ? location : location + SymbolConstant.SLASH);
                if (file.exists()) {
                    log.info("临时目录{}已存在", location);
                } else {
                    if (file.mkdir()) {
                        log.info("临时目录{}创建成功");
                    }
                }
            }
        }
    }
}

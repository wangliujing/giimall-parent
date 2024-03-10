package com.giimall.common.model.resultcode;

/**
 * 自定义结果代码
 * Interface IResultCode ...
 *
 * @author wangLiuJing
 * Created on 2019/12/4
 */
public interface IResultCode {

    /**
     * 请求状态码
     *
     * @return int
     * @author wangLiuJing
     * Created on 2019/12/4
     */
    int code();

    /**
     * 请求提示消息
     * @author wangLiuJing
     * Created on 2019/12/4
     * @return String
     */
    String msg();

    /**
     * 请求成功失败状态
     * @author wangLiuJing
     * Created on 2019/12/4
     * @return boolean
     */
    boolean state();
}

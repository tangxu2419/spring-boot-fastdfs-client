package com.vcredit.framework.fastdfs.exception;

import com.vcredit.framework.fastdfs.constants.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangxu
 * @date 2019/5/614:49
 */
public class FdfsServerException extends RuntimeException {


    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    protected FdfsServerException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    protected FdfsServerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 错误对照表
     */
    private static final Map<Integer, String> CODE_MESSAGE_MAPPING;

    static {
        Map<Integer, String> mapping = new HashMap<Integer, String>();
        mapping.put((int) Constants.ERR_NO_ENOENT, "找不到节点或文件");
        mapping.put((int) Constants.ERR_NO_EIO, "服务端发生io异常");
        mapping.put((int) Constants.ERR_NO_EINVAL, "无效的参数");
        mapping.put((int) Constants.ERR_NO_EBUSY, "服务端忙");
        mapping.put((int) Constants.ERR_NO_ENOSPC, "没有足够的存储空间");
        mapping.put((int) Constants.ECONNREFUSED, "服务端拒绝连接");
        mapping.put((int) Constants.ERR_NO_EALREADY, "文件已经存在？");
        CODE_MESSAGE_MAPPING = Collections.unmodifiableMap(mapping);
    }

    private int errorCode;

    /**
     *
     */
    private FdfsServerException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static FdfsServerException byCode(int errorCode) {
        String message = CODE_MESSAGE_MAPPING.get(errorCode);
        if (message == null) {
            message = "未知错误";
        }
        message = "错误码：" + errorCode + "，错误信息：" + message;

        return new FdfsServerException(errorCode, message);
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }
}
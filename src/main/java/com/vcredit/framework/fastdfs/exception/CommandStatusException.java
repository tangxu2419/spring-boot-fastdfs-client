package com.vcredit.framework.fastdfs.exception;

import com.vcredit.framework.fastdfs.constant.Constants;

import java.util.Map;

/**
 * 错误指令的异常
 *
 * @author dongzhuming
 */
public class CommandStatusException extends InvokeCommandException {

    /**
     * 错误对照表
     */
    private static final Map<Integer, String> CODE_MESSAGE_MAPPING = Map.of(
            (int) Constants.ERR_NO_ENOENT, "找不到节点或文件",
            (int) Constants.ERR_NO_EIO, "服务端发生io异常",
            (int) Constants.ERR_NO_EINVAL, "无效的参数",
            (int) Constants.ERR_NO_EBUSY, "服务端忙",
            (int) Constants.ERR_NO_ENOSPC, "没有足够的存储空间",
            (int) Constants.ECONNREFUSED, "服务端拒绝连接",
            (int) Constants.ERR_NO_EALREADY, "文件已经存在？");


    private int errorCode;

    public CommandStatusException(int errorCode) {
        super(CODE_MESSAGE_MAPPING.get(errorCode), null);
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

}

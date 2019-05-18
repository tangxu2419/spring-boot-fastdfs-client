/*
 *    Copyright 2019 vcredit
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
        super(CODE_MESSAGE_MAPPING.get(errorCode));
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

}

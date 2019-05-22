/*
 *    Copyright 2019 VCREDIT
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

/**
 * 客户端向服务端发送命令、文件或从服务端读取结果、下载文件时发生io异常
 * @author tangxu
 * @date 2019/5/814:11
 */
public class FdfsIOException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param cause Throwable
     */
    public FdfsIOException(Throwable cause) {
        super("客户端连接服务端出现了io异常", cause);
    }

    /**
     * @param message   exceptionMessage
     * @param cause Throwable
     */
    public FdfsIOException(String message, Throwable cause) {
        super("客户端连接服务端出现了io异常:" + message, cause);
    }

    public FdfsIOException(String message) {
        super(message);
    }
}

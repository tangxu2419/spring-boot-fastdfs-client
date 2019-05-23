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

package com.vcredit.framework.fastdfs.command;

import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.vcredit.framework.fastdfs.constant.Constants.PROTO_HEADER_CMD_INDEX;
import static com.vcredit.framework.fastdfs.constant.Constants.PROTO_HEADER_STATUS_INDEX;

/**
 * @author tangxu
 */
public class ProtoHead {
    /**
     * 报文长度
     */
    private static final int HEAD_LENGTH = Constants.FDFS_PROTO_PKG_LEN_SIZE + 2;

    /**
     * 报文内容长度1-7位
     */
    private long contentLength = 0;
    /**
     * 报文类型8位
     */
    private byte cmd;
    /**
     * 处理状态9位
     */
    private byte status = (byte) 0;


    public long getContentLength() {
        return contentLength;
    }

    public byte getStatus() {
        return status;
    }


    /**
     * 请求报文构造函数
     */
    public ProtoHead(byte cmd) {
        super();
        this.cmd = cmd;
    }

    /**
     * 返回报文构造函数
     *
     * @param contentLength 响应体长度
     * @param cmd           指令字节
     * @param status        响应状态
     */
    public ProtoHead(long contentLength, byte cmd, byte status) {
        this.contentLength = contentLength;
        this.cmd = cmd;
        this.status = status;
    }


    /**
     * 通过FastDFS传输协议打包标头
     *
     * @return 打包字节缓冲区
     */
    byte[] packHeader() {
        byte[] header = new byte[HEAD_LENGTH];
        Arrays.fill(header, (byte) 0);
        byte[] hexLen = ProtoPackageUtil.long2buff(contentLength);
        System.arraycopy(hexLen, 0, header, 0, hexLen.length);
        header[PROTO_HEADER_CMD_INDEX] = cmd;
        header[PROTO_HEADER_STATUS_INDEX] = status;
        return header;
    }

    /**
     * 读取输入流创建报文头
     *
     * @param ins 输入流
     * @return ProtoHead
     * @throws IOException IO异常
     */
    static ProtoHead createFromInputStream(InputStream ins) throws IOException {
        byte[] header = new byte[HEAD_LENGTH];
        int bytes;
        // 读取HEAD_LENGTH长度的输入流
        if ((bytes = ins.read(header)) != header.length) {
            throw new IOException("recv package size " + bytes + " != " + header.length);
        }
        long returnContentLength = ProtoPackageUtil.buff2long(header, 0);
        byte returnCmd = header[Constants.PROTO_HEADER_CMD_INDEX];
        byte returnStatus = header[Constants.PROTO_HEADER_STATUS_INDEX];
        // 返回解析出来的ProtoHead
        return new ProtoHead(returnContentLength, returnCmd, returnStatus);
    }

    /**
     * 验证服务端返回报文有效性
     *
     * @throws InvokeCommandException 调用指令异常
     */
    void validateResponseHead() throws InvokeCommandException {
        // 检查是否是正确反馈报文
        if (cmd != ProtocolCommand.TRACKER_PROTO_CMD_RESP) {
            throw new InvokeCommandException(
                    "recv cmd: " + cmd + " is not correct, expect cmd: " + ProtocolCommand.TRACKER_PROTO_CMD_RESP);
        }
        // 获取处理错误状态
        if (status != 0) {
            throw new CommandStatusException(status);
        }

        if (contentLength < 0) {
            throw new InvokeCommandException("recv body length: " + contentLength + " < 0!");
        }
    }

    @Override
    public String toString() {
        return "ProtoHead{ contentLength=" + contentLength + ", cmd=" + cmd + ", status=" + status + '}';
    }

    void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
}

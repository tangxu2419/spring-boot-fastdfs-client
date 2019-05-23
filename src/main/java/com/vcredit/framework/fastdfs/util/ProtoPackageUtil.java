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

package com.vcredit.framework.fastdfs.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangxu
 */
public class ProtoPackageUtil {

    /**
     * 从响应流中获取字节数组
     *
     * @param in      输入流
     * @param bodyLen 头信息中指定响应包体长
     * @return 打包字节缓冲区
     * @throws IOException 输入流响应体字节长度与头信息指定长度不一致
     */
    public static byte[] recvResponseBody(InputStream in, long bodyLen) throws IOException {
        byte[] body = new byte[(int) bodyLen];
        int totalBytes = 0;
        int remainBytes = (int) bodyLen;
        int bytes;
        while (totalBytes < bodyLen) {
            if ((bytes = in.read(body, totalBytes, remainBytes)) < 0) {
                break;
            }
            totalBytes += bytes;
            remainBytes -= bytes;
        }
        if (totalBytes != bodyLen) {
            throw new IOException("响应字节数组长度不一致：" + totalBytes + " != " + bodyLen);
        }
        return body;
    }


    /**
     * long 转换为 byte数组
     *
     * @param n long
     * @return 长度为8的字节数组
     */
    public static byte[] long2buff(long n) {
        byte[] bs = new byte[8];
        bs[0] = (byte) ((n >> 56) & 0xFF);
        bs[1] = (byte) ((n >> 48) & 0xFF);
        bs[2] = (byte) ((n >> 40) & 0xFF);
        bs[3] = (byte) ((n >> 32) & 0xFF);
        bs[4] = (byte) ((n >> 24) & 0xFF);
        bs[5] = (byte) ((n >> 16) & 0xFF);
        bs[6] = (byte) ((n >> 8) & 0xFF);
        bs[7] = (byte) (n & 0xFF);

        return bs;
    }


    /**
     * byte数组 转换为 long
     *
     * @param bs     字节数组
     * @param offset 要解码的第一个字节的索引
     * @return long number
     */
    public static long buff2long(byte[] bs, int offset) {
        return (((long) (bs[offset] >= 0 ? bs[offset] : 256 + bs[offset])) << 56) |
                (((long) (bs[offset + 1] >= 0 ? bs[offset + 1] : 256 + bs[offset + 1])) << 48) |
                (((long) (bs[offset + 2] >= 0 ? bs[offset + 2] : 256 + bs[offset + 2])) << 40) |
                (((long) (bs[offset + 3] >= 0 ? bs[offset + 3] : 256 + bs[offset + 3])) << 32) |
                (((long) (bs[offset + 4] >= 0 ? bs[offset + 4] : 256 + bs[offset + 4])) << 24) |
                (((long) (bs[offset + 5] >= 0 ? bs[offset + 5] : 256 + bs[offset + 5])) << 16) |
                (((long) (bs[offset + 6] >= 0 ? bs[offset + 6] : 256 + bs[offset + 6])) << 8) |
                ((long) (bs[offset + 7] >= 0 ? bs[offset + 7] : 256 + bs[offset + 7]));
    }

    /**
     * byte数组 转换为 int
     *
     * @param bs     字节数组
     * @param offset 要解码的第一个字节的索引
     * @return int number
     */
    public static int buff2int(byte[] bs, int offset) {
        return ((bs[offset] >= 0 ? bs[offset] : 256 + bs[offset]) << 24) |
                ((bs[offset + 1] >= 0 ? bs[offset + 1] : 256 + bs[offset + 1]) << 16) |
                ((bs[offset + 2] >= 0 ? bs[offset + 2] : 256 + bs[offset + 2]) << 8) |
                (bs[offset + 3] >= 0 ? bs[offset + 3] : 256 + bs[offset + 3]);
    }

}

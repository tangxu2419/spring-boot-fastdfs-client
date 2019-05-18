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

package com.vcredit.framework.fastdfs.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangxu
 */
public class ProtoPackageUtil {

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
            throw new IOException("recv package size " + totalBytes + " != " + bodyLen);
        }
        return body;
    }


    /**
     * long convert to buff (big-endian)
     *
     * @param n long number
     * @return 8 bytes buff
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
     * buff convert to long
     *
     * @param bs     the buffer (big-endian)
     * @param offset the start position based 0
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

}

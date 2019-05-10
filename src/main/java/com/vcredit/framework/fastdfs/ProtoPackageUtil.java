package com.vcredit.framework.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.vcredit.framework.fastdfs.constants.Constants.*;

/**
 * @author tangxu
 * @date 2019/5/517:30
 */
public class ProtoPackageUtil {


    /**
     * 将String转换为byte
     *
     * @param value
     * @param charset
     * @return
     */
    public static byte[] objString2Byte(String value, Charset charset) {
        if (null == value) {
            return null;
        }
        // 获取byte
        return value.getBytes(charset);

    }

    /**
     * 将String转换为byte
     */
    public static byte[] objString2Byte(String value, int max, Charset charset) {
        byte[] fullContentBytes = new byte[max];
        // 填充默认值
        Arrays.fill(fullContentBytes, (byte) 0);
        if (null == value) {
            return fullContentBytes;
        }
        // 获取byte
        byte[] realContent = value.getBytes(charset);
        int length;
        if (realContent.length <= max) {
            length = realContent.length;
        } else {
            length = max;
        }
        // 复制数值
        System.arraycopy(realContent, 0, fullContentBytes, 0, length);
        return fullContentBytes;
    }

    /**
     * receive whole pack
     *
     * @param in            input stream
     * @param expectCmd     expect response command
     * @param expectBodyLen expect response package body length
     * @return RecvPackageInfo: errno and reponse body(byte buff)
     */
    public static RecvPackageInfo recvPackage(InputStream in, byte expectCmd, long expectBodyLen) throws IOException {
        RecvHeaderInfo header = recvHeader(in, expectCmd, expectBodyLen);
        if (header.errno != 0) {
            return new RecvPackageInfo(header.errno, null);
        }
        byte[] body = new byte[(int) header.bodyLen];
        int totalBytes = 0;
        int remainBytes = (int) header.bodyLen;
        int bytes;
        while (totalBytes < header.bodyLen) {
            if ((bytes = in.read(body, totalBytes, remainBytes)) < 0) {
                break;
            }
            totalBytes += bytes;
            remainBytes -= bytes;
        }
        if (totalBytes != header.bodyLen) {
            throw new IOException("recv package size " + totalBytes + " != " + header.bodyLen);
        }
        return new RecvPackageInfo((byte) 0, body);
    }


    /**
     * receive pack header
     *
     * @param in            input stream
     * @param expectCmd     expect response command
     * @param expectBodyLen expect response package body length
     * @return RecvHeaderInfo: errno and pkg body length
     */
    public static RecvHeaderInfo recvHeader(InputStream in, byte expectCmd, long expectBodyLen) throws IOException {
        byte[] header = new byte[FDFS_PROTO_PKG_LEN_SIZE + 2];
        int bytes;
        if ((bytes = in.read(header)) != header.length) {
            throw new IOException("recv package size " + bytes + " != " + header.length);
        }
        if (header[PROTO_HEADER_CMD_INDEX] != expectCmd) {
            throw new IOException("recv cmd: " + header[PROTO_HEADER_CMD_INDEX] + " is not correct, expect cmd: " + expectCmd);
        }
        if (header[PROTO_HEADER_STATUS_INDEX] != 0) {
            return new RecvHeaderInfo(header[PROTO_HEADER_STATUS_INDEX], 0);
        }
        long pkgLen = buff2long(header, 0);
        if (pkgLen < 0) {
            throw new IOException("recv body length: " + pkgLen + " < 0!");
        }
        if (expectBodyLen >= 0 && pkgLen != expectBodyLen) {
            throw new IOException("recv body length: " + pkgLen + " is not correct, expect length: " + expectBodyLen);
        }
        return new RecvHeaderInfo((byte) 0, pkgLen);
    }

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
     * pack header by FastDFS transfer protocol
     *
     * @param cmd    which command to send
     * @param pkgLen package body length
     * @param errno  status code, should be (byte)0
     * @return packed byte buffer
     */
    public static byte[] packHeader(byte cmd, long pkgLen, byte errno) {
        byte[] header = new byte[FDFS_PROTO_PKG_LEN_SIZE + 2];
        Arrays.fill(header, (byte) 0);
        byte[] hexLen = long2buff(pkgLen);
        System.arraycopy(hexLen, 0, header, 0, hexLen.length);
        header[PROTO_HEADER_CMD_INDEX] = cmd;
        header[PROTO_HEADER_STATUS_INDEX] = errno;
        return header;
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

    /**
     * receive package info
     */
    public static class RecvPackageInfo {
        public byte errno;
        public byte[] body;

        public RecvPackageInfo(byte errno, byte[] body) {
            this.errno = errno;
            this.body = body;
        }
    }

    /**
     * receive header info
     */
    public static class RecvHeaderInfo {
        public byte errno;
        public long bodyLen;

        public RecvHeaderInfo(byte errno, long bodyLen) {
            this.errno = errno;
            this.bodyLen = bodyLen;
        }

        @Override
        public String toString() {
            return "RecvHeaderInfo{" +
                    "errno=" + errno +
                    ", bodyLen=" + bodyLen +
                    '}';
        }
    }

}

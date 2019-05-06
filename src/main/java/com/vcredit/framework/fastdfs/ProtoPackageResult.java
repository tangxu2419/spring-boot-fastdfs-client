package com.vcredit.framework.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.vcredit.framework.fastdfs.constants.Constants.*;

/**
 * @author tangxu
 * @date 2019/5/517:30
 */
public class ProtoPackageResult {

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
    }

}

package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.ProtoPackageResult;

import java.util.Arrays;

import static com.vcredit.framework.fastdfs.ProtoPackageResult.long2buff;
import static com.vcredit.framework.fastdfs.constants.Constants.*;

/**
 * @author tangxu
 * @date 2019/5/614:19
 */
public class ProtoHead {

    private byte cmd;




    /**
     * pack header by FastDFS transfer protocol
     *
     * @param cmd    which command to send
     * @param pkgLen package body length
     * @param errno  status code, should be (byte)0
     * @return packed byte buffer
     */
    public static byte[] packHeader( long pkgLen, byte errno) {
        byte[] header = new byte[FDFS_PROTO_PKG_LEN_SIZE + 2];
        Arrays.fill(header, (byte) 0);
        byte[] hexLen = ProtoPackageResult.long2buff(pkgLen);
        System.arraycopy(hexLen, 0, header, 0, hexLen.length);
        header[PROTO_HEADER_CMD_INDEX] = cmd;
        header[PROTO_HEADER_STATUS_INDEX] = errno;
        return header;
    }

}

package com.vcredit.framework.fastdfs.proto;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author tangxu
 * @date 2019/5/614:18
 */
public abstract class FdfsRequest {

    /**
     * 报文头
     */
    protected ProtoHead head;


    /**
     * 参数字节
     */
    protected byte[] param;

    /**
     * 发送文件
     */
    protected InputStream inputFile;


    /**
     * 获取报文头
     *
     * @param charset
     * @return
     */
    public byte[] getHeadByte(Charset charset) {
        // 设置报文长度
        head.setContentLength(getBodyLength(charset));
        // 返回报文byte
        return head.packHeader();
    }

    /**
     * 打包参数
     *
     * @param charset
     * @return
     */
    public abstract byte[] encodeParam(Charset charset);

    public byte[] getParam() {
        return param;
    }

    /**
     * 获取参数域长度
     *
     * @return 参数域长度
     */
    protected long getBodyLength(Charset charset) {
        param = encodeParam(charset);
        return null == param ? 0 : param.length;
    }

    public ProtoHead getHead() {
        return head;
    }

    public InputStream getInputFile() {
        return inputFile;
    }

    public long getFileSize() {
        return 0;
    }


    protected byte[] encodeRequestParam(String param, int paramSize, Charset charset) {
        byte[] bParam = new byte[paramSize];
        byte[] bs = param.getBytes(charset);
        int groupLen;
        if (bs.length <= paramSize) {
            groupLen = bs.length;
        } else {
            groupLen = paramSize;
        }
        Arrays.fill(bParam, (byte) 0);
        System.arraycopy(bs, 0, bParam, 0, groupLen);
        return bParam;
    }


    protected byte[] byteMergerAll(byte[]... values) {
        int lengthByte = 0;
        for (byte[] value : values) {
            lengthByte += value.length;
        }
        byte[] allByte = new byte[lengthByte];
        int countLength = 0;
        for (byte[] b : values) {
            System.arraycopy(b, 0, allByte, countLength, b.length);
            countLength += b.length;
        }
        return allByte;
    }


}

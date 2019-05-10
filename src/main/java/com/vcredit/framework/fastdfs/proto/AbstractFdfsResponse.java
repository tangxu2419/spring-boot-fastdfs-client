package com.vcredit.framework.fastdfs.proto;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/814:07
 */
public abstract class AbstractFdfsResponse<T> {
    /**
     * 报文头
     */
    protected ProtoHead head;

    /**
     * 获取报文长度
     */
    protected long getContentLength() {
        return head.getContentLength();
    }


    /**
     * 解析反馈结果,head已经被解析过
     *
     * @param head    头信息对象
     * @param in      响应输入流
     * @param charset 编码
     * @return 响应对象
     * @throws Exception 异常
     */
    T decode(ProtoHead head, InputStream in, Charset charset) throws Exception {
        this.head = head;
        return decodeContent(in, charset);
    }


    /**
     * 解析反馈内容
     *
     * @param in      响应输入流
     * @param charset 编码
     * @return 响应对象
     * @throws Exception 异常
     */
    public abstract T decodeContent(InputStream in, Charset charset) throws Exception;

}

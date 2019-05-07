package com.vcredit.framework.fastdfs.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/710:54
 */
public interface Connection {

    /**
     * 关闭连接
     */
    void close();

    /**
     * 连接是否关闭
     *
     * @return
     */
    boolean isClosed();

    /**
     * 测试连接是否有效
     *
     * @return
     */
    boolean isValid();

    /**
     * 获取输出流
     *
     * @return
     * @throws IOException
     */
    public OutputStream getOutputStream() throws IOException;

    /**
     * 获取输入流
     *
     * @return 输入流
     * @throws IOException 获取输入流错误
     */
    public InputStream getInputStream() throws IOException;

    /**
     * 获取字符集
     *
     * @return 字符集
     */
    public Charset getCharset();


}

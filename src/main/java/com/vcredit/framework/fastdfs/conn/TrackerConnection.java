package com.vcredit.framework.fastdfs.conn;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1417:41
 */
public class TrackerConnection extends DefaultConnection {

    private InetSocketAddress address;

    public TrackerConnection(InetSocketAddress address, int soTimeout, int connectTimeout, Charset charset) {
        super(address, soTimeout, connectTimeout, charset);
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}

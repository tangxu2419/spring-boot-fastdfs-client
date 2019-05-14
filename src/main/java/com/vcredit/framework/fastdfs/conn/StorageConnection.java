package com.vcredit.framework.fastdfs.conn;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class StorageConnection extends DefaultConnection {

    private InetSocketAddress address;

    public StorageConnection(InetSocketAddress address, int soTimeout, int connectTimeout, Charset charset) {
        super(address, soTimeout, connectTimeout, charset);
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}

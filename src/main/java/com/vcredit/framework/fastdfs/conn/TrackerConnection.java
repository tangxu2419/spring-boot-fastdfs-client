package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author tangxu
 */
public class TrackerConnection implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(TrackerConnection.class);

    private final Socket socket;
    private final InetSocketAddress inetSocketAddress;
    private boolean inUse;


    TrackerConnection(InetSocketAddress address, int soTimeout, int connectTimeout, boolean inUse) throws IOException {
        socket = new Socket();
        socket.setSoTimeout(soTimeout);
        log.debug("connect to {} soTimeout={} connectTimeout={}", address, soTimeout, connectTimeout);
        socket.connect(address, connectTimeout);
        this.inetSocketAddress = address;
        this.inUse = inUse;
    }

    Socket getSocket() {
        return socket;
    }

    InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public OutputStream getOutputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getOutputStream();
    }

    public InputStream getIntputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getInputStream();
    }

    /**
     * 关闭连接
     */
    @Override
    public synchronized void close() {
        if (this.socket != null && !socket.isClosed()) {
            log.debug("disconnect from {}", socket);
            byte[] header = new byte[Constants.FDFS_PROTO_PKG_LEN_SIZE + 2];
            Arrays.fill(header, (byte) 0);
            byte[] hexLen = ProtoPackageUtil.long2buff(0);
            System.arraycopy(hexLen, 0, header, 0, hexLen.length);
            header[Constants.PROTO_HEADER_CMD_INDEX] = ProtocolCommand.FDFS_PROTO_CMD_QUIT;
            header[Constants.PROTO_HEADER_STATUS_INDEX] = (byte) 0;
            try {
                socket.getOutputStream().write(header);
                socket.close();
            } catch (IOException e) {
                log.error("close tracker connection error", e);
            } finally {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    log.error("close tracker connection error", ioe);
                }
            }
        }
        this.inUse = false;
    }

    /**
     * 校验连接是否可用
     *
     * @return
     */
    public boolean isValid() {
        if (this.socket != null && !socket.isClosed()) {
            log.debug("check connection status of {} ", this);
            try {
                byte[] header = new byte[Constants.FDFS_PROTO_PKG_LEN_SIZE + 2];
                Arrays.fill(header, (byte) 0);
                byte[] hexLen = ProtoPackageUtil.long2buff(0);
                System.arraycopy(hexLen, 0, header, 0, hexLen.length);
                header[Constants.PROTO_HEADER_CMD_INDEX] = ProtocolCommand.FDFS_PROTO_CMD_ACTIVE_TEST;
                header[Constants.PROTO_HEADER_STATUS_INDEX] = (byte) 0;
                socket.getOutputStream().write(header);
                if (socket.getInputStream().read(header) != header.length) {
                    return false;
                }
                return header[Constants.PROTO_HEADER_STATUS_INDEX] == 0;
            } catch (IOException e) {
                log.error("valid tracker connection error", e);
                return false;
            }
        }
        return false;
    }


    public boolean isInUse() {
        return false;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}

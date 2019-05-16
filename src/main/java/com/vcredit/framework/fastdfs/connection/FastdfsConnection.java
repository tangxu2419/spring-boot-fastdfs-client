package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * Fastdfs连接
 *
 * @author dongzhuming
 */
public class FastdfsConnection {

    private static final Logger log = LoggerFactory.getLogger(FastdfsConnection.class);

    /**
     * 封装socket
     */
    private Socket socket;

    private ConnectionInfo connectionInfo;

    public InetSocketAddress getInetSocketAddress() {
        return connectionInfo.getInetSocketAddress();
    }

    FastdfsConnection(ConnectionInfo connectionInfo, int soTimeout, int connectTimeout) throws IOException {
        socket = new Socket();
        socket.setSoTimeout(soTimeout);
        log.debug("connect to {} soTimeout={} connectTimeout={}", connectionInfo.getInetSocketAddress(), soTimeout, connectTimeout);
        socket.connect(connectionInfo.getInetSocketAddress());
        this.connectionInfo = connectionInfo;
    }

    ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
    }

    public OutputStream getOutputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        Assert.notNull(this.socket, "socket shouldn't be null");
        return this.socket.getInputStream();
    }

    /**
     * 关闭连接
     */
    synchronized void close() {
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
            } catch (IOException e) {
                log.error("close connection error", e);
            } finally {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    log.error("close connection error", ioe);
                }
            }
        }
    }

    /**
     * 校验连接是否可用
     *
     * @return
     */
    boolean isValid() {
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
                log.error("valid connection error", e);
                return false;
            }
        }
        return false;
    }


    public enum Type {
        /**
         * TRACKER
         */
        TRACKER,
        /**
         * STORAGE
         */
        STORAGE
    }

    static class ConnectionInfo {
        private Type type;
        private InetSocketAddress inetSocketAddress;

        ConnectionInfo() {
        }

        ConnectionInfo(Type type, InetSocketAddress inetSocketAddress) {
            this.type = type;
            this.inetSocketAddress = inetSocketAddress;
        }

        Type getType() {
            return type;
        }

        void setType(Type type) {
            this.type = type;
        }

        InetSocketAddress getInetSocketAddress() {
            return inetSocketAddress;
        }

        void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
            this.inetSocketAddress = inetSocketAddress;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ConnectionInfo) {
                return this.type == ((ConnectionInfo) obj).getType();
            } else {
                return super.equals(obj);
            }
        }
    }

}

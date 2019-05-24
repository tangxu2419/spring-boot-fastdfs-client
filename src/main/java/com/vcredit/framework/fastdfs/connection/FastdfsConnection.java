/*
 *    Copyright 2019 VCREDIT
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.vcredit.framework.fastdfs.connection;

import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;
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
     * @return boolean
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

    public static class ConnectionInfo {
        private Type type;
        private InetSocketAddress inetSocketAddress;

        public ConnectionInfo() {
        }

        ConnectionInfo(Type type, InetSocketAddress inetSocketAddress) {
            this.type = type;
            this.inetSocketAddress = inetSocketAddress;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public InetSocketAddress getInetSocketAddress() {
            return inetSocketAddress;
        }

        public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
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

        @Override
        public final int hashCode() {
            if (null != type && inetSocketAddress != null) {
                return type.hashCode() + inetSocketAddress.hashCode();
            } else {
                return super.hashCode();
            }
        }

        @Override
        public String toString() {
            return "ConnectionInfo{" +
                    "type=" + type +
                    ", inetSocketAddress=" + inetSocketAddress +
                    '}';
        }
    }

}

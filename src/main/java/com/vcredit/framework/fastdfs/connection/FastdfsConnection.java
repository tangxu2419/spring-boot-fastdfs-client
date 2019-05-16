package com.vcredit.framework.fastdfs.connection;

import java.net.InetSocketAddress;

/**
 * Fastdfs连接
 * @author dongzhuming
 */
public class FastdfsConnection {

    private ConnectionInfo connectionInfo;

    public InetSocketAddress getInetSocketAddress() {
        return connectionInfo.getInetSocketAddress();
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    FastdfsConnection(InetSocketAddress inetSocketAddress) {
        //TODO
    }

    public ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
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
            if(obj instanceof ConnectionInfo) {
                return this.type == ((ConnectionInfo) obj).getType();
            } else {
                return super.equals(obj);
            }
        }
    }

}

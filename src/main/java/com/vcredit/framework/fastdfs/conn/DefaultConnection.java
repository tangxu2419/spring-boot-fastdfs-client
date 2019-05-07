package com.vcredit.framework.fastdfs.conn;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.exception.FdfsConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author tangxu
 * @date 2019/5/710:57
 */
public class DefaultConnection implements Connection {


    private static final Logger log = LoggerFactory.getLogger(DefaultConnection.class);

    /**
     * 封装socket
     */
    private Socket socket;

    /**
     * 字符集
     */
    private Charset charset;


    public DefaultConnection(InetSocketAddress address, int soTimeout, int connectTimeout, Charset charset) {
        try {
            socket = new Socket();
            socket.setSoTimeout(soTimeout);
            log.debug("connect to {} soTimeout={} connectTimeout={}", address, soTimeout, connectTimeout);
            this.charset = charset;
            socket.connect(address, connectTimeout);
        } catch (IOException e) {
            throw new FdfsConnectException("can't create connection to" + address, e);
        }
    }

    @Override
    public void close() {
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
            log.error("close connection error", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioe) {
                    log.error("close connection error", ioe);
                }
            }
        }
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public boolean isValid() {
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

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}

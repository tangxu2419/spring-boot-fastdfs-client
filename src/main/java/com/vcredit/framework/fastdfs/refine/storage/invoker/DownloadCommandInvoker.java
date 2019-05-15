package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.storage.StorageDownloadRequest;
import com.vcredit.framework.fastdfs.refine.storage.AbstractStorageCommandInvoker;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1510:06
 */
public class DownloadCommandInvoker extends AbstractStorageCommandInvoker {


    public DownloadCommandInvoker(StorageCommand.Download command) {
        this.command = command;
        super.request = new StorageDownloadRequest(command.getGroupName(), command.getFileName(), command.getFileOffset(), command.getDownloadBytes());
    }

    @Override
    protected DownLoadResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        // 如果有内容
        if (head.getContentLength() > 0) {
            byte[] body = new byte[(int) head.getContentLength()];
            int totalBytes = 0;
            int remainBytes = (int) head.getContentLength();
            int bytes;
            while (totalBytes < head.getContentLength()) {
                if ((bytes = in.read(body, totalBytes, remainBytes)) < 0) {
                    break;
                }
                totalBytes += bytes;
                remainBytes -= bytes;
            }
            if (totalBytes != head.getContentLength()) {
                throw new IOException("recv package size " + totalBytes + " != " + head.getContentLength());
            }
            return new DownLoadResult(body);
        } else {
            return new DownLoadResult(new byte[0]);
        }
    }
}

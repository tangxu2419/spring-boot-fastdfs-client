package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.request.StorageDownloadRequest;
import com.vcredit.framework.fastdfs.command.storage.result.DownloadResult;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class DownloadCommandInvoker extends AbstractStorageCommandInvoker {


    public DownloadCommandInvoker(StorageCommand.Download command) {
        this.command = command;
        super.request = new StorageDownloadRequest(command.getGroupName(), command.getFileName(), command.getFileOffset(), command.getDownloadBytes());
    }

    @Override
    protected DownloadResult parseContent(InputStream in, ProtoHead head, Charset charset) throws InvokeCommandException, IOException {
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
                throw new InvokeCommandException("recv package size " + totalBytes + " != " + head.getContentLength());
            }
            return new DownloadResult(body);
        } else {
            return new DownloadResult(new byte[0]);
        }
    }
}

package com.vcredit.framework.fastdfs.refine.storage.invoker;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.MetaDataResult;
import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.proto.storage.StorageGetMetadataRequest;
import com.vcredit.framework.fastdfs.refine.MetaData;
import com.vcredit.framework.fastdfs.refine.storage.StorageCommand;
import com.vcredit.framework.fastdfs.refine.storage.AbstractStorageCommandInvoker;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1513:58
 */
public class GetMetaDataCommandInvoker extends AbstractStorageCommandInvoker {

    public GetMetaDataCommandInvoker(StorageCommand.GetMeta command) {
        this.command = command;
        super.request = new StorageGetMetadataRequest(command.getGroupName(), command.getFileName());
    }

    @Override
    protected OperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws Exception {
        if (head.getContentLength() > 0) {
            // 解析报文内容
            byte[] content = new byte[(int) head.getContentLength()];
            int contentSize = in.read(content);
            if (contentSize != head.getContentLength()) {
                throw new IOException("读取到的数据长度与协议长度不符");
            }
            MetaData metaData = new MetaData();
            String metaBuff = new String(content, charset);
            String[] rows = metaBuff.split(Constants.FDFS_RECORD_SEPERATOR);
            for (String row : rows) {
                String[] cols = row.split(Constants.FDFS_FIELD_SEPERATOR, 2);
                if (cols.length == 2) {
                    metaData.put(cols[0], cols[1]);
                }
            }
            return new MetaDataResult(metaData);
        }
        return new MetaDataResult();

    }
}

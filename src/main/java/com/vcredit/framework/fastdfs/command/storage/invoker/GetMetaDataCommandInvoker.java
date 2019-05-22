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

package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.request.StorageGetMetadataRequest;
import com.vcredit.framework.fastdfs.command.storage.result.MetaDataResult;
import com.vcredit.framework.fastdfs.constant.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class GetMetaDataCommandInvoker extends AbstractStorageCommandInvoker {

    public GetMetaDataCommandInvoker(StorageCommand.GetMeta command) {
        this.command = command;
        super.request = new StorageGetMetadataRequest(command.getGroupName(), command.getFileName());
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws IOException {
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
}

/*
 *    Copyright 2019 vcredit
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

package com.vcredit.framework.fastdfs.command.storage.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.nio.charset.Charset;

/**
 * 文件下载请求
 *
 * @author tangxu
 */
public class StorageDownloadRequest extends AbstractFdfsRequest {


    /**
     * 开始位置
     */
    private long fileOffset;
    /**
     * 读取文件长度
     */
    private long downloadBytes;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 文件路径
     */
    private String path;

    public StorageDownloadRequest(String groupName, String path, long fileOffset, long downloadBytes) {
        super();
        this.groupName = groupName;
        this.downloadBytes = downloadBytes;
        this.path = path;
        this.fileOffset = fileOffset;
        head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_DOWNLOAD_FILE);
    }

    /**
     * 打包参数
     *
     * @param charset 编码
     * @return byte[]
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bsOffset = ProtoPackageUtil.long2buff(fileOffset);
        byte[] bsDownBytes = ProtoPackageUtil.long2buff(downloadBytes);
        byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] bPath = path.getBytes(charset);
        return this.byteMergerAll(bsOffset, bsDownBytes, bGroupName, bPath);
    }

}

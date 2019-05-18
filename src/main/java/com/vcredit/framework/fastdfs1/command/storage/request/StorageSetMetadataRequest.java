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
import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class StorageSetMetadataRequest extends AbstractFdfsRequest {

    /**
     * 操作标记（重写/覆盖）
     */
    private byte opFlag;
    /**
     * 组名
     */
    private String groupName;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 元数据
     */
    private MetaData metaData;

    public StorageSetMetadataRequest(String groupName, String fileName, MetaData metaData, byte type) {
        super();
        Validate.notBlank(groupName, "groupName不能为空");
        Validate.notBlank(fileName, "fileName不能为空");
        Validate.notEmpty(metaData, "metaData不能为空");
        this.groupName = groupName;
        this.path = fileName;
        this.metaData = metaData;
        this.opFlag = type;
        head = new ProtoHead(ProtocolCommand.STORAGE_PROTO_CMD_SET_METADATA);
    }


    /**
     * 打包参数
     *
     * @param charset 编码
     * @return 请求参数
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] bopFlag = {opFlag};
        byte[] groupNameBytes = super.encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
        byte[] filenameBytes = path.getBytes(charset);
        byte[] metaDataBytes = this.packMetaData(metaData, charset);
        //////////////////////////
        byte[] fileNameSizeBytes = ProtoPackageUtil.long2buff(filenameBytes.length);
        byte[] metaDataSizeBytes = ProtoPackageUtil.long2buff(metaDataBytes.length);
        return super.byteMergerAll(fileNameSizeBytes, metaDataSizeBytes, bopFlag, groupNameBytes, filenameBytes, metaDataBytes);
    }

    /**
     * 将元数据映射为byte
     *
     * @param metadata 元数据
     * @param charset  编码
     * @return result
     */
    private byte[] packMetaData(MetaData metadata, Charset charset) {
        if (null == metadata || metadata.isEmpty()) {
            return new byte[0];
        }
        StringBuilder sb = new StringBuilder(32 * metadata.size());
        for (String key : metadata.keySet()) {
            sb.append(key).append(Constants.FDFS_FIELD_SEPERATOR).append(metadata.get(key));
            sb.append(Constants.FDFS_RECORD_SEPERATOR);
        }
        // 去除最后一个分隔符
        sb.delete(sb.length() - Constants.FDFS_RECORD_SEPERATOR.length(), sb.length());
        return sb.toString().getBytes(charset);
    }

}

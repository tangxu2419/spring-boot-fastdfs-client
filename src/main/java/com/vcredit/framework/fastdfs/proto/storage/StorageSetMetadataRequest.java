package com.vcredit.framework.fastdfs.proto.storage;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import com.vcredit.framework.fastdfs.refine.MetaData;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * @author tangxu
 * @date 2019/5/1011:13
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
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(fileName, "分组不能为空");
        Validate.notEmpty(metaData, "分组不能为空");
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

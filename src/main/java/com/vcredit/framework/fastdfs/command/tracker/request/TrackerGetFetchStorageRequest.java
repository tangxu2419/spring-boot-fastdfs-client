package com.vcredit.framework.fastdfs.command.tracker.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * 获取源服务器
 *
 * @author tangxu
 */
public class TrackerGetFetchStorageRequest extends AbstractFdfsRequest {

    /**
     * 组名
     */
    private String groupName;
    /**
     * 路径名
     */
    private String path;

    /**
     * 是否更新
     */
    private boolean toUpdate;

    public TrackerGetFetchStorageRequest(String groupName, String path, boolean toUpdate) {
        Validate.notBlank(groupName, "分组不能为空");
        Validate.notBlank(path, "文件路径不能为空");
        this.groupName = groupName;
        this.path = path;
        this.toUpdate = toUpdate;
        if (toUpdate) {
            head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_UPDATE);
        } else {
            head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_FETCH_ONE);
        }
    }

    /**
     * 打包参数
     *
     * @param charset 编码
     * @return byte[]
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        byte[] wholePkg;
        if (toUpdate) {
            byte[] bGroupName = encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
            byte[] bPath = path.getBytes(charset);
            wholePkg = this.byteMergerAll(bGroupName, bPath);
        } else {
            //TODO
            return null;
        }
        return wholePkg;
    }


}

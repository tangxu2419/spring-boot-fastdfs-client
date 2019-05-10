package com.vcredit.framework.fastdfs.proto.tracker.interactive;

import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.constants.ProtocolCommand;
import com.vcredit.framework.fastdfs.proto.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.proto.ProtoHead;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * 获取源服务器
 *
 * @author tangxu
 * @date 2019/5/917:18
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
     * @param charset
     * @return
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

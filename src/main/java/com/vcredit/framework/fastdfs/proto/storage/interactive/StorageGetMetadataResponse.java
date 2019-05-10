package com.vcredit.framework.fastdfs.proto.storage.interactive;

import com.vcredit.framework.fastdfs.MetaInfo;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.proto.FdfsResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tangxu
 * @date 2019/5/1011:44
 */
public class StorageGetMetadataResponse extends FdfsResponse<Set<MetaInfo>> {


    /**
     * 解析反馈内容
     *
     * @param in        响应流
     * @param charset   编码
     * @return          meta_data
     * @throws IOException  IO异常
     */

    @Override
    public Set<MetaInfo> decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        byte[] bytes = new byte[(int) getContentLength()];
        int contentSize = in.read(bytes);
        if (contentSize != getContentLength()) {
            throw new IOException("读取到的数据长度与协议长度不符");
        }
        return this.fromByte(bytes, charset);

    }

    /**
     * 将byte映射为对象
     *
     * @param content 文件字节
     * @param charset 编码
     * @return  mate_data
     */
    private Set<MetaInfo> fromByte(byte[] content, Charset charset) {
        Set<MetaInfo> mdSet = new HashSet<>();
        if (null == content) {
            return mdSet;
        }
        String metaBuff = new String(content, charset);
        String[] rows = metaBuff.split(Constants.FDFS_RECORD_SEPERATOR);
        for (String row : rows) {
            String[] cols = row.split(Constants.FDFS_FIELD_SEPERATOR, 2);
            MetaInfo md = new MetaInfo(cols[0]);
            if (cols.length == 2) {
                md.setValue(cols[1]);
            }
            mdSet.add(md);
        }
        return mdSet;
    }
}

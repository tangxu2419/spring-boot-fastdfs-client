package com.vcredit.framework.fastdfs.proto.mapper;

import com.vcredit.framework.fastdfs.constants.Constants;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tangxu
 * @date 2019/5/816:58
 */
public class MetadataMapper {
    private MetadataMapper() {
        // hide for utils
    }

    /**
     * 将元数据映射为byte
     *
     * @param metadataSet
     * @param charset
     * @return
     */
    public static byte[] toByte(Set<MetaData> metadataSet, Charset charset) {
        if (null == metadataSet || metadataSet.isEmpty()) {
            return new byte[0];
        }
        StringBuilder sb = new StringBuilder(32 * metadataSet.size());
        for (MetaData md : metadataSet) {
            sb.append(md.getName()).append(Constants.FDFS_FIELD_SEPERATOR).append(md.getValue());
            sb.append(Constants.FDFS_RECORD_SEPERATOR);
        }
        // 去除最后一个分隔符
        sb.delete(sb.length() - Constants.FDFS_RECORD_SEPERATOR.length(), sb.length());
        return sb.toString().getBytes(charset);
    }

    /**
     * 将byte映射为对象
     *
     * @param content
     * @param charset
     * @return
     */
    public static Set<MetaData> fromByte(byte[] content, Charset charset) {
        Set<MetaData> mdSet = new HashSet<MetaData>();
        if (null == content) {
            return mdSet;
        }
        String metaBuff = new String(content, charset);
        String[] rows = metaBuff.split(Constants.FDFS_RECORD_SEPERATOR);

        for (String row : rows) {
            String[] cols = row.split(Constants.FDFS_FIELD_SEPERATOR, 2);
            MetaData md = new MetaData(cols[0]);
            if (cols.length == 2) {
                md.setValue(cols[1]);
            }
            mdSet.add(md);
        }

        return mdSet;
    }

}

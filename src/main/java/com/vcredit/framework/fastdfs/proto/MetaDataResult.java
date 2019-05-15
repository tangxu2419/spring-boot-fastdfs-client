package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.refine.MetaData;

/**
 * @author tangxu
 * @date 2019/5/1514:02
 */
public class MetaDataResult extends OperationResult {

    private MetaData metaData;

    public MetaDataResult() {
    }

    public MetaDataResult(MetaData metaData) {
        this.metaData = metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public MetaData getMetaData() {
        return metaData;
    }
}

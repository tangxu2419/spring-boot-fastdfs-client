package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author tangxu
 * @date 2019/5/1514:02
 */
public class MetaDataResult extends BaseOperationResult {

    private MetaData metaData;

    public MetaDataResult(MetaData metaData) {
        this.metaData = metaData;
    }

    public MetaDataResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }

    public MetaData getMetaData() {
        return metaData;
    }
}

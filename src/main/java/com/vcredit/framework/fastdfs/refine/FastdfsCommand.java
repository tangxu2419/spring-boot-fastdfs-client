package com.vcredit.framework.fastdfs.refine;

import com.vcredit.framework.fastdfs.proto.OperationResult;

/**
 *
 */
public interface FastdfsCommand {

    /**
     * 执行Fastdfs指令
     * @return
     */
    OperationResult execute();
}

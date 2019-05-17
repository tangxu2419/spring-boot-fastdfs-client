package com.vcredit.framework.fastdfs.command;

import com.vcredit.framework.fastdfs.exception.InvokeCommandException;

/**
 * Fastdfs指令类
 *
 * @author dongzhuming
 */
public interface FastdfsCommand {

    /**
     * 执行Fastdfs指令
     *
     * @return 执行结果
     * @throws InvokeCommandException 指令调用异常
     */
    BaseOperationResult execute() throws InvokeCommandException;
}

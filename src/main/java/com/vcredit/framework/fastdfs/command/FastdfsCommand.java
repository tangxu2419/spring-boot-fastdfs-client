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

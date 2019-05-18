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

package com.vcredit.framework.fastdfs.command.storage.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.exception.CommandStatusException;

/**
 * @author Dong Zhuming
 */
public class DeleteResult extends BaseOperationResult {

    public DeleteResult(CommandStatusException exception) {
        setErrorCode(exception.getErrorCode());
        setErrorMessage(exception.getMessage());
    }

    public DeleteResult(int errorCode) {
        super(errorCode);
    }

    @Override
    public String toString() {
        return "DeleteResult{" +
                "errorCode=" + getErrorCode() +
                '}';
    }
}

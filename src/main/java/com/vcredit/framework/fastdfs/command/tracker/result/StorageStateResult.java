/*
 *   Copyright 2019 VCREDIT
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
 *
 */

package com.vcredit.framework.fastdfs.command.tracker.result;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;

import java.util.List;

/**
 * @author tangxu
 */
public class StorageStateResult extends BaseOperationResult {

    private List<StorageState> list;

    public StorageStateResult(List<StorageState> list) {
        this.list = list;
    }

    public List<StorageState> getList() {
        return list;
    }

    public void setList(List<StorageState> list) {
        this.list = list;
    }
}
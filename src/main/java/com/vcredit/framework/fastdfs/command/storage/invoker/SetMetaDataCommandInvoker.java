/*
 *    Copyright 2019 VCREDIT
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

package com.vcredit.framework.fastdfs.command.storage.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.storage.StorageCommand;
import com.vcredit.framework.fastdfs.command.storage.request.StorageSetMetadataRequest;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class SetMetaDataCommandInvoker extends AbstractStorageCommandInvoker {

    public SetMetaDataCommandInvoker(StorageCommand.SetMeta command) {
        this.command = command;
        super.request = new StorageSetMetadataRequest(command.getGroupName(), command.getFileName(), command.getMetaData(), command.getOpFlag());
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) {
        //TODO
        return null;
    }

}

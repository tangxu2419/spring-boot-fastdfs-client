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

package com.vcredit.framework.fastdfs.command.tracker.request;

import com.vcredit.framework.fastdfs.command.AbstractFdfsRequest;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;

import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class TrackerGetStorageRequest extends AbstractFdfsRequest {

    public TrackerGetStorageRequest() {
        super();
        this.head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE);
    }

    @Override
    public byte[] encodeParam(Charset charset) {
        return null;
    }
}

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
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.constant.ProtocolCommand;
import org.apache.commons.lang3.Validate;

import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class TrackerGetStorageWithGroupRequest extends AbstractFdfsRequest {

    private final String groupName;

    public TrackerGetStorageWithGroupRequest(String groupName) {
        Validate.notBlank(groupName, "分組信息不能為空");
        this.groupName = groupName;
        this.head = new ProtoHead(ProtocolCommand.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE);
    }

    /**
     * 打包参数
     *
     * @param charset 编码
     * @return byte[]
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        return encodeRequestParam(groupName, Constants.FDFS_GROUP_NAME_MAX_LEN, charset);
    }

}

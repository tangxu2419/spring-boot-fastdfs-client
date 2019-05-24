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

package com.vcredit.framework.fastdfs.command.tracker.invoker;

import com.vcredit.framework.fastdfs.command.BaseOperationResult;
import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.command.tracker.request.TrackerGetStorageRequest;
import com.vcredit.framework.fastdfs.command.tracker.request.TrackerGetStorageWithGroupRequest;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNodeResult;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.vcredit.framework.fastdfs.constant.Constants.*;

/**
 * @author tangxu
 */
public class GetStorageCommandInvoker extends AbstractTrackerCommandInvoker {

    public GetStorageCommandInvoker(TrackerCommand.GetStorage command) {
        this.command = command;
        if (StringUtils.isBlank(command.getGroupName())) {
            super.request = new TrackerGetStorageRequest();
        } else {
            super.request = new TrackerGetStorageWithGroupRequest(command.getGroupName());
        }
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws IOException {
        byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
        String groupName = new String(body, 0, FDFS_GROUP_NAME_MAX_LEN, charset).trim();
        String ip = new String(body, FDFS_GROUP_NAME_MAX_LEN, FDFS_IPADDR_SIZE - 1, charset).trim();
        int port = (int) ProtoPackageUtil.buff2long(body, FDFS_GROUP_NAME_MAX_LEN + FDFS_IPADDR_SIZE - 1);
        byte storePath = body[TRACKER_QUERY_STORAGE_FETCH_BODY_LEN];
        return new StorageNodeResult(new StorageNode(groupName, ip, port, storePath));
    }
}

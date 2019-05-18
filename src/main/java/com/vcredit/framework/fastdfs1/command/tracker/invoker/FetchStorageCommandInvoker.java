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

package com.vcredit.framework.fastdfs.command.tracker.invoker;

import com.vcredit.framework.fastdfs.command.ProtoHead;
import com.vcredit.framework.fastdfs.command.tracker.TrackerCommand;
import com.vcredit.framework.fastdfs.command.tracker.request.TrackerGetFetchStorageRequest;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageNode;
import com.vcredit.framework.fastdfs.command.tracker.result.TrackerResult;
import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author tangxu
 */
public class FetchStorageCommandInvoker extends AbstractTrackerCommandInvoker {

    public FetchStorageCommandInvoker(TrackerCommand.FetchStorage command) {
        this.command = command;
        super.request = new TrackerGetFetchStorageRequest(command.getGroupName(), command.getFileName(), command.isToUpdate());
    }

    @Override
    protected TrackerResult parseContent(InputStream in, ProtoHead head, Charset charset) throws IOException {
        byte[] body = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
        String groupName = new String(body, 0, Constants.FDFS_GROUP_NAME_MAX_LEN, charset).trim();
        String ip = new String(body, Constants.FDFS_GROUP_NAME_MAX_LEN, Constants.FDFS_IPADDR_SIZE - 1, charset).trim();
        int port = (int) ProtoPackageUtil.buff2long(body, Constants.FDFS_GROUP_NAME_MAX_LEN + Constants.FDFS_IPADDR_SIZE - 1);
        return new TrackerResult(new StorageNode(groupName, ip, port));

    }
}

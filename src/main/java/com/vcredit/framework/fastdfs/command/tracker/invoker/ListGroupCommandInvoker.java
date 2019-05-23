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
import com.vcredit.framework.fastdfs.command.tracker.request.TrackerListGroupsRequest;
import com.vcredit.framework.fastdfs.command.tracker.result.GroupState;
import com.vcredit.framework.fastdfs.command.tracker.result.GroupStateResult;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author tangxu
 */
public class ListGroupCommandInvoker extends AbstractTrackerCommandInvoker {

    public ListGroupCommandInvoker(TrackerCommand.ListGroups command) {
        this.command = command;
        super.request = new TrackerListGroupsRequest();
    }

    @Override
    protected BaseOperationResult parseContent(InputStream in, ProtoHead head, Charset charset) throws IOException, InvokeCommandException {
        byte[] content = ProtoPackageUtil.recvResponseBody(in, head.getContentLength());
        List<GroupState> list = packageList(GroupState.class, content, charset);
        return new GroupStateResult(list);
    }

}

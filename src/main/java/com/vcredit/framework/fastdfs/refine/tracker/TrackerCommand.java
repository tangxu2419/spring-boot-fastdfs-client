package com.vcredit.framework.fastdfs.refine.tracker;

import com.vcredit.framework.fastdfs.proto.OperationResult;
import com.vcredit.framework.fastdfs.refine.AbstractCommandInvoker;
import com.vcredit.framework.fastdfs.refine.FastdfsCommand;

/**
 * Tracker指令
 */
public class TrackerCommand implements FastdfsCommand {

    /**
     * 执行Fastdfs指令
     *
     * @return 操作结果
     */
    @Override
    public OperationResult execute() {
        return AbstractCommandInvoker.prepare(this).action();
    }


    public static class GetStorage extends TrackerCommand {
        /**
         * 组名
         */
        private String groupName;

        public static TrackerCommand.GetStorage create() {
            return new TrackerCommand.GetStorage();
        }

        public TrackerCommand.GetStorage groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }
    }

    public static class FetchStorage extends TrackerCommand {
        /**
         * 组名
         */
        private String groupName;
        /**
         * 文件路径
         */
        private String fileName;
        /**
         * 是否更新
         */
        private boolean toUpdate;

        public static TrackerCommand.FetchStorage create() {
            return new TrackerCommand.FetchStorage();
        }

        public TrackerCommand.FetchStorage groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }


        public TrackerCommand.FetchStorage fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public TrackerCommand.FetchStorage toUpdate(boolean toUpdate) {
            this.toUpdate = toUpdate;
            return this;
        }

        public String getGroupName() {
            return groupName;
        }

        public String getFileName() {
            return fileName;
        }

        public boolean isToUpdate() {
            return toUpdate;
        }
    }
}

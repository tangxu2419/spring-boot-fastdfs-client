package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.storage.result.DeleteResult;
import com.vcredit.framework.fastdfs.command.storage.result.DownloadResult;
import com.vcredit.framework.fastdfs.command.storage.result.MetaDataResult;
import com.vcredit.framework.fastdfs.command.storage.result.UploadResult;
import com.vcredit.framework.fastdfs.command.tracker.result.GroupState;
import com.vcredit.framework.fastdfs.command.tracker.result.StorageState;
import com.vcredit.framework.fastdfs.exception.FastdfsException;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import com.vcredit.framework.fastdfs.service.FastdfsClient;
import com.vcredit.framework.fastdfs.service.TrackerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

/**
 * @author Dong Zhuming
 */
@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastdfsClientTest {

    @Autowired
    private FastdfsClient fastdfsClient;
    @Autowired
    private TrackerClient trackerClient;

    @Test
    public void testListGroups() throws InvokeCommandException {
        List<GroupState> list = trackerClient.listGroups();
        if (null == list || list.isEmpty()) {
            System.out.println("未获取到任何组信息");
            return;
        }
        for (GroupState groupState : list) {
            System.out.println(groupState.toString());
        }
    }

    @Test
    public void testListStorage() throws InvokeCommandException {
//        List<StorageState> list = trackerClient.listStorage("group1");
        List<StorageState> list = trackerClient.listStorage("group1","10.138.30.42");
        if (null == list || list.isEmpty()) {
            System.out.println("未获取到任何组信息");
            return;
        }
        for (StorageState storageState : list) {
            System.out.println(storageState.toString());
        }
    }

    @Test
    public void fileUploadAndDownloadAndDelete() throws Exception {
        for (int i = 0; i < 1; i++) {
            System.out.println("==============[文件上传]=================");
            MetaData metaData = new MetaData();
//            metaData.put("desc", "this is a metaData");
            File file = File.createTempFile("test", "sql");
//            File file = new File("C:\\Users\\tangx\\Desktop\\project_info\\note\\MongodbCluster复制集迁移步骤.docx");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("Test Upload file").flush();
            UploadResult result = fastdfsClient.upload("contract1", new FileInputStream(file), file.length(), "", null);
            System.out.println("上传文件信息：" + result.toString());
            if (result.getErrorCode() != 0) {
                System.out.println("==============[文件上传-失败]=================");
                return;
            }
            System.out.println("==============[MetaData 获取]=================");
            MetaDataResult metadata = fastdfsClient.getMetaData(result.getGroupName(), result.getFileName());
            if (metadata.getErrorCode() != 0) {
                System.out.println("==============[MetaData 获取-失败]=================");
                return;
            }
            for (String key : metadata.getMetaData().keySet()) {
                System.out.println(metadata.getMetaData().get(key));
            }
            System.out.println("==============[文件下载]=================");
            this.testDownload2(result.getGroupName(), result.getFileName(), "d://download_2.sql");
            System.out.println("==============[文件删除]=================");
            DeleteResult deleteResult = fastdfsClient.delete(result.getGroupName(), result.getFileName());
            if (deleteResult.getErrorCode() != 0) {
                System.out.println("==============[文件下载-失败]=================");
            }
            System.out.println("=======================================================================================================================");
        }
    }


    @Test
    public void test2() throws FastdfsException {
        String groupName = "group2";
        String fileName = "M00/00/00/Cooel1zStHOAM-wjABQAMJXj3jc615.asdasda";
        DownloadResult download = fastdfsClient.download(groupName, fileName);
        if (download.getErrorCode() != 0) {
            System.out.println(download.getErrorCode() + download.getErrorMessage());
            System.err.println("==============[文件下载-失败]=================");
        } else {
            byte[] fileBytes = download.getFileBytes();
            if (fileBytes == null) {
                System.err.println("下载文件字节数组为空");
            } else {
                System.out.println("下载字节长度:" + fileBytes.length);
            }
        }
    }

    public void testDownload2(String groupName, String fileName, String localFileName) throws Exception {
        DownloadResult download = fastdfsClient.download(groupName, fileName);
        if (download.getErrorCode() != 0) {
            System.out.println("==============[文件下载-失败]=================");
            return;
        }
        if (download.getFileBytes() == null) {
            System.out.println("文件内容为空");
            return;
        }
        FileOutputStream outputStream = new FileOutputStream(new File(localFileName));
        outputStream.write(download.getFileBytes());
        outputStream.flush();
        outputStream.close();
    }


}
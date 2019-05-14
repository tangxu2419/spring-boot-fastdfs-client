package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.MetaInfo;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.refine.FastdfsClient2;
import com.vcredit.framework.fastdfs.service.FastdfsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dong Zhuming
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastdfsClientTest {

    @Autowired
    private FastdfsClient fastdfsClient;

    @Autowired
    private FastdfsClient2 fastdfsClient2;

    @Test
    public void test() throws IOException {
        File file = File.createTempFile("test", "sql");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append("Test Upload file").flush();
        UploadResult result = fastdfsClient2.upload(new FileInputStream(file), file.length(), "sql", null);
        System.out.println("上传文件信息：" + result.toString());
    }


    @Test
    public void testFastDFS() throws Exception {
        System.out.println("==============[文件上传]=================");
        UploadResult result = this.testUploadByMateData();
        System.out.println("上传文件信息：" + result.toString());
        System.out.println("==============[MetaData 获取]=================");
        Set<MetaInfo> metaInfos = this.testGetMetadata(result.getGroupName(), result.getFileName());
        for (MetaInfo metaInfo : metaInfos) {
            System.out.println(metaInfo.toString());
        }
        System.out.println("==============[文件下载]=================");
        this.testDownload(result.getGroupName(), result.getFileName(), "d://download_1.sql");
        System.out.println("==============[文件删除]=================");
        this.testDelete(result.getGroupName(), result.getFileName());
    }

    private UploadResult testUploadByMateData() throws IOException {
        Set<MetaInfo> metaInfo = new HashSet<>();
        metaInfo.add(new MetaInfo("desc", "this is a metaData"));
        File file = File.createTempFile("test", "sql");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append("Test Upload file").flush();
        return fastdfsClient.upload(new FileInputStream(file), file.length(), "sql", metaInfo);
    }


    private Set<MetaInfo> testGetMetadata(String groupName, String fileName) {
        return fastdfsClient.getMetadata(groupName, fileName);
    }



    public UploadResult testUpload() throws IOException {
        File file = File.createTempFile("test", "sql");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append("Test Upload file").flush();
        return fastdfsClient2.upload(new FileInputStream(file), file.length(), "sql",null);
    }

    public void testDownload(String groupName, String fileName, String localFileName) throws Exception {
        DownLoadResult download = fastdfsClient.download(fileName, groupName);
        if (download.getFileBytes() == null) {
            System.out.println("文件内容为空");
            return;
        }
        FileOutputStream outputStream = new FileOutputStream(new File(localFileName));
        outputStream.write(download.getFileBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void testDelete(String groupName, String fileName) {
        DeleteResult delete = fastdfsClient.delete(fileName, groupName);
        System.out.println(delete.toString());
    }


}
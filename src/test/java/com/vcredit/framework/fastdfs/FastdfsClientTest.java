package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.DownLoadResult;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.service.FastdfsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Dong Zhuming
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastdfsClientTest {

    @Autowired
    private FastdfsClient fastdfsClient;


    @Test
    public void testFastDFS() throws Exception {
        System.out.println("==============[文件上传]=================");
        UploadResult result = this.testUpload();
        System.out.println("上传文件信息：" + result.toString());
        System.out.println("==============[文件下载]=================");
        this.testDownload(result.getGroupName(), result.getFileName(), "d://download_1.sql");
        System.out.println("==============[文件删除]=================");
        this.testDelete(result.getGroupName(), result.getFileName());
    }

    public UploadResult testUpload() throws IOException {
        File file = File.createTempFile("test", "sql");
        return fastdfsClient.upload(new FileInputStream(file), file.length(), "sql");
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
package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.command.MetaData;
import com.vcredit.framework.fastdfs.command.storage.result.DownloadResult;
import com.vcredit.framework.fastdfs.command.storage.result.MetaDataResult;
import com.vcredit.framework.fastdfs.command.storage.result.UploadResult;
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

/**
 * @author Dong Zhuming
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FastdfsClientTest {

    @Autowired
    private FastdfsClient fastdfsClient;

    @Test
    public void test() throws Exception {
        System.out.println("==============[文件上传]=================");
        MetaData metaData = new MetaData();
        metaData.put("desc","this is a metaData");
        File file = File.createTempFile("test", "sql");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.append("Test Upload file").flush();
        UploadResult result = fastdfsClient.upload(null, new FileInputStream(file), file.length(), "sql", metaData);
        System.out.println("上传文件信息：" + result.toString());
        System.out.println("==============[MetaData 获取]=================");
        MetaDataResult metadata = fastdfsClient.getMetaData(result.getGroupName(), result.getFileName());
        for (String key : metadata.getMetaData().keySet()) {
            System.out.println(metadata.getMetaData().get(key));
        }
        System.out.println("==============[文件下载]=================");
        this.testDownload2(result.getGroupName(), result.getFileName(), "d://download_2.sql");
        System.out.println("==============[文件删除]=================");
        fastdfsClient.delete(result.getGroupName(), result.getFileName());
    }

    public void testDownload2(String groupName, String fileName, String localFileName) throws Exception {
        DownloadResult download = fastdfsClient.download(groupName, fileName);
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
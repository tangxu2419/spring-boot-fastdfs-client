package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import com.vcredit.framework.fastdfs.proto.DeleteResult;
import com.vcredit.framework.fastdfs.proto.UploadResult;
import com.vcredit.framework.fastdfs.service.FastdfsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;

/**
 * @author Dong Zhuming
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastdfsClientTest {

    @Autowired
    private FastdfsClient fastdfsClient;

    @Autowired
    private FastdfsProperties fastdfsProperties;

    @Test
    public void testUpload() throws IOException, ExecutionException, InterruptedException {
        fastdfsProperties.toString();
        File file = new File("d:\\script.sql");
        Future<UploadResult> result = fastdfsClient.upload(new FileInputStream(file), file.length(), "sql", null);
        UploadResult result1 = result.get();
        System.out.println("path:" + result1.getGroupName() + "," + result1.getFileName());
        assertTrue(true);
    }


    @Test
    public void testDownload() throws Exception {
        String groupName = "group3";
        String fileName = "M00/00/00/Cooel1zRTHmADsMcAAAD_0MWjj0837.sql";
        ByteArrayOutputStream download = (ByteArrayOutputStream) fastdfsClient.download(fileName, groupName);
        byte[] bytes = download.toByteArray();
        FileOutputStream outputStream = new FileOutputStream(new File("d://download_1.sql"));
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }


    @Test
    public void testDelete() throws Exception {
        String groupName = "group3";
        String fileName = "M00/00/00/Cooel1zRTHmADsMcAAAD_0MWjj0837.sql";
        Future<DeleteResult> delete = fastdfsClient.delete(fileName, groupName);
        DeleteResult deleteResult = delete.get();
        System.out.println(deleteResult.toString());
    }


}
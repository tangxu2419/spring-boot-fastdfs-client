package com.vcredit.framework.fastdfs;

import com.vcredit.framework.fastdfs.config.FastdfsProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
    public void testUpload() throws IOException {
        fastdfsProperties.toString();
        File file = new File("d:\\script.sql");
        fastdfsClient.upload(new FileInputStream(file), file.length(), "sql", null);
        assertTrue(true);
    }
}
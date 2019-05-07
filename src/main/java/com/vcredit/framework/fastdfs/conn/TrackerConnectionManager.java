package com.vcredit.framework.fastdfs.conn;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangxu
 * @date 2019/5/715:55
 */
@Component
public class TrackerConnectionManager extends ConnectionManager {

    /**
     *
     */
    private List<String> trackerList = new ArrayList<String>();

    public TrackerConnectionManager() {
    }

    public TrackerConnectionManager(FdfsConnectionPool pool) {
        super(pool);
    }
}

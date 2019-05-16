package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.exception.FdfsServerException;

import java.util.ArrayList;

/**
 * @author tangxu
 * @date 2019/5/815:55
 */
public class CircularList<E> extends ArrayList<E> {

    private int index = -1;


    /**
     * 下一个元素
     */
    public E next() {
        check();
        synchronized (this) {
            index++;
            if (index >= this.size()) {
                index = 0;
            }
            return this.get(index);
        }
    }


    private void check() {
        if (this.size() == 0) {
            throw new FdfsServerException("空的列表，无法获取元素");
        }
    }
}

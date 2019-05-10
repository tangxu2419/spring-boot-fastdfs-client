package com.vcredit.framework.fastdfs.proto.mapper;

/**
 * @author tangxu
 * @date 2019/5/816:48
 */
public enum DynamicFieldType {
    /**
     * 非动态属性
     */
    NULL,
    /**
     * 剩余的所有Byte
     */
    allRestByte,
    /**
     * 可空的属性
     */
    nullable,
    /**
     * 文件元数据Set
     */
    metadata
}

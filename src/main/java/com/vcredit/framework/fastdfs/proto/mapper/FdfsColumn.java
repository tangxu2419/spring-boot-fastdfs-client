package com.vcredit.framework.fastdfs.proto.mapper;

import java.lang.annotation.*;

/**
 * 传输参数定义标签
 * @author tangxu
 * @date 2019/5/816:49
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FdfsColumn {
    /**
     * 映射顺序(从0开始)
     */
    int index() default 0;

    /**
     * String最大值
     */
    int max() default 0;

    /**
     * 动态属性
     */
    DynamicFieldType dynamicField() default DynamicFieldType.NULL;
}

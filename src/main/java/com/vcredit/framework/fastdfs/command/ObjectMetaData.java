/*
 *   Copyright 2019 VCREDIT
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.vcredit.framework.fastdfs.command;

import com.vcredit.framework.fastdfs.constant.Constants;
import com.vcredit.framework.fastdfs.exception.InvokeCommandException;
import com.vcredit.framework.fastdfs.util.ProtoPackageUtil;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 映射对象元数据
 * 映射对象元数据必须由{@code @FdfsField}注解
 *
 * @author tangxu
 */
public class ObjectMetaData<T> {

    /**
     * 对象类名
     */
    private Class<T> genericType;

    /**
     * 所有属性占用总长度
     */
    private int fieldsTotalSize = 0;

    /**
     * 属性映射集合
     */
    private List<FieldMetaData> fieldList;

    /**
     * 映射对象元数据构造函数
     *
     * @param genericType 映射类型
     */
    public ObjectMetaData(Class<T> genericType) throws InvokeCommandException {
        this.genericType = genericType;
        // 获得对象类名
        this.fieldList = praseFieldList(genericType);
    }

    /**
     * 解析映射对象数据映射情况
     */
    private List<FieldMetaData> praseFieldList(Class genericType) throws InvokeCommandException {
        List<Field> fields = this.getOrderedField(genericType);
        List<FieldMetaData> fieldMetaDataList = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FdfsField.class)) {
                FieldMetaData fieldMetaData = new FieldMetaData(field, fieldsTotalSize);
                fieldMetaDataList.add(fieldMetaData);
                // 计算偏移量
                fieldsTotalSize += fieldMetaData.getSize();
            }
        }
        return fieldMetaDataList;
    }


    /**
     * 对此类中所有声明字段按照注解{@code FdfsField} order()排序
     *
     * @param genericType 类型
     * @return 排序后Field集合
     */
    private List<Field> getOrderedField(Class genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            if (field.getAnnotation(FdfsField.class) != null) {
                fieldList.add(field);
            }
        }
        fieldList.sort(Comparator.comparingInt(m -> m.getAnnotation(FdfsField.class).order()));
        return fieldList;
    }

    /**
     * 属性映射对象
     */
    public static class FieldMetaData {
        /**
         * field
         */
        private Field field;
        /**
         * 单元长度
         */
        private int size;
        /**
         * 偏移量
         */
        private int offset;

        FieldMetaData(Field field, int offset) throws InvokeCommandException {
            this.field = field;
            this.size = this.getFieldSize(field);
            this.offset = offset;
            int maxLength = field.getAnnotation(FdfsField.class).maxLength();
            if (maxLength > 0 && this.size > maxLength) {
                this.size = maxLength;
            }
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        /**
         * 获取属性字节长度
         */
        private int getFieldSize(Field field) throws InvokeCommandException {
            if (String.class == field.getType()) {
                return field.getAnnotation(FdfsField.class).maxLength();
            } else if (long.class == field.getType()) {
                return Constants.FDFS_PROTO_PKG_LEN_SIZE;
            } else if (int.class == field.getType()) {
                return Constants.FDFS_PROTO_PKG_LEN_SIZE;
            } else if (Date.class == field.getType()) {
                return Constants.FDFS_PROTO_PKG_LEN_SIZE;
            } else if (byte.class == field.getType()) {
                return 1;
            } else if (boolean.class == field.getType()) {
                return 1;
            } else if (Set.class == field.getType()) {
                return 0;
            }
            throw new InvokeCommandException(field.getName() + "获取Field大小时未识别的FdfsField类型" + field.getType());
        }

        /**
         * 获取属性值值
         *
         * @param content 字节缓冲区
         */
        public Object getFieldValue(byte[] content, Charset charset) throws InvokeCommandException {
            if (String.class == field.getType()) {
                if (!field.getAnnotation(FdfsField.class).dynameicFieldType().equals(DynamicFieldType.NOT)) {
                    return (new String(content, offset, content.length - offset, charset)).trim();
                }
                return (new String(content, offset, size, charset)).trim();
            } else if (long.class == field.getType()) {
                return ProtoPackageUtil.buff2long(content, offset);
            } else if (int.class == field.getType()) {
                return (int) ProtoPackageUtil.buff2long(content, offset);
            } else if (java.util.Date.class == field.getType()) {
                return new Date(ProtoPackageUtil.buff2long(content, offset) * 1000);
            } else if (byte.class == field.getType()) {
                return content[offset];
            } else if (boolean.class == field.getType()) {
                return content[offset] != 0;
            }
            throw new InvokeCommandException(field.getName() + "获取值时未识别的FdfsColumn类型" + field.getType());
        }

    }

    public Class<T> getGenericType() {
        return genericType;
    }

    public int getFieldsTotalSize() {
        return fieldsTotalSize;
    }


    public List<FieldMetaData> getFieldList() {
        return fieldList;
    }

}

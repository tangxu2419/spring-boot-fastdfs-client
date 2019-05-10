package com.vcredit.framework.fastdfs.proto;

import com.vcredit.framework.fastdfs.ProtoPackageUtil;
import com.vcredit.framework.fastdfs.constants.Constants;
import com.vcredit.framework.fastdfs.exception.FdfsColumnMapException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.core.GenericTypeResolver;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.Date;

import static com.vcredit.framework.fastdfs.constants.Constants.FDFS_GROUP_NAME_MAX_LEN;

/**
 * @author tangxu
 * @date 2019/5/814:07
 */
public abstract class FdfsResponse<T> {
    /**
     * 报文头
     */
    protected ProtoHead head;

    /**
     * 返回值类型
     */
    protected final Class<T> genericType;

    /**
     * 获取报文长度
     */
    protected long getContentLength() {
        return head.getContentLength();
    }

    /**
     * 构造函数
     */
    @SuppressWarnings("unchecked")
    public FdfsResponse() {
        super();
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), FdfsResponse.class);
    }


    /**
     * 解析反馈结果,head已经被解析过
     *
     * @param head
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public T decode(ProtoHead head, InputStream in, Charset charset) throws Exception {
        this.head = head;
        return decodeContent(in, charset);
    }


    /**
     * 解析反馈内容
     *
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public T decodeContent(InputStream in, Charset charset) throws Exception {
        // 如果有内容
        if (getContentLength() > 0) {
            byte[] bytes = new byte[(int) getContentLength()];
            int contentSize = in.read(bytes);
            // 获取数据
            if (contentSize != getContentLength()) {
                throw new IOException("读取到的数据长度与协议长度不符");
            }
            T obj = genericType.getDeclaredConstructor().newInstance();
            Field[] fields = genericType.getDeclaredFields();
            for (Field field : fields) {
                BeanUtils.setProperty(obj, field.getName(), this.getValue(field, bytes, charset));
            }
            return obj;
        }
        return null;
    }


    /**
     * 获取值
     *
     * @param bs
     * @return
     */
    public Object getValue(Field field, byte[] bs, Charset charset) {
//        if (String.class == field.getType()) {
//            if (true) {
//                return (new String(bs, offsize, bs.length - offsize, charset)).trim();
//            }
//            return (new String(bs, offsize, size, charset)).trim();
//        } else if (long.class == field.getType()) {
//            return ProtoPackageUtil.buff2long(bs, offsize);
//        } else if (int.class == field.getType()) {
//            return (int) ProtoPackageUtil.buff2long(bs, offsize);
//        } else if (java.util.Date.class == field.getType()) {
//            return new Date(ProtoPackageUtil.buff2long(bs, offsize) * 1000);
//        } else if (byte.class == field.getType()) {
//            return bs[offsize];
//        } else if (boolean.class == field.getType()) {
//            return bs[offsize] != 0;
//        }
        throw new FdfsColumnMapException(field.getName() + "获取值时未识别的FdfsColumn类型" + field.getType());
    }


}

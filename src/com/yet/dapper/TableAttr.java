package com.yet.dapper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017-04-24.
 * 实体类注解，Table：表名称，PrimaryKey：主键名称，Identity：是否自增
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableAttr {
    String TableName();
    String KeyName() default ""; //为空表示此表没有主键
    boolean IsIdentity() default false;
}

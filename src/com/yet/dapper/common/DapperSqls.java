package com.yet.dapper.common;

import java.util.List;

/**
 * Created by Administrator on 2017-04-24.
 */
public class DapperSqls {

    public String TableName;

    public String KeyName;
    public boolean IsIdentity;

    public Class<?> KeyType;

    public List<String> AllFieldList; //所有列
    public List<String> ExceptKeyFieldList;//除主键列
    public String AllFields; //所有列逗号分隔

    public String InsertSql;
    public String InsertIdentitySql;
    public String GetByIdSql;
    public String GetByIdsSql;
    public String GetAllSql;
    public String DeleteByIdSql;
    public String DeleteByIdsSql;
    public String DeleteAllSql;
}

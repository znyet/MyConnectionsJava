package com.yet.dapper.common;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.yet.dapper.Igore;
import com.yet.dapper.TableAttr;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017-04-24.
 */
public class DapperCommon {

    public static DapperSqls CreateSqls(Class<?> type) {
        DapperSqls sqls = new DapperSqls();
        TableAttr attr = type.getAnnotation(TableAttr.class);
        if (attr != null) {
            sqls.TableName = attr.TableName();
            sqls.KeyName = attr.KeyName();
            sqls.IsIdentity = attr.IsIdentity();

            List<String> AllFieldList = new ArrayList<>(); //所有字段
            List<String> ExceptKeyFieldList = new ArrayList<>(); //去除主键的所有字段

            Field[] fields = type.getDeclaredFields(); //获取所有字段
            for (Field field : fields) {
                Igore igore = field.getAnnotation(Igore.class);
                if (igore == null) { //去除忽略列
                    String name = field.getName();
                    AllFieldList.add(name);
                    if (!name.equals(sqls.KeyName)) {
                        ExceptKeyFieldList.add(name);
                    } else {
                        sqls.KeyType = field.getType();
                    }
                }
            }

            sqls.AllFieldList = AllFieldList;
            sqls.ExceptKeyFieldList = ExceptKeyFieldList;
        }
        return sqls;
    }

    /// 关键字处理如 name,sex 变成 `name`,`sex`
    public static String GetFieldsStr(List<String> fieldList, String leftChar, String rightChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldList.size(); i++) {
            sb.append(leftChar);
            sb.append(fieldList.get(i));
            sb.append(rightChar);
            if (i < fieldList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /// 关键字处理name sex变成 `name` = ?,`sex`=?
    public static String GetFieldsEqStr(List<String> fieldList, String leftChar, String rightChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldList.size(); i++) {
            sb.append(leftChar);
            sb.append(fieldList.get(i));
            sb.append(rightChar);
            sb.append("=");
            sb.append("?");
            if (i < fieldList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /// 关键字处理name sex变成 ?,?
    public static <T> String GetFieldsAtStr(List<T> fieldList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldList.size(); i++) {
            sb.append("?");
            if (i < fieldList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /// 关键字处理name sex变成 ?,?
    public static String GetFieldsAtStr(Object[] params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            sb.append("?");
            if (i < params.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

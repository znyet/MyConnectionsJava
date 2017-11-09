package model;

import com.yet.dapper.TableAttr;

import java.util.Date;
//==============================================================================================
// Author: yet
// CreateDate: 2017-10-11 04:57:08
// Descript: 模板生成属性，对应[people]表字段。
//
//==============================================================================================

/// <summary>
///
/// </summary>
@TableAttr(TableName = "people", KeyName = "id", IsIdentity = true)
public class peopleT {
    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.Int
    /// 数据长度:0
    /// 允许空值:False
    /// </summary>
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int val) {
        id = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.VarChar
    /// 数据长度:765
    /// 允许空值:True
    /// </summary>
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String val) {
        name = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.Int
    /// 数据长度:0
    /// 允许空值:True
    /// </summary>
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int val) {
        score = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.DateTime
    /// 数据长度:0
    /// 允许空值:True
    /// </summary>
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date val) {
        time = val;
    }


}
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
@TableAttr(TableName = "people", KeyName = "Id", IsIdentity = true)
public class peopleT {
    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.Int
    /// 数据长度:0
    /// 允许空值:False
    /// </summary>
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int val) {
        Id = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.VarChar
    /// 数据长度:765
    /// 允许空值:True
    /// </summary>
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String val) {
        Name = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.Int
    /// 数据长度:0
    /// 允许空值:True
    /// </summary>
    private int Score;

    public int getScore() {
        return Score;
    }

    public void setScore(int val) {
        Score = val;
    }


    /// <summary>
    /// 字段描述:
    /// 数据类型:SqlDbType.DateTime
    /// 数据长度:0
    /// 允许空值:True
    /// </summary>
    private Date Time;

    public Date getTime() {
        return Time;
    }

    public void setTime(Date val) {
        Time = val;
    }


}
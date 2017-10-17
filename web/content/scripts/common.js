/**
 * Created by Administrator on 2017-04-20.
 */
function loading() {
    layer.wait("请稍等")
}

function Add() {
    return "添加数据";
}

function Search() {
    var key = $("#keyword").val();
    if(key)
        alert("true");
    else
        alert("false");
}
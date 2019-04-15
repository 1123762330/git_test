<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
</head>
<body>
<h3 align="center">查询所有的用户信息<span id="time1"></span></h3>
<div align="center">
    <a href="#" onclick="searchPoolDay()">矿池开启天数</a>
    <a href="#" onclick="searchPoolHour()">矿池在线时长</a>
    <a href="#" onclick="searchUserDay()">用户在线天数</a>
    <a href="#" onclick="searchUsersHour()">用户在线时长</a>
    <a href="#" onclick="searchWorkersDay()">矿机在线天数</a>
    <a href="#" onclick="searchWorkersHour()">矿机在线时长</a>
</div>
<p></p>
<table border="1" bordercolor="#ccc" cellspacing="0" cellpadding="0" align="center" id="table"></table>
<script>
    //查询用户列表
    $(document).ready(function () {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findUsers",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var worksData = works.userlist;//json里面的userlist
                    //user用户数据展示
                    var user_lis = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">用户id</th>\n' +
                        ' <th width="70px"> 用户名</th>\n' +
                        ' <th width="70px"> 用户密码</th>\n' +
                        ' <th width="70px"> 邮箱</th>\n' +
                        ' </tr>';
                    user_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < worksData.length; i++) {
                        var workData = worksData[i];
                        var shuju = '<tr>\n' +
                            '<th>' + workData.id + '</th>' +
                            '<th>' + workData.username + '</th>' +
                            '<th>' + workData.pass + '</th>' +
                            '<th>' + workData.email + '</th>' +
                            '</tr>';
                        user_lis += shuju;//将数据都添加到列表中
                    }


                    $("#table").html(user_lis);//将用户数据列表添加到页面上
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    });

    //查找矿池在线天数
    function searchPoolDay() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findPool",
            "data": $("#formid").serialize(),
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var poolData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var pool_lis = "";
                    var table = ' <tr>\n' +
                        ' <th width="120px">矿机在线天数</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 幸运值</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    pool_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < poolData.length; i++) {
                        var pool = poolData[i];
                        var acceptDayData = getTimeData(pool.shareAccept);
                        var rejectDayData = getTimeData(pool.shareReject);
                        var shuju = '<tr>\n' +
                            '<th>' + pool.day + '</th>' +
                            '<th>' + acceptDayData + '</th>' +
                            '<th>' + rejectDayData + '</th>' +
                            '<th>' + pool.rejectRate + '</th>' +
                            '<th>' + pool.score + '</th>' +
                            '<th>' + pool.earn + '</th>' +
                            '<th>' + pool.lucky + '</th>' +
                            '<th>' + pool.createdAt + '</th>' +
                            '<th>' + pool.updatedAt + '</th>' +
                            '</tr>';
                        pool_lis += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(pool_lis);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //统计矿池在线时长
    function searchPoolHour() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findPoolHour",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var poolData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var pool_lis = "";
                    var table = ' <tr>\n' +
                        ' <th width="120px">矿机在线时长</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    pool_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < poolData.length; i++) {
                        var pool = poolData[i];
                        var acceptHourData = getHourTimeData(pool.shareAccept);
                        var rejectHourData = getHourTimeData(pool.shareReject);
                        var shuju = '<tr>\n' +
                            '<th>' + pool.hour + '</th>' +
                            '<th>' + acceptHourData + '</th>' +
                            '<th>' + rejectHourData + '</th>' +
                            '<th>' + pool.rejectRate + '</th>' +
                            '<th>' + pool.score + '</th>' +
                            '<th>' + pool.earn + '</th>' +
                            '<th>' + pool.createdAt + '</th>' +
                            '<th>' + pool.updatedAt + '</th>' +
                            '</tr>';
                        pool_lis += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(pool_lis);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //统计用户在线天数
    function searchUserDay() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findUserDay",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var userData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var user_list = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">用户ID</th>\n' +
                        ' <th width="70px">用户天数</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    user_list += table;//将第一行标题追加到页面上

                    for (var i = 0; i < userData.length; i++) {
                        var user = userData[i];
                        var shuju = '<tr>\n' +
                            '<th>' + user.puid + '</th>' +
                            '<th>' + user.day + '</th>' +
                            '<th>' + getTimeData(user.shareAccept) + '</th>' +
                            '<th>' + getTimeData(user.shareReject) + '</th>' +
                            '<th>' + user.rejectRate + '</th>' +
                            '<th>' + user.score + '</th>' +
                            '<th>' + user.earn + '</th>' +
                            '<th>' + renderTime(user.createdAt) + '</th>' +
                            '<th>' + renderTime(user.updatedAt) + '</th>' +
                            '</tr>';
                        user_list += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(user_list);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //统计用户在线小时
    function searchUsersHour() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findUsersHour",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var userHourData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var user_list = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">用户ID</th>\n' +
                        ' <th width="70px">用户天数</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    user_list += table;//将第一行标题追加到页面上

                    for (var i = 0; i < userHourData.length; i++) {
                        var user = userHourData[i];
                        var acceptHourData = getHourTimeData(user.shareAccept);
                        var rejectHourData = getHourTimeData(user.shareReject);
                        var shuju = '<tr>\n' +
                            '<th>' + user.puid + '</th>' +
                            '<th>' + user.hour + '</th>' +
                            '<th>' + acceptHourData + '</th>' +
                            '<th>' + rejectHourData + '</th>' +
                            '<th>' + user.rejectRate + '</th>' +
                            '<th>' + user.score + '</th>' +
                            '<th>' + user.earn + '</th>' +
                            '<th>' + user.createdAt + '</th>' +
                            '<th>' + user.updatedAt + '</th>' +
                            '</tr>';
                        user_list += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(user_list);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //统计矿机在线天数
    function searchWorkersDay() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findWorkersDay",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var workersDayData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var list = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">用户ID</th>\n' +
                        ' <th width="70px">矿机ID</th>\n' +
                        ' <th width="70px">用户天数</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    list += table;//将第一行标题追加到页面上

                    for (var i = 0; i < workersDayData.length; i++) {
                        var workersDay = workersDayData[i];
                        var acceptDayData = getTimeData(workersDay.shareAccept);
                        var rejectDayData = getTimeData(workersDay.shareReject);
                        var shuju = '<tr>\n' +
                            '<th>' + workersDay.puid + '</th>' +
                            '<th>' + workersDay.workerId + '</th>' +
                            '<th>' + workersDay.day + '</th>' +
                            '<th>' + acceptDayData + '</th>' +
                            '<th>' + rejectDayData + '</th>' +
                            '<th>' + workersDay.rejectRate + '</th>' +
                            '<th>' + workersDay.score + '</th>' +
                            '<th>' + workersDay.earn + '</th>' +
                            '<th>' + workersDay.createdAt + '</th>' +
                            '<th>' + workersDay.updatedAt + '</th>' +
                            '</tr>';
                        list += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(list);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //统计矿机在线小时
    function searchWorkersHour() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findWorkersHour",
            "data": '',
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var workersHourData = works.userlist;//json里面的userlist
                    //矿池数据展示
                    var list = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">用户ID</th>\n' +
                        ' <th width="70px">矿机ID</th>\n' +
                        ' <th width="70px">用户小时</th>\n' +
                        ' <th width="70px"> 接收数据</th>\n' +
                        ' <th width="70px"> 超时数据</th>\n' +
                        ' <th width="70px"> 超时率</th>\n' +
                        ' <th width="120px"> 等级</th>\n' +
                        ' <th width="120px"> 利润</th>\n' +
                        ' <th width="120px"> 创建时间</th>\n' +
                        ' <th width="120px"> 修改时间</th>\n' +
                        ' </tr>';
                    list += table;//将第一行标题追加到页面上

                    for (var i = 0; i < workersHourData.length; i++) {
                        var workersHour = workersHourData[i];
                        var acceptHourData = getHourTimeData(workersHour.shareAccept);
                        var rejectHourData = getHourTimeData(workersHour.shareReject);
                        var shuju = '<tr>\n' +
                            '<th>' + workersHour.puid + '</th>' +
                            '<th>' + workersHour.workerId + '</th>' +
                            '<th>' + workersHour.hour + '</th>' +
                            '<th>' + acceptHourData + '</th>' +
                            '<th>' + rejectHourData + '</th>' +
                            '<th>' + workersHour.rejectRate + '</th>' +
                            '<th>' + workersHour.score + '</th>' +
                            '<th>' + workersHour.earn + '</th>' +
                            '<th>' + workersHour.createdAt + '</th>' +
                            '<th>' + workersHour.updatedAt + '</th>' +
                            '</tr>';
                        list += shuju;//将数据都添加到列表中
                    }
                    //将数据列表添加到页面上
                    $("#table").html(list);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        });
    }

    //Hour转换方法
    function getHourTimeData(dataTime) {
        if (dataTime < 1024) {
            return dataTime.toFixed(2) + "B";
        } else if (dataTime >= 1024) {//KB转换
            var KB = ( (dataTime * (2 ^ 32)) / 1024);
            if (KB >= 1024) {//MB转换
                var MB = KB / 1024;
                if (MB >= 1024) {//GB转换
                    var GB = MB / 1024;
                    if (GB >= 1024) {//TB转换
                        var TB = GB / 1024;
                        return TB.toFixed(2) + "TB";
                    }
                    return GB.toFixed(2) + "GB";
                }
                return MB.toFixed(2) + "MB";
            }
            return KB.toFixed(2) + "KB";
        }
    }

    //Day转换方法
    function getTimeData(dataTime) {
        var dayTimeData = dataTime / 24 / 3600;
        if (dayTimeData < 1024) {
            return dayTimeData.toFixed(2) + "B";
        } else if (dayTimeData >= 1024) {//KB转换
            var KB = ( (dayTimeData * (2 ^ 32)) / 1024);
            if (KB >= 1024) {//MB转换
                var MB = KB / 1024;
                if (MB >= 1024) {//GB转换
                    var GB = MB / 1024;
                    if (GB >= 1024) {//TB转换
                        var TB = GB / 1024;
                        return TB.toFixed(2) + "TB";
                    }
                    return GB.toFixed(2) + "GB";
                }
                return MB.toFixed(2) + "MB";
            }
            return KB.toFixed(2) + "KB";
        }
    }

    //特殊时间格式转换
    function renderTime(date) {
        var dataTime = new Date(date).toJSON();
        return new Date(+new Date(dataTime) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    }
</script>
</body>
</html>

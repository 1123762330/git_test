<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户列表展示</title>
    <script src="js/jquery.min.js"></script>
    <script src="js/jquery-getUrlParam.js"></script>
</head>
<body>
<h3 align="center">查询用户名下的所有矿机信息<span id="time1"></span></h3>
<table border="1" bordercolor="#ccc" cellspacing="0" cellpadding="0" align="center" id="table"></table>


<script>
    //执行数据列表展示方法
    $(function () {
        var puid = $.getUrlParam("puid");
        search(puid);
    });

    //查找用户名下的矿机信息
    function search(puid) {
        $.ajax({
            "url": "${pageContext.request.contextPath}/findWorkerList?puid=" + puid,
            "data": $("#formid").serialize(),
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var works = json.data;//后台返回到前台的数据
                    var worksData = works.userlist;//json里面的userlist
                    //user用户数据展示
                    var user_lis = "";
                    var table = ' <tr>\n' +
                        ' <th width="70px">矿机ID</th>\n' +
                        ' <th width="70px"> 用户ID</th>\n' +
                        ' <th width="70px"> 组ID</th>\n' +
                        ' <th width="70px"> 矿机名</th>\n' +
                        ' <th width="120px"> 连接1m</th>\n' +
                        ' <th width="120px"> 连接5m</th>\n' +
                        ' <th width="120px"> 连接15m</th>\n' +
                        ' <th width="120px"> 超时15m</th>\n' +
                        ' <th width="120px"> 连接1小时</th>\n' +
                        ' <th width="120px"> 超时1小时</th>\n' +
                        ' <th width="70px"> 连接次数</th>\n' +
                        ' <th width="120px"> 最后登录的ip</th>\n' +
                        ' <th width="120px"> 超时百分比</th>\n' +
                        ' </tr>';
                    user_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < worksData.length; i++) {
                        var workData = worksData[i];
                        //时间换算  Math.round进行四舍五入
                        var accept1Data = getTimeData(workData.accept_1m / 60);
                        var accept5Data = getTimeData(workData.accept_5m / 300);
                        var accept15Data = getTimeData(workData.accept_15m / 900);
                        var reject15Data = getTimeData(workData.reject_15m / 900);
                        var accept1hData = getTimeData(workData.accept_1h / 3600);
                        var reject1hData = getTimeData(workData.reject_1h / 3600);

                        //计算百分比
                        var acceptTimeData = workData.accept_1h / 3600;
                        var rejectTimeData = Math.round(workData.reject_1h / 3600);
                        var overtime = getPercent(acceptTimeData, rejectTimeData);//调用计算百分比方法
                        var percent = (overtime * 100).toFixed(2);

                        var shuju = '<tr>\n' +
                            '<th>' + workData.worker_id + '</th>' +
                            '<th>' + workData.puid + '</th>' +
                            '<th>' + workData.group_id + '</th>' +
                            '<th>' + workData.worker_name + '</th>' +
                            '<th>' + accept1Data + '</th>' +
                            '<th>' + accept5Data + '</th>' +
                            '<th>' + accept15Data + '</th>' +
                            '<th>' + reject15Data + '</th>' +
                            '<th>' + accept1hData + '</th>' +
                            '<th>' + reject1hData + '</th>' +
                            '<th>' + workData.accept_count + '</th>' +
                            '<th>' + workData.last_share_ip + '</th>' +
                            '<th>' + percent + "%" + '</th>' +
                            '</tr>';
                        user_lis += shuju;//将数据都添加到列表中
                    }
                    //将用户数据列表添加到页面上
                    $("#table").html(user_lis);
                    //将程序总耗时添加到页面上
                    $("#time1").html("<span style='margin-left: 30px' id='times'>总共耗时" + works.time + "毫秒.</span>");
                } else {
                    //如果json查询有异常,返回消息
                    alert(json.message);
                }
            }
        })

        //KB转换
        function getTimeData(time) {
            if (time < 1024) {
                return time.toFixed(2) + "B";
            } else if(time >= 1024) {//KB转换
                var KB = ( (time * (2^32)) / 1024);
                if (KB >= 1024) {//MB转换
                    var MB = KB / 1024;
                    if (MB >= 1024) {//GB转换
                        var GB = MB /1024;
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

        //计算百分比
        function getPercent(acceptTimeData, rejectTimeData) {
            if (acceptTimeData > 1024 && rejectTimeData > 1024) {//都大于1024
                var accept = ( (acceptTimeData * (2^32) ) / 1024);//接受一小时的数据
                var reject = ( (rejectTimeData * (2^32) ) / 1024);//超时1小时的数据
                return reject / (accept + reject);//计算百分比
            } else if (acceptTimeData < 1024 && rejectTimeData < 1024) {//都小于1024
                return rejectTimeData / (acceptTimeData + rejectTimeData);
            } else if (0 < acceptTimeData < 1024) {//接受大于1024
                var accept = ( (acceptTimeData * (2^32) ) / 1024);//接受一小时的数据
                return rejectTimeData / (accept + rejectTimeData);//计算百分比
            } else if (0 < rejectTimeData < 1024) {//超时大于1024
                var reject = ( (rejectTimeData * (2^32) ) / 1024);//超时1小时的数据
                return reject / (acceptTimeData + reject);//计算百分比
            }
        }

    }
</script>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
</head>
<body>
<h3 align="center">查询用户名下的所有矿机信息</h3>
<div align="center">
    <form class="form-inline" action="${pageContext.request.contextPath}/accounts/findUserList" method="POST"
          id="formid">
        <div class="form-group">
            <label for="exampleInputName2">请输入用户名</label>
            <input type="text" name="username" class="form-control" id="exampleInputName2">
            <button type="button" class="btn btn-default" onclick="search()" id="btn">
                查询
            </button>
            <span style="margin-left: 30px" id="time1"></span>
        </div>
    </form>
</div>
<table border="1" bordercolor="#ccc" cellspacing="0" cellpadding="0" align="center" id="table"></table>


<script>
    function search() {
        $.ajax({
            "url": "${pageContext.request.contextPath}/accounts/findUserList",
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
                                    ' <th width="70px"> 连接1m</th>\n' +
                                    ' <th width="70px"> 连接5m</th>\n' +
                                    ' <th width="75px"> 连接15m</th>\n' +
                                    ' <th width="75px"> 超时15m</th>\n' +
                                    ' <th width="80px"> 连接1小时</th>\n' +
                                    ' <th width="80px"> 超时1小时</th>\n' +
                                    ' <th width="70px"> 连接次数</th>\n' +
                                    ' <th width="120px"> 最后登录的ip</th>\n' +
                                ' </tr>';
                    user_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < worksData.length; i++) {
                        var workData = worksData[i];
                        var shuju = '<tr>\n' +
                            '<th>' + workData.worker_id + '</th>' +
                            '<th>' + workData.puid + '</th>' +
                            '<th>' + workData.group_id + '</th>' +
                            '<th>' + workData.worker_name + '</th>' +
                            '<th>' + workData.accept_1m + '</th>' +
                            '<th>' + workData.accept_5m + '</th>' +
                            '<th>' + workData.accept_15m + '</th>' +
                            '<th>' + workData.reject_15m + '</th>' +
                            '<th>' + workData.accept_1h + '</th>' +
                            '<th>' + workData.reject_1h + '</th>' +
                            '<th>' + workData.accept_count + '</th>' +
                            '<th>' + workData.last_share_ip + '</th>' +
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
    }
</script>
</body>
</html>

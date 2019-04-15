<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
</head>
<body>
<h3 align="center">查询所有的用户信息</h3>
<table border="1" bordercolor="#ccc" cellspacing="0" cellpadding="0" align="center" id="table"></table>


<script>
        $.ajax({
            "url": "${pageContext.request.contextPath}/accounts/findUsers",
            "data": $("#formid").serialize(),
            "type": "GET",
            "dataType": "json",
            "success": function (json) {
                if (json.state == 200) {
                    var worksData = json.data;//后台返回到前台的数据
                    //user用户数据展示
                    var user_lis = "";
                    var table = ' <tr>\n' +
                                    ' <th width="70px">用户id</th>\n' +
                                    ' <th width="70px"> 用户名</th>\n' +
                                    ' <th width="70px"> 用户密码</th>\n' +
                                    ' <th width="70px"> 邮箱</th>\n' +
                                    ' <th width="70px"> 操作</th>\n' +
                                ' </tr>';
                    user_lis += table;//将第一行标题追加到页面上

                    for (var i = 0; i < worksData.length; i++) {
                        var workData = worksData[i];
                        var shuju = '<tr>\n' +
                            '<th>' + workData.id + '</th>' +
                            '<th>' + workData.username + '</th>' +
                            '<th>' + workData.pass + '</th>' +
                            '<th>' + workData.email + '</th>' +
                            '<th>'+"<a href='SerachWorkerList.jsp?puid="+workData.id+"'>查看详情</a>"+'</th>' +
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
        })
</script>
</body>
</html>

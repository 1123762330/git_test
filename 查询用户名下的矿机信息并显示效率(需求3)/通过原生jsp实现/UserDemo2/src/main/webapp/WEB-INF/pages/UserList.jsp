<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</head>
<body>
    <h3 align="center">查询所有的帐户</h3>
    <div align="center">
    <form class="form-inline" action="${pageContext.request.contextPath}/accounts/findUserList" method="get">
        <div class="form-group">
            <label for="exampleInputName2">请输入用户名</label>
            <input type="text" name="username" class="form-control" id="exampleInputName2" >
            <button type="submit" class="btn btn-default">
                查询
            </button>
            <span style="margin-left: 30px">总共耗时 <span>${time}</span> 毫秒.</span>
        </div>
    </form>
    </div>

    <table class="list" border="1" bordercolor="#ccc" cellspacing="0" cellpadding="0" align="center">
        <tr>
            <th> 矿机ID </th>
            <th> 用户ID </th>
            <th> 组ID </th>
            <th> 矿机名 </th>
            <th> 连接1m </th>
            <th> 连接5m </th>
            <th> 连接15m </th>
            <th> 超时15m </th>
            <th> 连接1小时 </th>
            <th> 超时1小时 </th>
            <th> 连接次数 </th>
            <th> 最后登录的ip </th>
        </tr>

        <c:forEach items="${list}" var="works">
            <tr>
                <td>${works.worker_id}</td>
                <td>${works.puid}</td>
                <td>${works.group_id}</td>
                <td>${works.worker_name}</td>
                <td>${works.accept_1m}</td>
                <td>${works.accept_5m}</td>
                <td>${works.accept_15m}</td>
                <td>${works.reject_15m}</td>
                <td>${works.accept_1h}</td>
                <td>${works.reject_1h}</td>
                <td>${works.accept_count}</td>
                <td>${works.last_share_ip}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

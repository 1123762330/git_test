/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-03-25 06:45:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class SerachWorkerList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>用户列表展示</title>\r\n");
      out.write("    <script src=\"js/jquery.min.js\"></script>\r\n");
      out.write("    <script src=\"js/jquery-getUrlParam.js\"></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<h3 align=\"center\">查询用户名下的所有矿机信息<span id=\"time1\"></span></h3>\r\n");
      out.write("<table border=\"1\" bordercolor=\"#ccc\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" id=\"table\"></table>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("    //执行数据列表展示方法\r\n");
      out.write("    $(function () {\r\n");
      out.write("        var puid = $.getUrlParam(\"puid\");\r\n");
      out.write("        search(puid);\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    //查找用户名下的矿机信息\r\n");
      out.write("    function search(puid) {\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            \"url\": \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/accounts/findWorkerList?puid=\" + puid,\r\n");
      out.write("            \"data\": $(\"#formid\").serialize(),\r\n");
      out.write("            \"type\": \"GET\",\r\n");
      out.write("            \"dataType\": \"json\",\r\n");
      out.write("            \"success\": function (json) {\r\n");
      out.write("                if (json.state == 200) {\r\n");
      out.write("                    var works = json.data;//后台返回到前台的数据\r\n");
      out.write("                    var worksData = works.userlist;//json里面的userlist\r\n");
      out.write("                    //user用户数据展示\r\n");
      out.write("                    var user_lis = \"\";\r\n");
      out.write("                    var table = ' <tr>\\n' +\r\n");
      out.write("                        ' <th width=\"70px\">矿机ID</th>\\n' +\r\n");
      out.write("                        ' <th width=\"70px\"> 用户ID</th>\\n' +\r\n");
      out.write("                        ' <th width=\"70px\"> 组ID</th>\\n' +\r\n");
      out.write("                        ' <th width=\"70px\"> 矿机名</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 连接1m</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 连接5m</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 连接15m</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 超时15m</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 连接1小时</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 超时1小时</th>\\n' +\r\n");
      out.write("                        ' <th width=\"70px\"> 连接次数</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 最后登录的ip</th>\\n' +\r\n");
      out.write("                        ' <th width=\"120px\"> 超时百分比</th>\\n' +\r\n");
      out.write("                        ' </tr>';\r\n");
      out.write("                    user_lis += table;//将第一行标题追加到页面上\r\n");
      out.write("\r\n");
      out.write("                    for (var i = 0; i < worksData.length; i++) {\r\n");
      out.write("                        var workData = worksData[i];\r\n");
      out.write("                        //时间换算  Math.round进行四舍五入\r\n");
      out.write("                        var accept1Data = getTimeData(workData.accept_1m / 60);\r\n");
      out.write("                        var accept5Data = getTimeData(workData.accept_5m / 300);\r\n");
      out.write("                        var accept15Data = getTimeData(workData.accept_15m / 900);\r\n");
      out.write("                        var reject15Data = getTimeData(workData.reject_15m / 900);\r\n");
      out.write("                        var accept1hData = getTimeData(workData.accept_1h / 3600);\r\n");
      out.write("                        var reject1hData = getTimeData(workData.reject_1h / 3600);\r\n");
      out.write("\r\n");
      out.write("                        //计算百分比\r\n");
      out.write("                        var acceptTimeData = workData.accept_1h / 3600;\r\n");
      out.write("                        var rejectTimeData = Math.round(workData.reject_1h / 3600);\r\n");
      out.write("                        var overtime = getPercent(acceptTimeData, rejectTimeData);//调用计算百分比方法\r\n");
      out.write("                        var percent = (overtime * 100).toFixed(2);\r\n");
      out.write("\r\n");
      out.write("                        var shuju = '<tr>\\n' +\r\n");
      out.write("                            '<th>' + workData.worker_id + '</th>' +\r\n");
      out.write("                            '<th>' + workData.puid + '</th>' +\r\n");
      out.write("                            '<th>' + workData.group_id + '</th>' +\r\n");
      out.write("                            '<th>' + workData.worker_name + '</th>' +\r\n");
      out.write("                            '<th>' + accept1Data + '</th>' +\r\n");
      out.write("                            '<th>' + accept5Data + '</th>' +\r\n");
      out.write("                            '<th>' + accept15Data + '</th>' +\r\n");
      out.write("                            '<th>' + reject15Data + '</th>' +\r\n");
      out.write("                            '<th>' + accept1hData + '</th>' +\r\n");
      out.write("                            '<th>' + reject1hData + '</th>' +\r\n");
      out.write("                            '<th>' + workData.accept_count + '</th>' +\r\n");
      out.write("                            '<th>' + workData.last_share_ip + '</th>' +\r\n");
      out.write("                            '<th>' + percent + \"%\" + '</th>' +\r\n");
      out.write("                            '</tr>';\r\n");
      out.write("                        user_lis += shuju;//将数据都添加到列表中\r\n");
      out.write("                    }\r\n");
      out.write("                    //将用户数据列表添加到页面上\r\n");
      out.write("                    $(\"#table\").html(user_lis);\r\n");
      out.write("                    //将程序总耗时添加到页面上\r\n");
      out.write("                    $(\"#time1\").html(\"<span style='margin-left: 30px' id='times'>总共耗时\" + works.time + \"毫秒.</span>\");\r\n");
      out.write("                } else {\r\n");
      out.write("                    //如果json查询有异常,返回消息\r\n");
      out.write("                    alert(json.message);\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("        })\r\n");
      out.write("\r\n");
      out.write("        //KB转换\r\n");
      out.write("        function getTimeData(time) {\r\n");
      out.write("            if (time < 1024) {\r\n");
      out.write("                return time.toFixed(2) + \"B\";\r\n");
      out.write("            } else if(time >= 1024) {//KB转换\r\n");
      out.write("                var KB = ( (time * (2^32)) / 1024);\r\n");
      out.write("                if (KB >= 1024) {//MB转换\r\n");
      out.write("                    var MB = KB / 1024;\r\n");
      out.write("                    if (MB >= 1024) {//GB转换\r\n");
      out.write("                        var GB = MB /1024;\r\n");
      out.write("                        if (GB >= 1024) {//TB转换\r\n");
      out.write("                            var TB = GB / 1024;\r\n");
      out.write("                            return TB.toFixed(2) + \"TB\";\r\n");
      out.write("                        }\r\n");
      out.write("                        return GB.toFixed(2) + \"GB\";\r\n");
      out.write("                    }\r\n");
      out.write("                    return MB.toFixed(2) + \"MB\";\r\n");
      out.write("                }\r\n");
      out.write("                return KB.toFixed(2) + \"KB\";\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        //计算百分比\r\n");
      out.write("        function getPercent(acceptTimeData, rejectTimeData) {\r\n");
      out.write("            if (acceptTimeData > 1024 && rejectTimeData > 1024) {//都大于1024\r\n");
      out.write("                var accept = ( (acceptTimeData * (2^32) ) / 1024);//接受一小时的数据\r\n");
      out.write("                var reject = ( (rejectTimeData * (2^32) ) / 1024);//超时1小时的数据\r\n");
      out.write("                return reject / (accept + reject);//计算百分比\r\n");
      out.write("            } else if (acceptTimeData < 1024 && rejectTimeData < 1024) {//都小于1024\r\n");
      out.write("                return rejectTimeData / (acceptTimeData + rejectTimeData);\r\n");
      out.write("            } else if (0 < acceptTimeData < 1024) {//接受大于1024\r\n");
      out.write("                var accept = ( (acceptTimeData * (2^32) ) / 1024);//接受一小时的数据\r\n");
      out.write("                return rejectTimeData / (accept + rejectTimeData);//计算百分比\r\n");
      out.write("            } else if (0 < rejectTimeData < 1024) {//超时大于1024\r\n");
      out.write("                var reject = ( (rejectTimeData * (2^32) ) / 1024);//超时1小时的数据\r\n");
      out.write("                return reject / (acceptTimeData + reject);//计算百分比\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("    }\r\n");
      out.write("</script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.37
 * Generated at: 2016-12-12 09:08:48 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class register_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("jar:file:/home/linukey/AndroidStudioProjects/Todo/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/Todoweb/WEB-INF/lib/struts2-core-2.3.30.jar!/META-INF/struts-tags.tld", Long.valueOf(1467851796000L));
    _jspx_dependants.put("/WEB-INF/lib/struts2-core-2.3.30.jar", Long.valueOf(1481525183000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<HTML !DOCTYPE>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"utf-8\">\n");
      out.write("<title>Todo|登录</title>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./css/regist.css\" />\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<header>\n");
      out.write("\t\t");
      if (_jspx_meth_s_005finclude_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t</header>\n");
      out.write("\t<article>\n");
      out.write("\t\t<div id=\"regist_container\">\n");
      out.write("\t\t\t<form action=\"\" method=\"post\">\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"user_name\" placeholder=\"用户名\"><br />\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"user_email\" placeholder=\"邮箱\"><br />\n");
      out.write("\t\t\t\t<input type=\"text\" id=\"user_phone\" placeholder=\"手机号\"><br />\n");
      out.write("\t\t\t\t<input type=\"password\" id=\"user_pass\" placeholder=\"密码\"><br />\n");
      out.write("\t\t\t\t<input type=\"password\" id=\"user_pass_agin\" placeholder=\"确认密码\"><br />\n");
      out.write("\t\t\t\t<div id=\"about_pass\">\n");
      out.write("\t\t\t\t\t<span id=\"remember_pass_span\"><input type=\"checkbox\">我同意Todo的服务条款</span>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<input type=\"button\" value=\"注册\" id=\"regist_button\"><br />\n");
      out.write("\t\t\t</form>\n");
      out.write("\t\t\t<a href=\"./login.jsp\" id=\"gotologin_\">已有账号，去登录</a>\n");
      out.write("\t\t</div>\n");
      out.write("\t</article>\n");
      out.write("\t<footer>\n");
      out.write("\t\t");
      if (_jspx_meth_s_005finclude_005f1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t</footer>\n");
      out.write("</body>\n");
      out.write("</HTML>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_s_005finclude_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  s:include
    org.apache.struts2.views.jsp.IncludeTag _jspx_th_s_005finclude_005f0 = (org.apache.struts2.views.jsp.IncludeTag) _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.IncludeTag.class);
    try {
      _jspx_th_s_005finclude_005f0.setPageContext(_jspx_page_context);
      _jspx_th_s_005finclude_005f0.setParent(null);
      // /register.jsp(12,2) name = value type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_s_005finclude_005f0.setValue("./Master/nav.jsp");
      int _jspx_eval_s_005finclude_005f0 = _jspx_th_s_005finclude_005f0.doStartTag();
      if (_jspx_th_s_005finclude_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } finally {
      _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005finclude_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_s_005finclude_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  s:include
    org.apache.struts2.views.jsp.IncludeTag _jspx_th_s_005finclude_005f1 = (org.apache.struts2.views.jsp.IncludeTag) _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.IncludeTag.class);
    try {
      _jspx_th_s_005finclude_005f1.setPageContext(_jspx_page_context);
      _jspx_th_s_005finclude_005f1.setParent(null);
      // /register.jsp(31,2) name = value type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_s_005finclude_005f1.setValue("./Master/footer.jsp");
      int _jspx_eval_s_005finclude_005f1 = _jspx_th_s_005finclude_005f1.doStartTag();
      if (_jspx_th_s_005finclude_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } finally {
      _005fjspx_005ftagPool_005fs_005finclude_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005finclude_005f1);
    }
    return false;
  }
}

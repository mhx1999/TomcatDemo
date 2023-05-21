package com.mhx.tomcat.Servlet;


import com.mhx.tomcat.http.MhxRequest;
import com.mhx.tomcat.http.MhxResponse;
import com.mhx.tomcat.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

//
public class MhxCalServlet extends MhxHttpServlet {
 @Override
 public void doGet(MhxRequest request, MhxResponse response) {
  //写业务代码，完成计算任务
  int num1 = WebUtils.parseInt(request.getParameter("num1"), 0);
  int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);
  int sum = num1+num2;
//返回计算结果给浏览器
  //outputStream 和 当前的socket关联
  OutputStream outputStream = response.getOutputStream();
  String respMes = MhxResponse.respHeader+"<h1>"+num1+" + "+num2+" = "+sum+"</h1>";

  try {
   outputStream.write(respMes.getBytes());

   outputStream.flush();
   outputStream.close();

  } catch (IOException e) {
   e.printStackTrace();
  }


 }

 @Override
 public void doPost(MhxRequest request, MhxResponse response) {
   this.doGet(request,response);
 }

 @Override
 public void init() throws Exception {

 }

 @Override
 public void destroy() {

 }


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("被调用");
//
//        String num1 = req.getParameter("num1");
//        String num2 = req.getParameter("num2");
//
//       // System.out.println(one);
//       // System.out.println(two);
//        int one = Integer.parseInt(num1);
//        int two = Integer.parseInt(num2);
//        int result = one+two;
//        PrintWriter writer = resp.getWriter();
//        writer.write(one + " + " + two + " = " + result);
//        writer.flush();
//        writer.close();
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
 }
//}

package com.mhx.tomcat.Servlet;

import com.mhx.tomcat.http.MhxRequest;
import com.mhx.tomcat.http.MhxResponse;
import com.mhx.tomcat.utils.WebUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 马海鑫
 * @version 1.0
 */
public class Mhx6Servlet extends MhxHttpServlet {

    @Override
    public void doGet(MhxRequest request, MhxResponse response) {
        //写业务代码，完成计算任务
        int num1 = WebUtils.parseInt(request.getParameter("num1"), 0);
        int num2 = WebUtils.parseInt(request.getParameter("num2"), 0);
        int sum = num1*num2;
//返回计算结果给浏览器
        //outputStream 和 当前的socket关联
        OutputStream outputStream = response.getOutputStream();
        String respMes = MhxResponse.respHeader+"<h1>"+num1+" * "+num2+" = "+sum+"</h1>";

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
}

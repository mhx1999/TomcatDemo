package com.mhx.tomcat.Servlet;

import com.mhx.tomcat.http.MhxRequest;
import com.mhx.tomcat.http.MhxResponse;

import java.io.IOException;

/**
 * @author 马海鑫
 * @version 1.0
 */
public abstract class MhxHttpServlet implements MhxServlet{

    @Override
    public void service(MhxRequest request, MhxResponse response) throws IOException {

        //equalsIgnoreCase:比较字符串内容是否相同，且不区分大小写
        if("Get".equalsIgnoreCase(request.getMethod())){
            this.doGet(request,response);
        }else if ("Post".equalsIgnoreCase(request.getMethod())){
            this.doPost(request,response);
        }
    }

    //这里我们使用模板设计模式，让MhxHttpServlet的子类MhxCalServlet实现
    public  abstract void doGet(MhxRequest request, MhxResponse response);

    public  abstract void doPost(MhxRequest request, MhxResponse response);
}

package com.mhx.tomcat.Servlet;

import com.mhx.tomcat.http.MhxRequest;
import com.mhx.tomcat.http.MhxResponse;


import java.io.IOException;

/**
 * @author 马海鑫
 * @version 1.0
 */
public interface MhxServlet {
//保留三个核心方法，做了部分简化
    void init() throws Exception;

    void service(MhxRequest request, MhxResponse response) throws  IOException;

    void destroy();
}

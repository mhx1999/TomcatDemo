package com.mhx.tomcat.handler;

import com.mhx.tomcat.Servlet.MhxCalServlet;
import com.mhx.tomcat.Servlet.MhxHttpServlet;
import com.mhx.tomcat.http.MhxRequest;
import com.mhx.tomcat.http.MhxResponse;
import com.mhx.tomcat.mhxTomcatV1;
import com.mhx.tomcat.utils.WebUtils;

import java.io.*;
import java.net.Socket;

/**
 * @author 马海鑫
 * @version 1.0
 * mhxRequestHandler对象是一个线程对象，处理一个http请求的
 */
public class mhxRequestHandler implements  Runnable{

    private Socket socket=null;

    public mhxRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
     //       InputStream inputStream = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
//            String mes = null;
//            System.out.println("============接收到浏览器发送的数据==========");
//            while ((mes = bufferedReader.readLine())!=null){
//                if(mes.length() == 0){
//                    break;
//                }
//                System.out.println(mes);
//        }

            MhxRequest mhxRequest = new MhxRequest(socket.getInputStream());
//            String num1 = mhxRequest.getParameter("num1");
//            String num2 = mhxRequest.getParameter("num2");
//            String num3 = mhxRequest.getParameter("num3");
//            System.out.println("num1="+num1+" num2="+num2+" num3="+num3);
//            System.out.println(mhxRequest);






            //返回数据给浏览器→封装成Http响应

            MhxResponse mhxResponse = new MhxResponse(socket.getOutputStream());


//
//          String resp = mhxResponse.respHeader+"<h1>mhxResponse返回的信息：hi,tomcat</h1>";
//
//            OutputStream outputStream = mhxResponse.getOutputStream();
//
//
//System.out.println("========给浏览器发送的数据======");
// System.out.println(resp);
////将 resp 字符串以 byte[] 方式返回
//outputStream.write(resp.getBytes());
//
//
//            outputStream.flush();
//            outputStream.close();
//

            //创建mhxRequestHandler对象→一会再用反射来创建对象
//            MhxCalServlet mhxCalServlet = new MhxCalServlet();
//            mhxCalServlet.doGet(mhxRequest,mhxResponse);
//用反射来创建对象
            //1.得到uri→就是servletUrlMapping的urlPattren
            String uri = mhxRequest.getUri();

///====新增业务逻辑
            //1，判断url是什么资源
            //2.若是静态资源，就读取该资源，并返回给浏览器 content-type text/html
            //3

            if(WebUtils.isHtml(uri)){
                String content = WebUtils.readHtml(uri.substring(1));
                content = mhxResponse.respHeader+content;
                OutputStream outputStream = mhxResponse.getOutputStream();
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();
                socket.close();
                return;
            }






            String servletName = mhxTomcatV1.servletUrlMapping.get(uri);
            //2.通过servletName进一步获取到servletClass→servlet的实例，真正的运行类型是其MhxCalServlet

           MhxHttpServlet mhxHttpServlet =  mhxTomcatV1.servletMapping.get(servletName);
//调用service方法，通过OOP的动态绑定机制，调用运行类的doGet/doPost


            if(mhxHttpServlet!=null) {
                mhxHttpServlet.service(mhxRequest, mhxResponse);

            }else{
                //没有这个servlet，返回404的提示信息
                String resp = MhxResponse.respHeader+"<h1>404 Not Found</h1>";

                OutputStream outputStream = mhxResponse.getOutputStream();
                    outputStream.write(resp.getBytes());
                    outputStream.flush();
                    outputStream.close();


            }






          // inputStream.close();
            socket.close();


        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            //确保socket一定要关闭

            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }



    }}

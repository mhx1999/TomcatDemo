package com.mhx.tomcat.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;

/**
 * @author 马海鑫
 * @version 1.0
 * MhxRequest作用：封装http请求的数据，如method(get/post)、uri(/mhxCalServlet)、参数列表(name=value&name=value)
 * MhxRequest等价与原生servlet中的HttpServletRequest
 */
public class MhxRequest {
    String buf = "";
    private String method;      //请求方法
    private String uri;           //访问地址
    private HashMap<String,String> parametersMapping = new HashMap<>();    //参数列表

    private InputStream inputStream = null;

    public MhxRequest(InputStream inputStream) {
//完成对http请求数据的封装
    this.inputStream = inputStream;
    encapHttpRequest();

        }
//将http请求的相关数据进行封装，然后提供相关的方法进行获取
        private void encapHttpRequest(){
            try {

                BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

          /*
           ============接收到浏览器发送的数==========
            GET /mhxCalServlet?num1=10&num2=20 HTTP/1.1
            Host: localhost:8080
            User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/113.0
           */

//                while((buf = bufferedReader.readLine())!=null){
//                    System.out.println(buf);
//                }


                String requestLine = bufferedReader.readLine();

                String[] requestLineArr = requestLine.split(" ");
                //读取method
                method = requestLineArr[0];
                //解析得到 /mhxCalServlet →url
                int index = requestLineArr[1].indexOf("?");
                if(index == -1){
                    uri = requestLineArr[1];
                    //写post请求逻辑





                }else{
                    uri = requestLineArr[1].substring(0,index);
                    //获取参数列表parameterMapping
                    //parameters → num1=10&num2=20
                    String parameters =  requestLineArr[1].substring(index+1);
                    //parametersPair → [num1=10,num2=20]
                    String[] parametersPair = parameters.split("&");
                    //放在用户提交时  /mhxCalServlet?
                    if(null != parametersPair && !"".equals(parametersPair)){
                        for(String parameterPair:parametersPair){
                            //parameterVal → [num1,10]
                            String[] parameterVal = parameterPair.split("=");
                            if(parameterVal.length == 2){
                                parametersMapping.put(parameterVal[0],parameterVal[1]);
                            }
                        }
                    }
                }

//inputStream.close();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //request 有一个特别重要的方法getParameter
    public String getParameter(String name){
        if (parametersMapping.containsKey(name)){
            return parametersMapping.get(name);
        }else {
            return "";
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "MhxRequest{" +
                "method='" + method + '\'' +
                ", uri='" + uri + '\'' +
                ", parametersMapping=" + parametersMapping +
                '}';
    }
}


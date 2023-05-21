package com.mhx.tomcat.http;

import java.io.OutputStream;

/**
 * @author 马海鑫
 * @version 1.0
 * MhxResponse对象可以封装OutputStream(和socket关联)，即可以通过MhxResponse对象 返回http响应给浏览器/客户端
 * MhxResponse等价与原生servlet中的HttpServletResponse
 */
public class MhxResponse {
    public static OutputStream outputStream = null;





//    //构建一个 http 响应头
////\r\n 表示换行
////http 响应体，需要前面有两个换行 \r\n\r\n
//    String respHeader = "HTTP/1.1 200 OK\r\n" +
//            "Content-Type: text/html;charset=utf-8\r\n\r\n";
//    String resp = respHeader + "hi,tomcat";
//            System.out.println("========给浏览器发送的数据======");
//            System.out.println(resp);
//    //将 resp 字符串以 byte[] 方式返回
//            outputStream.write(resp.getBytes());


    //构建一个 http 响应头
    //\r\n 表示换行
//http 响应体，需要前面有两个换行 \r\n\r\n
  public static final String respHeader = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html;charset=utf-8\r\n\r\n";






    //在创建MhxResponse对象时，传入的outputStream是和Socket关联的
    public MhxResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    //当需要给浏览器返回数据时，可以通过MhxResponse的输出流完成
    public static OutputStream getOutputStream() {
        return outputStream;
    }
}

package com.mhx.tomcat;

import com.mhx.tomcat.Servlet.MhxHttpServlet;
import com.mhx.tomcat.Servlet.MhxServlet;
import com.mhx.tomcat.handler.mhxRequestHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 马海鑫
 * @version 1.0
 * 这是第一个版本的Tomcat，可以完成接收浏览器的请求，并返回信息
 */
public class mhxTomcatV1 {

    //1.存放容器 servletMapping
    //-ConcurrentHashMap
    //-HashMap
    //key         -    value
    //ServletName      对应的实例
    //MhxCalServlet    /mhxCalServlet

    public static final HashMap<String, MhxHttpServlet>
            servletMapping = new HashMap<>();


    //2.存放容器 servletUrlMapping
    //-ConcurrentHashMap
    //-HashMap
    //key                     -              value
    //url-pattern                            ServletName
    //com.mhx.tomcat.Servlet.MhxCalServlet   MhxCalServlet

    public static final HashMap<String, String>
            servletUrlMapping = new HashMap<>();

//Tomcat启动时，会直接加载这两个容器
    public void init() throws Exception {
       //读取web.xml  → dom4j


        String path = mhxTomcatV1.class.getResource("/").getPath();
        SAXReader saxReader = new SAXReader();

            Document document = saxReader.read(new File(path + "web.xml"));

            //得到根元素
            Element rootElement = document.getRootElement();
            //得到根元素下的所有元素
            List<Element> elements = rootElement.elements();
            //遍历并过滤到 servlet servlet-mapping
            for(Element element:elements){
                if("servlet".equalsIgnoreCase(element.getName())){
                    //这里面的是servlet配置
                   //使用反射将该servlet实例放入到servletMapping
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");

                    servletMapping.put(servletName.getText(),(MhxHttpServlet) Class.forName(servletClass.getText().trim()).newInstance());

                } else if ("servlet-mapping".equalsIgnoreCase(element.getName())) {
                    //这里面的是servlet-mapping配置

                    //将该servlet-mapping实例放入到servletUrlMapping
                    Element servletName = element.element("servlet-name");
                    Element utlPattern = element.element("utl-pattern");

                    servletUrlMapping.put(utlPattern.getText(),servletName.getText());


                }
            }

        System.out.println("servletMapping"+servletMapping+" servletUrlMapping"+servletUrlMapping);
    }


//启动Tomcat容器
    public void run() throws IOException{
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("==========mhxTomcatV1在8080监听============");

        //只要serverSocket没有关闭，就一直等待浏览器/客户端的连接
        while(!serverSocket.isClosed()){
//1.接收到浏览器的连接后，若成功，就会得到一个socket
            //2.这个socket就是 服务器和浏览器的数据通道

            Socket socket = serverSocket.accept();

            //创建一个线程对象，并且把socket给该线程
            mhxRequestHandler mhxRequestHandler = new mhxRequestHandler(socket);
            new Thread(mhxRequestHandler).start();
        }

    }




public static void main(String[]args) throws Exception {




    mhxTomcatV1 mhxTomcat = new mhxTomcatV1();
    mhxTomcat.init();
//启动Tomcat容器
    mhxTomcat.run();




        }


}




package com.mhx.tomcat.utils;

import com.mhx.tomcat.mhxTomcatV1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author 马海鑫
 * @version 1.0
 */
public class WebUtils {
    //将一个字符串转成数组的方法
    public static int parseInt(String strNum,int defaultVal){
        try {
            return Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            System.out.println(strNum+"不能转成数字");
        }
        return defaultVal;
    }


    //判断url是不是html文件
    public static boolean isHtml(String uri){
        return uri.endsWith(".html");

    }

    //根据文件名来读取文件，返回String
    public static String readHtml(String filename){
        String path = WebUtils.class.getResource("/").getPath();
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path + filename));
            String buf = "";



            while ((buf=bufferedReader.readLine())!=null){

                stringBuilder.append(buf);
            }

        } catch (Exception e) {
            return "<h1>404 Not Found</h1>";

        }

       return stringBuilder.toString();


    }



}

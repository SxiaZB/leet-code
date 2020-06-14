package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class Units {

    public static boolean campareJsonArray(String oldJsonStr, String newJsonStr) {
        JSONArray oldArray = JSON.parseArray(oldJsonStr);
        JSONArray newArray = JSON.parseArray(newJsonStr);
        return campareJsonArray(oldArray, newArray);
    }

    private static boolean campareJsonArray(JSONArray oldArray, JSONArray newArray) {
        if (oldArray.size() != newArray.size()) {
            return false;
        }
        for (int i = 0; i < oldArray.size(); i++) {
            Object o = oldArray.get(i);
            Object n = newArray.get(i);
            if (!o.equals(n)) {
                System.out.println(o +"!="+n);
//                return false;
            }
        }
        return true;
    }

    public static boolean campareArray(int[] o, int[] n) {

        if (o.length != n.length) {
            return false;
        }
        for (int i = 0; i < o.length; i++) {
            if (o[i] != n[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 读取json文件，返回json串
     *
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String path = Units.class.getClassLoader().getResource("test.json").getPath();
        String s = readJsonFile(path);
        JSONObject test = JSON.parseObject(s);
        System.out.print("比对结果是：" + campareJsonArray(test.getJSONArray("java-lcp05"), test.getJSONArray("go-lcp05")));
    }
}

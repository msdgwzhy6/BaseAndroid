package com.che.carcheck.support.constant;

/**
 * 作者：余天然 on 16/5/4 下午4:32
 */
public class WebApi {

    public static String hostname    = "http://appapi.che.com/usedcar";//正式环境;
    // public static String hostname = "http://192.168.1.35:8080/usedcar";//测试环境;

    public static final String getNewsClass = hostname + "/app/getInfoClassify"; //获取购车攻略分类
    public static final String getNewsList = hostname + "/app/infoList"; //获取购车攻略列表
}

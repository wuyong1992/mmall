package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * ftp工具类
 * Created by Administrator on 2017/6/21.
 */
public class FTPUtil {

    public static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
    public static String ftpUser = PropertiesUtil.getProperty("ftp.user");
    public static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

    private String ip;
    private int port;   //端口
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    //构造方法
    public FTPUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    //静态方法
    public static Boolean uploadFile(List<File> fileList){
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        return null;
    }

    /**
     * 上传方法
     * @param remotePath    远端路劲
     * @param fileList      文件集合
     * @return
     */
    private boolean uploadFile(String remotePath,List<File> fileList){
        boolean uploaded =true;
        FileInputStream fileInputStream = null;
        //连接FTP服务器
        return true;
    }

    private boolean connectServer(String ip, int port, String user, String pwd){
        return true;

    }




    //Getter Setter
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}

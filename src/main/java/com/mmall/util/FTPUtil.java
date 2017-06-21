package com.mmall.util;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * ftp工具类
 * Created by Administrator on 2017/6/21.
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

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

    /**
     * 开方的公共静态方法
     * @param fileList  文件集合
     * @return
     * @throws IOException
     */
    public static Boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始链接服务器");
        boolean result = ftpUtil.uploadFile("img",fileList);
        logger.info("结束上传，上传结果：{}",result);
        return result;
    }

    /**
     * 上传方法
     * @param remotePath 远端路劲
     * @param fileList   文件集合
     * @return
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fileInputStream = null;
        //连接FTP服务器
        if (connectServer(this.ip, this.port, this.user, this.pwd)) {
            try {
                //切换文件夹
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区
                ftpClient.setBufferSize(1024);
                //字符集编码
                ftpClient.setControlEncoding("UTF-8");
                //设置文本类型为二进制
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地的被动模式
                ftpClient.enterLocalPassiveMode();
                //开始上传
                for (File fileItem : fileList) {
                    fileInputStream = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(), fileInputStream);
                }
            } catch (IOException e) {
                logger.error("上传文件异常：", e);
                uploaded = false;
            } finally {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            }
            return uploaded;
        }


        return true;
    }

    /**
     * 链接ftp服务器
     * @param ip    ip
     * @param port  端口
     * @param user  用户名
     * @param pwd   密码
     * @return
     */
    private boolean connectServer(String ip, int port, String user, String pwd) {
        ftpClient = new FTPClient();
        boolean isSuccess = false;
        try {
            ftpClient.connect(ip);
            //登陆ftp服务器
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("FTP链接异常：", e);
        }
        return isSuccess;
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

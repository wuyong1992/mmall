package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 商品后台
 * Created by Administrator on 2017/6/20.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    /**
     * 更新或者保存产品
     * @param session   会话
     * @param product   产品
     * @return
     */
    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 更新产品销售状态
     * @param session
     * @param productId 产品ID
     * @param status    产品状态
     * @return
     */
    @RequestMapping(value = "set_status.do")
    @ResponseBody
    public ServerResponse setStatus(HttpSession session, Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            return iProductService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 获取产品详情VO
     * @param session   会话
     * @param productId 产品ID
     * @return
     */
    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 分页显示产品列表
     * @param session   会话
     * @param pageNum   页码，第几页，默认为第一页1
     * @param pageSize  页面展示多少条，默认展示10条
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize" ,defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            return iProductService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 产品搜索功能
     * @param session   会话
     * @param productName   名称
     * @param productId     id
     * @param pageNum       页码
     * @param pageSize      内容容量
     * @return
     */
    @RequestMapping(value = "search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId,
                                  @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize" ,defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 上传文件到ftp服务器
     * @param file  文件
     * @param request   请求
     * @return
     */
    @RequestMapping(value = "upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要管理员身份登录");
        }
        //判断权限
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            //跟webapp下的WEB-INF同级别目录
            String path = request.getSession().getServletContext().getRealPath("/upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            //构建容器收纳
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 富文本中图片上传
     * @param file  文件
     * @param request   请求
     * @return
     */
    @RequestMapping(value = "richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Map resultMap = Maps.newHashMap();
        if (user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请先登录");
            return resultMap;
        }
        //判断权限
        //根据富文本的要求返回相应数据
        /*{
            "success": true/false,
                "msg": "error message", # optional
            "file_path": "[real file path]"
        }*/
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            //跟webapp下的WEB-INF同级别目录
            String path = request.getSession().getServletContext().getRealPath("/upload");
            String targetFileName = iFileService.upload(file,path);
            if (StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            resultMap.put("success",true);
            resultMap.put("msg","成功");
            resultMap.put("file_path",url);
            //修改header
            response.setHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }
    }




}

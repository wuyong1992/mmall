package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    public ServerResponse upload(MultipartFile file, HttpServletRequest request) {
        //跟webapp下的WEB-INF同级别目录
        String path = request.getSession().getServletContext().getRealPath("upload");

        return null;
    }

}

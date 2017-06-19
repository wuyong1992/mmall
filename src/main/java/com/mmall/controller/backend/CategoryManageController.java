package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 分了管理
 * Created by wuyong on 2017/6/19.
 */
@Controller
@RequestMapping(value = "/manage/category")
public class CategoryManageController {


    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId" ,defaultValue = "0") int parentId){
        return null;
    }
}

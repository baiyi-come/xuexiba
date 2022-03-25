package com.tzf.larservice.controller;


import com.tzf.commonutil.JwtUtils;
import com.tzf.commonutil.Resouce;
import com.tzf.commonutil.UcenterMemberOrder;
import com.tzf.larservice.entity.UcenterMember;
import com.tzf.larservice.entity.vo.RegisterVo;
import com.tzf.larservice.entity.vo.UcenterMemberVO;
import com.tzf.larservice.service.UcenterMemberService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/larservice/ucenter")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 登录
     *
     * @param ucenterMemberVO
     * @return
     */
    @DisableAuth
    @PostMapping("login")
    public Resouce login(@RequestBody UcenterMemberVO ucenterMemberVO) {

        String token = ucenterMemberService.login(ucenterMemberVO);
        return Resouce.ok().data("token", token);
    }

    /**
     * 注册
     *
     * @param registerVo
     * @return
     */
    @DisableAuth
    @PostMapping("register")
    public Resouce register(@RequestBody RegisterVo registerVo) {
            ucenterMemberService.register(registerVo);
            return Resouce.ok();
    }

    /**
     * 根据token得到用户信息
     *
     * @param request
     * @return
     */
    @DisableAuth
    @GetMapping("token/getLoginInfo")
    public Resouce getLoginInfo(HttpServletRequest request) {
        // 得到用户的id
        String token = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(token);
        //System.out.println(member);
        return Resouce.ok().data("userInfo", member);
    }

    @DisableAuth
    @PostMapping("getUserById/{id}")
    public UcenterMemberOrder getUserById(@PathVariable("id") String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     * 获取当天注册的人数
     * @param day 日期
     * @return
     */
    @DisableAuth
    @GetMapping("registerCount/{day}")
    public Integer registerCount(@PathVariable("day") String day) {
        Integer count = ucenterMemberService.getRegisterCount(day);
        return count;
    }
    /**
     * 后台登录
     * @param user
     * @param pswd
     * @return
     */
    @DisableAuth
    @PostMapping("get/{user}/{pswd}")
    public Resouce adminLogin(@PathVariable String user, @PathVariable String pswd) {
        Map<String, Object> map = ucenterMemberService.AdminLogin(user, pswd);
        return Resouce.ok().data(map);
    }

    /**
     * 评论功能远程调用方法获取当前评论人信息
     *
     * @param id
     * @return
     */
    @DisableAuth
    @PostMapping("getUcenterMember/{id}")
    public UcenterMember getUcenterMember(@PathVariable("id") String id) {
        System.out.println("++++++++"+id);
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return ucenterMember;
    }

    /**
     * 获取当前用户Id，判断是否本人，用于删除
     * @param request
     * @return
     */
    @DisableAuth
    @PostMapping("getUserId")
    public Resouce getUserId(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
       return Resouce.ok().data("userId", id);
    }

    /**
     * 依据id查询用户信息
     * @param id
     * @return
     */
    @PostMapping("getUser/{id}")
    public Resouce getUser(@PathVariable String id) {
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return Resouce.ok().data("user", ucenterMember);
    }
}


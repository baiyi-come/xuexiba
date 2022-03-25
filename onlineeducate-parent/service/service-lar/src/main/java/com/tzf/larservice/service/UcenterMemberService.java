package com.tzf.larservice.service;

import com.tzf.larservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzf.larservice.entity.vo.RegisterVo;
import com.tzf.larservice.entity.vo.UcenterMemberVO;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>

 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMemberVO ucenterMemberVO);

    void register(RegisterVo registerVo);

    Integer getRegisterCount(String day);

    Map<String, Object> AdminLogin(String user, String pswd);
}

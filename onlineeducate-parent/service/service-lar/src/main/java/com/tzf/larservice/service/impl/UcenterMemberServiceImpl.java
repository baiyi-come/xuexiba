package com.tzf.larservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.JwtUtils;
import com.tzf.commonutil.MD5;
import com.tzf.larservice.entity.UcenterMember;
import com.tzf.larservice.entity.vo.RegisterVo;
import com.tzf.larservice.entity.vo.UcenterMemberVO;
import com.tzf.larservice.mapper.UcenterMemberMapper;
import com.tzf.larservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param ucenterMemberVO
     * @return
     */
    @Override
    public String login(UcenterMemberVO ucenterMemberVO) {
        String mobile = ucenterMemberVO.getMobile();
        String password = ucenterMemberVO.getPassword();
        // 若账号或密码有为空项，报错
        if (mobile == null || password == null) {
            throw new GlobalCommonExceptionHandler(20001, "用户名或密码为空");
        }
        // 设置查询条件
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        // 根据手机号查询是否有此账号
        wrapper.eq("mobile", mobile);

        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        // 若无此账号 报错
        if (ucenterMember == null) {
            throw new GlobalCommonExceptionHandler(20001, "此账号不存在");
        }
        // 存在账号
        // 检查密码是否一致
        // 加密
        String encrypt = MD5.encrypt(password);

        // 比较
        if (!encrypt.equals(ucenterMember.getPassword())) {

            throw new GlobalCommonExceptionHandler(20001, "密码错误");
        }

        //校验是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new GlobalCommonExceptionHandler(20001, "抱歉，此账号禁止访问此网站");
        }

        // 以下代码是计算当天有多少人登录网站
        String count = redisTemplate.opsForValue().get("logincount");


        // 判断redis里是否有数据，没有添加，数据到redis中
        //
        if (count == null || count == "") {
            redisTemplate.opsForValue().set("logincount", "1");
        } else {
            // 有数据，自增加一
            redisTemplate.opsForValue().increment("logincount");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        // 检查是否有空值
        if (StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GlobalCommonExceptionHandler(20001, "信息填写不完整");
        }
        // 校验验证码
        // 从redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!redisCode.equals(code)) {
            throw new GlobalCommonExceptionHandler(20002, "验证码错误");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count > 0) {
            throw new GlobalCommonExceptionHandler(20003,"该账号已存在");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
//        member.setIsDisabled(false);
//        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
        // 注册成功，删除redis中的数据
        redisTemplate.delete(mobile);
    }

    @Override
    public Integer getRegisterCount(String day) {
        int date = baseMapper.getDayDataByDate(day);
        return date;
    }
    @Override
    public Map<String,Object> AdminLogin(String user, String pswd) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", user);
        String encrypt = MD5.encrypt(pswd);
        wrapper.eq("password", encrypt);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer == null || integer == 0) {
            throw new GlobalCommonExceptionHandler(20001, "账号或密码错误");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", "admin");
        return map;
    }
}

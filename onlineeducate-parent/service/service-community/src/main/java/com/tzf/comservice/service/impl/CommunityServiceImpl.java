package com.tzf.comservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzf.commonutil.JwtUtils;
import com.tzf.comservice.entity.Community;
import com.tzf.comservice.entity.CommunityText;
import com.tzf.comservice.mapper.CommunityMapper;
import com.tzf.comservice.service.CommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.comservice.service.CommunityTextService;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baiyi
 * @since 2021-10-22
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityTextService communityTextService;



    public void saveCommunity(Community comm, HttpServletRequest request) {
        // 创建一个community对象
        Community community = new Community();

        // 把前端带过来的数据复制到community中
        community.setAvator(comm.getAvator());

        // 判断名字是否重复
        QueryWrapper<Community> wrapper = new QueryWrapper<>();
        wrapper.eq("title", comm.getTitle());
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer > 0) {
            throw new GlobalCommonExceptionHandler(20001, "该名字已被注册");
        }
        community.setTitle(comm.getTitle());
        // 获取当前创建人姓名
        String id = JwtUtils.getMemberIdByJwtToken(request);
        // 把创建人id复制进community
        community.setUserId(id);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s = format.format(new Date());
//        community.setGmtCreate(s);
//        System.out.println(community);
        baseMapper.insert(community);
    }
    @Override
    public Page<Community> getCommunity(Long pageNum, Long pageSize) {

        Page<Community> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Community> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("gmt_create");

        this.page(page, wrapper);
        return page;
    }

    @Override
    public List<Community> getCommunityByName(String communityName) {

        QueryWrapper<Community> wrapper = new QueryWrapper<>();

        wrapper.like("title", communityName);

        List<Community> list = baseMapper.selectList(wrapper);

        return list;
    }

    @Override
    public void removeCommunity(String id) {

        // 获取该社区下所有文章
        List<CommunityText> communityTextList = communityTextService.getCommunityText(id);

        // 遍历删除文章（评论也删除）
        for (CommunityText communityText : communityTextList) {
            //获取每篇文章的id
            String communityTextId = communityText.getId();
            // 调用删除
            communityTextService.removeCommuityById(communityTextId);
        }

        // 删除社区
        baseMapper.deleteById(id);
    }


}

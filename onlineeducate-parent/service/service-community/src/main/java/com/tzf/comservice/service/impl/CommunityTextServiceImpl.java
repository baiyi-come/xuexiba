package com.tzf.comservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.JwtUtils;
import com.tzf.comservice.Clients.LarClient;
import com.tzf.comservice.entity.CComment;
import com.tzf.comservice.entity.CommunityText;
import com.tzf.comservice.entity.vo.CommunityItem;
import com.tzf.comservice.entity.vo.CommunityTextVO;
import com.tzf.comservice.entity.vo.CommunityTitle;
import com.tzf.comservice.mapper.CommunityTextMapper;
import com.tzf.comservice.service.CCommentService;
import com.tzf.comservice.service.CommunityService;
import com.tzf.comservice.service.CommunityTextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.larservice.entity.UcenterMember;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baiyi
 * @since 2021-10-22
 */
@Service
public class CommunityTextServiceImpl extends ServiceImpl<CommunityTextMapper, CommunityText> implements CommunityTextService {

    @Autowired
    private CommunityService communityService;


    @Autowired
    private LarClient larClient;


    @Autowired
    private CCommentService cCommentService;

    @Override
    public void saveCommunityText(CommunityTextVO communityTextVO, HttpServletRequest request) {

        System.out.println("oooooo"+communityTextVO.getText());
        // new一个CommunityText对象
        CommunityText communityText = new CommunityText();

        // 把communityTextVo中的数据赋值给communityText
        BeanUtils.copyProperties(communityTextVO, communityText);

        // 获取当前操作人id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        // 加入对象
        communityText.setPublicId(id);

//        // 格式化时间
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 生成当前时间
//        String s = format.format(new Date());
//
//        // 存入时间
//        communityText.setGmtCreate(s);

        // 保存数据库·
        baseMapper.insert(communityText);
    }

    @Override
    public List<CommunityItem> getCommunityTextByCommunityId(String communityId) {

        // 添加查询获取
        QueryWrapper<CommunityText> wrapper = new QueryWrapper<>();
        wrapper.eq("community_id", communityId);

        wrapper.orderByDesc("gmt_create");
        // 查询出该社区下所有的文章
        List<CommunityText> list = baseMapper.selectList(wrapper);
        System.out.println(list);
        // 存储组装好的信息
        ArrayList<CommunityItem> communityItems = new ArrayList<CommunityItem>();

        // 遍历查询结果，查出发表人信息，并组装
        for (CommunityText communityText : list) {
            // 创建组装对象
            CommunityItem communityItem = new CommunityItem();

            // 把文章基本信息存入
            BeanUtils.copyProperties(communityText, communityItem);
            //communityItem.setGmtCreate(communityText.getGmtCreate());

            // 获取发表人Id
            String publicId = communityText.getPublicId();

            // 根据发表人id查询出发表人基本信息
            UcenterMember ucenterMember = larClient.getUcenterMember(publicId);

            // 复制
//            BeanUtils.copyProperties(ucenterMember,communityItem);
            communityItem.setAvatar(ucenterMember.getAvatar());

            communityItem.setNickname(ucenterMember.getNickname());

            communityItem.setPublicId(ucenterMember.getId());
            // 添加进communityItems
            communityItems.add(communityItem);

        }

        return communityItems;
    }

    @Override
    public CommunityItem getCommunityTextOne(String id) {

        // 存放查出来的数据
        CommunityItem item = new CommunityItem();

        // 获取基本信息
        CommunityText communityText = baseMapper.selectById(id);

        // 获取发表人信息
        String publicId = communityText.getPublicId();

        // 根据id查出发表人信息
        UcenterMember ucenterMember = larClient.getUcenterMember(publicId);

        BeanUtils.copyProperties(item, communityText);

        item.setNickname(ucenterMember.getNickname());

        item.setAvatar(ucenterMember.getAvatar());
        return item;
    }

    @Override
    public List<CommunityTitle> getCommunityLimitTwo() {


        QueryWrapper<CommunityText> wrapper = new QueryWrapper<>();

        // 降序排列
        wrapper.orderByDesc("gmt_create");

        // 查询两条
        wrapper.last("limit 2");

        // 查询出前两条
        List<CommunityText> list = baseMapper.selectList(wrapper);

        // 存放筛选还得两条信息
        ArrayList<CommunityTitle> arrayList = new ArrayList<>();

        // 遍历
        for (CommunityText communityText : list) {

            CommunityTitle communityTitle = new CommunityTitle();

            communityTitle.setId(communityText.getId());

            communityTitle.setTitle(communityText.getTitle());

            arrayList.add(communityTitle);
        }
        return arrayList;
    }

    @Override
    public void removeCommuityById(String id) {

        // 删除文章下所有评论
        QueryWrapper<CComment> wrapper = new QueryWrapper<>();
        wrapper.eq("community_text_id", id);
        cCommentService.remove(wrapper);

        // 删除文章
        baseMapper.deleteById(id);
    }

    /**
     * 删除社区时使用
     *
     * @param id
     * @return
     */
    @Override
    public List<CommunityText> getCommunityText(String id) {
        // 查询出该社区下的所有文章
        QueryWrapper<CommunityText> wrapper = new QueryWrapper<>();

        wrapper.eq("community_id", id);

        List<CommunityText> list = baseMapper.selectList(wrapper);
        return list;
    }


}

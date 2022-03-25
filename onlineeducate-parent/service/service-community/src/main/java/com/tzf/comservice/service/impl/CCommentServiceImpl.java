package com.tzf.comservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.JwtUtils;
import com.tzf.comservice.Clients.LarClient;
import com.tzf.comservice.entity.CComment;
import com.tzf.comservice.entity.vo.CommunityTextComment;
import com.tzf.comservice.mapper.CCommentMapper;
import com.tzf.comservice.service.CCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.larservice.entity.UcenterMember;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
public class CCommentServiceImpl extends ServiceImpl<CCommentMapper, CComment> implements CCommentService {

    @Autowired
    private LarClient larClient;


    @Override
    public void saveCommunityTextComment(CComment cComment, HttpServletRequest request) {
         // 获取当前评论人id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        // 存入cComment
        cComment.setUser(id);
        // 如果是一级评论
        if (cComment.getParentId() == ""||cComment.getParentId()==null) {
            cComment.setParentId("0");
        }
        baseMapper.insert(cComment);
    }

    @Override
    public List<CommunityTextComment> getCommunityCommentByTextId(String id) {

        // 根据文章id查询出对应的评论
        QueryWrapper<CComment> wrapper = new QueryWrapper<>();
        wrapper.eq("community_text_id", id);
        wrapper.eq("parent_id", 0);
        // 获取所有的一级评论
        List<CComment> commentList = baseMapper.selectList(wrapper);

        // 创建一个集合VO对象,用于最终返回
        List<CommunityTextComment> communityTextComment = new ArrayList<>();

        // 遍历所有的一级标题
        for (CComment comment : commentList) {
            // 创建一个VO对象
            CommunityTextComment textComment = new CommunityTextComment();

            // 复制
            BeanUtils.copyProperties(comment, textComment);

            // 获取其中的发布者id
            String userId = comment.getUser();

            // 远程调用查询该用户信息
            UcenterMember ucenterMember = larClient.getUcenterMember(userId);

            // 复制昵称
            textComment.setNickname(ucenterMember.getNickname());

            // 复制头像到当前属性中
            textComment.setAvatar(ucenterMember.getAvatar());

            // 查询该评论下是否有二级评论
            // 获取一级评论的id
            String commentId = comment.getId();

            // 根据此id查询其二级评论
            QueryWrapper<CComment> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id", commentId);
            List<CComment> cComments = baseMapper.selectList(wrapper1);
            // 若为空
            if (cComments == null ) {
                // 把查询好的一个对象放进集合
                communityTextComment.add(textComment);
            }else{

                // 创建一个集合用于存放二级评论
                List<CommunityTextComment> communityTextComments = new ArrayList<>();
                // 遍历 二级评论
                for (CComment cComment : cComments) {
                    // 创建一个Vo对象
                    CommunityTextComment textComment1 = new CommunityTextComment();

                    // 复制
                    BeanUtils.copyProperties(cComment, textComment1);

                    // 获取评论人id
                    String user = cComment.getUser();
                    // 远程调用查询评论人信息
                    UcenterMember ucenterMember1 = larClient.getUcenterMember(user);

                    // 赋值昵称
                    textComment1.setNickname(ucenterMember1.getNickname());

                    // 赋值头像
                    textComment1.setAvatar(ucenterMember1.getAvatar());


                    // 加入集合
                    communityTextComments.add(textComment1);
                }
                textComment.setChildren(communityTextComments);

                // 把二级评论集合加入一级评论集合
                communityTextComment.add(textComment);
            }


        }

        // 返回最终的List集合
        return communityTextComment;
    }

    @Override
    public void removeCommunityTextOneComment(String id) {
        // 删除二级评论
        QueryWrapper<CComment> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        baseMapper.delete(wrapper);

        // 删除一级评论
        baseMapper.deleteById(id);
    }

}

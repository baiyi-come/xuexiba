package com.tzf.comservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommunityTextComment implements Serializable {
    // 评论id
    private String id;

    // 评论人id
    private String user;

    // 评论人昵称
    private String nickname;

    // 用户头像
    private String avatar;
    // 评论内容
    private String text;

//    private String parentId;

    // 社区id
    private String communityId;

    // 评论文章id
    private String communityTextId;


    // 评论回复
    List<CommunityTextComment> children = new ArrayList<>();

    // 评论日期
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

}

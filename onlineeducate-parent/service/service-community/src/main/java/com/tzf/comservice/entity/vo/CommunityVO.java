package com.tzf.comservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommunityVO implements Serializable {
    @ApiModelProperty(value = "社区id")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "头像")
    private String avator;

    // 用来保存两个推荐文章
    List<CommunityTitle> communityTitleList = new ArrayList<>();
}

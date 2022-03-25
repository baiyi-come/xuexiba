package com.tzf.comservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章显示
 */
@Data
public class CommunityItem implements Serializable {

    @ApiModelProperty(value = "会员id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "文章标题")
    private String title;
    @ApiModelProperty(value = "文章内容")
    private String text;
    @ApiModelProperty(value = "用户id")
    private String publicId;
    @ApiModelProperty(value = "发表时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;


}

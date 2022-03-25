package com.tzf.comservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 保存文章
 */
@Data
public class CommunityTextVO implements Serializable {
    private String communityId;

    private String title;

    private String text;
}

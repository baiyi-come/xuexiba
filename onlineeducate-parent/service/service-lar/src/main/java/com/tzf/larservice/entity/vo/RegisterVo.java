package com.tzf.larservice.entity.vo;

import lombok.Data;

@Data
public class RegisterVo {

    private String nickname;// 昵称

    private String mobile;

    private String password;

    private String code;
}

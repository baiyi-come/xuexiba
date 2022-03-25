package com.tzf.larservice.mapper;

import com.tzf.larservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    int getDayDataByDate(String day);
}

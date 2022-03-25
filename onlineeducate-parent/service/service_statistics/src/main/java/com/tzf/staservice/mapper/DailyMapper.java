package com.tzf.staservice.mapper;

import com.tzf.staservice.entity.Daily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 */
@Mapper
public interface DailyMapper extends BaseMapper<Daily> {
}

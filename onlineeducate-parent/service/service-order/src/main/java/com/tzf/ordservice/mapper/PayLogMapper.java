package com.tzf.ordservice.mapper;

import com.tzf.ordservice.entity.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 */
@Mapper
public interface PayLogMapper extends BaseMapper<PayLog> {

}

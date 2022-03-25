package com.tzf.ordservice.mapper;

import com.tzf.ordservice.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}

package com.tzf.ordservice.service.impl;

import com.tzf.ordservice.entity.PayLog;
import com.tzf.ordservice.mapper.PayLogMapper;
import com.tzf.ordservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}

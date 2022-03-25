package com.tzf.ordservice.service;

import com.tzf.ordservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberIdByJwtToken, HttpServletResponse response);

    String payOrder(String orderNo);

    boolean isBuy(String courseId, String userId);
}

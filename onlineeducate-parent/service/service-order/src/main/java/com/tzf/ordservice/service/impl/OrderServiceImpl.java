package com.tzf.ordservice.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.tzf.commonutil.CourseWebVoOrder;
import com.tzf.commonutil.Resouce;
import com.tzf.commonutil.UcenterMemberOrder;
import com.tzf.ordservice.client.CourseClient;
import com.tzf.ordservice.client.UserClient;
import com.tzf.ordservice.entity.Order;
import com.tzf.ordservice.entity.PayProperties;
import com.tzf.ordservice.mapper.OrderMapper;
import com.tzf.ordservice.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzf.ordservice.utils.OrderNoUtil;
import com.tzf.servicebase.exceptionHandler.GlobalCommonExceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UserClient userClient;

    @Override

    public String createOrders(String courseId, String memberId, HttpServletResponse response) {
        System.out.println("=====,,"+memberId);
        if (memberId == "" && memberId == null) {
            throw new GlobalCommonExceptionHandler(20001, "请先登录");
        }
        CourseWebVoOrder  courseInfoOrder = courseClient.getCourseInfoOrder(courseId);
        UcenterMemberOrder   userInfoOrder= userClient.getUserById(memberId);
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(2);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }


    @Override
    public String payOrder(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = baseMapper.selectOne(wrapper);
        String pay = sendRequestToPay(order);
        return pay;
    }

    @Override
    public boolean isBuy(String courseId, String userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", userId);
        wrapper.eq("status", 1);
        Order order = baseMapper.selectOne(wrapper);
        if (order != null) {
            return true;
        }
        return false;
    }


    @Autowired
    private PayProperties payProperties;
    private String sendRequestToPay(Order order){
        try {
            //获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(
                    payProperties.getGatewayUrl(),
                    payProperties.getAppId(),
                    payProperties.getMerchantPrivateKey(),
                    "json",
                    payProperties.getCharset(),
                    payProperties.getAlipayPublicKey(),
                    payProperties.getSignType());

            //设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(payProperties.getReturnUrl());
            alipayRequest.setNotifyUrl(payProperties.getNotifyUrl());

            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = new String(order.getOrderNo().getBytes("ISO-8859-1"), "UTF-8");
            //付款金额，必填
            String total_amount =String.valueOf(order.getTotalFee());
            //订单名称，必填
            String subject = new String(order.getCourseTitle().getBytes("ISO-8859-1"), "UTF-8");
            //商品描述，可空
        String body = new String("".getBytes("ISO-8859-1"),"UTF-8");

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                    + "\"total_amount\":\"" + total_amount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"body\":\"" + body + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
            //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
            //		+ "\"total_amount\":\""+ total_amount +"\","
            //		+ "\"subject\":\""+ subject +"\","
            //		+ "\"body\":\""+ body +"\","
            //		+ "\"timeout_express\":\"10m\","
            //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

            //请求
            return alipayClient.pageExecute(alipayRequest).getBody();

            //输出
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}

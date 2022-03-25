package com.tzf.ordservice.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzf.commonutil.JwtUtils;
import com.tzf.commonutil.Resouce;
import com.tzf.ordservice.entity.Order;
import com.tzf.ordservice.entity.PayLog;
import com.tzf.ordservice.entity.PayProperties;
import com.tzf.ordservice.service.OrderService;
import com.tzf.ordservice.service.PayLogService;
import com.tzf.servicebase.config.DisableAuth;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/ordservice/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayProperties payProperties;

    @Autowired
    private PayLogService payLogService;

    Order order1=new Order();
    //1 生成订单的方法
    @DisableAuth
    @PostMapping("createOrder/{courseId}")
    public Resouce saveOrder(@PathVariable("courseId") String courseId, HttpServletRequest request, HttpServletResponse response) {
        //创建订单，返回订单号
        //创建订单，返回订单号

        String orderNo =
                orderService.createOrders(courseId, JwtUtils.getMemberIdByJwtToken(request), response);
        return Resouce.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @DisableAuth
    @GetMapping("getOrderInfo/{orderId}")
    public Resouce getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        BeanUtils.copyProperties(order,order1);
        return Resouce.ok().data("item",order);
    }

    /**
     * 支付
     * @param orderNo
     * @return
     */
    @DisableAuth
    @GetMapping("payOrder/{orderNo}")
    @Transactional
    public Resouce payOrder(@PathVariable String orderNo) {

        String result = orderService.payOrder(orderNo);
        return Resouce.ok().data("result",result);
    }

    /**\
     * 判断是否购买过该课程
     * @param courseId  课程id
     * @param memberId    用户id
     * @return   布尔值
     */
    @DisableAuth
    @GetMapping("isBuy/{courseId}/{memberId}")
    public Boolean isBuy(
            @PathVariable String courseId,
            @PathVariable String memberId) {

        boolean b=orderService.isBuy(courseId, memberId);

        return b;

    }




    /**
     * 支付宝验证函数
     * @param request
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @DisableAuth
    @RequestMapping("/notify")
    public void notifyUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                payProperties.getAlipayPublicKey(),
                payProperties.getCharset(),
                payProperties.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            //out.println("success");

        }else {//验证失败
            // out.println("fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }
    }

    /**
     * 支付宝支付成功回调函数
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @RequestMapping("/return")
    @Transactional
    @DisableAuth
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(
                params,
                payProperties.getAlipayPublicKey(),
                payProperties.getCharset(),
                payProperties.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            order1.setStatus(1);
            String orderNo = order1.getOrderNo();
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            orderService.update(order1, wrapper);
//            以上为更新订单状态，从未支付到支付状态

//            以下为订单支付成功后，记录日志状态
            PayLog payLog = new PayLog();
            payLog.setOrderNo(order1.getOrderNo());
            payLog.setAttr("订单已完成交易");
            payLog.setPayType(2);
            payLog.setTransactionId(trade_no);
            payLog.setOrderNo(out_trade_no);
            payLog.setTradeState("SUCCESS");
            payLog.setIsDeleted(false);

            payLog.setTotalFee(total_amount);
            payLogService.save(payLog);
            return "SUCCESS";
        } else {
            return "验签失败";
        }
    }

}


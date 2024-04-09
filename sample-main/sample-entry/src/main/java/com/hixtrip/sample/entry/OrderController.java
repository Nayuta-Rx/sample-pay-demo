package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * todo 这是你要实现的
 */
@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * todo 这是你要实现的接口
     *
     * @param commandOderCreateDTO 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public CommandPayDTO order(@RequestBody CommandOderCreateDTO commandOderCreateDTO) {
        try {
            log.info("商品下单，根据商品ID创建支付单开始 commandOderCreateDTO:{}", commandOderCreateDTO);
            //登录信息可以在这里模拟
            var userId = "sakula";
            commandOderCreateDTO.setUserId(userId);
            CommandPayDTO commandPayDTO = orderService.createOrder(commandOderCreateDTO);
            return commandPayDTO;
        } catch (Exception e) {
            log.info("商品下单，根据商品ID创建支付单失败 commandOderCreateDTO:{}", commandOderCreateDTO, e);
            return null;
        }
    }

    /**
     * todo 这是模拟创建订单后，支付结果的回调通知
     * 【中、高级要求】需要使用策略模式处理至少三种场景：支付成功、支付失败、重复支付(自行设计回调报文进行重复判定)
     *  策略模式定义了一系列算法，并将每个算法封装在独立的策略类中，使得它们可以相互替换。通过使用不同的策略对象，可以在运行时改变对象的行为。
     *  个人认为策略模式不适合使用在支付状态的判断场景
     *
     *  重复支付的方案处理
     *  重复支付 支付回调的时候根据不同渠道的支付单，以及订单是否完成 需要即使删除支付单来防止重复支付
     *  以及在生成新的支付单的时候需要清楚其他渠道的支付单
     *  但不能保证一定没问题， 可以使用任务调度做补偿策略  定时对账，重复的支付单做主动退款或人工介入
     *
     * @param request 入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(HttpServletRequest request) {
        try {
            log.info("支付回调，消息接收 {}", request.getParameter("trade_status"));
            if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
                Map<String, String> params = new HashMap<>();
                Map<String, String[]> requestParams = request.getParameterMap();
                for (String name : requestParams.keySet()) {
                    params.put(name, request.getParameter(name));
                }
                return orderService.verifySig(params);
            }
        } catch (Exception e) {
            log.error("支付回调，处理失败", e);
            return "false";
        }
        return null;
    }

}

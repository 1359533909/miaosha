package com.mxl.miaosha.controller;

import com.mxl.miaosha.domain.MiaoshaUser;
import com.mxl.miaosha.domain.OrderInfo;
import com.mxl.miaosha.redis.RedisService;
import com.mxl.miaosha.result.CodeMsg;
import com.mxl.miaosha.result.Result;
import com.mxl.miaosha.service.GoodsService;
import com.mxl.miaosha.service.MiaoshaUserService;
import com.mxl.miaosha.service.OrderService;
import com.mxl.miaosha.vo.GoodsVo;
import com.mxl.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
    RedisService redisService;
	
	@Autowired
    OrderService orderService;
	
	@Autowired
    GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                      @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }
    
}

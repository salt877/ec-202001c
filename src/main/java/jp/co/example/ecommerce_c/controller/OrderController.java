package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.service.OrderService;

@RequestMapping("/order")
@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("")
	public String order(OrderForm orderForm, BindingResult result) {
		orderService.order(orderForm);
		return "redirect:/order/toFinished";
	}
	
	@RequestMapping("/toFinished")
	public String toFinished() {
		return "order_confirm";
	}
}

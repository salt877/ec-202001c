package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.service.OrderService;

@RequestMapping("/order")
@Controller
public class OrderController {

	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("")
	public String order(OrderForm orderForm, BindingResult result) {
		orderService.order(orderForm);
		return "redirect:/order/toFinished";
	}
	
	@RequestMapping("/toFinished")
	public String toFinished() {
		return "order_finished";
	}
}

package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.service.ShowOrderConfirmService;

@Controller
@RequestMapping("/showOrderConfirm")
public class ShowOrderConfirmController {
	@Autowired
	private ShowOrderConfirmService showOrderConfirmService;
	
	@RequestMapping("")
	public String showOrderConfirm(Model model) {
		model.addAttribute("orderList", showOrderConfirmService.showInCart(1, 0));
		return "order_confirm";
	}
}

package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.service.ShowOrderHistoryService;

@Controller
@RequestMapping("/show-order-history")
public class ShowOrderHistoryController {
	@Autowired
	private ShowOrderHistoryService showOrderHistoryService;

	@RequestMapping("")
	public String showOrderHistory(Integer userId, Model model) {
		Order orderHistory = showOrderHistoryService.searchOrderHistoryByUserId(1).get(0);
		model.addAttribute("orderHistory", orderHistory);
		return "order_history";
	}
}

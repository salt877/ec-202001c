package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.service.ShowOrderHistoryService;

/**
 * 注文履歴を操作するコントローラ.
 * 
 * @author sota_adachi
 *
 */
@Controller
@RequestMapping("/show-order-history")
public class ShowOrderHistoryController {

	@Autowired
	private ShowOrderHistoryService showOrderHistoryService;

	@RequestMapping("")
	public String showOrderHistory(Model model) {
		Integer userId = 1;
		Order orderHistory = null;
		try {
			orderHistory = showOrderHistoryService.searchOrderHistoryByUserId(userId).get(0);
		} catch (Exception e) {
			model.addAttribute("orderHistorySize", 0);
			model.addAttribute("orderHistory", orderHistory);
			return "order_history";
		}
		model.addAttribute("orderHistorySize", orderHistory.getOrderItemList().size());
		model.addAttribute("orderHistory", orderHistory);
		return "order_history";
	}
}

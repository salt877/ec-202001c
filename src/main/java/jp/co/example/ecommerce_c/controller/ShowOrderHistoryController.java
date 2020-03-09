package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.service.ShowOrderHistoryService;

/**
 * 注文履歴を操作するコントローラ.
 * 
 * @author sota_adachi
 *
 */
@Controller
public class ShowOrderHistoryController {

	@Autowired
	private ShowOrderHistoryService showOrderHistoryService;

	@RequestMapping("/show_order_history")
	public String showOrderHistory(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = new User();
		if(loginUser != null) {
			user = loginUser.getUser();
		}else {
			//　この中の記述要検討
		}
		Integer userId = user.getId();
		
		Order orderHistory = null;
		try {
			List<Order> orderHistoryList = showOrderHistoryService.searchOrderHistoryByUserId(userId);
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

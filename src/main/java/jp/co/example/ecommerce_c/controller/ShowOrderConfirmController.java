package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.service.ShowOrderConfirmService;

/**
 * 注文確認画面を表示するコントローラ.
 * 
 * @author sota_adachi
 *
 */
@Controller
@RequestMapping("/show-order-confirm")
public class ShowOrderConfirmController {
	@Autowired
	private ShowOrderConfirmService showOrderConfirmService;

	@RequestMapping("")
	public String showOrderConfirm(Model model) {
		Integer userId = 1;
		List<Order> orderList = showOrderConfirmService.showInCart(userId);
		Order order = orderList.get(0);
		model.addAttribute("order", order);
		return "order_confirm";
	}
}

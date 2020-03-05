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
public class ShowOrderConfirmController {
	
	@Autowired
	private ShowOrderConfirmService showOrderConfirmService;

	@RequestMapping("/show_order_confirm")
	public String showOrderConfirm(Model model) {
		Integer userId = 1; //あとでuserIdに変える
		List<Order> orderList = showOrderConfirmService.showInCart(userId);
		Order order = orderList.get(0);
		model.addAttribute("order", order);
		return "order_confirm";
	}
}

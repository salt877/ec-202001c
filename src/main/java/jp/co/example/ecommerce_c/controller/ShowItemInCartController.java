package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.service.ShowItemInCartService;

/**
 * カート内の情報を操作するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/show-item-in-cart")
public class ShowItemInCartController {

	@Autowired
	private ShowItemInCartService showItemInCartService;

	@RequestMapping("")
	public String showItemInCart(Model model) {
		int userId = 1;
		List<Order> orderList = showItemInCartService.showItemInCart(userId);
		System.out.println("orderList:" + orderList);
		Order order = orderList.get(0);
		System.out.println("order.getOrderItemList().size():" + order.getOrderItemList().size());
		model.addAttribute("orderSize", order.getOrderItemList().size());
		System.out.println("order:" + order);
		model.addAttribute("order", order);
		return "cart_list";
	}
}

package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.service.ShowItemInCartService;

/**
 * カート内の情報を操作するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
public class ShowItemInCartController {

	@Autowired
	private ShowItemInCartService showItemInCartService;

	@RequestMapping("/show_item_in_cart")
	public String showItemInCart(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user = new User();
		if(loginUser != null) {
			user = loginUser.getUser();
		}else {
			//　この中の記述要検討
		}
		Integer userId = user.getId();
		
		Order order = null;
		try {
			order = showItemInCartService.showItemInCart(userId).get(0);
		}catch(Exception e) {
			model.addAttribute("orderSize", 0);
			model.addAttribute("order", order);
			return "cart_list";
		}
		model.addAttribute("orderSize", order.getOrderItemList().size());
		model.addAttribute("order", order);
		return "cart_list";
	}
}

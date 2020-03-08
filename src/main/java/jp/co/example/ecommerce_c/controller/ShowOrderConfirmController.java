package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.repository.UserRepository;
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
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@RequestMapping("/show_order_confirm")
	public String showOrderConfirm(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		User user1 = new User();
		if(loginUser != null) {
			user1 = loginUser.getUser();
		}else {
			//　この中の記述要検討
		}
		Integer userId = user1.getId();
		List<Order> orderList = showOrderConfirmService.showInCart(userId);
		Order order = orderList.get(0);
		model.addAttribute("order", order);

		User user = userRepository.findByEmail("test@test.co.jp");
		model.addAttribute("user", user);
		return "order_confirm";
	}
}

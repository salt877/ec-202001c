package jp.co.example.ecommerce_c.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/show-order-confirm")
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
	
	@RequestMapping("")
	public String showOrderConfirm(Model model) {
		Integer userId = 1; //あとでuserIdに変える
		List<Order> orderList = showOrderConfirmService.showInCart(userId);
		Order order = orderList.get(0);
		model.addAttribute("order", order);

		User user = userRepository.findByEmail("test@test.co.jp");
		StringBuilder sb = new StringBuilder(user.getZipcode());
		sb.insert(3, "-");
		user.setZipcode(sb.toString());
		sb = new StringBuilder(user.getTelephone());
		sb.insert(3, "-");
		sb.insert(7, "-");
		user.setTelephone(sb.toString());
		model.addAttribute("user", user);
		return "order_confirm";
	}
}

package jp.co.example.ecommerce_c.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import jp.co.example.ecommerce_c.service.RegisterUserService;
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
	private RegisterUserService registerUserService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	/**
	 * 注文確認画面を表示します.
	 * 
	 * @param model モデル
	 * @param loginUser ユーザ情報
	 * @return 注文確認画面
	 */
	@RequestMapping("/show_order_confirm")
	public String showOrderConfirm(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = 0;
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		List<Order> orderList = showOrderConfirmService.showInCart(userId, loginUser);

		// カートの中身に商品がない場合は商品一覧画面に遷移させる
		Order order = new Order();
		order = orderList.get(0);
		if (order.getTotalPrice() == 0) {
			return "redirect:/";			
		}
		model.addAttribute("order", order);

		// 届け先フォームに事前にユーザー情報を入力しておく為、ログインユーザーのメールアドレスにてユーザーを特定し
		// リクエストスコープに格納
		User user = registerUserService.searchUserByEmail(loginUser.getUser().getEmail());
		model.addAttribute("user", user);
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = sdf.format(nowDate);
		model.addAttribute("nowDate", nowDateStr);
		return "order_confirm";
	}
}

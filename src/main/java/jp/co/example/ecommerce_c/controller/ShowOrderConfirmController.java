package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.OrderForm;
import jp.co.example.ecommerce_c.repository.OrderRepository;
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
		Integer userId = 0;
		List<OrderItem> orderItemList = new ArrayList<>();
		if(loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		List<Order> orderList = showOrderConfirmService.showInCart(userId, loginUser);
		
		//カートの中身に商品がない場合は商品一覧画面に遷移させる
		Order order = new Order();
		try {
			order = orderList.get(0);
		}catch(Exception e){
			return "redirect:/";
		}
		
		
		//この記述いらない？コメントアウト後動作に問題なければ削除
//		List<OrderItem> idOrderItemList = order.getOrderItemList();
//		for(int i = 0; i < orderItemList.size(); i++) {
//			idOrderItemList.add(orderItemList.get(i));
//		}
//		order.setOrderItemList(idOrderItemList);
		model.addAttribute("order", order);
		
		//届け先フォームに事前にユーザー情報を入力しておく為、ログインユーザーのメールアドレスにてユーザーを特定し
		//リクエストスコープに格納
		User user = userRepository.findByEmail(loginUser.getUser().getEmail());
		model.addAttribute("user", user);
		return "order_confirm";
	}
}

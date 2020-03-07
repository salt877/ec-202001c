package jp.co.example.ecommerce_c.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
import jp.co.example.ecommerce_c.repository.OrderRepository;
import jp.co.example.ecommerce_c.repository.UserRepository;
import jp.co.example.ecommerce_c.service.AddItemToCartService;

/**
 * カートの中身に商品を追加するコントローラ.
 * 
 * @author hatakeyamakouta
 *
 */
@Controller
@RequestMapping("/addItemToCart")
public class AddItemToCartColler {

	@Autowired
	private AddItemToCartService addItemToCartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * カートに商品を追加するメソッド.
	 * 
	 * @param addItemToCartForm リクエストパラメータで送られてくる値
	 * @param loginUser ログイン中のユーザーid
	 * @return ショッピングカート一覧画面
	 */
	@RequestMapping("")
	public String addItem(AddItemToCartForm addItemToCartForm, @AuthenticationPrincipal LoginUser loginUser) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		Integer userId = 0;
		if(principal instanceof LoginUser) {
			userId = ((LoginUser)principal).getUser().getId();
		}else {
			userId = session.getId().hashCode();
			System.out.println(userId);
		}
		addItemToCartService.addItem(addItemToCartForm, userId);
		return "redirect:/show-item-in-cart";
	}
}

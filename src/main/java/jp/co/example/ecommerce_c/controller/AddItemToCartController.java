package jp.co.example.ecommerce_c.controller;

import java.util.List;

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
public class AddItemToCartController {

	@Autowired
	private AddItemToCartService addItemToCartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * カートに商品を追加するメソッド.
	 * 
	 * @param addItemToCartForm リクエストパラメータで送られてくる値
	 * @param loginUser ログイン中のユーザーid
	 * @return ショッピングカート一覧画面
	 */
	@RequestMapping("/add_item_to_cart")
	public String addItem(AddItemToCartForm addItemToCartForm, @AuthenticationPrincipal LoginUser loginUser) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer userId = 0;
		if(principal instanceof LoginUser) {
			userId = ((LoginUser)principal).getUser().getId();
		}else {
			List<User> userList = userRepository.findAll();
			for(User userForId : userList) {
				if(userForId.getId() > userId) {
					userId = userForId.getId() + 1;
				}
			}			
		}
		addItemToCartService.addItem(addItemToCartForm, userId);
		return "redirect:/show_item_in_cart";
	}
}

package jp.co.example.ecommerce_c.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.form.AddItemToCartForm;
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
	private HttpSession session;
	
	/**
	 * カートに商品を追加するメソッド.
	 * 
	 * @param addItemToCartForm リクエストパラメータで送られてくる値
	 * @param loginUser ログイン中のユーザーid
	 * @return ショッピングカート一覧画面
	 */
	@RequestMapping("/add_item_to_cart")
	public String addItem(AddItemToCartForm addItemToCartForm, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = 0;
		if(loginUser != null) {
			userId = loginUser.getUser().getId();
		}else {
			userId = session.getId().hashCode();
		}
		addItemToCartService.addItem(addItemToCartForm, userId, loginUser);
		return "redirect:/show_item_in_cart";
	}
}
package jp.co.example.ecommerce_c.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ログイン画面へ遷移させるコントローラー.
 * 
 * @author katsuya.fujishima
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private HttpSession session;

	/**
	 * ログイン画面へフォワードするメソッド.
	 * 
	 * @param model リクエストスコープ
	 * @param error エラー
	 * @return ログイン画面
	 */
	@RequestMapping("/show_login")
	public String showLogin(Model model, @RequestParam(required = false) String error) {
		System.err.println("login error:" + error);
		if (error != null) {
			System.err.println("login failed");
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
		}
		return "login";
	}

}

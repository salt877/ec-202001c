package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.RegisterForm;
import jp.co.example.ecommerce_c.service.RegisterService;

/**
 * ユーザ情報を操作するコントローラー.
 * 
 * @author rinashioda
 *
 */
@Controller
@RequestMapping("/")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public RegisterForm setUpForm() {
		return new RegisterForm();
	}

	/**
	 * ユーザ登録画面を出力します.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/registration")
	public String showRegistration() {
		return "register_user";
	}

	public String registerUser(@Validated RegisterForm form, BindingResult result) {

		// メールアドレスが重複している場合
		User duplicationUser = registerService.findByEmail(form.getEmail());
		if (duplicationUser != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		// エラーがあれば登録画面に戻る
		if (result.hasErrors()) {
			return showRegistration();
		}
		
		User user = new User();
		BeanUtils.copyProperties(form, user);

		registerService.insert(user);

		return "redirect:/login";

	}

}

package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.form.RegisterUserForm;
import jp.co.example.ecommerce_c.service.RegisterUserService;

/**
 * ユーザ情報を操作するコントローラー.
 * 
 * @author rinashioda
 *
 */
@Controller
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザ登録画面を出力します.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("/show_register_user")
	public String showRegisterUser() {
		return "register_user";
	}

	/**
	 * ユーザ情報を登録します.
	 * 
	 * @param form	 ユーザ情報用フォーム
	 * @param result　エラー格納オブジェクト
	 * @return　ログイン画面へリダイレクト
	 */
	@RequestMapping("/register_user")
	public String registerUser(@Validated RegisterUserForm form, BindingResult result) {

		// メールアドレスが重複している場合
		User duplicationUser = registerUserService.searchUserByEmail(form.getEmail());
		if (duplicationUser != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		// エラーがあれば登録画面に戻る
		if (result.hasErrors()) {
			return showRegisterUser();
		}
		
		User user = new User();
		BeanUtils.copyProperties(form, user);
		registerUserService.registerUser(user);

		return "redirect:/show_login";

	}

}
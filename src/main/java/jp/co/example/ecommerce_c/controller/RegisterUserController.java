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
import jp.co.example.ecommerce_c.service.RegisterUserUserService;

/**
 * ユーザ情報を操作するコントローラー.
 * 
 * @author rinashioda
 *
 */
@Controller
@RequestMapping("/")
public class RegisterUserUserController {

	@Autowired
	private RegisterUserUserService RegisterUserUserService;

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
	@RequestMapping("/registration")
	public String showRegistration() {
		return "RegisterUser_user";
	}

	/**
	 * ユーザ情報を登録します.
	 * 
	 * @param form	 ユーザ情報用フォーム
	 * @param result　エラー格納オブジェクト
	 * @return　ログイン画面へリダイレクト
	 */
	@RequestMapping("/RegisterUser")
	public String RegisterUserUser(@Validated RegisterUserForm form, BindingResult result) {

		// メールアドレスが重複している場合
		User duplicationUser = RegisterUserService.findByEmail(form.getEmail());
		if (duplicationUser != null) {
			result.rejectValue("email", "", "そのメールアドレスはすでに使われています");
		}

		// エラーがあれば登録画面に戻る
		if (result.hasErrors()) {
			return showRegistration();
		}
		
		User user = new User();
		BeanUtils.copyProperties(form, user);
		RegisterUserService.(user);

		return "redirect:/showLogin";

	}

}
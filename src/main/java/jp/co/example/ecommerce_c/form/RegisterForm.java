package jp.co.example.ecommerce_c.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ユーザ情報登録時に使用するフォーム.
 * 
 * @author rinashioda
 *
 */
public class RegisterForm {

	/** 登録ユーザ名 */
	@NotBlank(message = "お名前を入力して下さい")
	private String name;
	/** メールアドレス */
	@Email(message = "アドレスが不正です")
	@NotBlank(message = "メールアドレスを入力して下さい")
	private String email;
	/** パスワード */
	@NotBlank(message = "パスワードを入力して下さい")
	private String password;
	/** 郵便番号 */
	private String zipcode;
	/** 住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力して下さい")
	private String telephone;
	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力して下さい")
	private String confirmPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
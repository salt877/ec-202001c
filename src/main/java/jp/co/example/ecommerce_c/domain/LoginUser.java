package jp.co.example.ecommerce_c.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.co.example.ecommerce_c.domain.User;

public class LoginUser extends User{

	private static final long SerialVersionUID = 1L;
	/** ユーザ情報 */
	private final User User;
	
	/**
	 * 通常のユーザ情報に加え、認可用ロールを設定する。
	 * 
	 * @param User　ユーザ情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(User User, Collection<GrantedAuthority> authorityList) {
		super(User.getEmail(), User.getPassword(), authorityList);
		this.User = User;
	}

	public User getUser() {
		return User;
	}

}

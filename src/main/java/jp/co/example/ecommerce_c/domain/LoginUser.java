package jp.co.example.ecommerce_c.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * ユーザのログイン情報を格納するエンティティ.
 * 
 * @author katsuya.fujishima
 *
 */
public class LoginUser extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = 1L;
	/** ユーザ情報 */
	private final User user;
	
	/**
	 * 通常のユーザ情報に加え、認可用ロールを設定する.
	 * 
	 * @param User　ユーザ情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(), authorityList);
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

}

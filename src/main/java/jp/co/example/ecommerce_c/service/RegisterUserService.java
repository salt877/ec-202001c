package jp.co.example.ecommerce_c.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.User;
import jp.co.example.ecommerce_c.repository.UserRepository;

/**
 * ユーザ情報を操作するサービス.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザ情報を登録します.
	 * 
	 * パスワードのハッシュ化をしてから登録
	 * 
	 * @param user ユーザ情報
	 */
	public void registerUser(User user) {
		
		//パスワードのハッシュ化
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		
		userRepository.insert(user);
	}

	/**
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返します
	 */
	public User searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}

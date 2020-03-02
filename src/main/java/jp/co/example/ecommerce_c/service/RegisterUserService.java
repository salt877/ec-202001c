package jp.co.example.ecommerce_c.service;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * ユーザ情報を登録します.
	 * 
	 * @param user ユーザ情報
	 */
	public void registerUser(User user) {
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

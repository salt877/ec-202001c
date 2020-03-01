package jp.co.example.ecommerce_c.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.LoginUser;
import jp.co.example.ecommerce_c.repository.UserRepository;

/**
 * ログイン後のユーザ情報に権限情報を付与するサービスクラス.
 * 
 * @author katsuya.fujishima
 *
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
		/** DBから情報を得るためのリポジトリ */
		@Autowired
		private UserRepository userRepository;
		
		@Override
		public UserDetails loadUserByUsername(String email)
				throws UsernameNotFoundException {
			User user = userRepository.findByEmail(email);
			if(user == null) {
				throw new UsernameNotFoundException("そのEmailは登録されていません。");
			}
			Collection<GrantedAuthority> authorityList = new ArrayList<>();
			authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
			return new LoginUser(user,authorityList);
		}

}

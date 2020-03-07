package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author rinashioda
 *
 */
@Repository
public class UserRepository {

	/**
	 * Userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザ情報を挿入します.
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users(name,email,password,zipcode,address,telephone)VALUES(:name,:email,:password,:zipcode,:address,:telephone)";
		template.update(sql, param);
	}

	/**
	 * メールアドレスからユーザ情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返します
	 */
	public User findByEmail(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone from users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * ユーザー情報を全件取得sルウ
	 * 
	 * @return 全てのユーザー情報
	 */
	public List<User> findAll(){
		String sql = "SELECT id,name,email,password,zipcode,address,telephone from users ORDER BY id";
		return template.query(sql, USER_ROW_MAPPER);
	}
	
	public List<User> findById(Integer id) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone from users where id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.query(sql, param, USER_ROW_MAPPER);
		}
	}

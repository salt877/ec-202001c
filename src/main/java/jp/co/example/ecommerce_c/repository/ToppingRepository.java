package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.Topping;

/**
 * toppingsテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Toppingオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		return topping;
	};
	
	/**
	 * 全てのトッピング詳細を取得する.
	 * 
	 * @return トッピング詳細全件
	 */
	public List<Topping> findAll(){
		String sql = "SELECT id, name, price_m, price_l FROM toppings";
		List<Topping> toppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return toppingList;
	}
	
	/**
	 * idに一致したトッピング詳細を取得する.
	 * 
	 * @param id ID
	 * @return IDに一致したトッピング詳細
	 */
	public Topping findById(Integer id) {
		String sql = "SELECT id, name, price_m, price_l FROM toppings WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
	}
}

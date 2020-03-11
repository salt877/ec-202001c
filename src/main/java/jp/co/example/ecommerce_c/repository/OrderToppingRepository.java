package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.OrderTopping;

/**
 * order_toppingsテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class OrderToppingRepository {

	/**
	 * OrderToppingオブジェクトを生成するローマッパー
	 */
	private static final RowMapper<OrderTopping> ORDERTOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setToppingId(rs.getInt("topping_id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
		return orderTopping;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * order_toppingsテーブルにデータを挿入する.
	 * 
	 * @param orderTopping 挿入するインスタンス
	 */
	public void insert(OrderTopping orderTopping) {
		String insertSql = "INSERT INTO order_toppings(topping_id, order_item_id) VALUES(:toppingId, :orderItemId);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(insertSql, param);
	}

	/**
	 * orderItemIdが一致するトッピングを削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 */
	public void deleteItemById(Integer orderItemId) {
		String sql = "DELETE FROM order_toppings WHERE order_item_id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}
	
	/**
	 * orderItemIdが一致するオーダートッピングを取得するメソッド.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return 注文したトッピングのリスト
	 */
	public List<OrderTopping> findByOrderItemId(Integer orderItemId){
		String sql = "SELECT id, topping_id, order_item_id FROM order_toppings WHERE order_item_id = :orderItemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		return template.query(sql, param, ORDERTOPPING_ROW_MAPPER);
	}
}

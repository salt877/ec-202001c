package jp.co.example.ecommerce_c.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.OrderItem;

/**
 * order_itemsテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	/**
	 * テーブルにデータ挿入時、挿入したオブジェクトのidを取得する為に必要なメソッド.
	 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	/**
	 * orderItemsテーブルにデータを挿入する.
	 * 
	 * @param orderItem 挿入するインスタンス
	 * @return 挿入したインスタンス
	 */
	public OrderItem save(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());
		return orderItem;
	}

	/**
	 * order_itemsテーブルに注文商品を挿入する.
	 * 
	 * @param orderItem 注文商品
	 */
	public void insert(OrderItem orderItem) {
		String insertSql = "INSERT INTO order_items(item_id, order_id, quantity, size) "
				+ "VALUES(:itemId, :orderId, :quantity, :size);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(insertSql, param);
	}

	/**
	 * idが一致する商品を削除するメソッド.
	 * 
	 * @param orderItemId カート内の商品ID
	 */
	public void deleteItemById(Integer orderItemId) {
		String sql = "DELETE FROM order_items WHERE id = :orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}
}

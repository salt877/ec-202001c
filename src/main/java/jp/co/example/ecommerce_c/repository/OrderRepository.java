package jp.co.example.ecommerce_c.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.Order;
import jp.co.example.ecommerce_c.domain.OrderItem;
import jp.co.example.ecommerce_c.domain.OrderTopping;
import jp.co.runy.bbs.joined.domain.JoinedComment;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author hatakeyamakouta
 *
 */
@Repository
public class OrderRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new LinkedList<Order>();
		List<OrderItem> OrderItemList = null;
		List<OrderTopping> OrderToppingList = null;
		long beforeOrderId = 0;
		while (rs.next()) {
			int nowOrderId = rs.getInt("id");
			if (nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(nowOrderId);
				order.setUserId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				OrderItemList = new ArrayList<OrderItem>();
				orderList.add(order);
			}
			if (rs.getInt("order_item_id") != 0) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("order_item_id"));
				orderItem.setItemId(rs.getInt("order_item_item_id"));
				orderItem.setOrderId(rs.getInt("order_item_order_id"));
				orderItem.setQuantity(rs.getInt("order_item_quantity"));
				orderItem.setSize(rs.getString("order_item_size"));
				
				commentList.add(comment);
			}
			beforeArticleId = nowArticleId;
		}
		return articleList;
	};
	
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) ->{
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};
	
	/**
	 * 引数のuserIdとstatusに一致した注文情報を取得する.
	 * 
	 * @param userId ユーザーid
	 * @param status 注文状態(0.注文前 1.未入金 2.入金済 3.発送済 4.配送完了 9.キャンセル)
	 * @return 引数のuserIdとstatusに一致した注文情報
	 */
	public List<Order> findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method FROM orders WHERE user_id = :userId AND status = :status;";
		System.out.println(status);
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		return template.query(sql, param, ORDER_ROW_MAPPER);
	}
		
	/**
	 * ordersテーブルに注文情報を挿入する.
	 * 
	 * @param order 注文情報
	 */
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String insertSql = "INSERT INTO orders(user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, "
				+ "destination_address, destination_tel, delivery_time, payment_method) "
				+ "VALUES(:userId, :status, :totalPrice, :orderDate, :destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, "
				+ ":destinationTel, :deliveryTime, :paymentMethod);";
		template.update(insertSql, param);
	}
	
	public void update(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String updateSql = "UPDATE orders SET total_price = :totalPrice WHERE user_id = :userId AND status = :status;";
		template.update(updateSql, param);
	}
}

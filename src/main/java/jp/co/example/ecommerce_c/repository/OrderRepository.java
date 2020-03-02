package jp.co.example.ecommerce_c.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import jp.co.example.ecommerce_c.domain.Order;

/**
 * ordersテーブルを操作するリポジトリ
 * 
 * @author hatakeyamakouta
 *
 */
public class OrderRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

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
	
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		String sql = "SELECT id, user_id, total_price, order_date, destination_name, destination_email, destination_zipcode, "
				+ "destination_address, destination_tel, delivery_time, payment_method "
				+ "FROM orders WHERE user_id = :id AND status = :status;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("user_id", userId).addValue("status", status);
		return template.queryForObject(sql, param, ORDER_ROW_MAPPER);
	}
	
	public void insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String insertSql = "INSERT INTO orders(user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, "
				+ "destination_address, destination_tel, delivery_time, payment_method "
				+ "VALUES(:userId, :status, :totalPrice, :orderDate, :destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, "
				+ ":destinationTel, :deliveryTime, :paymentMethod;";
		template.update(insertSql, param);
	}
}

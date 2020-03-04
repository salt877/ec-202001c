package jp.co.example.ecommerce_c.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.ecommerce_c.domain.Item;

@Repository
public class ItemRepository {

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品一覧情報をID順で取得します.
	 * 
	 * @return 商品一覧
	 */
	public List<Item> findAll() {

		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY id";

		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);

		return itemList;
	}

	/**
	 * 商品名から商品を(曖昧)検索します.
	 * 
	 * @param name 商品名
	 * @return 検索された商品の一覧
	 */
	public List<Item> findByItemName(String name) {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items WHERE name LIKE :name ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");

		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * idから商品詳細情報を取得する
	 * 
	 * @param id ID
	 * @return IDに一致した商品詳細情報
	 */
	public Item findById(Integer id) {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, ITEM_ROW_MAPPER);
	}
	
	//しお
	
	public List<Item> 
		
		
		
	}
	
	

}

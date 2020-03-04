package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.domain.Topping;
import jp.co.example.ecommerce_c.repository.ItemRepository;
import jp.co.example.ecommerce_c.repository.ToppingRepository;

/**
 * 商品詳細を操作するサービス.
 * 
 * @author hatakeyamakouta
 *
 */
@Service
@Transactional
public class ShowDetailService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	/**
	 * 商品詳細を取得する.
	 * 
	 * @param id ID
	 * @return IDに一致する商品詳細
	 */
	public Item showDetail(Integer id) {
		return itemRepository.findById(id);
	}
	
	/**
	 * 全てのトッピング詳細を取得する.
	 * 
	 * @return トッピング詳細全件
	 */
	public List<Topping> showTopping() {
		return toppingRepository.findAll();
	}
}

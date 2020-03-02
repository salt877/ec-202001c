package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

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
	 * 全てのトッピング詳細.
	 * 
	 * @return トッピング詳細全件
	 */
	public List<Topping> showTopping() {
		return toppingRepository.findAll();
	}
}

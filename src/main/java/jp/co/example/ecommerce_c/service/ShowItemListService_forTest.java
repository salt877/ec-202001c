package jp.co.example.ecommerce_c.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.repository.ItemRepository;

/**
 * 商品一覧画面を操作するサービス.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class ShowItemListService_forTest {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報を全件検索します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> showList() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

	/**
	 * 商品名から商品を(曖昧)検索します.
	 * 
	 * @param name 商品名
	 * @return 検索された商品の情報一覧
	 */
	public List<Item> searchByItemName(String name) {
		return itemRepository.findByItemName(name);
	}

	public List<Item> searchBySomeway(Integer methodNum){
		if(methodNum == 1) {
			return itemRepository.orderByLowerMsizePrice();
		}else if(methodNum == 2) {
			return itemRepository.orderByHigherMsizePrice();
		}else if(methodNum == 3) {
			return itemRepository.orderByLowerLsizePrice();
		}else {
			return itemRepository.orderByHigherLsizePrice();
		}
	}

	public List<Item> paging(Integer page) {
		return itemRepository.showPage(page);
	}

	/**
	 * オートコンプリート機能.
	 * 
	 * @param itemList 商品一覧
	 * @return オートコンプリート用JavaScriptの配列の文字列
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}
}

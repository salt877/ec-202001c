package jp.co.example.ecommerce_c.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.form.SortItemListForm;
import jp.co.example.ecommerce_c.repository.ItemRepository;

/**
 * 商品一覧画面を操作するサービス.
 * 
 * @author rinashioda
 *
 */
@Service
@Transactional
public class ShowItemListService {

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

	/**
	 * 
	 * @param methodNumber
	 * @return
	 */
	public List<Item> searchBySomeway(SortItemListForm form) {
		if (form.getSort().equals(1)) {
			return itemRepository.orderByLowerMsizePrice();
		} else if (form.getSort().equals(2)) {
			return itemRepository.orderByHigherMsizePrice();
		} else if (form.getSort().equals(3)) {
			return itemRepository.orderByLowerLsizePrice();
		} else {
			return itemRepository.orderByHigherLsizePrice();
		}
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

	/**
	 * Mサイズの商品の価格が安い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByLowerMsizePrice() {
		return itemRepository.orderByLowerMsizePrice();
	}

	/**
	 * Mサイズの商品の価格が高い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByHigherMsizePrice() {
		return itemRepository.orderByHigherMsizePrice();
	}

	/**
	 * Lサイズの商品の価格が安い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByLowerLsizePrice() {
		return itemRepository.orderByLowerLsizePrice();
	}

	/**
	 * Lサイズの商品の価格が高い順番で表示します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> orderByHigherLsizePrice() {
		return itemRepository.orderByHigherLsizePrice();
	}

}

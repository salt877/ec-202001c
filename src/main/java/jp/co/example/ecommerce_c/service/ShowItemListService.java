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
	public List<Item>showList(){
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}
	
	/**
	 * 商品名から商品を(曖昧)検索します.
	 * 
	 * @param name 商品名
	 * @return　検索された商品の情報一覧
	 */
	public List<Item> searchByItemName(String name){
		return itemRepository.findByItemName(name);
	}
	
	/**
	 * ページング機能.
	 * 
	 * @param page 表示させたいページ数
	 * @param size 1ページに表示させる商品数
	 * @param itemList 絞り込み対象リスト
	 * @return 1ページに表示されるサイズ分の商品一覧情報
	 */
//	public Page<List<Item>> showListPaging(int page, int size, List<List<Item>> AllItemList) {
//
//		page--;
//
//		int startItemCount = page * size;
//
//		List<List<Item>> list;
//
//		if (AllItemList.size() < startItemCount) {
//			list = Collections.emptyList();
//		} else {
//			int toIndex = Math.min(startItemCount + size, AllItemList.size());
//			list = AllItemList.subList(startItemCount, toIndex);
//		}
//
//		Page<List<Item>> itemPage = new PageImpl<List<Item>>(list, PageRequest.of(page, size), AllItemList.size());
//		return itemPage;
//	}
	
	/**
	 * オートコンプリート機能.
	 * 
	 * @param itemList 商品一覧
	 * @return　オートコンプリート用JavaScriptの配列の文字列
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

	
	public List<Item> orderByLowerMsizePrice(){
		return itemRepository.orderByHigherMsizePrice();
	}
	public List<Item> orderByHigherMsizePrice(){
		return itemRepository.orderByHigherMsizePrice();
	}
	public List<Item> orderByLowerLsizePrice(){
		return itemRepository.orderByHigherMsizePrice();
	}
	public List<Item> orderByHigherLsizePrice(){
		return itemRepository.orderByHigherMsizePrice();
	}
	
	
	}
	
	
	

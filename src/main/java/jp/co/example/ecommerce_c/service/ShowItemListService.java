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
	 * @param size 1ページに表示させる従業員数
	 * @param itemList 絞り込み対象リスト
	 * @return 1ページに表示されるサイズ分の商品一覧情報
	 */
	public Page<Item> showListPaging(int page, int size, List<Item> itemList) {

		page--;

		int startItemCount = page * size;

		List<Item> list;

		if (itemList.size() < startItemCount) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItemCount + size, itemList.size());
			list = itemList.subList(startItemCount, toIndex);
		}

		Page<Item> itemPage = new PageImpl<Item>(list, PageRequest.of(page, size), itemList.size());
		return itemPage;
	}

}

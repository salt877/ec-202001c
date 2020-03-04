package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.service.ShowItemListService;

/**
 * 商品一覧情報を出力するコントローラ.
 * 
 * @author rinashioda
 *
 */
@Controller
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	// 1ページに表示する商品数は9個
	private static final int VIEW_SIZE = 9;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @param       page 出力したいページ数
	 * @param       searchName 検索文字列
	 * @return 商品一覧画面
	 */
	@RequestMapping("/")
	public String showItemList(Model model, Integer page, String searchName) {

//		List<Integer> numberList = new ArrayList<>();
//		for (int i = 1; i <= 18 ; i++) {
//			numberList.add(i);
//		}
//		
//		List<Item> itemList = new ArrayList<>();
//		List<Integer> threeItem = new ArrayList<>();
//		//System.out.println(itemList);
//		for (int i = 0; i < numberList.size(); i++) {
//			threeItem.add(numberList.get(i));			
//			if(itemList.size() % 3 == 0) {
//				itemList.add(threeItem);				
		List<Item> itemList = showItemListService.showList();

		// ページング機能を追加
		if (page == null) {
			page = 1;
		}

		// 商品名検索機能
		if (searchName != null) {
			itemList = showItemListService.searchByItemName(searchName);
			model.addAttribute("searchName", searchName);
		}
		if (itemList.size() == 0) {
			model.addAttribute("message", "該当する商品がありません");
			itemList = showItemListService.showList();
		}

		Page<Item> itemPage = showItemListService.showListPaging(page, VIEW_SIZE, itemList);
		model.addAttribute("itemPage", itemPage);

		List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
		model.addAttribute("pageNumbers", pageNumbers);

		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		return "item_list";
	}

	/**
	 * ページングのリンクに使うページ数をスコープに格納
	 * 
	 * @param model    モデル
	 * @param itemPage ぺージング情報
	 * @return
	 */
	public List<Integer> calcPageNumbers(Model model, Page<Item> itemPage) {
		int totalPages = itemPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

}

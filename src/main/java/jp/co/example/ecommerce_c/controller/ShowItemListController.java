package jp.co.example.ecommerce_c.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.ecommerce_c.domain.Item;
import jp.co.example.ecommerce_c.form.SortItemListForm;
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

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public SortItemListForm setUpSortItemListForm() {
		return new SortItemListForm();
	}

	@Autowired
	private HttpSession session;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @param form 並び順を変更するフォーム
	 * @param model      モデル
	 * @param searchName 検索文字列
	 * @return 商品一覧画面
	 */
	@RequestMapping("/")
	public String showItemList(SortItemListForm form, Model model, String searchName) {

		Integer searchNumber = form.getSort();
		List<Item> allItemList = showItemListService.showList();

		//トップページはMサイズの価格が安い順番で表示
		if (form.getSort() == null) {
			form.setSort(0);
		}

		List<Item> itemList = showItemListService.searchBySomeway(form);
		Integer pageNumber = 0;

		if (pageNumber % 9 != 0) {
			pageNumber = itemList.size() / 9 + 1;
		} else {
			pageNumber = itemList.size() / 9;
		}
		List<Integer> pageNumberList = new ArrayList<>();
		for (int i = 2; i <= pageNumber; i++) {
			pageNumberList.add(i);
		}
		model.addAttribute("pageNumberList", pageNumberList);

		List<List<List<Item>>> groupItemList = new ArrayList<>();
		List<List<Item>> nineItemList = new ArrayList<>();
		List<Item> threeItemList = new ArrayList<>();
		for (int i = 1; i <= itemList.size(); i++) {
			threeItemList.add(itemList.get(i - 1));
			if (i % 3 == 0 || i == itemList.size()) {
				nineItemList.add(threeItemList);
				threeItemList = new ArrayList<>();
				if (i % 9 == 0 || i == itemList.size()) {
					groupItemList.add(nineItemList);
					nineItemList = new ArrayList<>();
				}
			}
		}
		session.setAttribute("groupItemList", groupItemList);
		model.addAttribute("nineItemList", groupItemList.get(0));

		// 商品名検索機能
		if (searchName != null) {
			itemList = showItemListService.searchByItemName(searchName);
			// ページングの数字からも検索できるように検索文字列をスコープに格納しておく
			model.addAttribute("searchName", searchName);

			// 検索結果を3×3の横並びにする
			List<List<List<Item>>> groupItemList2 = new ArrayList<>();
			List<List<Item>> nineItemList2 = new ArrayList<>();
			List<Item> threeItemList2 = new ArrayList<>();
			for (int i = 1; i <= itemList.size(); i++) {
				threeItemList2.add(itemList.get(i - 1));
				if (i % 3 == 0 || i == itemList.size()) {
					nineItemList2.add(threeItemList2);
					threeItemList2 = new ArrayList<>();
					if (i % 9 == 0 || i == itemList.size()) {
						groupItemList2.add(nineItemList2);
						nineItemList2 = new ArrayList<>();
					}
				}

			}
			try {
				session.setAttribute("groupItemList", groupItemList2);
				model.addAttribute("nineItemList", groupItemList2.get(0));
			} catch (Exception e) {
				model.addAttribute("searchName", searchName);
				model.addAttribute("message", "該当する商品がありません");
				itemList = showItemListService.showList();
			}
		}
		// オートコンプリート
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(allItemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		model.addAttribute("searchNumber", searchNumber);

		return "item_list";
	}

	/**
	 * ページング機能を実装します.
	 * 
	 * @param form 並び順を選択するフォーム
	 * @param page ページ数
	 * @param searchName 検索文字列
	 * @param model　モデル
	 * @return 商品一覧画面の○ページ目
	 */
	@RequestMapping("/to_other_page")
	public String toOtherPage(SortItemListForm form,Integer page, String searchName,Model model) {
		List<Item> itemList = showItemListService.showList();
		Integer pageNumber = itemList.size() / 9;
		List<Integer> pageNumberList = new ArrayList<>();
		for (int i = 1; i <= pageNumber; i++) {
			if (i == page) {
				continue;
			}
			pageNumberList.add(i);
		}
		model.addAttribute("pageNumberList", pageNumberList);
		model.addAttribute("searchName", searchName);
		
		List<List<List<Item>>> groupItemList = (List<List<List<Item>>>) session.getAttribute("groupItemList");
		model.addAttribute("nineItemList", groupItemList.get(page - 1));

		// オートコンプリート
		List<Item> allItemList = showItemListService.showList();
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(allItemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		return "item_list";
	}

}

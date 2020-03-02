package jp.co.example.ecommerce_c.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jp.co.example.ecommerce_c.service.ShowItemService;

@Controller
public class ShowListController {
	
	@Autowired
	private ShowItemService showItemService;
	
	private static final int VIEW_SIZE = 9;
	
	public String showList()

}

package com.zqw.gmh.count.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zqw.gmh.count.common.BindInfo;
import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.service.CountTypeService;


@Controller
public class IndexController {
	@Autowired
	private CountTypeService countTypeService;
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("index1");
		List<CountType> cts = countTypeService.findAll();
		mav.addObject(BindInfo.COUNT_TYPE, cts);
		return mav;
	}
	
	@RequestMapping("/toType")
	public ModelAndView toType(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("type");
		
		return mav;
	}
	
	@RequestMapping("/toStastics")
	public ModelAndView toStastics(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("stastics");
		
		return mav;
	}
	
}

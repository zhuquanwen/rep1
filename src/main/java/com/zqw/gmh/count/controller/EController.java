package com.zqw.gmh.count.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EController {
	@RequestMapping("/500")
	public String page500(){
		return "500";
	}
	@RequestMapping("/404")
	public String page404(){
		return "404";
	}
	@RequestMapping("/502")
	public String page502(){
		return "502";
	}
}

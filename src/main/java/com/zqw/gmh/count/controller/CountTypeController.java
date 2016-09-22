package com.zqw.gmh.count.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqw.gmh.count.domain.p.Count;
import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.service.CountService;
import com.zqw.gmh.count.service.CountTypeService;
import com.zqw.gmh.count.util.JacksonUtil;

@RestController
/*@RequestMapping("countType")*/
public class CountTypeController {
	@Autowired
	private CountTypeService countTypeService;
	@Autowired
	private CountService countService;
	@RequestMapping(value="countType/getCountTypes",method=RequestMethod.GET)
	public Map getCounts(HttpServletRequest request , Integer pageNumber ,Integer pageSize ){
		Map map = new HashMap();
		
		Pageable pageable = new PageRequest(pageNumber-1, pageSize);
		
		Page<CountType> cts = countTypeService.findAll(pageable);
		
		List<CountType> counts = cts.getContent();
		
		map.put("rows", counts);
		map.put("total", cts.getTotalElements());
		return map;
	}
	
	@RequestMapping(value="countType/addCountType",method=RequestMethod.POST)
	public Map addCountType(HttpServletRequest request , Integer inoutType , String typeName ){
		Map map = new HashMap();
		CountType ct = new CountType();
		ct.setIntout(inoutType);
		ct.setName(typeName);
		map.put("status", true);
		try{
			countTypeService.save(ct);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="countType/updateCountType",method=RequestMethod.POST)
	public Map updateCountType(HttpServletRequest request , CountType countType ){
		Map map = new HashMap();
		
		map.put("status", true);
		try{
			countTypeService.save(countType);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/countType/removeCountType",method=RequestMethod.POST)
	public Map removeCountType(HttpServletRequest request , String datas ){
		
		
		Map map = new HashMap();
		
		map.put("status", true);
		try{
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = JacksonUtil.getCollectionType(mapper,ArrayList.class, CountType.class); 
	        List<CountType> cts =  (List<CountType>)mapper.readValue(datas, javaType);
	        //级联删除
	        List<CountType> ctsx = new ArrayList<CountType>();
	        for (int i = 0; i < cts.size(); i++) {
				CountType ct = countTypeService.findOne(cts.get(i).getId());
				ctsx.add(ct);
			}
	        countTypeService.delete(ctsx);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
}

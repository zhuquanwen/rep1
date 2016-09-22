package com.zqw.gmh.count.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqw.gmh.count.domain.p.Count;
import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.domain.p.InoutType;
import com.zqw.gmh.count.service.CountService;
import com.zqw.gmh.count.service.CountTypeService;
import com.zqw.gmh.count.util.JacksonUtil;
@RestController
@RequestMapping("count")
public class CountController {
	@Autowired
	private CountService countService;
	@Autowired
	private CountTypeService countTypeService;
	@RequestMapping(value="/getCounts",method=RequestMethod.GET)
	public Map getCounts(HttpServletRequest request , Integer pageNumber ,Integer pageSize ,
			String startTime ,String endTime){
		Map map = new HashMap();
		Sort sort = new Sort(Direction.DESC,"datetime");
		Pageable pageable = new PageRequest(pageNumber-1, pageSize, sort);
		
		Page<Count> pcs=null;
		if(StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			pcs = countService.findAll(pageable);
		}else if(StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
			pcs = countService.findByDatetimeBefore(Timestamp.valueOf(endTime),pageable);
		}else if(!StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)){
			pcs = countService.findByDatetimeAfter(Timestamp.valueOf(startTime),pageable);
		}else{
			pcs = countService.findByDatetimeBetween(Timestamp.valueOf(startTime), Timestamp.valueOf(endTime), pageable);
		}
		
		
		List<Count> counts = pcs.getContent();
		if(counts!=null){
			for (int i = 0; i < counts.size(); i++) {
				CountType ct = counts.get(i).getCountType();
			/*	InoutType it = counts.get(i).getInoutType();*/
				counts.get(i).setCountTypeName(ct.getName());
				counts.get(i).setInoutTypeName(ct.getIntout());
				counts.get(i).setCountTypeId(ct.getId());
			}
		}
		map.put("rows", counts);
		map.put("total", pcs.getTotalElements());
		return map;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Map add(HttpServletRequest request , Long typeName , Double money , String datetime ){
		Map map = new HashMap();
		
		map.put("status", true);
		try{
			CountType ct = countTypeService.findOne(typeName);
			Timestamp dt = Timestamp.valueOf(datetime);
			Count count = new Count();
			count.setCountType(ct);
			count.setDatetime(dt);
			count.setMoney(money);
			countService.save(count);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map update(HttpServletRequest request ,Long id, Long typeName , Double money , String datetime ){
		Map map = new HashMap();
		
		map.put("status", true);
		try{
			CountType ct = countTypeService.findOne(typeName);
			Count count = countService.findOne(id);
			count.setDatetime(Timestamp.valueOf(datetime));
			count.setMoney(money);
			count.setCountType(ct);
			countService.save(count);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public Map remove(HttpServletRequest request , String datas  ){
		Map map = new HashMap();
		
		map.put("status", true);
		try{
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = JacksonUtil.getCollectionType(mapper,ArrayList.class, Count.class); 
	        List<Count> cts =  (List<Count>)mapper.readValue(datas, javaType);
	        
	        countService.delete(cts);
		}catch(Exception e){
			e.printStackTrace();
			map.put("status", false);
		}
		
		return map;
	}
	
}

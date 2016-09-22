package com.zqw.gmh.count.service;


import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import com.zqw.gmh.count.domain.p.Count;
import com.zqw.gmh.count.domain.p.CountDao;

public interface CountService extends BaseService<Count, CountDao>{

	public ModelAndView selectAll(HttpServletRequest request,ModelAndView mav);
	public Page<Count> findByDatetimeAfter(Timestamp time,Pageable pageable);
	
	public Page<Count> findByDatetimeBefore(Timestamp time,Pageable pageable);
	
	public Page<Count> findByDatetimeBetween(Timestamp time1,Timestamp time2,Pageable pageable);
	
	public List<Count> findCount1(Integer id);
}

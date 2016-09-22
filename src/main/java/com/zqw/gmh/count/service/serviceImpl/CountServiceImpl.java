package com.zqw.gmh.count.service.serviceImpl;

import java.nio.file.DirectoryIteratorException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.zqw.gmh.count.common.BindInfo;
import com.zqw.gmh.count.common.CommonUtil;
import com.zqw.gmh.count.commonEntity.PageUpDown;
import com.zqw.gmh.count.domain.p.Count;
import com.zqw.gmh.count.domain.p.CountDao;
import com.zqw.gmh.count.service.CountService;
@Service
public class CountServiceImpl extends BaseServiceImpl<Count, CountDao> implements CountService{
	@Autowired
	private CountDao countDao;
	@Override
	public ModelAndView selectAll(HttpServletRequest request,ModelAndView mav) {
		Pageable pageable = CommonUtil.getPage(request);
		Page<Count> countPage = countDao.findAll(pageable);
		List<PageUpDown> puds = CommonUtil.getPageUpDown(countPage);
		mav.addObject(BindInfo.COUNT_ALL,countPage);
		mav.addObject(BindInfo.PAGE_UP_DOWN,puds);
		return mav;
	}
	@Override
	public Page<Count> findByDatetimeAfter(Timestamp time,Pageable pageable) {
		
		return countDao.findByDatetimeAfter(time,pageable);
	}
	@Override
	public Page<Count> findByDatetimeBefore(Timestamp time,Pageable pageable) {
		
		return countDao.findByDatetimeBefore(time,pageable);
	}
	@Override
	public Page<Count> findByDatetimeBetween(Timestamp time1, Timestamp time2, Pageable pageable) {
		
		return countDao.findByDatetimeBetween(time1, time2, pageable);
	}
	@Override
	public List<Count> findCount1(Integer id) {
		
		return countDao.findCount1(id);
	}

}

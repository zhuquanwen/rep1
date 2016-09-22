package com.zqw.gmh.count.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.zqw.gmh.count.commonEntity.MyPage;
import com.zqw.gmh.count.commonEntity.MySort;
import com.zqw.gmh.count.commonEntity.PageUpDown;

public class CommonUtil {
	/**获取request中的信息注入MyPage*/
	public static MyPage getPageFromRequest(HttpServletRequest request){
		MyPage page = new MyPage(0,10);
		String pageIndexObj=request.getParameter("page");
		if(pageIndexObj!=null){
			Integer pageIndex=Integer.parseInt(pageIndexObj);
			page.setPage(pageIndex);
		}
		String pageSizeObj=request.getParameter("pageSize");
		if(pageSizeObj!=null){
			Integer pageSize=Integer.parseInt(pageSizeObj);
			page.setPageSize(pageSize);
		}
		String attr=request.getParameter("sortAttr");
		if(attr!=null){
			MySort sort = new MySort();
			sort.setAttr(attr);
			sort.setDirection("asc");
			page.setSort(sort);
			String direction=request.getParameter("sortDirection");
			if(direction!=null){
				sort.setDirection(direction);
			}
		}
		
		return page;
	}
	
	
	public static Pageable toPage(MyPage page){
		Pageable pageable = null;
		if(page.getSort()==null){
			pageable = new PageRequest(page.getPage(), page.getPageSize()); 
		}else{
			Sort sort = new Sort("asc".equals(page.getSort().getDirection())?Direction.ASC:Direction.DESC, page.getSort().getAttr());
			pageable = new PageRequest(page.getPage(), page.getPageSize(),sort); 
		}
		return pageable;
	}
	
	public static Pageable getPage(HttpServletRequest request){
		MyPage myPage = getPageFromRequest(request);
		return toPage(myPage);
	}
	
	public static List<PageUpDown> getPageUpDown(Page page){
		List<PageUpDown> puds = new ArrayList<PageUpDown>();
		Integer pageIndex=page.getNumber()+1;
		int start = 1;
		int end = 1;
		if(page.getTotalPages()<=5){
			start = 1;
			end = page.getTotalPages();
		}else{
			if(pageIndex <= 5){
				start = 1;
				end = 5;
			}else{
				start = pageIndex - 5;
				end = pageIndex;
			}
		}
		
		if(page.getTotalPages()<=5){
			for (int i = start; i <= end; i++) {
				PageUpDown pud = new PageUpDown();
				pud.setPageIndex(i);
				if(i == pageIndex){
					pud.setStyle("active");
				}else{
					pud.setStyle("");
				}
				puds.add(pud);
			}
		}
		return puds;
	}
}

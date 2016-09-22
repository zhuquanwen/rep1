package com.zqw.gmh.count.commonEntity;

public class MyPage {
	private Integer page;
	private Integer pageSize;
	private MySort  sort = null;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public MySort getSort() {
		return sort;
	}
	public void setSort(MySort sort) {
		this.sort = sort;
	}
	public MyPage(Integer page, Integer pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}
	public MyPage() {
		super();
	}
	
}

package com.zqw.gmh.count.service;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseService<R extends Object,T extends JpaRepository> {
	public List<R> findAll();
	public Page<R> findAll(Pageable p);
	
	public Object save(R r);
	
	public R findOne(Serializable s);
	
	public void delete(R r);
	
	public void delete(List<R> rs);
	
	//。。。。。。
}

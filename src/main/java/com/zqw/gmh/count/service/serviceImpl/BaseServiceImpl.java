package com.zqw.gmh.count.service.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zqw.gmh.count.service.BaseService;
@Transactional(value="transactionManagerPrimary",isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
public class BaseServiceImpl<R extends Object,T extends JpaRepository> implements BaseService<R,T>{
	@Autowired
	private T t;
	/**查询全部*/
	@Override
	public List<R> findAll() {
		
		return t.findAll();
	}

	/**分页排序查询*/
	@Override
	public Page<R> findAll(Pageable p) {
		return t.findAll(p);
	}

	/**保存*/
	@Override
	public Object save(R r) {
		return t.save(r);
	}

	@Override
	public R findOne(Serializable s) {
		
		return (R) t.findOne(s);
	}
	
	public void delete(R r){
		t.delete(r);
		
	}

	@Override
	public void delete(List<R> rs) {
		t.delete(rs);
	}

	

}

package com.zqw.gmh.count.domain.p;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountDao extends JpaRepository<Count, Long>{
	public Page<Count> findByDatetimeAfter(Timestamp time,Pageable pageable);
	
	public Page<Count> findByDatetimeBefore(Timestamp time,Pageable pageable);
	
	public Page<Count> findByDatetimeBetween(Timestamp time1,Timestamp time2,Pageable pageable);
	
	@Query("from Count t left join fetch t.countType s where   s.id = ?1")
	public List<Count> findCount1(Integer id);
}

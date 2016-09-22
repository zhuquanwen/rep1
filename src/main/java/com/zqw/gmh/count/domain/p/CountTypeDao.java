package com.zqw.gmh.count.domain.p;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountTypeDao extends JpaRepository<CountType, Long>{
	/*@Query(value="select sum(money) as sumCost,"
			+ "count_type_id as id from count where datetime >=?1 and datetime<=?2  group by count_type_id",nativeQuery=true)
	public List<CountType> getCountSumByDatetime(Timestamp time1,Timestamp time2);*/
	public List<CountType> findByIntout(int i);
}

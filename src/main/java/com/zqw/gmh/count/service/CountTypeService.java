package com.zqw.gmh.count.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.domain.p.CountTypeDao;

public interface CountTypeService extends BaseService<CountType, CountTypeDao>{
	public List getCountSumByDatetime(Timestamp time1,Timestamp time2 ,List<CountType> cts);

	public List<CountType> findByIntout(int i);

	public List getCountSum(List<CountType> cts_out);

	public List getTypeColumnSeries(String startTime, String endTime, List<String> categories,Map<String,Long> countTypeMap);

	public List<Map<String, Object>> getTypePieSeries(String startTime, String endTime, List<String> categories,
			Map<Long, String> countTypeMap1,Map<String,Long> countTypeMap);


	public List<Map<String, Object>> getInoutColumnSeries(String startTime, String endTime, List<CountType> cts_in,
			List<CountType> cts_out);

	public List<Map<String, Object>> getInoutPieSeries(String startTime, String endTime, List<CountType> cts_in,
			List<CountType> cts_out);

	public List getTimeLineSeries(List<CountType> cts_in, List<CountType> cts_out, List<String> categories,String type);

}

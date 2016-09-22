package com.zqw.gmh.count.service.serviceImpl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.domain.p.CountTypeDao;
import com.zqw.gmh.count.service.CountTypeService;
@Service
public class CountTypeServiceImpl extends BaseServiceImpl<CountType, CountTypeDao> implements CountTypeService{
	@Autowired
	private CountTypeDao countTypeDao;
	@Autowired
	private EntityManager entityManagerPrimary;
	@Override
	public List getCountSumByDatetime(Timestamp time1, Timestamp time2 ,List<CountType> cts) {
		String sql = "select sum(money) as sumCost"
				+ " from count where datetime >= :time1 and datetime<= :time2 "; 
		if(cts==null||cts.size() == 0){
			return new ArrayList();
		} else{
			sql+= " and (";
			for (int i = 0; i < cts.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + cts.get(i).getId();
				}else {
					sql+= " or count_type_id = " + cts.get(i).getId();
				}
				
			}
			sql+= " ) ";
			
			Query query = entityManagerPrimary.createNativeQuery(sql);  
			query.setParameter("time1", time1);
			query.setParameter("time2", time2);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			List rows = query.getResultList();  
			return rows;
		}
				
		
		
	}
	@Override
	public List<CountType> findByIntout(int i) {
		
		return countTypeDao.findByIntout(i);
	}
	@Override
	public List getCountSum(List<CountType> cts) {
		String sql = "select sum(money) as sumCost"
				+ " from count where 1=1 "; 
		if(cts==null||cts.size() == 0){
			return new ArrayList();
		} else{
			sql+= " and (";
			for (int i = 0; i < cts.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + cts.get(i).getId();
				}else {
					sql+= " or count_type_id = " + cts.get(i).getId();
				}
				
			}
			sql+= " ) ";
			
			Query query = entityManagerPrimary.createNativeQuery(sql);  
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			List rows = query.getResultList();  
			return rows;
		}
	}
	@Override
	public List<Map<String,Object>> getTypeColumnSeries(String startTime, String endTime, List<String> categories, Map<String,Long> countTypeMap) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select sum(money) as sumCost , count_type_id as id from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " and datetime <=:time2 ";
		}
		if(categories != null&&categories.size()>0){
			sql+= " and (";
			for (int i = 0; i < categories.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + countTypeMap.get(categories.get(i));
				}else {
					sql+= " or count_type_id = " + countTypeMap.get(categories.get(i));
				}
				
			}
			sql+= " ) ";
		}
		
		
		sql += "group by count_type_id ";
		
			
		Query query = entityManagerPrimary.createNativeQuery(sql);  
		if(!StringUtils.isEmpty(startTime)){
			query.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows = query.getResultList();  
		
		
		Map<Long,Double> mapx = new HashMap<Long,Double>();
		if(rows!=null&&rows.size()>0){
			for (int i = 0; i < rows.size(); i++) {
				Map<String,Object> mapz = new HashMap<String,Object>();
				Map map = (Map) rows.get(i);
				Double sum = (Double) (map.get("sumCost") == null?0.0:map.get("sumCost"));
				Integer id ;
				if(map.get("id") instanceof BigInteger){
					id = ((BigInteger)map.get("id")).intValue();
				}else{
					id = (Integer)map.get("id");
				}
				
				mapx.put(id.longValue(), sum);
				
			}
		}
		
		
		Double[] se = null;
		if(categories!=null&&categories.size()>0){
			se = new Double[categories.size()];
			for (int i = 0; i < categories.size(); i++) {
				Double result = 0.0;
				if(mapx!=null&&mapx.get(countTypeMap.get(categories.get(i)))!=null){
					result = mapx.get(countTypeMap.get(categories.get(i)));
				}
				se[i] = result;
			}
		}
		Map<String,Object> mapy=new HashMap<String,Object>();
		mapy.put("name", "按类型统计");
		mapy.put("data", se);
		list.add(mapy);
		return list;
	}
	@Override
	public List<Map<String, Object>> getTypePieSeries(String startTime, String endTime, List<String> categories,
			Map<Long, String> countTypeMap1 ,Map<String,Long> countTypeMap) {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listx = new ArrayList<Map<String,Object>>();
		String sql = "select sum(money) as sumCost , count_type_id as id from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " and datetime <=:time2 ";
		}
		if(categories != null&&categories.size()>0){
			sql+= " and (";
			for (int i = 0; i < categories.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + countTypeMap.get(categories.get(i));
				}else {
					sql+= " or count_type_id = " + countTypeMap.get(categories.get(i));
				}
				
			}
			sql+= " ) ";
		}
		
		
		sql += "group by count_type_id ";
		
			
		Query query = entityManagerPrimary.createNativeQuery(sql);  
		if(!StringUtils.isEmpty(startTime)){
			query.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows = query.getResultList();  
		
		Double sumCostAll = 0.0;
		Map<String,Double> mapx = new HashMap<String,Double>();
		if(rows!=null&&rows.size()>0){
			for (int i = 0; i < rows.size(); i++) {
				Map map = (Map) rows.get(i);
				Double sum = (Double) (map.get("sumCost") == null?0.0:map.get("sumCost"));
				Integer id;
				if(map.get("id") instanceof java.math.BigInteger){
					id = ((BigInteger)map.get("id")).intValue();
				}else{
					id = (Integer)map.get("id");
				}
				mapx.put(countTypeMap1.get(id.longValue()), sum);
				sumCostAll += sum;
				
			}
		}
		
		for (int i = 0; i < categories.size(); i++) {
			String name = categories.get(i);
			if(mapx.get(name)!=null){
				Map<String,Object> mapz = new HashMap<String,Object>();
				mapz.put("name", name);
				Double sum = mapx.get(name);
				Double per = Math.round(sum/sumCostAll*100)/100.0;
				mapz.put("y", per);
				listx.add(mapz);
			}
		}
		Map<String,Object> mapm = new HashMap<String,Object>();
		mapm.put("data", listx);
		mapm.put("name", "占比");
		mapm.put("colorByPoint", true);
		list.add(mapm);
		
		
		return list;
	}
	@Override
	public List<Map<String, Object>> getInoutColumnSeries(String startTime, String endTime, List<CountType> cts_in,
			List<CountType> cts_out) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select sum(money) as sumCost  from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " and datetime <=:time2 ";
		}
		if(cts_in != null&&cts_in.size()>0){
			sql+= " and (";
			for (int i = 0; i < cts_in.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + cts_in.get(i).getId();
				}else {
					sql+= " or count_type_id = " + cts_in.get(i).getId();
				}
				
			}
			sql+= " ) ";
		}
		
		
		
		
			
		Query query = entityManagerPrimary.createNativeQuery(sql);  
		if(!StringUtils.isEmpty(startTime)){
			query.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows1 = query.getResultList();  
		
		String sql2 = "select sum(money) as sumCost  from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql2 += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql2 += " and datetime <=:time2 ";
		}
		if(cts_out != null&&cts_out.size()>0){
			sql2+= " and (";
			for (int i = 0; i < cts_out.size(); i++) {
				if(i == 0){
					sql2+= "count_type_id = " + cts_out.get(i).getId();
				}else {
					sql2+= " or count_type_id = " + cts_out.get(i).getId();
				}
				
			}
			sql2+= " ) ";
		}
		
		Query query2 = entityManagerPrimary.createNativeQuery(sql2);  
		if(!StringUtils.isEmpty(startTime)){
			query2.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query2.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows2 = query2.getResultList();  
		
		Double in = (Double) (rows1==null||rows1.size()==0||rows1.get(0)==null?0.0:((Map)rows1.get(0)).get("sumCost"));
		Double out = (Double) (rows2==null||rows2.size()==0||rows2.get(0)==null?0.0:((Map)rows2.get(0)).get("sumCost"));
		Double[] se = new Double[2];
		se[0] = in;
		se[1] = out;
		
		Map<String,Object> mapy=new HashMap<String,Object>();
		mapy.put("name", "按类型统计");
		mapy.put("data", se);
		list.add(mapy);
		return list;
	}
	@Override
	public List<Map<String, Object>> getInoutPieSeries(String startTime, String endTime, List<CountType> cts_in,
			List<CountType> cts_out) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select sum(money) as sumCost  from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql += " and datetime <=:time2 ";
		}
		if(cts_in != null&&cts_in.size()>0){
			sql+= " and (";
			for (int i = 0; i < cts_in.size(); i++) {
				if(i == 0){
					sql+= "count_type_id = " + cts_in.get(i).getId();
				}else {
					sql+= " or count_type_id = " + cts_in.get(i).getId();
				}
				
			}
			sql+= " ) ";
		}
		
		
		
		
			
		Query query = entityManagerPrimary.createNativeQuery(sql);  
		if(!StringUtils.isEmpty(startTime)){
			query.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows1 = query.getResultList();  
		
		String sql2 = "select sum(money) as sumCost  from count where 1=1 ";
		if(!StringUtils.isEmpty(startTime)){
			sql2 += " and datetime >=:time1 ";
		}
		if(!StringUtils.isEmpty(endTime)){
			sql2 += " and datetime <=:time2 ";
		}
		if(cts_out != null&&cts_out.size()>0){
			sql2+= " and (";
			for (int i = 0; i < cts_out.size(); i++) {
				if(i == 0){
					sql2+= "count_type_id = " + cts_out.get(i).getId();
				}else {
					sql2+= " or count_type_id = " + cts_out.get(i).getId();
				}
				
			}
			sql2+= " ) ";
		}
		
		Query query2 = entityManagerPrimary.createNativeQuery(sql2);  
		if(!StringUtils.isEmpty(startTime)){
			query2.setParameter("time1", Timestamp.valueOf(startTime));
		}	
		if(!StringUtils.isEmpty(endTime)){
			query2.setParameter("time2", Timestamp.valueOf(endTime));
		}
			
		query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		List rows2 = query2.getResultList();  
		
		Double in = (Double) (rows1==null||rows1.size()==0||rows1.get(0)==null?0.0:((Map)rows1.get(0)).get("sumCost"));
		Double out = (Double) (rows2==null||rows2.size()==0||rows2.get(0)==null?0.0:((Map)rows2.get(0)).get("sumCost"));
		
		List<Map<String,Object>> listx = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("name", "收入");
		map1.put("y", in+out == 0?0:Math.round((in/(in+out))*100)/100.0);
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("name", "支出");
		map2.put("y", in+out == 0?0:Math.round((out/(in+out))*100)/100.0);
		listx.add(map1);
		listx.add(map2);
		
		Map<String,Object> mapm = new HashMap<String,Object>();
		mapm.put("data", listx);
		mapm.put("name", "占比");
		mapm.put("colorByPoint", true);
		list.add(mapm);
		return list;
	}
	private String getLastDateStr(String yearMonth){
		String lastDateStr = "";
		Integer year = Integer.parseInt(yearMonth.split("-")[0]);
		Integer month = Integer.parseInt(yearMonth.split("-")[1]);
		
		switch (month) {
		case 1:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 2:{
			
			if (((year % 100 == 0) && (year % 400 == 0))
					|| ((year % 100 != 0) && (year % 4 == 0))){
				lastDateStr = "-29 23:59:59";
			}else{
				lastDateStr = "-28 23:59:59";
			}
			break;
		}
		case 3:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 4:{
			lastDateStr = "-30 23:59:59";
			break;
		}
		case 5:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 6:{
			lastDateStr = "-30 23:59:59";
			break;
		}
		case 7:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 8:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 9:{
			lastDateStr = "-30 23:59:59";
			break;
		}
		case 10:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		case 11:{
			lastDateStr = "-30 23:59:59";
			break;
		}
		case 12:{
			lastDateStr = "-31 23:59:59";
			break;
		}
		default:
			break;
		}
		return lastDateStr;
	}
	@Override
	public List getTimeLineSeries(List<CountType> cts_in, List<CountType> cts_out, List<String> categories ,String type) {
		List list = new ArrayList();
		List<Double> inL = new ArrayList<Double>(); 
		List<Double> outL = new ArrayList<Double>();
		List<Double> inoutL = new ArrayList<Double>();
		for (int m = 0; m < categories.size(); m++) {
			String startTime = null,endTime = null;
			if("year".equals(type)){
				startTime = categories.get(m)+"-01-01 00:00:00";
				endTime = categories.get(m) +"-12-31 23:59:59";
			}else if("month".equals(type)){
				startTime = categories.get(m) + "-01 00:00:00";
				endTime = categories.get(m) +this.getLastDateStr(categories.get(m));
			}
			
			
			String sql1 = "select sum(money) as sumCost  from count where 1=1 ";
			
			sql1 += " and datetime >=:time1 ";
			
				sql1 += " and datetime <=:time2 ";
			if(cts_in != null&&cts_in.size()>0){
				sql1+= " and (";
				for (int i = 0; i < cts_in.size(); i++) {
					if(i == 0){
						sql1+= "count_type_id = " + cts_in.get(i).getId();
					}else {
						sql1+= " or count_type_id = " + cts_in.get(i).getId();
					}
					
				}
				sql1+= " ) ";
			}
			Query query1 = entityManagerPrimary.createNativeQuery(sql1);  
			
			query1.setParameter("time1", Timestamp.valueOf(startTime));
			
			
			query1.setParameter("time2", Timestamp.valueOf(endTime));
			
			query1.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			List rows1 = query1.getResultList();  
			
			String sql2 = "select sum(money) as sumCost  from count where 1=1 ";
			
			sql2 += " and datetime >=:time1 ";
			
				sql2 += " and datetime <=:time2 ";
			if(cts_out != null&&cts_out.size()>0){
				sql2+= " and (";
				for (int i = 0; i < cts_out.size(); i++) {
					if(i == 0){
						sql2+= "count_type_id = " + cts_out.get(i).getId();
					}else {
						sql2+= " or count_type_id = " + cts_out.get(i).getId();
					}
					
				}
				sql2+= " ) ";
			}
			Query query2 = entityManagerPrimary.createNativeQuery(sql2);  
			
			query2.setParameter("time1", Timestamp.valueOf(startTime));
			
			
			query2.setParameter("time2", Timestamp.valueOf(endTime));
			
			query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
			List rows2 = query2.getResultList();  
			Double costOut = (Double) (rows2==null||rows2.size()==0||rows2.get(0)==null?0.0:((Map)rows2.get(0)).get("sumCost"));
			Double costIn = (Double) (rows1==null||rows1.size()==0||rows1.get(0)==null?0.0:((Map)rows1.get(0)).get("sumCost"));
			if(costOut==null){
				costOut = 0.0;
			}
			if(costIn == null){
				costIn = 0.0;
			}
			outL.add(costOut);
			inL.add(costIn);
			inoutL.add(costIn-costOut);
		}
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		map1.put("name", "收入");
		map1.put("data", inL);   
		map2.put("name", "支出");
		map2.put("data", outL);   
		map3.put("name", "余额");
		map3.put("data", inoutL);   
		list.add(map1);
		list.add(map2);
		list.add(map3);
		return list;	
		
	}
	
	
}

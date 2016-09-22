package com.zqw.gmh.count.controller;

import static org.mockito.Matchers.anyCollection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zqw.gmh.count.common.BindInfo;
import com.zqw.gmh.count.domain.p.CountType;
import com.zqw.gmh.count.service.CountTypeService;
import com.zqw.gmh.count.util.DateUtil;
@Controller
@RequestMapping("stastics")
public class StasticsController {
	@Autowired
	private CountTypeService countTypeService;
	@RequestMapping(value="getGeneral",method=RequestMethod.POST)
	@ResponseBody
	public Map getGeneral(){
		Map map = new HashMap();
		Timestamp firstDay = DateUtil.getFirstDayOfMonth();
		Timestamp lastDay = DateUtil.getLastDayOfMonth();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		List ins = countTypeService.getCountSumByDatetime(firstDay, lastDay ,cts_in);
		List outs = countTypeService.getCountSumByDatetime(firstDay, lastDay ,cts_out);
		
		double inCost = (Double) (ins==null||ins.size()==0||((Map) ins.get(0)).get("sumCost")==null?0.0:((Map) ins.get(0)).get("sumCost"));
		double outCost = (Double) (outs==null||outs.size()==0||((Map) outs.get(0)).get("sumCost")==null?0.0:((Map) outs.get(0)).get("sumCost"));
		double inoutCost = inCost - outCost;
		map.put(BindInfo.STASTICS_MONTH_IN, inCost);
		map.put(BindInfo.STASTICS_MONTH_OUT, outCost);
		map.put(BindInfo.STASTICS_MONTH_IN_OUT, inoutCost);
		
		//历史统计
		List insh = countTypeService.getCountSum(cts_in);
		List outsh = countTypeService.getCountSum(cts_out);
		double inCosth = (Double) (insh==null||insh.size()==0||((Map) insh.get(0)).get("sumCost")==null?0.0:((Map) insh.get(0)).get("sumCost"));
		double outCosth = (Double) (outsh==null||outsh.size()==0||((Map) outsh.get(0)).get("sumCost")==null?0.0:((Map) outsh.get(0)).get("sumCost"));
		double inoutCosth = inCosth - outCosth;
		map.put(BindInfo.STASTICS_MONTH_IN_OUT_H, inoutCosth);
		return map;
	}
	
	@RequestMapping(value="getType/column")
	@ResponseBody
	public Map getType_column(String startTime,String endTime){
		Map map = new HashMap();
		List<String> categories = new ArrayList<String>();
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		Map<String,Long> countTypeMap = new HashMap<String,Long>();
		if(cts_in!=null){
			for (CountType ct : cts_in) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
			}
		}
		if(cts_out!=null){
			for (CountType ct : cts_out) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
			}
		}
		series = countTypeService.getTypeColumnSeries(startTime, endTime ,categories,countTypeMap);
		
		map.put("categories", categories);
		map.put("series", series);
		return map;
	}
	
	@RequestMapping(value="getType/pie")
	@ResponseBody
	public Map getType_pie(String startTime,String endTime){
		Map map = new HashMap();
		List<String> categories = new ArrayList<String>();
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		Map<String,Long> countTypeMap = new HashMap<String,Long>();
		Map<Long,String> countTypeMap1 = new HashMap<Long,String>();
		if(cts_in!=null){
			for (CountType ct : cts_in) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
				countTypeMap1.put(ct.getId(), ct.getName());
			}
		}
		if(cts_out!=null){
			for (CountType ct : cts_out) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
				countTypeMap1.put(ct.getId(), ct.getName());
			}
		}
		series = countTypeService.getTypePieSeries(startTime, endTime ,categories,countTypeMap1,countTypeMap);
		
		map.put("series", series);
		return map;
	}
	
	@RequestMapping(value="getInout/column")
	@ResponseBody
	public Map getInout_column(String startTime,String endTime){
		Map map = new HashMap();
		List<String> categories = new ArrayList<String>();
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		Map<String,Long> countTypeMap = new HashMap<String,Long>();
		/*if(cts_in!=null){
			for (CountType ct : cts_in) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
			}
		}
		if(cts_out!=null){
			for (CountType ct : cts_out) {
				categories.add(ct.getName());
				countTypeMap.put(ct.getName(), ct.getId());
			}
		}*/
		categories.add("收入");
		categories.add("支出");
		series = countTypeService.getInoutColumnSeries(startTime, endTime ,cts_in ,cts_out);
		
		map.put("categories", categories);
		map.put("series", series);
		return map;
	}
	
	@RequestMapping(value="getInout/pie")
	@ResponseBody
	public Map getInout_pie(String startTime,String endTime){
		Map map = new HashMap();
		List<String> categories = new ArrayList<String>();
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		Map<String,Long> countTypeMap = new HashMap<String,Long>();
		series = countTypeService.getInoutPieSeries(startTime, endTime ,cts_in ,cts_out);
		
		map.put("series", series);
		return map;
	}
	
	@RequestMapping(value="getTime/line")
	@ResponseBody
	public Map getTime_line(String startTime,String endTime,String timeType){
		Map map = new HashMap();
		List<CountType> cts_in = countTypeService.findByIntout(1);
		List<CountType> cts_out = countTypeService.findByIntout(-1);
		if("year".equals(timeType)){
			Integer yearSum = Integer.parseInt(endTime.split("-")[0]) - Integer.parseInt(startTime.split("-")[0]);
			List<String> years = new ArrayList<String>();
			for (int i = 0; i <= yearSum; i++) {
				years.add((Integer.parseInt(startTime.split("-")[0])+i)+"");
			}
			List<String> categories = years;
		
			List series = countTypeService.getTimeLineSeries(cts_in, cts_out ,categories,timeType);
			map.put("series", series);
			map.put("categories", categories);
		}else if("month".equals(timeType)){
			Integer startM = Integer.parseInt(startTime.split("-")[0])*12+Integer.parseInt(startTime.split("-")[1]);
			Integer endM = Integer.parseInt(endTime.split("-")[0])*12+Integer.parseInt(endTime.split("-")[1]);
			Integer chaM = endM - startM;
			List<String> months = new ArrayList<String>();
			Integer y = Integer.parseInt(startTime.split("-")[0]);
			Integer mz = Integer.parseInt(startTime.split("-")[1]);
			for (int i = 0; i <= chaM; i++) {
				Integer m = mz;
				String date ;
				m = m + i;
				if(m <= 12){
					date = y + "-" +m;
				}else{
					Integer yx = y + m/12;
					Integer mx = m%12;
					if(mx == 0){
						yx = yx -1;
						mx = 12;
					}
					date = yx +"-" + mx;
				}
				months.add(date);
			}
			List<String> categories = months;
			List series = countTypeService.getTimeLineSeries(cts_in, cts_out ,categories,timeType);
			map.put("series", series);
			map.put("categories", categories);
		
		}
		
		
		
		return map;
	}
}

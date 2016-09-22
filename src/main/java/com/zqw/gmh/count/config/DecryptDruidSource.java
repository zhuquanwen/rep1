package com.zqw.gmh.count.config;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;


public class DecryptDruidSource extends DruidDataSource{
	@Override
	public void setUsername(String username){
		try {
			this.username=ConfigTools.decrypt(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void setPassword(String password){
		try {
			this.password=ConfigTools.decrypt(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

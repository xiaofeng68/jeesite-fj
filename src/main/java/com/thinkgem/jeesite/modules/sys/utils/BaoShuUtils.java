/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.fojiao.dao.FjBaoshuDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 报数统计工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class BaoShuUtils {
	
	private static FjBaoshuDao baoshuDao = SpringContextHolder.getBean(FjBaoshuDao.class);

	
	public static List<Map<String,Object>> getUserBaoshu(){
		User user = UserUtils.getUser();
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userId", user.getId());
		List<Map<String,Object>> list = baoshuDao.getBaoshuTj(param);
		param.put("today", DateUtils.formatDate(new Date()));
		Map<String,Object> dayMap = change2Map(baoshuDao.getBaoShuByTime(param));
		Object key ;
		for(Map<String,Object> map : list) {
			key = map.get("protype");
			if(key!=null)
				map.put("today", dayMap.get(key.toString()));
		}
		return list;
	}
	public static Map<String,Object> change2Map(List<Map<String,Object>> list){
		Map<String,Object> map = new HashMap<String, Object>();
		for(Map<String,Object> m : list) {
			map.put(m.get("protype").toString(), m.get("nums"));
		}
		return map;
	}
	public static void updateUser(User user) {
		baoshuDao.updateBaoshuOffice(user);
		baoshuDao.updateBaoshuTjOffice(user);
	}
}

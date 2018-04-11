/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 报数统计DAO接口
 * @author 小风
 * @version 2018-03-18
 */
@MyBatisDao
public interface FjBaoshuDao extends CrudDao<FjBaoshu> {
	/**
	 * 用户获取报数统计
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getBaoshuTj(Map<String,Object> param);
	/**
	 * 获取用户日期的报数统计
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> getBaoShuByTime(Map<String,Object> param);
	
	/**
	 * 获取报数统计
	 * @return
	 */
	List<Map<String,Object>> getBaoShuNum(Office officeId);
	
	/**
	 * 删除用户报数
	 * @param fjBaoshu
	 */
	void deleteByFjBaoshu(String userId,String date);
	
	void saveBaoShuTj(Map<String,Object> map);
	
	/**
	 * 根据时间统计班级报数人数
	 * @param date
	 * @return
	 */
	List<Map<String,Object>> getClassStatics(FjBaoshu fjBaoshu);
	
	/**
	 * 看未报数的人员清单
	 * @param date
	 * @return
	 */
	List<Map<String,Object>> noDetailStatics(String date);
	/**
	 * 长时间未报人员清单
	 * @param date
	 * @return
	 */
	List<Map<String,Object>> noDaysDetailStatics(String date);
	
	/**
	 * 查询所有用户统计
	 * @param fjBaoshu
	 * @return
	 */
	List<Map<String,Object>> getUserStatics(FjBaoshu fjBaoshu );
	
	/**
	 * 查询个人历史数据
	 * @param fjBaoshu
	 * @return
	 */
	List<Map<String,Object>> findPeopleData(FjBaoshu fjBaoshu );
	
	void updateBaoshuOffice(User user);
	void updateBaoshuTjOffice(User user);
}
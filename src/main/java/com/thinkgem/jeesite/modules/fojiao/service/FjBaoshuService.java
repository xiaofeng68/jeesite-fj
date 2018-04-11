/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.fojiao.dao.FjBaoshuDao;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshu;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshuTj;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 亿遍报数Service
 * 
 * @author 小风
 * @version 2018-03-17
 */
@Service
@Transactional(readOnly = true)
public class FjBaoshuService extends CrudService<FjBaoshuDao, FjBaoshu> {
	@Autowired
	FjBaoshuDao baoshuDao;

	public FjBaoshu get(String id) {
		return super.get(id);
	}

	public List<FjBaoshu> findList(FjBaoshu fjBaoshu) {
		return super.findList(fjBaoshu);
	}

	public Page<FjBaoshu> findPage(Page<FjBaoshu> page, FjBaoshu fjBaoshu) {
		return super.findPage(page, fjBaoshu);
	}

	@Transactional(readOnly = false)
	public void save(FjBaoshu fjBaoshu) {
		super.save(fjBaoshu);
	}

	@Transactional(readOnly = false)
	public void delete(FjBaoshu fjBaoshu) {
		super.delete(fjBaoshu);
	}

	@Transactional(readOnly = false)
	public void saveBatch(FjBaoshu fjBaoshu, String baoshuNum) {
		User user = UserUtils.getUser();
		if (fjBaoshu.getCreateBy() != null && !StringUtils.isEmpty(fjBaoshu.getCreateBy().getLoginName())) {
			user = UserUtils.getByLoginName(fjBaoshu.getCreateBy().getLoginName());
		}
		fjBaoshu.setCreateBy(user);
		// 删除用户当天的报数
		baoshuDao.deleteByFjBaoshu(user.getId(), DateUtils.formatDate(fjBaoshu.getCreateDate()));

		String[] bsArr = baoshuNum.split(";");
		FjBaoshu baoShu;
		for (String bs : bsArr) {
			if (StringUtils.isEmpty(bs))
				continue;
			String[] arr = bs.split(":");
			if (arr.length != 2)
				continue;
			if (StringUtils.isEmpty(arr[1]))
				continue;
			baoShu = new FjBaoshu();
			try {
				BeanUtils.copyProperties(baoShu, fjBaoshu);
			} catch (Exception e) {
				e.printStackTrace();
			}
			baoShu.setProtype(arr[0]);
			baoShu.setNum(arr[1]);
			save(baoShu);
		}
		// 更新统计
		List<Map<String, Object>> list = baoshuDao.getBaoShuNum(user.getOffice());
		Map<String, FjBaoshuTj> keyMap = new HashMap<String, FjBaoshuTj>();
		for (Map<String, Object> map : list) {
			String key = map.get("office_id") + "" + map.get("protype");
			int nums = ((BigDecimal) map.get("nums")).intValue();
			FjBaoshuTj tj = keyMap.get(key);
			if (tj == null) {
				tj = new FjBaoshuTj();
				tj.setNums(nums);
				tj.setSeq(1);
				keyMap.put(key, tj);
			}
			if (nums == tj.getNums()) {
				tj.setSeq(tj.getSeq());
			} else {
				tj.setSeq(tj.getSeq() + 1);
			}
			map.put("seq", tj.getSeq());
			baoshuDao.saveBaoShuTj(map);
		}

	}

	public List<Map<String, Object>> getClassStatics(FjBaoshu fjBaoshu) {
		return baoshuDao.getClassStatics(fjBaoshu);
	}

	public List<Map<String, Object>> noDetailStatics(FjBaoshu fjBaoshu) {
		return baoshuDao.noDetailStatics(DateUtils.formatDate(fjBaoshu.getCreateDate()));
	}

	public List<Map<String, Object>> noDaysDetailStatics(Date date) {
		return baoshuDao.noDaysDetailStatics(DateUtils.formatDate(date));
	}

	public List<Map<String, Object>> getUserBaoshu(FjBaoshu fjBaoshu) {
		User user = fjBaoshu.getCreateBy()!=null?fjBaoshu.getCreateBy():UserUtils.getUser();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", user.getId());
		param.put("officeId", user.getOffice().getId());
		List<Map<String, Object>> list = baoshuDao.getBaoshuTj(param);
		param.put("today", DateUtils.formatDate(fjBaoshu.getCreateDate()));
		Map<String, Object> dayMap = change2Map(baoshuDao.getBaoShuByTime(param));
		Object key;
		for (Map<String, Object> map : list) {
			key = map.get("protype");
			if (key != null)
				map.put("today", dayMap.get(key.toString()));
		}
		return list;
	}
	public List<Map<String, Object>> getUserStatics(FjBaoshu fjBaoshu ) {
		List<Map<String,Object>> cache = baoshuDao.getUserStatics(fjBaoshu);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Map<String,Object>> cahceMap = new HashMap<String, Map<String,Object>>();
		Map<String,Object> obj;
		Map<String,Object> sumMap = new HashMap<String, Object>();
		for(Map<String,Object> map : cache) {
			String key = map.get("create_by").toString();
			obj = cahceMap.get(key);
			if(obj==null) {
				obj = new HashMap<String, Object>();
				obj.put("login_name", map.get("login_name"));
				obj.put("name", map.get("name"));
				obj.put("create_by", key);
				cahceMap.put(key, obj);
				list.add(obj);
			}
			String type = map.get("protype").toString();
			int num = StringUtils.changeObject2Int(map.get("num"));
			obj.put(type, num);
			int sumNum = StringUtils.changeObject2Int(sumMap.get(type));
			sumMap.put(type, sumNum+num);
		}
		list.add(sumMap);
		return list;
	}
	public Map<String, Object> change2Map(List<Map<String, Object>> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map<String, Object> m : list) {
			map.put(m.get("protype").toString(), m.get("nums"));
		}
		return map;
	}
	public List<Map<String, Object>> findPeopleData(FjBaoshu fjBaoshu ) {
		return baoshuDao.findPeopleData(fjBaoshu);
	}
}
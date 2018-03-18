/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshuTj;
import com.thinkgem.jeesite.modules.fojiao.dao.FjBaoshuTjDao;

/**
 * 报数统计Service
 * @author 小风
 * @version 2018-03-18
 */
@Service
@Transactional(readOnly = true)
public class FjBaoshuTjService extends CrudService<FjBaoshuTjDao, FjBaoshuTj> {

	public FjBaoshuTj get(String id) {
		return super.get(id);
	}
	
	public List<FjBaoshuTj> findList(FjBaoshuTj fjBaoshuTj) {
		return super.findList(fjBaoshuTj);
	}
	
	public Page<FjBaoshuTj> findPage(Page<FjBaoshuTj> page, FjBaoshuTj fjBaoshuTj) {
		return super.findPage(page, fjBaoshuTj);
	}
	
	@Transactional(readOnly = false)
	public void save(FjBaoshuTj fjBaoshuTj) {
		super.save(fjBaoshuTj);
	}
	
	@Transactional(readOnly = false)
	public void delete(FjBaoshuTj fjBaoshuTj) {
		super.delete(fjBaoshuTj);
	}
	
}
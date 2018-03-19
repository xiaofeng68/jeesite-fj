/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报数统计Entity
 * @author 小风
 * @version 2018-03-18
 */
public class FjBaoshu extends DataEntity<FjBaoshu> {
	
	private static final long serialVersionUID = 1L;
	private String protype;		// 项目类型编号
	private String num;		// 次数
	private String startDate;
	private String endDate;
	public FjBaoshu() {
		super();
	}

	public FjBaoshu(String id){
		super(id);
	}

	@Length(min=0, max=10, message="项目类型编号长度必须介于 0 和 10 之间")
	public String getProtype() {
		return protype;
	}

	public void setProtype(String protype) {
		this.protype = protype;
	}
	
	@Length(min=0, max=11, message="次数长度必须介于 0 和 11 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
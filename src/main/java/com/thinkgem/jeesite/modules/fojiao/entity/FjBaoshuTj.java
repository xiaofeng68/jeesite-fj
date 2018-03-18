/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Office;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报数统计Entity
 * @author 小风
 * @version 2018-03-18
 */
public class FjBaoshuTj extends DataEntity<FjBaoshuTj> {
	
	private static final long serialVersionUID = 1L;
	private String protype;		// 项目类型编号
	private Office office;		// office_id
	private Integer seq;		// seq
	private Integer nums;		// nums
	
	public FjBaoshuTj() {
		super();
	}

	public FjBaoshuTj(String id){
		super(id);
	}

	@Length(min=0, max=10, message="项目类型编号长度必须介于 0 和 10 之间")
	public String getProtype() {
		return protype;
	}

	public void setProtype(String protype) {
		this.protype = protype;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=11, message="seq长度必须介于 0 和 11 之间")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Length(min=0, max=11, message="nums长度必须介于 0 和 11 之间")
	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}
	
}
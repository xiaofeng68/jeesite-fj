/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshuTj;
import com.thinkgem.jeesite.modules.fojiao.service.FjBaoshuTjService;

/**
 * 报数统计Controller
 * @author 小风
 * @version 2018-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fojiao/fjBaoshuTj")
public class FjBaoshuTjController extends BaseController {

	@Autowired
	private FjBaoshuTjService fjBaoshuTjService;
	
	@ModelAttribute
	public FjBaoshuTj get(@RequestParam(required=false) String id) {
		FjBaoshuTj entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fjBaoshuTjService.get(id);
		}
		if (entity == null){
			entity = new FjBaoshuTj();
		}
		return entity;
	}
	
	@RequiresPermissions("fojiao:fjBaoshuTj:view")
	@RequestMapping(value = {"list", ""})
	public String list(FjBaoshuTj fjBaoshuTj, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FjBaoshuTj> page = fjBaoshuTjService.findPage(new Page<FjBaoshuTj>(request, response), fjBaoshuTj); 
		model.addAttribute("page", page);
		return "modules/fojiao/fjBaoshuTjList";
	}

	@RequiresPermissions("fojiao:fjBaoshuTj:view")
	@RequestMapping(value = "form")
	public String form(FjBaoshuTj fjBaoshuTj, Model model) {
		model.addAttribute("fjBaoshuTj", fjBaoshuTj);
		return "modules/fojiao/fjBaoshuTjForm";
	}

	@RequiresPermissions("fojiao:fjBaoshuTj:edit")
	@RequestMapping(value = "save")
	public String save(FjBaoshuTj fjBaoshuTj, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fjBaoshuTj)){
			return form(fjBaoshuTj, model);
		}
		fjBaoshuTjService.save(fjBaoshuTj);
		addMessage(redirectAttributes, "保存报数统计成功");
		return "redirect:"+Global.getAdminPath()+"/fojiao/fjBaoshuTj/?repage";
	}
	
	@RequiresPermissions("fojiao:fjBaoshuTj:edit")
	@RequestMapping(value = "delete")
	public String delete(FjBaoshuTj fjBaoshuTj, RedirectAttributes redirectAttributes) {
		fjBaoshuTjService.delete(fjBaoshuTj);
		addMessage(redirectAttributes, "删除报数统计成功");
		return "redirect:"+Global.getAdminPath()+"/fojiao/fjBaoshuTj/?repage";
	}

}
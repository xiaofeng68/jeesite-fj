/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;

/**
 * 缓存Controller
 * @author ThinkGem
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/cache")
public class CacheController extends BaseController {
	
	@RequiresPermissions("sys:cache:view")
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/cacheList";
	}
	@RequiresPermissions("sys:cache:edit")
	@RequestMapping(value = "reload")
	public String reload(String name, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isEmpty(name)) {
			addMessage(redirectAttributes, "缓存名称不存在！");
		}else {
			CacheUtils.removeAll(name);
			addMessage(redirectAttributes, "缓存清理成功！");
		}
		return "modules/sys/cacheList";
	}
}

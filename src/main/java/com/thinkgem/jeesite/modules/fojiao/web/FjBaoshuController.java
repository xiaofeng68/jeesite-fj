/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshu;
import com.thinkgem.jeesite.modules.fojiao.service.FjBaoshuService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.BaoShuUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 亿遍报数Controller
 * @author 小风
 * @version 2018-03-17
 */
@Controller
@RequestMapping(value = "${adminPath}/fojiao/fjBaoshu")
public class FjBaoshuController extends BaseController {

	@Autowired
	private FjBaoshuService fjBaoshuService;
	
	@ModelAttribute
	public FjBaoshu get(@RequestParam(required=false) String id) {
		FjBaoshu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fjBaoshuService.get(id);
		}
		if (entity == null){
			entity = new FjBaoshu();
		}
		return entity;
	}
	
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = {"list", ""})
	public String list(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date now = new Date();
		Date date = DateUtils.getBegainAndEndDate(now, "week")[0];
		model.addAttribute("minDate", DateUtils.formatDate(date));
		model.addAttribute("maxDate", DateUtils.formatDate(now));
		return "modules/fojiao/fjBaoshuList";
	}
	
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "classStatic")
	public String classStatic(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(fjBaoshu.getCreateDate()==null) {
			fjBaoshu.setCreateDate(new Date());
		}
		model.addAttribute("list", fjBaoshuService.getClassStatics(fjBaoshu));
		return "modules/fojiao/fjBaoshuClassStatic";
	}
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "noDetail")
	public String noDetail(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(fjBaoshu.getCreateDate()==null) {
			fjBaoshu.setCreateDate(new Date());
		}
		model.addAttribute("list", fjBaoshuService.noDetailStatics(fjBaoshu));
		return "modules/fojiao/fjBaoShuNoDetail";
	}
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "noDaysDetail")
	public String noDaysDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -3);
		model.addAttribute("list3", fjBaoshuService.noDaysDetailStatics(cal.getTime()));
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -7);
		model.addAttribute("list7", fjBaoshuService.noDaysDetailStatics(cal.getTime()));
		return "modules/fojiao/fjBaoShuNoDaysDetail";
	}
	

	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "form")
	public String form(FjBaoshu fjBaoshu, Model model) {
		model.addAttribute("fjBaoshu", fjBaoshu);
		return "modules/fojiao/fjBaoshuForm";
	}

	@RequiresPermissions("fojiao:fjBaoshu:edit")
	@RequestMapping(value = "save")
	public String save(FjBaoshu fjBaoshu,String baoshuNum, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fjBaoshu)){
			return form(fjBaoshu, model);
		}
		fjBaoshuService.saveBatch(fjBaoshu,baoshuNum);
		addMessage(redirectAttributes, "保存报数成功");
		return "redirect:"+Global.getAdminPath()+"/fojiao/fjBaoshu/?repage";
	}
	
	@RequiresPermissions("fojiao:fjBaoshu:edit")
	@RequestMapping(value = "delete")
	public String delete(FjBaoshu fjBaoshu, RedirectAttributes redirectAttributes) {
		fjBaoshuService.delete(fjBaoshu);
		addMessage(redirectAttributes, "删除报数成功");
		return "redirect:"+Global.getAdminPath()+"/fojiao/fjBaoshu/?repage";
	}
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "报数统计.xlsx";
	    	List<String> headerList = Lists.newArrayList();
			for (int i = 1; i <= 4; i++) {
				headerList.add("");
			}
			User user = fjBaoshu.getCreateBy()==null?UserUtils.getUser():fjBaoshu.getCreateBy();
			User daixiu = user.getDaixiu()!=null?user.getDaixiu():new User();
			ExportExcel ee = new ExportExcel(fileName, headerList);
			Row row = ee.addRow();
			ee.addCell(row, 0, "共修师兄");
			ee.addCell(row, 1, user.getName());
			ee.addCell(row, 2, "代修师兄");
			ee.addCell(row, 3, daixiu.getName());
			
			row = ee.addRow();
			ee.addCell(row, 0, "共修编号");
			ee.addCell(row, 1, user.getLoginName());
			ee.addCell(row, 2, "代修编号");
			ee.addCell(row, 3, daixiu.getLoginName());
			String createDate = DateUtils.formatDate(fjBaoshu.getCreateDate());
			row = ee.addRow();
			ee.addCell(row, 0, "共修日期");
			ee.addCell(row, 1, createDate);
			ee.addCell(row, 2, "共修日期");
			ee.addCell(row, 3, createDate);
			
			row = ee.addRow();
			ee.addCell(row, 0, "共修项目");
			ee.addCell(row, 1, "共修报数");
			ee.addCell(row, 2, "已完成数量");
			ee.addCell(row, 3, "班内排名");
			
			List<Map<String,Object>> list = BaoShuUtils.getUserBaoshu();
			for(Map<String,Object> obj : list) {
				row = ee.addRow();
				ee.addCell(row, 0, obj.get("label"));
				ee.addCell(row, 1, obj.get("value"));
				ee.addCell(row, 2, obj.get("nums"));
				ee.addCell(row, 3, obj.get("seq"));
			}
	    	ee.write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出报数统计！失败信息："+e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()+ "/fojiao/fjBaoshuList?repage";
    }
}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.fojiao.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.fojiao.entity.FjBaoshu;
import com.thinkgem.jeesite.modules.fojiao.service.FjBaoshuService;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
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
		if(fjBaoshu.getCreateDate()==null) {
			fjBaoshu.setCreateDate(new Date());
		}
		if(fjBaoshu.getCreateBy()==null) {
			fjBaoshu.setCreateBy(UserUtils.getUser());
		}
		model.addAttribute("today",DateUtils.formatDate(fjBaoshu.getCreateDate()));
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
		
		model.addAttribute("minDate", DateUtils.formatDate(calendar.getTime()));
		model.addAttribute("maxDate", DateUtils.formatDate(now));
		model.addAttribute("list", fjBaoshuService.getUserBaoshu(fjBaoshu));
		return "modules/fojiao/fjBaoshuList";
	}
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "userList")
	public String userList(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		String today = DateUtils.formatDate(new Date());
		if(StringUtils.isEmpty(fjBaoshu.getStartDate())) {
			fjBaoshu.setStartDate(today);
		}
		if(StringUtils.isEmpty(fjBaoshu.getEndDate())) {
			fjBaoshu.setEndDate(today);
		}
		model.addAttribute("startDate",fjBaoshu.getStartDate());
		model.addAttribute("endDate",fjBaoshu.getEndDate());
		model.addAttribute("today",today);
		model.addAttribute("list", fjBaoshuService.getUserStatics(fjBaoshu));
		return "modules/fojiao/fjUserList";
	}
	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "people")
	public String people(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		String today = DateUtils.formatDate(new Date());
		if(StringUtils.isEmpty(fjBaoshu.getStartDate())) {
			fjBaoshu.setStartDate(today);
		}
		if(StringUtils.isEmpty(fjBaoshu.getEndDate())) {
			fjBaoshu.setEndDate(today);
		}
		
		if(fjBaoshu.getCreateBy()==null) {
			fjBaoshu.setCreateBy(UserUtils.getUser());
		}
		model.addAttribute("startDate",fjBaoshu.getStartDate());
		model.addAttribute("endDate",fjBaoshu.getEndDate());
		model.addAttribute("list", fjBaoshuService.findPeopleData(fjBaoshu));
		return "modules/fojiao/fjPeople";
	}

	@RequiresPermissions("fojiao:fjBaoshu:view")
	@RequestMapping(value = "classStatic")
	public String classStatic(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(StringUtils.isEmpty(fjBaoshu.getStartDate()) && StringUtils.isEmpty(fjBaoshu.getEndDate())) {
			Date now = new Date();
			Date[] dates = DateUtils.getBegainAndEndDate(now, "week");
			fjBaoshu.setStartDate(DateUtils.formatDate(dates[0]));
			fjBaoshu.setEndDate(DateUtils.formatDate(dates[1]));
		}
		if(fjBaoshu.getCreateBy()!=null && fjBaoshu.getCreateBy().getOffice()!=null) {
		String officeId = fjBaoshu.getCreateBy().getOffice().getId();
		if(!StringUtils.isEmpty(officeId)) {
			fjBaoshu.getCreateBy().setOffice(officeService.get(officeId));
		}
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
    @RequestMapping(value = "save", method=RequestMethod.POST)
    public String save(FjBaoshu fjBaoshu, String baoshuNum, Model model,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, fjBaoshu)){
			return form(fjBaoshu, model);
		}
		User user =	systemService.getUser(fjBaoshu.getCreateBy().getId());
		fjBaoshu.setCreateBy(user);
		fjBaoshuService.saveBatch(fjBaoshu,baoshuNum);
		if(user!=null) {
			redirectAttributes.addAttribute("createBy.id", user.getId());
			redirectAttributes.addAttribute("createBy.name", user.getName());
			redirectAttributes.addAttribute("createBy.loginName", user.getLoginName());
		}
		redirectAttributes.addAttribute("createDate",DateUtils.formatDate(fjBaoshu.getCreateDate()));
		addMessage(redirectAttributes, "报数成功，随喜您的功德");
		return "redirect:"+adminPath+"/fojiao/fjBaoshu/list?repage";
	}
	
	@RequiresPermissions("fojiao:fjBaoshu:edit")
	@RequestMapping(value = "delete")
	public String delete(FjBaoshu fjBaoshu, RedirectAttributes redirectAttributes) {
		fjBaoshuService.delete(fjBaoshu);
		addMessage(redirectAttributes, "删除报数成功");
		return "redirect:"+adminPath+"/fojiao/fjBaoshu/list?repage";
	}
	@RequiresPermissions("fojiao:fjBaoshu:view")
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
			
			List<Map<String,Object>> list = fjBaoshuService.getUserBaoshu(fjBaoshu);
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
		return "redirect:"+adminPath+"/fojiao/fjBaoshu/list?repage";
    }
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "exportUserStatiscs", method=RequestMethod.POST)
    public String exportUserStatiscsFile(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String today = DateUtils.formatDate(new Date());
		if(StringUtils.isEmpty(fjBaoshu.getStartDate())) {
			fjBaoshu.setStartDate(today);
		}
		if(StringUtils.isEmpty(fjBaoshu.getEndDate())) {
			fjBaoshu.setEndDate(today);
		}
		List<Map<String,Object>> list = fjBaoshuService.getUserStatics(fjBaoshu);
		List<Dict> dicList = DictUtils.getDictList("PROTYPE");
		try {
			String fileName = "用户统计.xlsx";
	    	List<String> headerList = Lists.newArrayList();
	    	headerList.add("共修编号");
	    	headerList.add("法号");
	    	headerList.add("登陆名");
			for (Dict dic : dicList) {
				headerList.add(dic.getLabel());
			}
			ExportExcel ee = new ExportExcel(fileName, headerList);
			Row row;
			for(Map<String,Object> obj : list) {
				row = ee.addRow();
				if(obj.get("login_name")==null) {
					CellStyle style = ee.addCell(row, 0, "合计").getCellStyle();
					style.setAlignment(CellStyle.ALIGN_CENTER);  
					style.setBorderBottom(CellStyle.BORDER_THIN);
					style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
					ee.addMergedCell(row, 0, 2);
				}else {
				ee.addCell(row, 0, obj.get("login_name"));
				ee.addCell(row, 1, obj.get("name"));
				ee.addCell(row, 2, obj.get("name"));
				}
				int index=3;
				for(Dict dic : dicList) {
					ee.addCell(row, index, obj.get(dic.getValue()));
					index++;
				}
			}
			
	    	ee.write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出报数统计！失败信息："+e.getMessage());
		}
		return "modules/fojiao/fjUserList";
	}
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "exportPeopleData", method=RequestMethod.POST)
    public String exportPeopleData(FjBaoshu fjBaoshu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String today = DateUtils.formatDate(new Date());
			if(StringUtils.isEmpty(fjBaoshu.getStartDate())) {
				fjBaoshu.setStartDate(today);
			}
			if(StringUtils.isEmpty(fjBaoshu.getEndDate())) {
				fjBaoshu.setEndDate(today);
			}
			
			if(fjBaoshu.getCreateBy()==null) {
				fjBaoshu.setCreateBy(UserUtils.getUser());
			}
			List<Map<String,Object>> list = fjBaoshuService.findPeopleData(fjBaoshu);
			String fileName = fjBaoshu.getCreateBy().getName()+"的数据查询.xlsx";
			List<String> headerList = Lists.newArrayList();
				headerList.add("共修项目");
				headerList.add("共修报数");
			String title = fjBaoshu.getStartDate()+"至"+fjBaoshu.getEndDate()+"个人报数查询";
			ExportExcel ee = new ExportExcel(title, headerList);
			
			Row row ;
			for(Map<String,Object> obj : list) {
				row = ee.addRow();
				ee.addCell(row, 0, obj.get("label"));
				ee.addCell(row, 1, obj.get("nums"));
			}
	    	ee.write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出失败！失败信息："+e.getMessage());
		}
		return "modules/fojiao/fjPeople";
    }
}
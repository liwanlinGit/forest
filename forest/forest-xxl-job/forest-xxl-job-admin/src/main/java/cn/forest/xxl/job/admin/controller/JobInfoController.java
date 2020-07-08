package cn.forest.xxl.job.admin.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.xxl.job.admin.core.cron.CronExpression;
import cn.forest.xxl.job.admin.core.model.XxlJobInfo;
import cn.forest.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import cn.forest.xxl.job.admin.core.thread.JobTriggerPoolHelper;
import cn.forest.xxl.job.admin.core.trigger.TriggerTypeEnum;
import cn.forest.xxl.job.admin.core.util.I18nUtil;
import cn.forest.xxl.job.admin.dao.XxlJobGroupDao;
import cn.forest.xxl.job.admin.service.XxlJobService;
import cn.forest.xxl.job.core.biz.model.ReturnT;
import cn.forest.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import cn.forest.xxl.job.core.glue.GlueTypeEnum;
import cn.forest.xxl.job.core.util.DateUtil;

@RestController
@RequestMapping("/jobinfo")
public class JobInfoController {
	@Resource
	private XxlJobGroupDao xxlJobGroupDao;
	@Resource
	private XxlJobService xxlJobService;
	
	
	
	@RequestMapping("/data")
	public Map<String,Object> data(){
		Map<String, Object> map=new HashMap<String, Object>();
		ExecutorRouteStrategyEnum[] executorRouteStrategyEnum = ExecutorRouteStrategyEnum.values(); // 路由策略-列表
		List<ExecutorRouteStrategyEnum> executorRouteStrategyList = Arrays.asList(executorRouteStrategyEnum);
		List executorRouteStrategyLists=new ArrayList();
		executorRouteStrategyList.forEach(e ->{
			executorRouteStrategyLists.add(e+"@"+e.getTitle());
		});
		
		ExecutorBlockStrategyEnum[] executorBlockStrategyEnum = ExecutorBlockStrategyEnum.values(); // 阻塞处理策略-字典
		List<ExecutorBlockStrategyEnum> executorBlockStrategyList = Arrays.asList(executorBlockStrategyEnum);
		List executorBlockStrategyLists=new ArrayList();
		executorBlockStrategyList.forEach(e ->{
			executorBlockStrategyLists.add(e+"@"+e.getTitle());
		});
		
		GlueTypeEnum[] glueTypeEnum = GlueTypeEnum.values();  // Glue类型-字典
		 List<GlueTypeEnum> glueTypeList = Arrays.asList(glueTypeEnum);
		 List glueTypeLists=new ArrayList();
		 glueTypeList.forEach(e ->{
			 glueTypeLists.add(e+"@"+e.getDesc());
		 });
		
		map.put("erse", executorRouteStrategyLists);
		map.put("ebse", executorBlockStrategyLists);
		map.put("glueType", glueTypeLists);
		map.put("jobGroup", xxlJobGroupDao.findAll());
		return map;
	}
	
	@RequestMapping("/pageList")
	public Map<String, Object> pageList( int page, int pageSize,
			 int triggerStatus, String jobDesc, String executorHandler, String author) {
		Map<String, Object> pageList = xxlJobService.pageList((page-1)*pageSize, pageSize, triggerStatus, jobDesc, executorHandler, author);
		pageList.put("page", page);
		return pageList;
	}
	
	@RequestMapping("/getById")
	public XxlJobInfo getById(Integer id) {
		return xxlJobService.loadById(id);
	}
	
	@RequestMapping("/add")
	public ReturnT<String> add(XxlJobInfo jobInfo) {
		return xxlJobService.add(jobInfo);
	}
	
	@RequestMapping("/update")
	public ReturnT<String> update(XxlJobInfo jobInfo) {
		return xxlJobService.update(jobInfo);
	}
	
	@RequestMapping("/remove")
	public ReturnT<String> remove(int id) {
		return xxlJobService.remove(id);
	}
	
	@RequestMapping("/stop")
	public ReturnT<String> pause(int id) {
		return xxlJobService.stop(id);
	}
	
	@RequestMapping("/start")
	public ReturnT<String> start(int id) {
		return xxlJobService.start(id);
	}
	
	@RequestMapping("/trigger")
	public ReturnT<String> triggerJob(int id, String executorParam, String addressList) {
		if (executorParam == null) {
			executorParam = "";
		}
		JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addressList);
		return ReturnT.SUCCESS;
	}

	@RequestMapping("/nextTriggerTime")
	public ReturnT<List<String>> nextTriggerTime(String cron) {
		List<String> result = new ArrayList<>();
		try {
			CronExpression cronExpression = new CronExpression(cron);
			Date lastTime = new Date();
			for (int i = 0; i < 5; i++) {
				lastTime = cronExpression.getNextValidTimeAfter(lastTime);
				if (lastTime != null) {
					result.add(DateUtil.formatDateTime(lastTime));
				} else {
					break;
				}
			}
		} catch (ParseException e) {
			return new ReturnT<List<String>>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid"));
		}
		return new ReturnT<List<String>>(result);
	}
	
	
}

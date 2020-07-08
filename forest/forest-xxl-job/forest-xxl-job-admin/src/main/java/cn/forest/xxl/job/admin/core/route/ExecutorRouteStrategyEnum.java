package cn.forest.xxl.job.admin.core.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteBusyover;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteConsistentHash;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteFailover;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteFirst;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteLFU;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteLRU;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteLast;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteRandom;
import cn.forest.xxl.job.admin.core.route.strategy.ExecutorRouteRound;
import cn.forest.xxl.job.admin.core.util.I18nUtil;

/**
 * Created by xuxueli on 17/3/10.
 */
public enum ExecutorRouteStrategyEnum {

    FIRST(I18nUtil.getString("jobconf_route_first"), new ExecutorRouteFirst()),
    LAST(I18nUtil.getString("jobconf_route_last"), new ExecutorRouteLast()),
    ROUND(I18nUtil.getString("jobconf_route_round"), new ExecutorRouteRound()),
    RANDOM(I18nUtil.getString("jobconf_route_random"), new ExecutorRouteRandom()),
    CONSISTENT_HASH(I18nUtil.getString("jobconf_route_consistenthash"), new ExecutorRouteConsistentHash()),
    LEAST_FREQUENTLY_USED(I18nUtil.getString("jobconf_route_lfu"), new ExecutorRouteLFU()),
    LEAST_RECENTLY_USED(I18nUtil.getString("jobconf_route_lru"), new ExecutorRouteLRU()),
    FAILOVER(I18nUtil.getString("jobconf_route_failover"), new ExecutorRouteFailover()),
    BUSYOVER(I18nUtil.getString("jobconf_route_busyover"), new ExecutorRouteBusyover()),
    SHARDING_BROADCAST(I18nUtil.getString("jobconf_route_shard"), null);

    ExecutorRouteStrategyEnum(String title, ExecutorRouter router) {
        this.title = title;
        this.router = router;
    }

    private String title;
    private ExecutorRouter router;

    public String getTitle() {
        return title;
    }
    public ExecutorRouter getRouter() {
        return router;
    }

    public static ExecutorRouteStrategyEnum match(String name, ExecutorRouteStrategyEnum defaultItem){
        if (name != null) {
            for (ExecutorRouteStrategyEnum item: ExecutorRouteStrategyEnum.values()) {
                if (item.name().equals(name)) {
                    return item;
                }
            }
        }
        return defaultItem;
    }
  
   public static List<Map<String, Object>> executorRouteStrategyEnumMap(){
	   List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	   ExecutorRouteStrategyEnum[] values = ExecutorRouteStrategyEnum.values();
	   for (ExecutorRouteStrategyEnum executorRouteStrategyEnum : values) {
		   Map<String, Object> e=new HashMap<String, Object>();
		   e.put(executorRouteStrategyEnum.name(), executorRouteStrategyEnum.title);
		   list.add(e);
	   }
	   return list;
   }

}

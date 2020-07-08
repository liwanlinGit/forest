package cn.forest.xxl.job.admin.core.route.strategy;

import java.util.List;

import cn.forest.xxl.job.admin.core.route.ExecutorRouter;
import cn.forest.xxl.job.core.biz.model.ReturnT;
import cn.forest.xxl.job.core.biz.model.TriggerParam;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteLast extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(addressList.size()-1));
    }

}

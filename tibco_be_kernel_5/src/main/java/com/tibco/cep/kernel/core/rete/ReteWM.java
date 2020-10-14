package com.tibco.cep.kernel.core.rete;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;

@Weave(type = MatchType.BaseClass)
public abstract class ReteWM {

	@Trace(dispatcher=true)
	public void executeRules()
	{ 
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom/ReteWM/executeRules()");
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void executeRules(String paramString, List paramList1, List paramList2, LinkedHashSet paramLinkedHashSet, List paramList3) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom/ReteWM/executeRules(....)");
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void invoke(Action action, Object aobj[])
	{ 
		List<String> metricNames = new ArrayList<String>();
		metricNames.add("Custom");
		metricNames.add("ReteWM");
		metricNames.add("invoke");
		if(action != null) {
			Rule rule = action.getRule();
			if(rule != null) {
				String name = rule.getName();
				if(name != null && !name.isEmpty()) {
					metricNames.add(name);
				}
			}
		}
		String[] metric_names = new String[metricNames.size()];
		metricNames.toArray(metric_names);
		NewRelic.getAgent().getTracedMethod().setMetricName(metric_names);
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Object invoke(RuleFunction paramRuleFunction, Map paramMap) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom/ReteWM/invoke(RuleFunction,Map)");
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object invoke(RuleFunction paramRuleFunction, Object[] paramArrayOfObject) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom/ReteWM/invoke(RuleFunction,Object[])");
		return Weaver.callOriginal();
	}
}

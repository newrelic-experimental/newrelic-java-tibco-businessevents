package com.tibco.cep.kernel.model.rule.impl;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.kernel.model.rule.Rule;

@Weave(type=MatchType.BaseClass)
public abstract class ActionImpl {

	public abstract Rule getRule();

	@Trace(dispatcher=true)
	public void execute(Object[] paramArrayOfObject)
	{
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] { "Custom", "ActionImpl", getClass().getSimpleName(), getRule().getName() });
		Weaver.callOriginal();
	}
}

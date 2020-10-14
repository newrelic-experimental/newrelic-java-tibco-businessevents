package com.tibco.cep.bpmn.runtime.agent;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class JobImpl {

	public abstract ProcessAgent getProcessAgent();
	
	@Trace(dispatcher=true)
	public void run() {
		AgentBridge.getAgent().startAsyncActivity(this);
		
		Weaver.callOriginal();
	}
}

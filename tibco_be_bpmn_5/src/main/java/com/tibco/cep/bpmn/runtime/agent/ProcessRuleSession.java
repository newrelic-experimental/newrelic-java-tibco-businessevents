package com.tibco.cep.bpmn.runtime.agent;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ProcessRuleSession {

	@Trace(dispatcher=true)
	public void invokeProcess(Job job, boolean isAsync) {
		if(isAsync) {
			AgentBridge.getAgent().getTransaction().registerAsyncActivity(job);
		}
		Weaver.callOriginal();
	}
}

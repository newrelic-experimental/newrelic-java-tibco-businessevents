package com.tibco.cep.kernel.core.rete;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class BeTransaction {

	public abstract String getIdentifier();
	
	@Trace(dispatcher=true)
	public void execute() {
		String identifier = getIdentifier();
		if(identifier == null) {
			identifier = "Unknown BeTransaction";
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BeTransaction",identifier});
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected abstract void doTxnWork();
}

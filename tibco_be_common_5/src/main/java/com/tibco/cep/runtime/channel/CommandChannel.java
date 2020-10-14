package com.tibco.cep.runtime.channel;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.runtime.model.event.CommandEvent;

@Weave(type=MatchType.Interface)
public abstract class CommandChannel {
	
	@Trace(dispatcher=true)
	public void sendCommand(String s, CommandEvent commandevent, boolean flag) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","CommandChannel",getClass().getName(),commandevent.getURI()});
		Weaver.callOriginal();
	}

}

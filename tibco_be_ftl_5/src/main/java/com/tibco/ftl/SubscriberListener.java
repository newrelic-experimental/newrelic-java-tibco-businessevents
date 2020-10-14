package com.tibco.ftl;

import java.util.List;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class SubscriberListener {
	
	@Trace(dispatcher=true)
	public void messagesReceived(List list, EventQueue eventqueue) {
		
		Weaver.callOriginal();
	}

}

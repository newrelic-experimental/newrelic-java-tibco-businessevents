package com.tibco.ftl;

import java.util.concurrent.TimeUnit;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class EventQueue {
	
	@Trace(dispatcher=true)
	public void dispatch(long l, TimeUnit timeunit) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void dispatch(double d) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void dispatch() {
		
		Weaver.callOriginal();
	}

}

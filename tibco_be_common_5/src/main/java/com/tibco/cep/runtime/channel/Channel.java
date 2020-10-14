package com.tibco.cep.runtime.channel;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.runtime.model.event.SimpleEvent;

@Weave(type=MatchType.Interface)
public abstract class Channel {
	
	public abstract String getURI();
	
	@Weave(type=MatchType.Interface)
	public static abstract class Destination {
		
		public abstract String getURI();
		
		@SuppressWarnings("rawtypes")
		@Trace(dispatcher=true)
		public int send(SimpleEvent simpleevent, Map map) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Destination","send",getURI(),simpleevent.getDestinationURI()});
			return Weaver.callOriginal();
		}
		
		@SuppressWarnings("rawtypes")
		@Trace(dispatcher=true)
		public String sendImmediate(SimpleEvent simpleevent, Map map) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Destination","sendImmediate",getURI(),simpleevent.getDestinationURI()});
			return Weaver.callOriginal();
		}
		public abstract DestinationConfig getConfig();
	}

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public void send(SimpleEvent simpleevent, String paramString, Map map) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Channel","send",getURI(),simpleevent.getDestinationURI()});
		Weaver.callOriginal();
	}

}

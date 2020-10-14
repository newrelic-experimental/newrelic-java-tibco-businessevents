package com.tibco.cep.driver.http;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.driver.http.client.HttpChannelClient;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.kernel.model.entity.Event;

@Weave
public abstract class HttpDestination {

	public abstract String getName();
	
	@Trace(dispatcher=true)
	public void processMessage(HttpChannelServerRequest serverRequest, HttpChannelServerResponse paramHttpChannelServerResponse, String paramString) {
		String requestURI = serverRequest.getRequestURI();
		String method = serverRequest.getMethod();
		String pathInfo = serverRequest.getPathInfo();
		String serverName = serverRequest.getServerName();
		int serverPort = serverRequest.getServerPort();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","processMessage",getName(),method,requestURI});

		NewRelic.addCustomParameter("server", serverName);
		NewRelic.addCustomParameter("port", serverPort);
		if(pathInfo != null) {
			NewRelic.addCustomParameter("path info", pathInfo);
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public SimpleEvent processResponse(HttpChannelClientResponse clientResponse, Map<String, Object> paramMap) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","processResponse",getName()});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public int send(SimpleEvent simpleEvent, Map paramMap) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","send",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public String sendImmediate(SimpleEvent simpleEvent, Map paramMap) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","sendImmediate",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public int sendMessage(HttpChannelClientRequest clientRequest, SimpleEvent simpleEvent) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","sendMessage",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public int sendMessage(HttpChannelClient channelClient, SimpleEvent simpleEvent) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","sendMessage",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Event sendSyncMessage(HttpChannelClient paramHttpChannelClient, SimpleEvent simpleEvent, String paramString, long paramLong) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","sendMessage",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Event sendSyncMessage(HttpChannelClientRequest paramHttpChannelClientRequest, SimpleEvent simpleEvent, String paramString, long paramLong) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpDestination","sendMessage",getName(),simpleEvent.getDestinationURI()});
		return Weaver.callOriginal();
	}



}

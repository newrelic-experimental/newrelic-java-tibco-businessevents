package com.tibco.ftl;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Publisher {
	
	@Trace(dispatcher=true)
	public void send(Message message) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void send(Message amessage[]) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void send(Message amessage[], int i) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void sendToInbox(Inbox inbox, Message message) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void send_eFTLRequest(Inbox inbox, Message message) {
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void send_eFTLReply(Message message, Message message1) {
		
		Weaver.callOriginal();
	}

}

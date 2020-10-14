package com.tibco.cep.runtime.channel.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractDestination<A> {

	abstract public String getName();
	
	@Trace(dispatcher=true)
	public void onMessage(RuleSession session, EventContext ctx) throws Exception {
		String sessionName = session.getName();
		NewRelic.addCustomParameter("RuleSession Name", sessionName);
		
		RuleServiceProvider ruleServiceProvider = session.getRuleServiceProvider();
		
		if(ruleServiceProvider != null) {
			NewRelic.addCustomParameter("Rule Service Provider", ruleServiceProvider.getName());
		}
		
		
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName(new String[] {"Custom","ChannelDestination","onMessage",getName()});
		traced.addRollupMetricName("Custom","ChannelDestination","onMessage");
		
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	 public void onMessage(RuleSession session, SimpleEvent event) throws Exception {
		String sessionName = session.getName();
		NewRelic.addCustomParameter("RuleSession Name", sessionName);
		
		RuleServiceProvider ruleServiceProvider = session.getRuleServiceProvider();
		
		if(ruleServiceProvider != null) {
			NewRelic.addCustomParameter("Rule Service Provider", ruleServiceProvider.getName());
		}

		ExpandedName expandedName = event.getExpandedName();
		if (expandedName != null) {
			String eventLocal = expandedName.getLocalName();
			NewRelic.addCustomParameter("SimpleEvent Local Name", eventLocal);
			
			String eventNS = expandedName.getNamespaceURI();
			NewRelic.addCustomParameter("SimpleEvent Namespace", eventNS);
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName(new String[] {"Custom","ChannelDestination","onMessage",getName()});
		traced.addRollupMetricName("Custom","ChannelDestination","onMessage");
		Weaver.callOriginal();
		
	}

}

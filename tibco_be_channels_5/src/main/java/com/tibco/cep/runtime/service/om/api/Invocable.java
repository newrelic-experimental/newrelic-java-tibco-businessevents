package com.tibco.cep.runtime.service.om.api;

import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Invocable {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public Object invoke(Map.Entry paramEntry) {

		String objName = paramEntry.getClass().getName();

		NewRelic.addCustomParameter("Object Name", objName);
		
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Channel", new String[] {"Custom/Tibco/Channels",objName});

		return Weaver.callOriginal();
	}
	
}

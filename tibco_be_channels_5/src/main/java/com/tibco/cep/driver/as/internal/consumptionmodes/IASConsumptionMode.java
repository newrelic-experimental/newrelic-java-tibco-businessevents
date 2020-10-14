package com.tibco.cep.driver.as.internal.consumptionmodes;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;


@Weave(type=MatchType.Interface)
public abstract class IASConsumptionMode {
	
	@Trace(dispatcher=true)
	public void consume(Object paramObject) throws Exception {

		String objName = paramObject.getClass().getName();

		NewRelic.addCustomParameter("Object Name", objName);

		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Channel", new String[] {"Custom/Tibco/Channels",objName});

		Weaver.callOriginal();
	}

}

package com.tibco.cep.runtime.session;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.agent.bridge.TransactionNamePriority;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class RuleSession {
	
	public abstract String getName();
	@Trace(dispatcher=true)
	public void executeRules() {
		
		NewRelic.addCustomParameter("Rule Name", getName());
				
		AgentBridge.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Rule", new String[] {"Custom/Tibco/Rules",getName(),"executeRules"});

		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Object invokeFunction(String functionURI, Object[] paramArrayOfObject, boolean paramBoolean) {

		NewRelic.addCustomParameter("Function URI", functionURI);
		
		AgentBridge.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Rule", new String[] {"Custom/Tibco/Rules",getName(),"invokeFunction",functionURI});

		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Object invokeCatalog(String catalog, Object... paramVarArgs) {
		NewRelic.addCustomParameter("Param String", catalog);
		
		AgentBridge.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Rule", new String[] {"Custom/Tibco/Rules",getName(),"invokeCatalog",catalog});

		return Weaver.callOriginal();
	}
}

package com.tibco.cep.kernel.core.base;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public class OperationList {
	@Trace
	public boolean performOps() {
        return Weaver.callOriginal();
    }

	@Trace
	public boolean performOps(boolean flags) {
		return Weaver.callOriginal();
	}
	
}

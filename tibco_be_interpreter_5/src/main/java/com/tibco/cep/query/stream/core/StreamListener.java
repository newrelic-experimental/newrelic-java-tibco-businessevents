package com.tibco.cep.query.stream.core;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.tibco.cep.query.stream.context.Context;

//Will match all implementations of the interface.
@Weave(type = MatchType.Interface)
public abstract class StreamListener {

	@Trace
    public abstract void process(Context paramContext);

}

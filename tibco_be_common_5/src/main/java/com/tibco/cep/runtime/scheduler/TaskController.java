package com.tibco.cep.runtime.scheduler;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.xml.data.primitive.ExpandedName;

@Weave(type=MatchType.Interface)
public abstract class TaskController {

	@Trace(dispatcher=true)
	public void processEvent(Channel.Destination destination, SimpleEvent simpleEvent, EventContext eventContext) throws Exception {
		String name = null;
		if(destination != null) {

			DestinationConfig config = destination.getConfig();
			if(config != null) {
				name = destination.getConfig().getName();
				
			}

		}
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),name});
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"Unknown"});
		}
		if (simpleEvent != null) {
			ExpandedName expandedName = simpleEvent.getExpandedName();
			if (expandedName != null) {
				String eventLocal = expandedName.getLocalName();
				String eventNS = expandedName.getNamespaceURI();
				NewRelic.addCustomParameter("SimpleEvent Namespace", eventNS);
				NewRelic.addCustomParameter("SimpleEvent Name", eventLocal);
			}
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void processTask(String paramString, Runnable paramRunnable) throws Exception {
		NewRelic.getAgent().getLogger().log(Level.FINE, "TaskController:processTask - input string: {0}", new Object[] {paramString});
		Weaver.callOriginal();
	}

}

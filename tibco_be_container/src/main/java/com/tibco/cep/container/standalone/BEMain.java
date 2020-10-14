package com.tibco.cep.container.standalone;

import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class BEMain {
	
	protected Properties env = Weaver.callOriginal();

	public void jumpStart() {
		dumpProperties();
		Weaver.callOriginal();
	}
	
	private void dumpProperties() {
		Logger logger = NewRelic.getAgent().getLogger();
		logger.log(Level.FINE, "Properties for BEMain:");
		Set<Object> keys = env.keySet();
		for(Object key : keys) {
			logger.log(Level.FINE, "\tkey: {0}, value: {1}", key,env.get(key));
		}
	}
}

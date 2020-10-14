package com.tibco.cep.runtime.session;

import java.lang.management.ManagementFactory;
import java.util.Properties;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.runtime.service.cluster.Cluster;

import con.nr.tibco.be.instrumentation.NRNamingUtils;

@Weave
public abstract class RuleServiceProviderManager {

	public synchronized RuleServiceProvider newProvider(String instanceName, Properties env) {
		RuleServiceProvider provider = Weaver.callOriginal();
		
		return provider;
	}
	
	public void setDefaultProvider(RuleServiceProvider provider) {
		
		if(provider != null) {
			String providerName = provider.getName();
			Cluster cluster = provider.getCluster();
			StringBuffer sb = new StringBuffer();
			if(cluster != null) {
				String clusterName = cluster.getClusterName();
				if(clusterName != null && !clusterName.isEmpty()) {
					sb.append(clusterName);
					sb.append('-');
				}
			}
			
			if(providerName != null && !providerName.isEmpty()) {
				sb.append(providerName);
			}
			String instanceName = sb.toString();
			if(!instanceName.isEmpty()) {
				NewRelic.setInstanceName(instanceName);
				NRNamingUtils.setApplicationName(instanceName);
			}
			
		}
		NewRelic.setProductName("Tibco");
		NewRelic.setAppServerPort(getPidNumber());
		Weaver.callOriginal();
	}
	
	private int getPidNumber() {
		String runtimeName = ManagementFactory.getRuntimeMXBean().getName();
		String[] split = runtimeName.split("@");
        if (split.length > 1) {
            return Integer.parseInt(split[0]);
        }
        return 80;	
    }
}

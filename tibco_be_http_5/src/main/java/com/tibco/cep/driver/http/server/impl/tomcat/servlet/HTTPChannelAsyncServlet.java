package com.tibco.cep.driver.http.server.impl.tomcat.servlet;

import java.io.IOException;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.cep.driver.http.HttpDestination;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Weave(type=MatchType.ExactClass)
public abstract class HTTPChannelAsyncServlet {


	abstract protected HttpDestination getMatchingDestination(HttpServletRequest request);
	
	@Trace
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpDestination destination = getMatchingDestination(request);
		
		String destinationName = "Unknown Destination";
		if(destination != null) {
			destinationName = destination.getName();
			NewRelic.getAgent().getLogger().log(Level.FINE, "Got destination name {0} from class {1}", new Object[] {destinationName,destination.getClass().getName()});
		}
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Web", new String[] {destinationName});
		
		Weaver.callOriginal();
	}
}

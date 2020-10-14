package com.tibco.cep.bpmn.runtime.activity;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.agent.bridge.TransactionNamePriority;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

@Weave(type=MatchType.Interface)
public abstract class Task {

	public abstract String getName();

	@Trace(dispatcher=true)
	public TaskResult exec(Job paramJob, Variables paramVariables, Task paramTask) {

		String jobName = paramJob.getJobContext().getProcessTemplate().getName();
		String varString = paramVariables.toString();
		
		String taskName = paramTask.getName();

		NewRelic.addCustomParameter("Job Name", jobName);
		NewRelic.addCustomParameter("Task Name", taskName);
		NewRelic.addCustomParameter("Variables String", varString);
		
		AgentBridge.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Task", new String[] {"Custom/Tibco/Tasks",getClass().getName(),getName()});

		return Weaver.callOriginal();
	}

}

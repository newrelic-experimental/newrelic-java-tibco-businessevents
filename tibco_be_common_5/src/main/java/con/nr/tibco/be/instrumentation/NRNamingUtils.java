package con.nr.tibco.be.instrumentation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.samplers.SamplerService;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.agent.util.DefaultThreadFactory;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class NRNamingUtils
{
  private static String applicationName = null;
  private static final String CONFIG_APPNAME = "newrelic.config.app_name";
  private static final String USENGINENAME = "TIBCO.UseEngineName.enabled";
  private static boolean engineNameEnabled = true;

  public static String getApplicationName() {
    return applicationName;
  }

  public static void setApplicationName() {
    String tmp = System.getProperty(CONFIG_APPNAME);
    if ((tmp != null) && (!tmp.isEmpty())) {
      applicationName = tmp;
      return;
    }
    Config config = NewRelic.getAgent().getConfig();
    if (AgentConfig.class.isInstance(config)) {
      AgentConfig agentConfig = (AgentConfig)config;
      applicationName = agentConfig.getApplicationName();
      if ((applicationName != null) && (!applicationName.isEmpty()))
        return;
    }
  }

  private static boolean systemAppNameSet()
  {
    String tmp = System.getProperty(CONFIG_APPNAME);

    return (tmp != null) && (!tmp.isEmpty());
  }

  public static void setApplicationName(String name) {
    Config config = NewRelic.getAgent().getConfig();
    Boolean b = (Boolean)config.getValue(USENGINENAME);
    NewRelic.getAgent().getLogger().log(Level.INFO, "Value of TIBCO.UseEngineName.enabled: {0}", new Object[] { b });

    if ((b != null) && (b.booleanValue() != engineNameEnabled)) {
      engineNameEnabled = b.booleanValue();
      NewRelic.getAgent().getLogger().log(Level.INFO, "Tibco Engine Naming is set to {0}", new Object[] { engineNameEnabled ? "enabled" : "disabled" });
    }
    if (engineNameEnabled) {
      String tmp = System.getProperty(CONFIG_APPNAME);
      if ((systemAppNameSet()) && 
        (!name.equalsIgnoreCase(tmp))) {
        NewRelic.getAgent().getLogger().log(Level.FINE, "Did not set application set because System Config name was set", new Object[0]);
        return;
      }

      if ((tmp == null) || (tmp.isEmpty())) {
        SamplerService samplerService = ServiceFactory.getSamplerService();
        try {
          samplerService.stop();
        } catch (Exception e) {
          NewRelic.getAgent().getLogger().log(Level.FINE, e, "Failed to stop SamplerService", new Object[0]);
        }
        NewRelic.getAgent().getLogger().log(Level.INFO, "In Tibco Engine, Using {0} as the New Relic application name", new Object[] { name });
        System.setProperty(CONFIG_APPNAME, name);
        DelayedStart delayedStart = new DelayedStart(samplerService);

        ThreadFactory threadFactory = new DefaultThreadFactory("New Relic Temp Service", true);
        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
        scheduledExecutor.schedule(delayedStart, 1L, TimeUnit.MINUTES);
      } else {
        NewRelic.getAgent().getLogger().log(Level.INFO, "Not using {0} as the New Relic application name, since property already set to {1}", new Object[] { name, tmp });
      }
    }
  }

  private static class DelayedStart implements Runnable {
    private SamplerService samplerService;

    public DelayedStart(SamplerService s) {
      this.samplerService = s;
    }

    public void run()
    {
      if (this.samplerService.isStopped())
        try {
          this.samplerService.start();
        } catch (Exception e) {
          NewRelic.getAgent().getLogger().log(Level.FINE, e, "Failed to start SamplerService", new Object[0]);
        }
    }
  }
}
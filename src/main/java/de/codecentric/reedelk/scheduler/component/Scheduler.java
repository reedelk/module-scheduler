package de.codecentric.reedelk.scheduler.component;


import de.codecentric.reedelk.runtime.api.annotation.*;
import de.codecentric.reedelk.runtime.api.component.AbstractInbound;
import de.codecentric.reedelk.scheduler.internal.attribute.SchedulerAttributes;
import de.codecentric.reedelk.scheduler.internal.scheduler.SchedulerJob;
import de.codecentric.reedelk.scheduler.internal.scheduler.SchedulingStrategyBuilder;
import org.osgi.service.component.annotations.Component;

import static org.osgi.service.component.annotations.ServiceScope.PROTOTYPE;

@ModuleComponent("Scheduler")
@ComponentOutput(attributes = SchedulerAttributes.class, payload = { Void.class })
@Description("The Scheduler component can be used to fire flow events at " +
                "regular intervals or fires events according to the given cron expression. " +
                "The Scheduler is an Inbound component and it can only be placed at the beginning of a flow.")
@Component(service = Scheduler.class, scope = PROTOTYPE)
public class Scheduler extends AbstractInbound {

    @Property("Strategy")
    @Example("CRON")
    @InitValue("FIXED_FREQUENCY")
    @DefaultValue("FIXED_FREQUENCY")
    @Description("There are two possible execution strategies for a scheduler: <i>Fixed Frequency</i> fires flow events at regular intervals. " +
            "<i>Cron</i> fires events according to the given cron expression.")
    private SchedulingStrategy strategy;

    @Property("Fixed Frequency Configuration")
    @When(propertyName = "strategy", propertyValue = "FIXED_FREQUENCY")
    private FixedFrequencyConfiguration fixedFrequencyConfig;

    @Property("Cron Configuration")
    @When(propertyName = "strategy", propertyValue = "CRON")
    private CronConfiguration cronConfig;

    private SchedulerJob job;

    @Override
    public void onStart() {
        job = SchedulingStrategyBuilder.get(strategy)
                .withFixedFrequencyConfig(cronConfig)
                .withFixedFrequencyConfig(fixedFrequencyConfig)
                .build()
                .schedule(this);
    }

    @Override
    public void onShutdown() {
        if (job != null) {
            job.dispose();
        }
    }

    public void setStrategy(SchedulingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setFixedFrequencyConfig(FixedFrequencyConfiguration fixedFrequencyConfig) {
        this.fixedFrequencyConfig = fixedFrequencyConfig;
    }

    public void setCronConfig(CronConfiguration cronConfig) {
        this.cronConfig = cronConfig;
    }
}

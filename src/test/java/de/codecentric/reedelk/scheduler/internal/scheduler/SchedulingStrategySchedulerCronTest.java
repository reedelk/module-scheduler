package de.codecentric.reedelk.scheduler.internal.scheduler;

import de.codecentric.reedelk.runtime.api.component.InboundEventListener;
import de.codecentric.reedelk.scheduler.component.CronConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingStrategySchedulerCronTest {

    @Mock
    private InboundEventListener listener;

    @Test
    void shouldCorrectlyScheduleJob() {
        // Given
        CronConfiguration configuration = new CronConfiguration();
        configuration.setExpression("* * * ? * *");
        SchedulingStrategySchedulerCron strategy = strategyWith(configuration);

        // When
        strategy.schedule(listener);

        // Then
        verify(strategy).scheduleJob(any(InboundEventListener.class), any(JobDetail.class), any(Trigger.class));
    }

    private SchedulingStrategySchedulerCron strategyWith(CronConfiguration configuration) {
        SchedulingStrategySchedulerCron strategy =
                Mockito.spy(new SchedulingStrategySchedulerCron(configuration));
        doNothing().when(strategy).scheduleJob(any(InboundEventListener.class), any(JobDetail.class), any(Trigger.class));
        return strategy;
    }
}

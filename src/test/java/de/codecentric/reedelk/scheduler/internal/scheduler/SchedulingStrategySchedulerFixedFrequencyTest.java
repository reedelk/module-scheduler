package de.codecentric.reedelk.scheduler.internal.scheduler;

import de.codecentric.reedelk.runtime.api.component.InboundEventListener;
import de.codecentric.reedelk.scheduler.component.FixedFrequencyConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulingStrategySchedulerFixedFrequencyTest {

    @Mock
    private InboundEventListener listener;

    @Test
    void shouldCorrectlyScheduleJob() {
        // Given
        FixedFrequencyConfiguration configuration = new FixedFrequencyConfiguration();
        SchedulingStrategySchedulerFixedFrequency strategy = strategyWith(configuration);

        // When
        strategy.schedule(listener);

        // Then
        verify(strategy).scheduleJob(any(InboundEventListener.class), any(JobDetail.class), any(Trigger.class));
    }

    private SchedulingStrategySchedulerFixedFrequency strategyWith(FixedFrequencyConfiguration configuration) {
        SchedulingStrategySchedulerFixedFrequency strategy =
                spy(new SchedulingStrategySchedulerFixedFrequency(configuration));
        doNothing().when(strategy).scheduleJob(any(InboundEventListener.class), any(JobDetail.class), any(Trigger.class));
        return strategy;
    }
}

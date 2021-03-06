package de.codecentric.reedelk.scheduler.internal.scheduler;

import org.quartz.JobKey;

public class SchedulerJob {

    private final JobKey jobKey;

    SchedulerJob(JobKey jobKey) {
        this.jobKey = jobKey;
    }

    public void dispose() {
        SchedulerProvider.scheduler().deleteJob(jobKey);
    }
}

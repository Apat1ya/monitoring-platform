package io.github.apat1ya.monitor.scheduler;

import io.github.apat1ya.monitor.service.EndpointCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class EndpointSchedulerService {
    private final TaskScheduler taskScheduler;
    private final EndpointCheckService endpointCheckService;
    private final Map<Long, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    public void scheduleEndpoint(Long endpointId, Integer intervalSeconds) {
        tasks.compute(endpointId, (id, oldTask) -> {
            if (oldTask!=null) {
                oldTask.cancel(false);
            }

            Runnable task = () -> {
                try {
                    endpointCheckService.checkEndpoint(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            return taskScheduler.scheduleAtFixedRate(
                    task,
                    Duration.ofSeconds(intervalSeconds)
            );
        });

    }

    public void cancelEndpoint(Long endpointId) {
        ScheduledFuture<?> task = tasks.remove(endpointId);

        if (task!=null) {
            task.cancel(false);
        }
    }
}

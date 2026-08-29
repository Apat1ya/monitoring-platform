package io.github.apat1ya.monitor.service.support;

import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long getCurrentUserId() {
        return 1L; //TODO с монитора кафка будет в этот сервис отправлять и тут consumer будет сюда передавать
    }
}

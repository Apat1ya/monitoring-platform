package io.github.apat1ya.monitor.service.support;

import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public Long getCurrentUserId() {
        return 1L; //TODO передавать юзера через gateway ?kafka?
    }
}

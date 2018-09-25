package com.app.security.events;

import com.app.model.security.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationEventData extends ApplicationEvent{

    private String url;
    private User user;

    public RegistrationEventData(String url, User user) {
        super(user);
        this.url = url;
        this.user = user;
    }
}

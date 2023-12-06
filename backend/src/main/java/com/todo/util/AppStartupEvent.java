package com.todo.util;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppStartupEvent implements ApplicationListener<ApplicationEvent>{

    public void onApplicationEvent(ApplicationEvent event) {

    }

}

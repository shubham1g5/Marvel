package com.example.shubham.marvel;

import com.example.shubham.marvel.common.EventBus;
import com.example.shubham.marvel.common.SchedulerModule;
import com.example.shubham.marvel.data.ComicsRepository;
import com.example.shubham.marvel.data.ComicsRepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ComicsRepositoryModule.class, SchedulerModule.class, MarvelAppModule.class})
public interface AppComponent {

    ComicsRepository getComicsRepository();

    EventBus getEventBus();
}

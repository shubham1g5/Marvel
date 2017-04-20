package com.example.shubham.marvel.data;

import com.example.shubham.marvel.MarvelAppModule;
import com.example.shubham.marvel.common.SchedulerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ComicsRepositoryModule.class, SchedulerModule.class, MarvelAppModule.class})
public interface ComicsRepositoryComponent {
    ComicsRepository getComicsRepository();
}

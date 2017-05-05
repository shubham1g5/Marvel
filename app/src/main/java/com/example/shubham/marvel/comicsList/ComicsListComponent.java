package com.example.shubham.marvel.comicsList;

import com.example.shubham.marvel.AppComponent;
import com.example.shubham.marvel.common.FragmentScoped;
import com.example.shubham.marvel.common.SchedulerModule;

import dagger.Component;

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = SchedulerModule.class)
public interface ComicsListComponent {
    void inject(ComicsListFragment comicsListFragment);
}

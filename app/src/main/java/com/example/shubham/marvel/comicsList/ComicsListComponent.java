package com.example.shubham.marvel.comicsList;

import com.example.shubham.marvel.common.FragmentScoped;
import com.example.shubham.marvel.common.SchedulerModule;
import com.example.shubham.marvel.data.ComicsRepositoryComponent;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ComicsRepositoryComponent.class, modules = SchedulerModule.class)
public interface ComicsListComponent {
    void inject(ComicsListFragment comicsListFragment);
}

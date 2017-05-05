package com.example.shubham.marvel.comicdetail;

import com.example.shubham.marvel.AppComponent;
import com.example.shubham.marvel.common.FragmentScoped;
import com.example.shubham.marvel.common.SchedulerModule;

import dagger.Component;

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {ComicDetailPresenterModule.class, SchedulerModule.class})
public interface ComicDetailComponent {
    void inject(ComicDetailFragment comicDetailFragment);
}

package com.example.shubham.marvel.comicdetail;

import com.example.shubham.marvel.common.FragmentScoped;
import com.example.shubham.marvel.common.SchedulerModule;
import com.example.shubham.marvel.data.ComicsRepositoryComponent;

import dagger.Component;

@FragmentScoped
@Component(dependencies = ComicsRepositoryComponent.class, modules = {ComicDetailPresenterModule.class, SchedulerModule.class})
public interface ComicDetailComponent {
    void inject(ComicDetailFragment comicDetailFragment);
}

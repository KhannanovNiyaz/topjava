package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Integer userId, Meal meal) {
        return checkNotFound(repository.save(userId, meal), "meal=" + meal);
    }

    public void delete(Integer userId, int id){
       checkNotFoundWithId(repository.delete(userId, id), id);
    }

    public Meal get(Integer userId, int id){
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    public Collection<MealTo> getAll(Integer userId){
        return MealsUtil.getTos(checkNotFound(repository.getAll(userId), "userId=" + userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId){
        return MealsUtil.getTos(checkNotFound(repository.getBetween(startDate, endDate, startTime, endTime, userId), "userId=" + userId),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
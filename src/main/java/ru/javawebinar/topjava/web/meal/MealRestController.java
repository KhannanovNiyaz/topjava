package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;

    public Collection<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("userId {} getAllMealTo", userId);
        return service.getAll(userId);
    }

    public Meal get(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("userId {} getMealId {}", userId, mealId);
        return service.get(userId, mealId);
    }

    public Meal save(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("userId {} saveMeal {}", userId, meal);
        checkNew(meal);
        return service.save(userId, meal);
    }

    public void delete(int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("userId {} deleteMealId {}", userId, mealId);
        service.delete(userId, mealId);
    }

    public void update(Meal meal, int mealId) {
        int userId = SecurityUtil.authUserId();
        log.info("userId {} updateMeal {} with id={}", userId, meal, mealId);
        assureIdConsistent(meal, mealId);
        service.save(userId, meal);
    }

    public Collection<MealTo> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();

        if (startDate == null) startDate = LocalDate.MIN;
        if (startTime == null) startTime = LocalTime.MIN;
        if (endDate == null) endDate = LocalDate.MAX;
        if (endTime == null) endTime = LocalTime.MAX;

        log.info("userId {} getBetweenMeals ", userId);
        return service.getBetween(startDate, endDate, startTime, endTime, userId);
    }


}
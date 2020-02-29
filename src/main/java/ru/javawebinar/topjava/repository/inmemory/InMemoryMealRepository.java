package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.stream().limit(3).forEach(meal -> save(1, meal));
        MealsUtil.MEALS.stream().limit(3).forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        log.info("userId {} saveMeal {}", userId, meal);
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, int id) {
        log.info("userId {} deleteMealId {}", userId, id);
        return this.get(userId, id) != null ? repository.remove(id) != null : null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        log.info("userId {} getMealId {}", userId, id);
        Meal meal = repository.get(id);
        return meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        log.info("userId {} getAllMealTo", userId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Integer userId) {
        log.info("userId {} getBetweenMeals ", userId);
        return this.getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), startDate, endDate))
                .filter(meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}


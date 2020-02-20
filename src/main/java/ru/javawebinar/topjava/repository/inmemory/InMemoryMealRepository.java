package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    {
        for (Meal meal : MealsUtil.MEALS) {
            this.save(meal.getUserId(), meal.getId(), meal);
        }
    }

    @Override
    public Meal save(int userId, Integer id, Meal meal) {
        log.info("userId {}, save {}", userId, meal);
        repository.putIfAbsent(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("userId {}, delete {}", userId, id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("userId {}, get {}", userId, id);
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("userId {}, getAll", userId);
        List<Meal> list = new ArrayList<>(repository.get(userId).values());
        list.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return list;
    }
}


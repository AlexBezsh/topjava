package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("userId {}, getAll", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public MealTo get(int id) {
        log.info("userId {}, get {}", SecurityUtil.authUserId(), id);
        List<MealTo> list = getAll();
        return checkNotFoundWithId(list.stream().filter(v -> v.getId() == id).findFirst().orElse(null), id);
    }

    public MealTo create(Meal meal) {
        log.info("userId {}, create {}", SecurityUtil.authUserId(), meal);
        checkNew(meal);
        service.create(SecurityUtil.authUserId(), null, meal);
        return get(meal.getId());
    }

    public void delete(int id) {
        log.info("userId {}, delete {}", SecurityUtil.authUserId(), id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("userId {}, update {} with id={}", SecurityUtil.authUserId(), meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal.getId(), meal);
    }

}
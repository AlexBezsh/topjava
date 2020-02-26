package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static { SLF4JBridgeHandler.install(); }

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL5.getId(), ADMIN_ID);
        assertMatch(meal, MEAL5);
        Meal meal1 = service.get(MEAL2.getId(), USER_ID);
        assertMatch(meal1, MEAL2);
        Meal meal2 = service.get(MEAL7.getId(), ADMIN_ID);
        assertMatch(meal2, MEAL7);
    }

    @Test(expected = NotFoundException.class)
    public void getMealWithWrongUserId() {
        service.get(MEAL5.getId(), USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL7.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAndGet() {
        service.delete(MEAL7.getId(), ADMIN_ID);
        service.get(MEAL7.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteMealWithWrongUserId() {
        service.delete(MEAL7.getId(), USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> meals1 = service.getBetweenHalfOpen(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(meals1, MEAL1, MEAL2, MEAL3);
        List<Meal> meals2 = service.getBetweenHalfOpen(LocalDate.of(2020, Month.JANUARY, 31), LocalDate.of(2020, Month.JANUARY, 31), ADMIN_ID);
        assertMatch(meals2, MEAL4, MEAL5, MEAL6, MEAL7);
    }

    @Test(expected = NotFoundException.class)
    public void getBetweenHalfOpenDateWithNoMeals() {
        List<Meal> meals = service.getBetweenHalfOpen(LocalDate.of(2020, Month.JANUARY, 29), LocalDate.of(2020, Month.JANUARY, 29), USER_ID);
        assertMatch(meals, MEAL1, MEAL2, MEAL3);
    }

    @Test
    public void getAllForUser() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL1, MEAL2, MEAL3);
    }

    @Test
    public void getAllForAdmin() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, MEAL4, MEAL5, MEAL6, MEAL7, MEAL8);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        Meal meal = service.get(MEAL1.getId(), USER_ID);
        assertMatch(meal, updated);
        service.update(MEAL1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() {
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
        assertMatch(service.get(newMeal.getId(), USER_ID), newMeal);
        service.delete(created.getId(), USER_ID);
    }
}
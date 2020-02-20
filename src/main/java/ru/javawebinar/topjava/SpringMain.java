package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            //TEST
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.get(1)); //take meal with id = 1
            System.out.println(mealRestController.getAll());
            //create new meal, which cause excess = true for a particular date. Created meal will be with id = 19
            System.out.println(mealRestController.create(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500)));
            System.out.println(mealRestController.getAll());
            mealRestController.delete(19);
            System.out.println(mealRestController.getAll());
            //mealRestController.delete(20); // NotFoundException
            //mealRestController.delete(4); // NotFoundException (meal with id = 4 belongs to user with id = 2
            mealRestController.update(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 600), 1);
            System.out.println(mealRestController.getAll());
            //mealRestController.update(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 600), 4); // NotFoundException
        }
    }
}

package uz.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HhWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhWebAppApplication.class, args);
    }

//    Bean classlar bu Spring dagi asosiy kerak boladigan classlar
//    bean class lani Spring IOC(Inversion of Control) konteynerga yigib turadi
//    Application Context bu IOC ni boshqaradigan texnalogiya
//    DI - Dependency injection - bu 1ta bean class ni boshqa bir bean class ichiga chaqirib ishlatish
//      turlari: 1) Constructor-based DI, 2) Setter based DI, 3) Field injection

//    Bean qiladigan annatasiyalar bor: @Component, Service, Repostory, ...
}

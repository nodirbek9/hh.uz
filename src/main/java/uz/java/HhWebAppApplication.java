package uz.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HhWebAppApplication {

//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final UserProfileRepository userProfileRepository;
//
//    public HhWebAppApplication(PasswordEncoder passwordEncoder, UserRepository userRepository, UserProfileRepository userProfileRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//        this.userProfileRepository = userProfileRepository;
//    }

    public static void main(String[] args) {
        SpringApplication.run(HhWebAppApplication.class, args);
    }

//    Bean classlar bu Spring dagi asosiy kerak boladigan classlar
//    bean class lani Spring IOC(Inversion of Control) konteynerga yigib turadi
//    Application Context bu IOC ni boshqaradigan texnalogiya
//    DI - Dependency injection - bu 1ta bean class ni boshqa bir bean class ichiga chaqirib ishlatish
//      turlari: 1) Constructor-based DI, 2) Setter based DI, 3) Field injection

//    Bean qiladigan annatasiyalar bor: @Component, Service, Repostory, ...

//    @PostConstruct
//    public void createUser(){
//        User user = new User();
//        user.setEmail("behruzizzatullayev@gmail.com");
//        user.setUsername("bekhruz");
//        user.setPassword(passwordEncoder.encode("1234"));
//        user.setRole(UserRole.ADMIN);
//        User save = userRepository.save(user);
//        UserProfile profile = new UserProfile();
//        profile.setFirstName("Behruz");
//        profile.setLastName("Izzatullayev");
//        profile.setUserId(save.getId());
//        userProfileRepository.save(profile);
//    }


//    Aplication run bolgandan keyin step-by-step ishlash jarayoni
//    1) Bean qilingan class lar scan qilib chiqiladi
//    2) configuration anotatsiya bilan belgilangan class lar scan qilib chiqiladi
//    3) @PostConstruct method bolsa ishlaydi bolmasa kngi stepga otib ketadi
//    4) Security ishlaydi
//    5) agar request Front dan kelsa avval filterga tushadi keyin controllerga boradi
}

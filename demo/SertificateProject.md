# Spring Boot simple E-Commerce web application

`Total projects provide Source Codes/Guides/App and several-party libraries, indeed down below represents. So, you can
get started to the project with as Spring Boot.`

## Introduction of project setup:

* User registration and access: Users can easily create an account and loginDto to the platform. Customers can change their
  password.
* Product List: Sellers can add products with basic information such as name, description and price. Customers can see
  the list of available products.
* Product list: Customers can onlineOrder the products they want to buy. Sellers are notified of new orders.
* Order Date: Users can track their onlineOrder history to see their previous orders.
* User notifications: Customers can add simple ratings and reviews to products. Sellers can see their product ratings.

## Those annotation which is implemented in the projects:

          @Component
          @Aspect
          @Repository
          @Service
          @RestController
          @Entity
          --------------------------------------------------
          @Configuration
          @EnableWebSecurity
          --------------------------------------------------
          @Data ---> at the class level is provided by Lombok and tells Lombok to generate all of those missing methods. 
          @FieldDefaults(level = AccessLevel.PRIVATE)
          --------------------------------------------------
          @ManyToMany
          @ManyToOne
          --------------------------------------------------
          @NotNull
          @NotBlank
          --------------------------------------------------
          @SpringBootApplication
          @Slf4j - Simple Logging Facade for Java. Lombok's @Slf4j annotation to create a free SLF4J Logger object at runtime.
          --------------------------------------------------
          @RequestMapping
          @GetMapping
          @PostMapping
          @PutMapping
          @DeleteMapping
          --------------------------------------------------
          @NoArgsConstructor (access=AccessLevel.PRIVATE, force=true)
          @RequiredArgsConstructor
          --------------------------------------------------
          @Table
          @Column
          --------------------------------------------------
          @Query("")
          --------------------------------------------------
          @Valid
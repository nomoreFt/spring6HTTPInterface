package com.example.spring6httpinterface;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class MyConfiguration {

    @Bean
    TodoClient todoClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder()
                .clientAdapter(WebClientAdapter.forClient(webClient))
                .build();

        return factory.createClient(TodoClient.class);
    }

    @Bean
    ApplicationRunner applicationRunner(TodoClient todoClient) {
        return args -> {
            System.out.println(todoClient.todos());
            Todo myTodo = new Todo(
                    null,
                    "Learn Spring",
                    false,
                    1L);
            Todo todo = todoClient.create(myTodo);
            System.out.println(todoClient.get(todo.id()));

        };
    }
}

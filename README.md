# spring6 HttpServiceProxyFactory

spring5의 webClinet를 이용해서 더 간결하게 API 설계 가능

1. Bean 등록


```java
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

```


<br>

# spring 6 Client Interface 

```java

@HttpExchange("/todos")
public interface TodoClient {

    @GetExchange
    List<Todo> todos();

    @PostExchange
    Todo create(@RequestBody Todo todo);

    @GetExchange("/{todoId}")
    ResponseEntity<Todo> get(@PathVariable("todoId") Long id);
}
```




# ApplicationRunner로 Test

> ApplicationRunner는 스프링 부트가 실행된 후에 실행되는 코드를 작성할 수 있게 해준다.

```java
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
```
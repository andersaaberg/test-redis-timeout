# test-redis-timeout

## How to reproduce that Micronaut-Redis waits forever to select database or authenticate
Listen on Redis port in another terminal:
`nc -l 6379 -C`

Run micronaut application:
`./gradlew run`

Call controller in another terminal:
`curl http://localhost/test`

Expected result:
Should throw exception after 5s, but waits forever. This problem does not exist in Lettuce, so it must be caused by Micronaut-Redis

## How this project is created
sdk u micronaut 2.0.1
micronaut create-app --lang=groovy test-redis-timeout
cd test-redis-timeout
mn create-controller Test

Add the following dependency to build.gradle:
`implementation("io.micronaut.redis:micronaut-redis-lettuce")`

Add the following configuration to application.yml:
```
redis:
  uri: redis://localhost/1 # or redis://password@localhost
  timeout: 5s
```

Modify TestController:
```
package test.redis.timeout

import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

import javax.inject.Inject

@Controller("/test")
class TestController {

    @Inject
    StatefulRedisConnection<String, String> statefulRedisConnection

    @Get(uri="/", produces="text/plain")
    String index() {
        RedisCommands redisCommands = statefulRedisConnection.sync()
        redisCommands.ping() // never executed if redis accepts TCP connection but does not respond
        "Example Response"
    }
}

```


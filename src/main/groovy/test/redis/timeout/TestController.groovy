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
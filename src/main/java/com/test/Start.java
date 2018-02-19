package com.test;

import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Start {

    public static void main(String[] args) throws InterruptedException {
        List<RedisAdvancedClusterCommands> collect = IntStream.range(0, 20)
                .mapToObj(i -> createRedisCommands())
                .collect(toList());
        Thread.sleep(10000000);
    }

    public static RedisAdvancedClusterCommands createRedisCommands() {
        RedisClusterClient redisClusterClient = RedisClusterClient.create("redis://127.0.0.1:7000/0");
        redisClusterClient.setOptions(ClusterClientOptions.builder()
                .topologyRefreshOptions(ClusterTopologyRefreshOptions.builder()
                        .enablePeriodicRefresh(Duration.ofSeconds(2))
                        .enableAllAdaptiveRefreshTriggers()
                        .build())
                .build());
        return redisClusterClient.connect().sync();
    }
}

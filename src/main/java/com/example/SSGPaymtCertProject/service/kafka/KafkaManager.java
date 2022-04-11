package com.example.SSGPaymtCertProject.service.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaManager {
    private final KafkaAdmin kafkaAdmin;
    private final AdminClient adminClient;

    public KafkaManager(KafkaAdmin kafkaAdmin, AdminClient adminClient) {
        this.kafkaAdmin = kafkaAdmin;
        this.adminClient = adminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    // 토픽 설정 출력
    public void describeTopicConfigs() throws ExecutionException, InterruptedException {
        // BROKER 정보가 필요하면 TYPE, NAME 을 브로커로 바꾸면 된다.
        Collection<ConfigResource> resources = List.of(
                new ConfigResource(ConfigResource.Type.TOPIC, "testListenerTopic-listener")
        );

        DescribeConfigsResult result = adminClient.describeConfigs(resources);
        System.out.println("result : " + result.all().get());
    }

    // 설정 변경
    public void changeConfig() throws ExecutionException, InterruptedException {
        ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, "testListenerTopic-listener");

        Map<ConfigResource, Collection<AlterConfigOp>> ops = new HashMap<>();
        // DELETE SET을 할지 정해준다.
        // ops.put(resource, List.of(new AlterConfigOp(new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, "6000"), AlterConfigOp.OpType.SET)));
        // DELEDTE 를 하게되면 default 가 들어가거나 null 이 들어간다.
        ops.put(resource, List.of(new AlterConfigOp(new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, null), AlterConfigOp.OpType.DELETE)));
        adminClient.incrementalAlterConfigs(ops);
        this.describeTopicConfigs();
    }

    // 레코드 삭제
    public void deleteRecords() throws ExecutionException, InterruptedException {
        TopicPartition tp = new TopicPartition("testListenerTopic-listener", 0);
        Map<TopicPartition, RecordsToDelete> target = new HashMap<>();
        // 1 번부터 지우게되면 0, 1 이 지워질 것이다.
        target.put(tp, RecordsToDelete.beforeOffset(1));

        DeleteRecordsResult deleteRecordsResult = adminClient.deleteRecords(target);
        Map<TopicPartition, KafkaFuture<DeletedRecords>> result = deleteRecordsResult.lowWatermarks();

        Set<Map.Entry<TopicPartition, KafkaFuture<DeletedRecords>>> entries = result.entrySet();
        for (Map.Entry<TopicPartition, KafkaFuture<DeletedRecords>> entry : entries) {
            System.out.println(
                    "Delete topic= " + entry.getKey().topic() +
                            ", partition= " + entry.getKey().partition() +
                            ", " + entry.getValue().get().lowWatermark());
        }
    }

    // 컨슈머 그룹 탐색
    public void findAllConsumerGroups() throws ExecutionException, InterruptedException {
        ListConsumerGroupsResult result = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> groups = result.valid().get();
        for(ConsumerGroupListing group : groups) {
            System.out.println("group info = " + group);
        }
    }

    // state=Optional[Empty]) 인 컨슈머만 지울 수 있다.
    // ordDtoTopic-listener 는 stable 이여서 에러가 발생
    public void deleteConsumerGroup() throws ExecutionException, InterruptedException {
        adminClient.deleteConsumerGroups(List.of("ordDtoTopic-listener", "testKafka-container")).all().get();
    }

    // offset 탐색
    public void findAllOffsets() throws ExecutionException, InterruptedException {
        Map<TopicPartition, OffsetSpec> target = new HashMap<>();
        target.put(new TopicPartition("testListenerTopic-listener", 0), OffsetSpec.latest());
        ListOffsetsResult result = adminClient.listOffsets(target);
        for(TopicPartition tp : target.keySet()) {
            System.out.println("topic=" + tp.topic() + ", partition=" + tp.partition() + ", offsets=" + result.partitionResult(tp).get());
        }
    }

}

package com.example.SSGPaymtCertProject.config.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * 카프카 스트림즈 설정 클래스
 * @EnableKafkaStreams : 기본 stream builder 를 사용하기 위함
 * 컨슈머의 autoCreate true 와 다르게 스트림즈는 메타정보만 가져와 만들어지므로
 * 토픽이 자동 생성되지 않아서 토픽을 미리 만들어야 한다.
 */
@Configuration
@EnableKafkaStreams
public class KafkaStreamsBaseConfiguration {

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("streamsTopic");
        // to : 어디로 전달할 것인지, topic 또는 produced 이용하여 파티션 제어
        // map : 실제 받은 메시지를 재발행 할때 map 처리(변경)
//        stream.peek((key, value) -> System.out.println("Stream. message=" + value))
//                .map((key, value) -> KeyValue.pair(key, "Hello, Listener!!"))
//                .to("streamsTopic-to");

        // groupBY 를 하면 KGroupedStream 이 만들어 진다.
        // value 를 기준으로 그룹화하여 KTable 생성
//        stream.groupBy((key, value) -> value)
//                .count()
//                .toStream()
//                .peek((key, value) -> System.out.println("key=" + key + ", value=" + value));

        // branch 를 하면 스트림 배열이 생성된다.
        // 첫번째 조건에 해당되지 않으면 두번째 조건으로 넘어간다.
        // 조건은 배열 인덱스를 이용해 확인할 수 있다.
        KStream<String, String>[] branches = stream.branch(
                (key, value) -> Long.valueOf(value) % 10 == 0,
                (key, value) -> true
        );

        branches[0].peek((key, value) -> System.out.println("Branch 0. mesage=" + value));
        branches[1].peek((key, value) -> System.out.println("Branch 1. mesage=" + value));

        return stream;
    }

    // 어플리케이션 ID -> 컨슈머의 그룹 ID, 클라이언트 ID 를 대체
    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "streamsTopic-id");
        configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        // 스트림즈 병렬처리를 위한 쓰레드 설정
        //configs.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 2);
        // AT LEAST ONCE, EXACTLY_ONCE, AT MOST ONCE
        configs.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        return new KafkaStreamsConfiguration(configs);
    }
}

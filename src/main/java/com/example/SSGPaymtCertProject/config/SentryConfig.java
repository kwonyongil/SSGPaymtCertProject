package com.example.SSGPaymtCertProject.config;

import io.sentry.spring.EnableSentry;
import org.springframework.context.annotation.Configuration;

//exceptionResolverOrder = Ordered.LOWEST_PRECEDENCE (가장 우선순위 낮은 에러는 Sentry 모니터링에서 제외)
@EnableSentry(dsn = "https://23aa1ce1f57946c5a16bc9d9e02c23da@o972006.ingest.sentry.io/5924418")
@Configuration
public class SentryConfig {
}

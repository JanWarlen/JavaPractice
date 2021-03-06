package com.janwarlen.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class LogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtil.class);

    public static void init() {
        try {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration configuration = context.getConfiguration();
            DefaultRolloverStrategy defaultRolloverStrategy = DefaultRolloverStrategy.createStrategy("3",
                    "1",
                    "max",
                    "6",
                    null,
                    true,
                    configuration);
            PatternLayout patternLayout = PatternLayout.newBuilder()
                    .withPattern("[%-5level] %d{HH:mm:ss.SSS} [%thread] %logger{36} - %msg%n")
                    .withCharset(StandardCharsets.UTF_8)
                    .build();
            RollingFileAppender customizeRollingFileAppender = RollingFileAppender.newBuilder()
                    .withFileName("1_test.log")
                    .withName("customizeRollingFileAppender")
                    .withPolicy(SizeBasedTriggeringPolicy.createPolicy("500M"))
                    .withStrategy(defaultRolloverStrategy)
                    .withFilePattern("1_test-%i.log.zip")
                    .withLayout(patternLayout)
                    .build();
            customizeRollingFileAppender.start();

            configuration.addAppender(customizeRollingFileAppender);

            // ?????????logger??????????????????????????????????????????????????????
            /*AppenderRef customizeRollingFileAppender1 = AppenderRef.createAppenderRef("customizeRollingFileAppender", Level.INFO, null);
            AppenderRef[] refs = new AppenderRef[] {customizeRollingFileAppender1};
            LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, "com.janwarlen",
                    "true", refs, null, configuration, null);
            loggerConfig.addAppender(customizeRollingFileAppender, Level.INFO, null);
            configuration.addLogger("com.janwarlen", loggerConfig);*/

            // ????????????????????????????????????
            configuration.getRootLogger().addAppender(customizeRollingFileAppender, Level.INFO, null);
        context.updateLoggers(configuration);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}

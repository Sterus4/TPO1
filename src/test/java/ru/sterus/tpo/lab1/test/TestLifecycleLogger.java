package ru.sterus.tpo.lab1.test;

import org.junit.jupiter.api.*;

import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TestLifecycleLogger {
    Logger logger = Logger.getLogger(TestLifecycleLogger.class.getName());
    @BeforeAll
    default void beforeAll(TestInfo info){
        logger.info("TestLifecycleLogger started on: " + info.getDisplayName());
    }
    @AfterAll
    default void afterAll(TestInfo info){
        logger.info("TestLifecycleLogger stopped on: " + info.getDisplayName());
    }
    @BeforeEach
    default void beforeEach(TestInfo info){
        logger.info(() -> String.format(
                "Test [%s] starting...", info.getTestMethod().get().getName()
        ));
    }
    @AfterEach
    default void afterEach(TestInfo info){
        logger.info(() -> String.format(
                "Test [%s] finished", info.getTestMethod().get().getName()
        ));
    }
}

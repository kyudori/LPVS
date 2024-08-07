/**
 * Copyright (c) 2022, Samsung Electronics Co., Ltd. All rights reserved.
 *
 * Use of this source code is governed by a MIT license that can be
 * found in the LICENSE file.
 */
package com.lpvs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.lpvs.util.LPVSExitHandler;

/**
 * The main class for the License Pre-Validation Service (LPVS) application.
 * This class configures and launches the LPVS Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = {"com.lpvs"})
@EnableAutoConfiguration
@EnableAsync
@Slf4j
public class LicensePreValidationService {

    /**
     * The core pool size for the asynchronous task executor.
     */
    private final int corePoolSize;

    /**
     * The exit handler for handling application exits.
     */
    private static LPVSExitHandler exitHandler;

    /**
     * Constructs a new LicensePreValidationService with the specified core pool size.
     *
     * @param corePoolSize The core pool size for the asynchronous task executor.
     */
    public LicensePreValidationService(@Value("${lpvs.cores:8}") int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    /**
     * The main entry point of the LPVS application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        try {
            log.info(getEmblem());
            ApplicationContext applicationContext =
                    SpringApplication.run(LicensePreValidationService.class, args);
            exitHandler = applicationContext.getBean(LPVSExitHandler.class);
        } catch (IllegalArgumentException e) {
            log.error("An IllegalArgumentException occurred: " + e.getMessage());
            try {
                exitHandler.exit(-1);
            } catch (Exception exitException) {
                log.error(
                        "An exception occurred during exit handling: "
                                + exitException.getMessage());
                System.exit(-1);
            }
        } catch (Exception e) {
            log.info("LPVS application is being closed.");
        }
    }

    /**
     * Configures and retrieves an asynchronous task executor bean.
     *
     * @return An asynchronous task executor bean.
     */
    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setThreadNamePrefix("LPVS::");
        return executor;
    }

    /**
     * Returns the emblem for the License Pre-Validation Service.
     *
     * @return the emblem as a String
     */
    protected static String getEmblem() {
        StringBuilder emblem = new StringBuilder();
        emblem.append("\n");
        emblem.append(
                "   .----------------.   .----------------.   .----------------.   .----------------. \n");
        emblem.append(
                "  | .--------------. | | .--------------. | | .--------------. | | .--------------. |\n");
        emblem.append(
                "  | |   _____      | | | |   ______     | | | | ____   ____  | | | |    _______   | |\n");
        emblem.append(
                "  | |  |_   _|     | | | |  |_   __ \\   | | | ||_  _| |_  _| | | | |   /  ___  |  | |\n");
        emblem.append(
                "  | |    | |       | | | |    | |__) |  | | | |  \\ \\   / /   | | | |  |  (__ \\_|  | |\n");
        emblem.append(
                "  | |    | |   _   | | | |    |  ___/   | | | |   \\ \\ / /    | | | |   '.___`-.   | |\n");
        emblem.append(
                "  | |   _| |__/ |  | | | |   _| |_      | | | |    \\ ' /     | | | |  |`\\____) |  | |\n");
        emblem.append(
                "  | |  |________|  | | | |  |_____|     | | | |     \\_/      | | | |  |_______.'  | |\n");
        emblem.append(
                "  | |              | | | |              | | | |              | | | |              | |\n");
        emblem.append(
                "  | '--------------' | | '--------------' | | '--------------' | | '--------------' |\n");
        emblem.append(
                "   '----------------'   '----------------'   '----------------'   '----------------' \n");
        emblem.append(
                "  :: License Pre-Validation Service ::                                      (v1.5.2)\n");
        return emblem.toString();
    }
}

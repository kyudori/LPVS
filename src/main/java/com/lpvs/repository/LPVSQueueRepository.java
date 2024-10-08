/**
 * Copyright (c) 2023-2024, Samsung Electronics Co., Ltd. All rights reserved.
 *
 * Use of this source code is governed by a MIT license that can be
 * found in the LICENSE file.
 */
package com.lpvs.repository;

import com.lpvs.entity.LPVSQueue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link LPVSQueue} entities.
 * Extends {@link org.springframework.data.jpa.repository.JpaRepository} for basic CRUD operations.
 */
public interface LPVSQueueRepository extends JpaRepository<LPVSQueue, Long> {}

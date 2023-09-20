package com.lpvs.entity.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HistoryEntity {
    private List<LPVSHistory> lpvsHistories;
    private Long count;
}

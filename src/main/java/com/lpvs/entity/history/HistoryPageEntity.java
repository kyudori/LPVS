package com.lpvs.entity.history;

import com.lpvs.entity.LPVSPullRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter @AllArgsConstructor
public class HistoryPageEntity {
    private Page<LPVSPullRequest> prPage;
    private Long count;
}

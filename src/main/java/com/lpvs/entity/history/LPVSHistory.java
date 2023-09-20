package com.lpvs.entity.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class LPVSHistory {
    private String scanDate; //pr
    private String repositoryName; //pr
    private Long pullRequestId; //pr
    private String url; // pr
    private String status; //pr
    private String sender;

    private String pullNumber; // pull/number
    private Boolean hasIssue;
}

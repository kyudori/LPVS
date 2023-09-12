package com.lpvs.controller;

import com.lpvs.entity.LPVSLoginMember;
import com.lpvs.entity.LPVSMember;
import com.lpvs.repository.LPVSDetectedLicenseRepository;
import com.lpvs.repository.LPVSLicenseRepository;
import com.lpvs.repository.LPVSMemberRepository;
import com.lpvs.repository.LPVSPullRequestRepository;
import com.lpvs.service.LPVSLoginCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LPVSWebController {
    private LPVSMemberRepository memberRepository;
    private LPVSDetectedLicenseRepository detectedLicenseRepository;
    private LPVSPullRequestRepository lpvsPullRequestRepository;
    private LPVSLicenseRepository licenseRepository;
    private LPVSLoginCheckService LPVSLoginCheckService;

    public LPVSWebController(LPVSMemberRepository memberRepository, LPVSDetectedLicenseRepository detectedLicenseRepository,
                             LPVSPullRequestRepository lpvsPullRequestRepository, LPVSLicenseRepository licenseRepository,
                             LPVSLoginCheckService LPVSLoginCheckService) {
        this.memberRepository = memberRepository;
        this.detectedLicenseRepository = detectedLicenseRepository;
        this.lpvsPullRequestRepository = lpvsPullRequestRepository;
        this.licenseRepository = licenseRepository;
        this.LPVSLoginCheckService = LPVSLoginCheckService;
    }

    @GetMapping("user/info")
    @ResponseBody
    public LPVSMember personalInfoSettings(Authentication authentication) {
        LPVSLoginCheckService.loginVerification(authentication);
        return LPVSLoginCheckService.getMemberFromMemberMap(authentication);
    }

    @GetMapping("login/check")
    @ResponseBody
    public LPVSLoginMember loginMember(Authentication authentication) {
        Boolean isLoggedIn = LPVSLoginCheckService.oauthLoginStatus(authentication);
        if (isLoggedIn) {
            LPVSMember findMember = LPVSLoginCheckService.getMemberFromMemberMap(authentication);
            return new LPVSLoginMember(isLoggedIn, findMember);
        } else {
            return new LPVSLoginMember(isLoggedIn, null);
        }
    }

}

package com.caostudy.ecommerce.controller;

import com.caostudy.ecommerce.service.conmmunication.AuthorityFeignClient;
import com.caostudy.ecommerce.service.conmmunication.UseFeignApi;
import com.caostudy.ecommerce.service.conmmunication.UseRestTemplateService;
import com.caostudy.ecommerce.service.conmmunication.UseRibbonService;
import com.caostudy.ecommerce.vo.JwtToken;
import com.caostudy.ecommerce.vo.UsernameAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cao Study
 * @description <h1>CommunicationController</h1>
 * @date 2021/9/22 21:57
 */
@RestController
@RequestMapping("/communication")
public class CommunicationController {
    @Autowired
    private UseRestTemplateService restTemplateService;

    @Autowired
    private UseRibbonService ribbonService;

    @Autowired
    private AuthorityFeignClient feignClient;

    @Autowired
    private UseFeignApi useFeignApi;

    @PostMapping("/rest-template")
    public JwtToken getTokenFromAuthorityService(
            @RequestBody UsernameAndPassword usernameAndPassword) {
        return restTemplateService.getTokenFromAuthorityService(usernameAndPassword);
    }

    @PostMapping("/rest-template-load-balancer")
    public JwtToken getTokenFromAuthorityServiceWithLoadBalancer(
            @RequestBody UsernameAndPassword usernameAndPassword) {
        return restTemplateService.getTokenFromAuthorityServiceWithLoadBalancer(
                usernameAndPassword);
    }

    @PostMapping("/ribbon")
    public JwtToken getTokenFromAuthorityServiceByRibbon(
            @RequestBody UsernameAndPassword usernameAndPassword) {
        return ribbonService.getTokenFromAuthorityServiceByRibbon(usernameAndPassword);
    }

    @PostMapping("/thinking-in-ribbon")
    public JwtToken thinkingInRibbon(@RequestBody UsernameAndPassword usernameAndPassword) {
        return ribbonService.thinkingInRibbon(usernameAndPassword);
    }

    @PostMapping("/token-by-feign")
    public JwtToken getTokenByFeign(@RequestBody UsernameAndPassword usernameAndPassword) {
        return feignClient.getTokenByFeign(usernameAndPassword);
    }

    @PostMapping("/thinking-in-feign")
    public JwtToken thinkingInFeign(@RequestBody UsernameAndPassword usernameAndPassword) {
        return useFeignApi.thinkingInFeign(usernameAndPassword);
    }
}

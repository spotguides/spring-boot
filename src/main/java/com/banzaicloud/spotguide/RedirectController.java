package com.banzaicloud.spotguide;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.RedirectView;

@Controller
public class RedirectController {

    @RequestMapping("/")
    public RedirectView redirectRootToKubernetesHealth() {
        return new RedirectView("/actuator/health/kubernetes");
    }
}

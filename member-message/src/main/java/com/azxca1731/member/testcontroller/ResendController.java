package com.azxca1731.member.testcontroller;

import com.azxca1731.member.domain.sign.kafka.SignOutProducer;
import com.azxca1731.member.domain.sign.kafka.SignOutTopicMessage;
import com.azxca1731.member.domain.sign.kafka.SignUpProducer;
import com.azxca1731.member.domain.sign.kafka.SignUpTopicMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResendController {
    private final SignUpProducer signUpProducer;
    private final SignOutProducer signOutProducer;


    @PostMapping(TestUrls.SIGN_UP)
    public void resendSignUp(@RequestBody SignUpTopicMessage message) {
        signUpProducer.send(message);
    }

    @PostMapping(TestUrls.SIGN_OUT)
    public void resendSignUp(@RequestBody SignOutTopicMessage message) {
        signOutProducer.send(message);
    }


}

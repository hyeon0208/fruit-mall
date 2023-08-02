package com.fruit.mall.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Slf4j
public class FireBaseInit {
    @Value("${firebase.firebaseApiKey}")
    private String firebaseApiKey;

    @PostConstruct
    public void init() {
        try {
            // ApiKey를 읽어 인증정보 불러오기
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(
                    GoogleCredentials.fromStream(
                            new ClassPathResource(firebaseApiKey).getInputStream())).build();

            // FireBaseApp이 초기화 되었는지 확인 (중복 초기화 방지)
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

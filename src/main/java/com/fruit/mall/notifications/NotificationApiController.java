package com.fruit.mall.notifications;

import com.fruit.mall.annotaion.Login;
import com.fruit.mall.config.SessionUser;
import com.fruit.mall.emitter.EmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j(topic = "elk")
@RequiredArgsConstructor
public class NotificationApiController {

    private final EmitterService emitterService;
    private final NotificationsService notificationsService;
    public static final Long DEFAULT_TIMEOUT = 1800L * 1000;

    @GetMapping(value = "/api/sse-connection", produces = "text/event-stream")
    public SseEmitter stream(@Login SessionUser sessionUser, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws IOException {
        return emitterService.addEmitter(String.valueOf(sessionUser.getUserIdNo()), lastEventId);
    }

    @PostMapping("/api/v1/notifications/read")
    public ResponseEntity<?> readNotifications(@RequestBody Map<String, Long> param) {
        Long notificationsId = param.get("notificationsId");
        notificationsService.updateRead(notificationsId);
        Long productId = notificationsService.selectProductIdByNotificationsId(notificationsId);
        return ResponseEntity.ok(productId);
    }
}
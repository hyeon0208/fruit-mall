package com.fruit.mall.emitter;

import com.fruit.common.NotificationMessage;
import com.fruit.mall.notifications.Notifications;
import com.fruit.mall.notifications.NotificationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

import static com.fruit.mall.notifications.NotificationApiController.DEFAULT_TIMEOUT;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmitterService {
    private final NotificationsRepository notificationsRepository;
    private final EmitterRepository emitterRepository;

    private static final int MAX_NOTIFICATIONS_COUNT = 6;

    @KafkaListener(topics = "comment-notifications", groupId = "group_1")
    public void listen(NotificationMessage message) {
        String userId = message.getUserId();
        Notifications notifications = Notifications.builder()
                .userIdNo(Long.valueOf(userId))
                .message(message.getMessage())
                .type("답글알림").build();

        notificationsRepository.insertNotifications(notifications);
        int curCnt = notificationsRepository.countNotificationsByUserId(Long.valueOf(userId));
        if (curCnt > MAX_NOTIFICATIONS_COUNT) {
            int delCount = curCnt - MAX_NOTIFICATIONS_COUNT;
            notificationsRepository.deleteOldestNotificationsByUserId(Long.valueOf(userId), delCount);
        }

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithById(userId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notifications);
                    sendToClient(emitter, key, notifications);
                }
        );
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
            log.info("Kafka로 부터 전달 받은 메세지 전송. emitterId : {}, message : {}", emitterId, data);
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            log.error("메시지 전송 에러 : {}", e);
        }
    }

    public SseEmitter addEmitter(String userId, String lastEventId) {
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        log.info("emitterId : {} 사용자 emitter 연결 ", emitterId);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitterRepository.deleteById(emitterId);
        });

        sendToClient(emitter, emitterId, "connected!"); // 503 에러방지 더미 데이터

        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithById(userId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
        return emitter;
    }

    @Scheduled(fixedRate = 180000) // 3분마다 heartbeat 메세지 전달.
    public void sendHeartbeat() {
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitters();
        sseEmitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event().id(key).name("heartbeat").data(""));
                log.info("하트비트 메세지 전송");
            } catch (IOException e) {
                emitterRepository.deleteById(key);
                log.error("하트비트 전송 실패: {}", e.getMessage());
            }
        });
    }
}
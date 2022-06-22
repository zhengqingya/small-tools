package com.zhengqing.monitor.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <p> 发送通知配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/6/22 15:39
 */
@Slf4j
@Component
public class SmallToolsStatusChangeNotifier extends AbstractStatusChangeNotifier {

    public SmallToolsStatusChangeNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        log.debug("[monitor] {}", instance);
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status) {
                    // 健康检查没通过
                    case "DOWN":
                        log.info("发送 健康检查没通过 的通知！");
                        break;
                    // 服务离线
                    case "OFFLINE":
                        log.info("发送 服务离线 的通知！");
                        break;
                    // 服务上线
                    case "UP":
                        log.info("发送 服务上线 的通知！");
                        break;
                    // 服务未知异常
                    case "UNKNOWN":
                        log.info("发送 服务未知异常 的通知！");
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
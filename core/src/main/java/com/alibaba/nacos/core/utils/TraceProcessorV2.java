/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.core.utils;

import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.common.notify.EventPublisher;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.notify.listener.SmartSubscriber;
import com.alibaba.nacos.common.trace.event.naming.DeregisterInstanceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.DeregisterServiceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.HealthStateChangeTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.PushServiceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.RegisterInstanceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.RegisterServiceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.SubscribeServiceTraceEvent;
import com.alibaba.nacos.common.trace.event.naming.UnsubscribeServiceTraceEvent;
import com.alibaba.nacos.common.trace.publisher.TraceEventPublisherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Trace processor for trace event.
 *
 * @author yanda
 */
@Component
public class TraceProcessorV2 extends SmartSubscriber {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(com.alibaba.nacos.core.utils.TraceProcessorV2.class);
    
    public TraceProcessorV2() {
        NotifyCenter.registerSubscriber(this, TraceEventPublisherFactory.getInstance());
        EventPublisher publisher = NotifyCenter.getPublisher(RegisterInstanceTraceEvent.class);
    }
    
    @Override
    public List<Class<? extends Event>> subscribeTypes() {
        List<Class<? extends Event>> result = new LinkedList<>();
        result.add(RegisterInstanceTraceEvent.class);
        result.add(DeregisterInstanceTraceEvent.class);
        result.add(RegisterServiceTraceEvent.class);
        result.add(DeregisterServiceTraceEvent.class);
        result.add(SubscribeServiceTraceEvent.class);
        result.add(UnsubscribeServiceTraceEvent.class);
        result.add(PushServiceTraceEvent.class);
        result.add(HealthStateChangeTraceEvent.class);
        return result;
    }
    
    @Override
    public void onEvent(Event event) {
        if (event instanceof RegisterInstanceTraceEvent) {
            RegisterInstanceTraceEvent traceRegisterInstanceEvent = (RegisterInstanceTraceEvent) event;
            LOGGER.info("[naming-trace]|[RegisterInstanceTraceEvent]|{}|{}|{}|{}|{}|{}|{}", traceRegisterInstanceEvent.getEventTime(),
                    traceRegisterInstanceEvent.getNamespace(), traceRegisterInstanceEvent.getGroup(),
                    traceRegisterInstanceEvent.getName(), traceRegisterInstanceEvent.isRpc(),
                    traceRegisterInstanceEvent.getClientIp(), traceRegisterInstanceEvent.toInetAddr());
        } else if (event instanceof DeregisterInstanceTraceEvent) {
            DeregisterInstanceTraceEvent traceDeregisterInstanceEvent = (DeregisterInstanceTraceEvent) event;
            LOGGER.info("[naming-trace]|[DeregisterInstanceTraceEvent]|{}|{}|{}|{}|{}|{}|{}|{}",
                    traceDeregisterInstanceEvent.getEventTime(), traceDeregisterInstanceEvent.getNamespace(),
                    traceDeregisterInstanceEvent.getGroup(), traceDeregisterInstanceEvent.getName(),
                    traceDeregisterInstanceEvent.isRpc(), traceDeregisterInstanceEvent.getReason(),
                    traceDeregisterInstanceEvent.getClientIp(), traceDeregisterInstanceEvent.toInetAddr());
        } else if (event instanceof RegisterServiceTraceEvent) {
            RegisterServiceTraceEvent registerServiceTraceEvent = (RegisterServiceTraceEvent) event;
            LOGGER.info("[naming-trace]|[RegisterServiceTraceEvent]|{}|{}|{}|{}",
                    registerServiceTraceEvent.getEventTime(), registerServiceTraceEvent.getNamespace(),
                    registerServiceTraceEvent.getGroup(), registerServiceTraceEvent.getName());
        } else if (event instanceof DeregisterServiceTraceEvent) {
            DeregisterServiceTraceEvent deregisterServiceTraceEvent = (DeregisterServiceTraceEvent) event;
            LOGGER.info("[naming-trace]|[DeregisterServiceTraceEvent]|{}|{}|{}|{}",
                    deregisterServiceTraceEvent.getEventTime(), deregisterServiceTraceEvent.getNamespace(),
                    deregisterServiceTraceEvent.getGroup(), deregisterServiceTraceEvent.getName());
        } else if (event instanceof SubscribeServiceTraceEvent) {
            SubscribeServiceTraceEvent subscribeServiceTraceEvent = (SubscribeServiceTraceEvent) event;
            LOGGER.info("[naming-trace]|[SubscribeServiceTraceEvent]|{}|{}|{}|{}|{}",
                    subscribeServiceTraceEvent.getEventTime(), subscribeServiceTraceEvent.getNamespace(),
                    subscribeServiceTraceEvent.getGroup(), subscribeServiceTraceEvent.getName(),
                    subscribeServiceTraceEvent.getClientIp());
        } else if (event instanceof UnsubscribeServiceTraceEvent) {
            UnsubscribeServiceTraceEvent unsubscribeServiceTraceEvent = (UnsubscribeServiceTraceEvent) event;
            LOGGER.info("[naming-trace]|[UnsubscribeServiceTraceEvent]|{}|{}|{}|{}|{}",
                    unsubscribeServiceTraceEvent.getEventTime(), unsubscribeServiceTraceEvent.getNamespace(),
                    unsubscribeServiceTraceEvent.getGroup(), unsubscribeServiceTraceEvent.getName(),
                    unsubscribeServiceTraceEvent.getClientIp());
        } else if (event instanceof PushServiceTraceEvent) {
            PushServiceTraceEvent pushServiceTraceEvent = (PushServiceTraceEvent) event;
            LOGGER.info("[naming-trace]|[PushServiceTraceEvent]|{}|{}|{}|{}|{}|{}|{}|{}|{}",
                    pushServiceTraceEvent.getEventTime(), pushServiceTraceEvent.getNamespace(),
                    pushServiceTraceEvent.getGroup(), pushServiceTraceEvent.getName(),
                    pushServiceTraceEvent.getClientIp(), pushServiceTraceEvent.getInstanceSize(),
                    pushServiceTraceEvent.getPushCostTimeForAll(), pushServiceTraceEvent.getPushCostTimeForNetWork(),
                    pushServiceTraceEvent.getServiceLevelAgreementTime());
        } else if (event instanceof HealthStateChangeTraceEvent) {
            HealthStateChangeTraceEvent healthStateChangeTraceEvent = (HealthStateChangeTraceEvent) event;
            LOGGER.info("[naming-trace]|[HealthStateChangeTraceEvent]|{}|{}|{}|{}|{}|{}|{}|{}",
                    healthStateChangeTraceEvent.getEventTime(), healthStateChangeTraceEvent.getNamespace(),
                    healthStateChangeTraceEvent.getGroup(), healthStateChangeTraceEvent.getName(),
                    healthStateChangeTraceEvent.toInetAddr(), healthStateChangeTraceEvent.isHealthy(),
                    healthStateChangeTraceEvent.getHealthCheckType(), healthStateChangeTraceEvent.getHealthStateChangeReason());
        }
    }
}
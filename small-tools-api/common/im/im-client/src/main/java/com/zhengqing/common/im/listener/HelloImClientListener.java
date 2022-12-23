package com.zhengqing.common.im.listener;

import lombok.extern.slf4j.Slf4j;
import org.jim.client.listener.ImClientListener;
import org.jim.core.ImChannelContext;
import org.jim.core.ImPacket;

/**
 * <p> 客户端连接监听器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/23 15:47
 */
@Slf4j
public class HelloImClientListener implements ImClientListener {
    @Override
    public void onAfterConnected(ImChannelContext imChannelContext, boolean isConnected, boolean isReconnect) throws Exception {

    }

    @Override
    public void onAfterDecoded(ImChannelContext imChannelContext, ImPacket packet, int packetSize) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ImChannelContext imChannelContext, int receivedBytes) throws Exception {

    }

    @Override
    public void onAfterSent(ImChannelContext imChannelContext, ImPacket packet, boolean isSentSuccess) throws Exception {

    }

    @Override
    public void onAfterHandled(ImChannelContext imChannelContext, ImPacket packet, long cost) throws Exception {

    }

    @Override
    public void onBeforeClose(ImChannelContext imChannelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {

    }

}

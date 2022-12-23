package com.zhengqing.common.im.util;

import com.zhengqing.common.im.handler.HelloImClientHandler;
import com.zhengqing.common.im.listener.HelloImClientListener;
import com.zhengqing.common.im.model.ImLoginDTO;
import com.zhengqing.common.im.model.ImMsgDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jim.client.ImClientChannelContext;
import org.jim.client.JimClient;
import org.jim.client.JimClientAPI;
import org.jim.client.config.ImClientConfig;
import org.jim.core.ImConst;
import org.jim.core.packets.ChatBody;
import org.jim.core.packets.Command;
import org.jim.core.packets.LoginReqBody;
import org.jim.core.tcp.TcpPacket;
import org.springframework.stereotype.Component;
import org.tio.core.Node;

/**
 * <p> IM客户端发送消息工具类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/23 11:33
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ImUtil {

    private ImClientChannelContext imClientChannelContext;

    @SneakyThrows
    public void connect() {
        //服务器节点
        Node serverNode = new Node("localhost", ImConst.SERVER_PORT);
        //构建客户端配置信息
        ImClientConfig imClientConfig = ImClientConfig.newBuilder()
                //客户端业务回调器,不可以为NULL
                .clientHandler(new HelloImClientHandler())
                //客户端事件监听器，可以为null，但建议自己实现该接口
                .clientListener(new HelloImClientListener())
                //心跳时长不设置，就不发送心跳包
                //.heartbeatTimeout(5000)
                //断链后自动连接的，不想自动连接请设为null
                //.reConnConf(new ReconnConf(5000L))
                .build();
        //生成客户端对象;
        JimClient jimClient = new JimClient(imClientConfig);
        //连接服务端
        this.imClientChannelContext = jimClient.connect(serverNode);
    }

    /**
     * 登录
     *
     * @param params 请求参数
     * @return void
     * @author zhengqingya
     * @date 2022/12/23 15:04
     */
    public void login(ImLoginDTO params) {
        byte[] loginBody = new LoginReqBody(params.getUserId(), params.getPassword()).toByte();
        TcpPacket loginPacket = new TcpPacket(Command.COMMAND_LOGIN_REQ, loginBody);
        JimClientAPI.send(this.imClientChannelContext, loginPacket);
    }


    /**
     * 发送消息
     *
     * @param params 消息体
     * @return void
     * @author zhengqingya
     * @date 2022/12/23 15:04
     */
    public void sengMsg(ImMsgDTO params) {
        TcpPacket chatPacket = new TcpPacket(Command.COMMAND_CHAT_REQ, ChatBody.newBuilder()
                .from(params.getFrom())
                .to(params.getTo())
                .msgType(params.getMsgType())
                .chatType(params.getChatType())
                .groupId(params.getGroupId())
                .content(params.getContent())
                .build().toByte()
        );
        JimClientAPI.send(this.imClientChannelContext, chatPacket);
    }

}

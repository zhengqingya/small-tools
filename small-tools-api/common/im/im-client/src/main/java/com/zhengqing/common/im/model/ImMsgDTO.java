package com.zhengqing.common.im.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> IM 消息体 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2022/12/23 15:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImMsgDTO {
    /**
     * 发送用户id;
     */
    private String from;
    /**
     * 目标用户id;
     */
    private String to;
    /**
     * 消息类型;(如：0:text、1:image、2:voice、3:vedio、4:music、5:news)
     * {@link org.jim.core.packets.MsgType}
     */
    private Integer msgType;
    /**
     * 聊天类型;(如1:公聊、2:私聊)
     * {@link org.jim.core.packets.ChatType}
     */
    private Integer chatType;
    /**
     * 消息内容;
     */
    private String content;
    /**
     * 消息发到哪个群组;
     */
    private String groupId;
}

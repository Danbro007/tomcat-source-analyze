package com.danbro.websocket;

import com.alibaba.fastjson.JSON;
import com.danbro.util.MessageUtil;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * @Classname ChatSocket
 * @Description TODO
 * @Date 2020/11/25 20:49
 * @Author Danrbo
 */
@ServerEndpoint(value = "/websocket", configurator = HttpSessionConfigurator.class)
public class ChatSocket {
    private Session session;
    private final OnlineUserMap onlineUserMap = OnlineUserMap.getInstance();
    private Integer onlineUserCount = 0;
    private final static String USERNAME = "username";
    private final static String ALL = "all";

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        // websocket 会话信息
        this.session = session;
        // 当前登录用户的消息
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession.getAttribute(USERNAME) != null) {
            String username = (String) httpSession.getAttribute(USERNAME);
            System.out.println("当前登录用户：" + username + "--->EndPoint:" + hashCode());
            // 记录为登录用户
            onlineUserMap.put(httpSession, this);
            // 登录用户自增 + 1
            incrCount();
            // 所有在线用户的用户名
            String onlineUsersName = getOnlineUsersName();
            // 显示所有登录用户的列表
            String message = MessageUtil.getContent(MessageUtil.TYPE_USER, username, "", onlineUsersName);
            // 推送给所有登录用户
            broadcastMessage(message);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Map<String, String> map = JSON.parseObject(message, Map.class);
        String fromName = map.get("fromName");
        String toName = map.get("toName");
        String content = map.get("content");
        if (toName == null || toName.isEmpty()) {
            return;
        }
        // 判断消息类型
        // 群发消息
        if (ALL.equals(toName)) {
            String broadCastMessage = MessageUtil.getContent(MessageUtil.TYPE_MESSAGE, fromName, toName, content);
            System.out.println("群发消息：" + content);
            broadcastMessage(broadCastMessage);
        }
        // 发送消息给一个人
        else {
            String singleMessage = MessageUtil.getContent(MessageUtil.TYPE_MESSAGE, fromName, toName, content);
            System.out.printf("发送给【%s】的消息:%s%n", toName, content);
            sendMessageToOnUser(singleMessage, toName, fromName);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        decrCount();
        System.out.println("登录用户减1");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        decrCount();
        System.out.println("服务异常！");
    }


    /**
     * 发送一个消息给一个用户
     *
     * @param singleMessage 消息
     * @param toName        用户名
     * @return 发送结果
     */
    private void sendMessageToOnUser(String singleMessage, String toName, String fromName) {
        // 先判断接收消息的用户是否在线
        if (isUserOnline(toName) && !onlineUserMap.isEmpty()) {
            // 发送消息
            // 消息要显示在发送方和接收方两个用户的界面上，所以一条消息要发送两次：发送方和接收方。
            for (HttpSession e : onlineUserMap.keySet()) {
                if (e.getAttribute(USERNAME).equals(fromName) || e.getAttribute(USERNAME).equals(toName)) {
                    try {
                        onlineUserMap.get(e).session.getBasicRemote().sendText(singleMessage);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 判断用户是否在线
     *
     * @param userName
     * @return
     */
    private boolean isUserOnline(String userName) {
        boolean flag = false;
        if (userName == null || userName.isEmpty()) {
            return flag;
        }
        for (HttpSession httpSession : onlineUserMap.keySet()) {
            if (httpSession.getAttribute(USERNAME).equals(userName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void incrCount() {
        onlineUserCount++;
    }

    private void decrCount() {
        onlineUserCount--;
    }

    public Integer getOnlineUserCount() {
        return onlineUserCount;
    }

    /**
     * 获取当前所有登录用户的用户名
     *
     * @return 登录用户的用户名
     */
    private String getOnlineUsersName() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!onlineUserMap.isEmpty()) {
            onlineUserMap.keySet().forEach(e -> {
                stringBuffer.append(e.getAttribute("username"));
                stringBuffer.append(",");
            });
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * 向所有登录用户广播消息
     */
    private void broadcastMessage(String message) {
        onlineUserMap.keySet().forEach(e -> {
            try {
                onlineUserMap.get(e).session.getBasicRemote().sendText(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}

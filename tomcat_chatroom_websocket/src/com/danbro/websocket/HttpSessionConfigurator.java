package com.danbro.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @Classname HttpSessionConfigurator
 * @Description TODO
 * @Date 2020/11/25 20:52
 * @Author Danrbo
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    /**
     * 自定义握手过程
     *
     * @param sec
     * @param request
     * @param response
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}

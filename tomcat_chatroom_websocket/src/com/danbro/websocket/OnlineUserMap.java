package com.danbro.websocket;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Classname UserMap
 * @Description TODO
 * @Date 2020/11/25 20:54
 * @Author Danrbo
 */
public class OnlineUserMap extends HashMap<HttpSession, ChatSocket> {
    private static volatile OnlineUserMap instance;

    private OnlineUserMap() {
    }

    public static OnlineUserMap getInstance() {
        if (instance == null) {
            synchronized (OnlineUserMap.class) {
                if (instance == null) {
                    instance = new OnlineUserMap();
                }
            }
        }
        return instance;
    }
}

package org.example.handler;

import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import org.example.config.HttpSessionConfigurator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket", configurator = HttpSessionConfigurator.class)
public class WebSocketHandler {

    /**
     * 用来存放每个客户端对应的WebSocket对象
     */
    private static CopyOnWriteArraySet<WebSocketHandler> webSocketSet = new CopyOnWriteArraySet<WebSocketHandler>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;

        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            log.info("HttpSessionId: {}", httpSession.getId());
        }

        webSocketSet.add(this); // 加入set中
        log.info("有新连接加入！当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为：" + webSocketSet.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        log.info("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);

        // 群发消息
        broadcast(message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    private void broadcast(String message) {
        for (WebSocketHandler item : webSocketSet) {
            // 同步异步说明参考：
            // http://blog.csdn.net/who_is_xiaoming/article/details/53287691
            // this.session.getBasicRemote().sendText(message);
            item.session.getAsyncRemote().sendText(message);// 异步发送消息.
        }
    }

}

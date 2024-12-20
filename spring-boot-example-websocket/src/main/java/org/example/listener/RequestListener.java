package org.example.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpSession httpSession = ((HttpServletRequest) sre.getServletRequest()).getSession();
        log.info("HttpSessionId: {}", httpSession.getId());
    }

}

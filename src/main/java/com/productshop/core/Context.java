package com.productshop.core;

import com.productshop.config.Configurations;
import com.productshop.router.Route;
import com.productshop.security.AuthenticationManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Context {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RedirectManager redirectManager;
    private CookieManager cookieManager;
    private ErrorManager errorManager;
    private Messenger messenger;
    private AuthenticationManager authManager;
    private Configurations config;
    private Route route;
    private AppInfo appInfo;

    public Context(HttpServletRequest request, HttpServletResponse response, String configPath) throws Exception {
        this.request = request;
        this.response = response;
        this.redirectManager = new RedirectManager(response);
        this.cookieManager = CookieManager.get(this);
        this.errorManager = new ErrorManager();
        this.messenger = new Messenger();
        //this.authManager = new AuthenticationManager(request);
        this.config = Configurations.createConfigurations(configPath);
        this.appInfo = new AppInfo(this);
        this.request.setCharacterEncoding("UTF-8");
        this.response.setContentType("text/html; charset=UTF-8");
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public RedirectManager getRedirectManager() {
        return redirectManager;
    }

    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public ErrorManager getErrorManager() {
        return errorManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authManager;
    }

    public Messenger getMessenger(){
        return messenger;
    }

    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    public void setAttribute(String name, Object o) {
        request.setAttribute(name, o);
    }

    public Configurations getConfig() {
        return config;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public Context getFinalContext() {
        request.setAttribute("errors", errorManager);
        request.setAttribute("messages", messenger);
        request.setAttribute("auth", authManager);
        request.setAttribute("config", config);
        request.setAttribute("appInfo", appInfo);
        return this;
    }

    public boolean isMethodPOST() {
        return request.getMethod().equals("POST");
    }

    public String getRequestParameter(String parameterName) {
        return request.getParameter(parameterName);
    }
}

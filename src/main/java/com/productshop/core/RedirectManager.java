package com.productshop.core;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class RedirectManager {

	private HttpServletResponse response;
	private boolean state;
	
	public RedirectManager(HttpServletResponse response) {
		this.response = response;
		this.state = false;
	}
	
	public void goTo(String url) throws IOException {
		response.sendRedirect(url);
		this.state = true;
	}
	
	public boolean isRedirectAllowed() {
		return state;
	}

}

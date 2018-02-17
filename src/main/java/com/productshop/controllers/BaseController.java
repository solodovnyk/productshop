package com.productshop.controllers;

import com.productshop.core.Context;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseController implements Controller {

	private ThreadLocal<Context> context = new ThreadLocal<>();

	public void invokeAction(Context context, Method action, Object[] params) throws ControllerException {
		this.context.set(context);

		try {
			action.invoke(this, params);
		} catch (InvocationTargetException e) {
			throw new ControllerException(e.getCause().getMessage(), e.getCause());
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		this.context.remove();
	}

	protected Context getContext() {
		return context.get();
	}
}
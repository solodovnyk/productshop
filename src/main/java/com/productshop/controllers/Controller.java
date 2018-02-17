package com.productshop.controllers;

import com.productshop.core.Context;
import java.lang.reflect.Method;

public interface Controller {
	void invokeAction(Context context, Method action, Object[] params) throws ControllerException;
}
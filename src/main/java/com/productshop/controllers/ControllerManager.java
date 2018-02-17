package com.productshop.controllers;

import java.lang.reflect.Method;
import java.util.HashMap;
import com.productshop.core.*;
import com.productshop.router.Route;

public class ControllerManager {

	private static ControllerManager cm;

	private ControllerManager() {}

	public static ControllerManager get() {
		if(ControllerManager.cm == null) {
			ControllerManager.cm = new ControllerManager();
		}

		return ControllerManager.cm;
	}

	public void run(Context context) throws ControllerException {

		//этот метод должен определять данные для запуска на основе роута
		//роут убираем из контекста, так как далее он больше не нужен
		//**************************************************************
		if(context.getRoute() == null) {
			throw new ControllerException("Route object does not exist");
		}

		HashMap<String, Object> preparedAppInfo = prepareAppInfo(context);


		//этот метод запускает метод нужного контроллера на основе входящих данных
		//***************************************************************

		if(preparedAppInfo == null) {
			return;
		}

		appendDataToAppInfo(context, preparedAppInfo);

		Controller controllerObject = (Controller) preparedAppInfo.get("controller");
		Method actionMethod = (Method) preparedAppInfo.get("action");
		Object[] params = (Object[]) preparedAppInfo.get("params");
		controllerObject.invokeAction(context, actionMethod, params);

		//*****************************************************************
	}

	private HashMap<String, Object> prepareAppInfo(Context context)
			throws ControllerException {

		Route route = context.getRoute();
		ErrorManager errorManager = context.getErrorManager();

		if(!route.isValid()) {
			errorManager.setErrorCode(400);
			return null;
		}
		
		Controller controllerObject = prepareController(context, route.getControllerName());
		
		if(controllerObject == null) {
			errorManager.setErrorCode(404);
			return null;
		}
		
		Method actionMethod = prepareActionMethod(context, controllerObject, route.getActionName());
		
		if(actionMethod == null) {
			errorManager.setErrorCode(404);
			return null;
		}
				
		Object params[] = prepareParams(actionMethod, route.getParams());
		
		if(params == null) {
			errorManager.setErrorCode(404);
			return null;
		}

		String viewLayoutName = prepareViewLayoutName(context, actionMethod);
		String title = prepareTitle(context, actionMethod);

		HashMap<String, Object> preparedAppInfo = new HashMap<>();
		preparedAppInfo.put("controller", controllerObject);
		preparedAppInfo.put("action", actionMethod);
		preparedAppInfo.put("params", params);
		preparedAppInfo.put("viewLayoutName", viewLayoutName);
		preparedAppInfo.put("title", title);
		return preparedAppInfo;
	}

	private Controller prepareController(Context context, String controllerName) throws ControllerException {
		try {
			if(controllerName == null) {
				controllerName = context.getAppInfo().getDefaultControllerName();
			}

			Class<? extends Controller> controllerClass = getControllerClass(controllerName);

			if(controllerClass == null) {
				return null;
			}

			return controllerClass.newInstance();
		} catch (Exception e) {
			throw new ControllerException(e.getMessage());
		}
	}
	
	private Method prepareActionMethod(Context context, Controller controllerObject, String actionName)
			throws ControllerException {
		
		if(actionName == null) {
			actionName = getDefaultActionName(context, controllerObject);
		}
		
		return getActionMethod(controllerObject.getClass(), actionName);
	}
	
	private Object[] prepareParams(Method actionMethod, String[] params)
			throws ControllerException {
		
		if(params == null) {
			params = new String[]{};
		}
		
		Class<?>[] paramClasses = actionMethod.getParameterTypes();
		
		if(paramClasses.length != params.length) {
			return null;
		}
		
		Object[] preparedParams = new Object[params.length];
		
		for(int i = 0; i < preparedParams.length; i++) {
			Class<?> paramClass = paramClasses[i];
			
			switch(paramClass.getName()) {
				case "int":
					try {
						preparedParams[i] = Integer.valueOf(params[i]);
					} catch (Exception e) {
						return null;
					}
					
					break;
				case "java.lang.String":
					preparedParams[i] = params[i];
					break;
				default:
					throw new ControllerException("The parameter types of action can be either 'int' or 'String'.");
			}
		}
		
		return preparedParams;
	}
	
	private Class<? extends Controller> getControllerClass(String controllerName) throws ControllerException {
		controllerName = getFullControllerName(controllerName);
		String controllerPackagePath = this.getClass().getPackage().getName();
		
        try {
			return (Class<? extends Controller>)Class.forName(controllerPackagePath + "." + controllerName);
        } catch (Exception userControllerNotFoundError) {
        	return null;
        }
	}
	
	private Method getActionMethod(Class<? extends Controller> controllerClass, String actionName)
			throws ControllerException {

		Method actionMethod = null;
		actionName = getFullActionName(actionName);
		Method[] controllerMethods = controllerClass.getMethods();

		/* The method getMethod of Class<?> does not used here because this requires parameter types of method.
		* On this point controller manager does not now anything about method parameters because the method does not
		* specified yet.
		*/

		for(Method methodObject : controllerMethods) {
			if(methodObject.getName().equals(actionName)) {
				actionMethod = methodObject;
			}
		}
		
		return actionMethod;
	}
	
	private String getFullControllerName(String controllerName) {
		StringBuilder fullName = new StringBuilder(controllerName.substring(0, 1).toUpperCase());
		fullName.append(controllerName.substring(1).toLowerCase());
		fullName.append("Controller");
        return fullName.toString();
    }	
	
	private String getFullActionName(String action) {
		StringBuilder fullName = new StringBuilder("action");
		fullName.append(action.substring(0, 1).toUpperCase());
		fullName.append(action.substring(1).toLowerCase());
        return fullName.toString();
    }

    private void appendDataToAppInfo(Context context, HashMap<String, Object> preparedAppInfo) throws ControllerException {
		Controller controllerObject = (Controller) preparedAppInfo.get("controller");
		Method actionMethod = (Method) preparedAppInfo.get("action");
		String viewLayouName = (String) preparedAppInfo.get("viewLayoutName");
		String title = (String) preparedAppInfo.get("title");

		AppInfo appInfo = context.getAppInfo();
		appInfo.setControllerName(getShortControllerName(controllerObject.getClass().getName()));
		appInfo.setActionName(getShortActionName(actionMethod.getName()));
		appInfo.setViewLayoutName(viewLayouName);
		appInfo.setTitle(title);
	}

	private String getDefaultActionName(Context context, Controller controllerObject) throws ControllerException {
		String defaultActionName;
		ControllerConfig controllerConfig = controllerObject.getClass().getAnnotation(ControllerConfig.class);

		if(controllerConfig != null && !controllerConfig.defaultActionName().isEmpty()) {
			defaultActionName = controllerConfig.defaultActionName();
		} else {
			try {
				defaultActionName = context.getAppInfo().getDefaultActionName();
			} catch (Exception e) {
				throw new ControllerException(e.getMessage());
			}
		}

		return defaultActionName;
	}

	private String prepareViewLayoutName(Context context, Method actionMethod) throws ControllerException {
		String viewLayoutName;
		ActionConfig actionConfig = actionMethod.getAnnotation(ActionConfig.class);

		if(actionConfig != null && !actionConfig.defaultViewLayoutName().isEmpty()) {
			viewLayoutName = actionConfig.defaultViewLayoutName();
		} else {
			try {
				viewLayoutName = context.getAppInfo().getDefaultViewLayoutName();
			} catch (Exception e) {
				throw new ControllerException(e.getMessage());
			}
		}

		return viewLayoutName;
	}

	private String prepareTitle(Context context, Method actionMethod) throws ControllerException {
		String title;
		ActionConfig actionConfig = actionMethod.getAnnotation(ActionConfig.class);

		if(actionConfig != null && !actionConfig.title().isEmpty()) {
			title = actionConfig.title();
		} else {
			try {
				title = context.getAppInfo().getDefaultTitle();
			} catch (Exception e) {
				throw new ControllerException(e.getMessage());
			}
		}

		return title;
	}

	private String getShortControllerName(String controllerName) throws ControllerException {
		int startPosition = controllerName.lastIndexOf(".");
		int finishPosition = controllerName.lastIndexOf("Controller");

		if(startPosition == -1 || finishPosition == -1 || startPosition >= finishPosition) {
			throw new ControllerException("Controller name is not valid.");
		}

		return controllerName.substring(startPosition+1, finishPosition).toLowerCase();
	}

	private String getShortActionName(String actionName) {
		return actionName.substring(6).toLowerCase();
	}
}
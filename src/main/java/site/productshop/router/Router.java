package site.productshop.router;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import site.productshop.config.Configurations;
import site.productshop.core.Context;
import site.productshop.helpers.RouterHelper;


public class Router {
	
	public static Context determineInternalRoute(Context context)
			throws RouterException {
		
		String internalRoute;
		
		try {
			internalRoute = URLDecoder.decode(context.getRequest().getRequestURI(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RouterException(e.getMessage(), e);
		}

		//internalRoute = internalRoute.substring(13);

		Set<String> userRoutesList = getUserRoutesList();
		
		if(userRoutesList != null && userRoutesList.size() > 0) {
			for(String routePattern : userRoutesList) {
				
				Pattern pattern = Pattern.compile(routePattern);
				Matcher matcher = pattern.matcher(internalRoute);
				
				if(matcher.find()) {
					String replacement = Configurations.getComponentValue("routes", routePattern);
					internalRoute = matcher.replaceAll(replacement);
					break;
				}
			}
		}
			
		return setInternalRouteInRequestObject(context, buildRoute(internalRoute));
	}
	
	private static Context setInternalRouteInRequestObject(Context context, Route internalRoute) {
		context.setRoute(internalRoute);
		return context;
	}
	
	private static Route buildRoute(String route) throws RouterException {
		
		String controllerName = null;
		String actionName = null;
		String params[] = null;
		
		route = route.trim();
		
		if(!routeValidate(route)) {
			Route routeObject = new Route(controllerName, actionName, params);
			routeObject.setValid(false);
			return routeObject;
		} 
		
		if(!route.equals("/") && !route.isEmpty()) {
			
			route = (route.charAt(0) == '/') ? route.substring(1) : route;
			
			String[] routeSegments = route.split("/");
			
			controllerName = routeSegments[0];
			
			if(routeSegments.length > 1 && !routeSegments[1].isEmpty()) {
				actionName = routeSegments[1];
			}
			
			if(routeSegments.length > 2) {
				int paramsQuantity = routeSegments.length-2;
				params = new String[paramsQuantity];
				System.arraycopy(routeSegments, 2, params, 0, paramsQuantity);
			}
		}
		
		return new Route(controllerName, actionName, params);
	}
	
	private static Set<String> getUserRoutesList() throws RouterException {

		Set<String> userRoutesList;
		
		try {
			userRoutesList = 
				Configurations
					.getConfigurations()
					.getConfiguration("routes")
					.getComponentList();
		} catch (Exception e) {
			throw new RouterException("Ошибка файла конфигурации.");
		}

		if(userRoutesList == null) {
			throw new RouterException("Не назначена конфигурация для роутинга.");
		}
		
		return userRoutesList;
	}
	
	private static boolean routeValidate(String route) {
		String partOfRoute = RouterHelper.getRoutePartRegExp();
		String param = RouterHelper.getRoutePartRegExpAsParam();
		
		String pattern = "^/?(((" + partOfRoute + "){0,2}$)|((" + partOfRoute + "){2}(" + param + ")*))$";
		
		return Pattern.compile(pattern).matcher(route).find();
	}
}

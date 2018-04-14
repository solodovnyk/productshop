package site.productshop.view_loader;

import java.io.IOException;
import javax.servlet.*;
import site.productshop.core.*;
import site.productshop.core.AppInfo;
import site.productshop.core.Context;
import site.productshop.core.ErrorManager;

public class ViewLoader {
	private static ViewLoader vl;

	private ViewLoader() {}

	public static ViewLoader get() {
		if(ViewLoader.vl == null) {
			ViewLoader.vl = new ViewLoader();
		}

		return ViewLoader.vl;
	}
	
	public void run(Context context) throws ViewLoaderException {
		if(context.getErrorManager().getErrorCode() > 0) {
			prepareErrorPageLoading(context);
		}

		context.getAppInfo().setViewPath(buildViewPath(context));
		loadPage(context);
	}

	private void prepareErrorPageLoading(Context context) {
		ErrorManager errorManager = context.getErrorManager();
		AppInfo appInfo = context.getAppInfo();
		int errorCode = errorManager.getErrorCode();

		appInfo.setViewLayoutName("main");
		appInfo.setControllerName("error");
		appInfo.setActionName(Integer.toString(errorCode));
		appInfo.setTitle("Error " + errorCode);
		//errorManager.setErrorCode(0);
	}

	private void loadPage(Context context) throws ViewLoaderException {
		String viewLayoutName = context.getAppInfo().getViewLayoutName();

		if(viewLayoutName == null) {
			viewLayoutName = "default";
		}

		try {
			RequestDispatcher ds = context.getRequest().getRequestDispatcher(buildLayoutPath(context));

			if(ds == null) {
				throw new ViewLoaderException("The view layout has not been found.");
			}

			ds.forward(context.getFinalContext().getRequest(), context.getResponse());
		} catch (ServletException | IOException e) {
			throw new ViewLoaderException(e.getMessage(), e);
		}
	}
	
	private String buildLayoutPath(Context context) throws ViewLoaderException {
		AppInfo appInfo = context.getAppInfo();
		StringBuilder layoutPath = new StringBuilder();
		String viewDirectoryPath;
		String layoutsDirectoryName;

		try {
			viewDirectoryPath = appInfo.getViewDirectoryPath();
			layoutsDirectoryName = appInfo.getLayoutsDirectoryName();
		} catch (Exception e) {
			throw new ViewLoaderException(e.getMessage());
		}

		return layoutPath
				.append(viewDirectoryPath)
				.append("/")
				.append(layoutsDirectoryName)
				.append("/")
				.append(appInfo.getViewLayoutName())
				.append(".jsp")
				.toString();
	}
	
	private String buildViewPath(Context context) throws ViewLoaderException {
		AppInfo appInfo = context.getAppInfo();
		StringBuilder viewPath = new StringBuilder();
		String viewDirectoryPath;

		try {
			viewDirectoryPath = appInfo.getViewDirectoryPath();
		} catch (Exception e) {
			throw new ViewLoaderException(e.getMessage());
		}

		String controllerName = appInfo.getControllerName();
		String actionName = appInfo.getActionName();

		if(controllerName == null || actionName == null) {
			throw new ViewLoaderException("Controller name or action name does not defined");
		}

		return viewPath
			.append(viewDirectoryPath)
			.append("/")
			.append(controllerName)
			.append("/")
			.append(actionName)
			.append(".jsp")
			.toString();
	}
}

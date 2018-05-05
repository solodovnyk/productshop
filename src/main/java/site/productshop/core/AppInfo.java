package site.productshop.core;

public class AppInfo {
    private Context context;
    private String controllerName;
    private String actionName;
    private String viewLayoutName;
    private String title;
    private String viewPath;

    public AppInfo(Context context) {
        this.context = context;
    }

    public String getDefaultControllerName() throws Exception {
        String defaultControllerName = context.getConfig().getComponentValue("site-settings", "defaultController");

        if(defaultControllerName == null) {
            throw new Exception("Default controller has not been found");
        }

        return defaultControllerName;
    }

    public String getDefaultActionName() throws Exception {
        String defaultActionName = context.getConfig().getComponentValue("site-settings", "defaultAction");

        if(defaultActionName == null) {
            throw new Exception("Default action has not been found");
        }

        return defaultActionName;
    }

    public String getDefaultViewLayoutName() throws Exception {
        String defaultViewLayoutName = context.getConfig().getComponentValue("site-settings", "defaultViewLayout");

        if(defaultViewLayoutName == null) {
            throw new Exception("Default view layout has not been found");
        }

        return  defaultViewLayoutName;
    }

    public String getDefaultTitle() throws Exception {
        String defaultTitle = context.getConfig().getComponentValue("site-settings", "defaultViewLayout");

        if(defaultTitle == null) {
            throw new Exception("Application name has not been found");
        }

        return defaultTitle;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getViewLayoutName() {
        return viewLayoutName;
    }

    public void setViewLayoutName(String viewLayoutName) {
        this.viewLayoutName = viewLayoutName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String  getViewDirectoryPath() throws Exception {
        String viewDirectoryPath = context.getConfig().getComponentValue("site-settings", "viewDirectoryPath");

        if(viewDirectoryPath == null) {
            throw new Exception("View directory path has not been found");
        }

        return  viewDirectoryPath;
    }

    public String  getLayoutsDirectoryName() throws Exception {
        String layoutsDirectoryName = context.getConfig().getComponentValue("site-settings", "layoutsDirectoryName");

        if(layoutsDirectoryName == null) {
            throw new Exception("Layouts directory name has not been found");
        }

        return  layoutsDirectoryName;
    }
}

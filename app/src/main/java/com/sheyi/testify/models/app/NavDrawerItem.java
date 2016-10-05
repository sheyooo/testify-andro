package com.sheyi.testify.models.app;

/**
 * Created by andela on 08/09/2016.
 */

public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int icon;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
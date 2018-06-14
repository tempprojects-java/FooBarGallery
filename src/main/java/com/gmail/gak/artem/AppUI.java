package com.gmail.gak.artem;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@Theme("app")
@SpringUI
@SpringViewDisplay
@PushStateNavigation
public class AppUI extends UI implements ViewDisplay{
    private VerticalLayout springViewDisplay;
    private final Navigator navigator;

    @Autowired
    public AppUI(SpringViewProvider viewProvider) {
        navigator = new Navigator(this, (ViewDisplay) this);
        navigator.addProvider(viewProvider);
    }


    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setWidth(100, Unit.PERCENTAGE);
        root.setSpacing(false);
        root.setHeightUndefined();
        setContent(root);

        if(null == getUI().getSession().getAttribute("images")) {
            getUI().getSession().setAttribute("images", new HashMap<>());
        }

        springViewDisplay = new VerticalLayout();
        springViewDisplay.setMargin(false);
        root.addComponent(springViewDisplay);
    }

    @Override
    public void showView(View view) {
        springViewDisplay.removeAllComponents();
        springViewDisplay.addComponent((Component) view);
    }
}

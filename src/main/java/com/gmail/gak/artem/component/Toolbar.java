package com.gmail.gak.artem.component;

import com.vaadin.ui.*;

import java.util.List;

public class Toolbar extends GridLayout {
    private HorizontalLayout left = new HorizontalLayout();
    private HorizontalLayout right = new HorizontalLayout();

    public enum POSITION {
        LEFT, RIGHT
    }

    public Toolbar() {
        super(2, 1);
        initLeft();
        initRight();

        setStyleName("tbar");
        setWidth(100, Unit.PERCENTAGE);
        setHeight(60, Unit.PIXELS);
    }

    private void initLeft() {
        addComponent(wrap(left, "tbar-let"));

        left.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        left.setWidthUndefined();
        left.setHeight(100, Unit.PERCENTAGE);
    }

    private void initRight() {
        addComponent(wrap(right, "tbar-right"));

        right.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        right.setWidthUndefined();
        right.setHeight(100, Unit.PERCENTAGE);
    }

    private CssLayout wrap(Component component, String style) {
        CssLayout wrapper = new CssLayout(component);
        wrapper.setStyleName(style);
        wrapper.setSizeFull();
        return wrapper;
    }

    public HorizontalLayout getLeftLayout() {
        return left;
    }

    public HorizontalLayout getRightLayout() {
        return right;
    }

    public void addComponent(Component component, POSITION position) {
        if(POSITION.LEFT.equals(position)) {
            left.addComponent(component);
        } else if(POSITION.RIGHT.equals(position)) {
            right.addComponent(component);
        }
    }

    public void addComponents(List<Component> components, POSITION position) {
        if(POSITION.LEFT.equals(position)) {
            for (Component component: components) {
                left.addComponent(component);
            }
        } else if(POSITION.RIGHT.equals(position)) {
            for (Component component: components) {
                right.addComponent(component);
            }
        }
    }
}

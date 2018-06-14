package com.gmail.gak.artem.component;

import com.vaadin.ui.*;

import java.util.Collection;
import java.util.List;

public class GridPanel<T> extends CssLayout {
    private Toolbar tbar = new Toolbar();
    private Grid<T> grid = new Grid<>();

    public GridPanel() {
        addComponents(tbar, grid);
        setWidth(100, Unit.PERCENTAGE);
        grid.setWidth(100, Unit.PERCENTAGE);
    }

    public Grid<T> getGrid() {
        return grid;
    }

    public void addToolbarComponent(Component component, Toolbar.POSITION position) {
        tbar.addComponent(component, position);
    }

    public void addToolbarComponents(List<Component> components, Toolbar.POSITION position) {
        tbar.addComponents(components, position);
    }

    public void setItems (Collection<T> items) {
        grid.setItems(items);
    }
}

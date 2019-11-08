package org.fluentness.controller.desktop.swing.component;

import javax.swing.*;
import javax.swing.plaf.PopupMenuUI;
import java.awt.*;

public class PopupMenuView extends AbstractComponentView<PopupMenuView, JPopupMenu> {

    private JPopupMenu jPopupMenu = new JPopupMenu();

    @Override
    public JPopupMenu getSwingView() {
        return jPopupMenu;
    }

    public PopupMenuView borderPainted(boolean b) {
        jPopupMenu.setBorderPainted(b);
        return this;
    }

    public PopupMenuView invoker(Component invoker) {
        jPopupMenu.setInvoker(invoker);
        return this;
    }

    public PopupMenuView label(String label) {
        jPopupMenu.setLabel(label);
        return this;
    }

    public PopupMenuView lightWeightPopupEnabled(boolean aFlag) {
        jPopupMenu.setLightWeightPopupEnabled(aFlag);
        return this;
    }

    public PopupMenuView popupSize(Dimension d) {
        jPopupMenu.setPopupSize(d);
        return this;
    }

    public PopupMenuView popupSize(int width, int height) {
        jPopupMenu.setPopupSize(width, height);
        return this;
    }

    public PopupMenuView selected(Component sel) {
        jPopupMenu.setSelected(sel);
        return this;
    }

    public PopupMenuView selectionModel(SingleSelectionModel model) {
        jPopupMenu.setSelectionModel(model);
        return this;
    }

    public PopupMenuView uI(PopupMenuUI ui) {
        jPopupMenu.setUI(ui);
        return this;
    }
}
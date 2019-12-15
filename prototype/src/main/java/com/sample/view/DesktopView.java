package com.sample.view;

import com.sample.controller.DesktopController;
import org.fluentness.controller.desktop.AbstractDesktop;
import org.fluentness.style.desktop.DesktopStyle;
import org.fluentness.view.desktop.component.MenuBarView;

import javax.swing.*;
import java.awt.*;

import static org.fluentness.style.desktop.DesktopStyleFactory.*;
import static org.fluentness.view.desktop.SwingViewFactory.*;

public class DesktopView implements AbstractDesktop<DesktopController> {

    public DesktopView(DesktopController controller) {
        super(controller);
    }

    @Override
    public DesktopStyle getStyle() {
        return ubuntuStyle(

//            byId("")


            byClass(JTable.class, jTable -> jTable.setDragEnabled(false))
        );
    }


    @Override
    public org.fluentness.controller.desktop.DesktopView getView() {
        return frame("Fluentness rocks",
            panel(
                panel(
                    colorChooser(),
                    table(
                        header("Spalte1", "Spalte2", "Spalte3", "Spalte4"),
                        row(1, "John", 40.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(2, "Rambo", 70.0, false),
                        row(3, "Zorro", 60.0, true)
                    ),
                    button("Test button")
                ).flowLayout()
            ).borderLayout(BorderLayout.NORTH, BorderLayout.SOUTH)
        ).title("Fluentness rocks").bounds(0, 0, 800, 600).center().minimumSize(300, 300).menuBar(topBar());
    }

    private MenuBarView topBar() {
        return menuBar(
            menu("File",
                menuItem("Load").accelerator(KeyStroke.getKeyStroke('C')),
                menuItem("Save"),
                menuItem("Close")
            ),
            menu("Edit",
                menuItem("Cut").actionListener(controller::cutPressed),
                menuItem("Copy"),
                menuItem("Paste")
            ),
            menu("RadioButtons",
                buttonGroupInMenu(
                    radioButtonMenuItem("Radio 1"),
                    radioButtonMenuItem("Radio 2")
                )
            ),
            menu("CheckBoxes",
                checkBoxMenuItem("CheckBox 1"),
                checkBoxMenuItem("CheckBox 2")
            ),
            menu("Help",
                menuItem("Manual"),
                menuItem("About")
            )
        );
    }
}
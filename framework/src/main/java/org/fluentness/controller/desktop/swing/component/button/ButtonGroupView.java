package org.fluentness.controller.desktop.swing.component.button;

import org.fluentness.controller.desktop.swing.component.PanelView;

import javax.swing.*;

/* As button group is per se not a Swing view, implement it as a panel view,
 * alternatively use for grouping buttons new ButtonGroup(button1, button2, etc.)
 */
public class ButtonGroupView extends PanelView {

    public ButtonGroupView(AbstractButtonView... abstractButtonViews) {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (AbstractButtonView abstractButtonView : abstractButtonViews) {
            buttonGroup.add(abstractButtonView.getSwingView());
            jPanel.add(abstractButtonView.getSwingView());
        }
    }
}

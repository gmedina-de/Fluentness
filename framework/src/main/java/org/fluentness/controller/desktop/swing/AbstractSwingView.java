package org.fluentness.controller.desktop.swing;

import org.fluentness.controller.desktop.DesktopView;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractSwingView<Self extends AbstractSwingView, View extends Container> implements DesktopView {

    public abstract View getSwingView();

    protected AbstractSwingView(Class<Self> selfClass) {
        //apply default styles
    }

    @Override
    public void render() {
        getSwingView().setVisible(true);
    }

    // ==== instead of layout()
    public Self borderLayout(String... arrangements) {
        getSwingView().setLayout(new BorderLayout());
        Component[] components = getSwingView().getComponents();
        getSwingView().removeAll();
        for (int i = 0; i < components.length && i < arrangements.length; i++) {
            getSwingView().add(components[i], arrangements[i]);
        }
        return (Self) this;
    }

    public Self boxLayout(int align) {
        getSwingView().setLayout(new BoxLayout(getSwingView(), align));
        Component[] components = getSwingView().getComponents();
        getSwingView().removeAll();
        for (Component component : components) {
            getSwingView().add(component, Component.CENTER_ALIGNMENT);
        }
        return (Self) this;
    }

    public Self flowLayout() {
        getSwingView().setLayout(new FlowLayout());
        return (Self) this;
    }


    // ==== from java.awt.Component
    public Self componentListener(ComponentListener l) {
        getSwingView().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {

            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
        return (Self) this;
    }

    public Self focusListener(FocusListener l) {
        getSwingView().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {

            }
        });
        return (Self) this;
    }

    public Self hierarchyBoundsListener(HierarchyBoundsListener l) {
        getSwingView().addHierarchyBoundsListener(new HierarchyBoundsListener() {
            @Override
            public void ancestorMoved(HierarchyEvent hierarchyEvent) {

            }

            @Override
            public void ancestorResized(HierarchyEvent hierarchyEvent) {

            }
        });
        return (Self) this;
    }

    public Self hierarchyListener(HierarchyListener l) {
        getSwingView().addHierarchyListener(l);
        return (Self) this;
    }

    public Self inputMethodListener(InputMethodListener l) {
        getSwingView().addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent inputMethodEvent) {

            }

            @Override
            public void caretPositionChanged(InputMethodEvent inputMethodEvent) {

            }
        });
        return (Self) this;
    }

    public Self keyListener(KeyListener l) {
        getSwingView().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        return (Self) this;
    }

    public Self mouseListener(MouseListener l) {
        getSwingView().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        return (Self) this;
    }

    public Self mouseMotionListener(MouseMotionListener l) {
        getSwingView().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
        return (Self) this;
    }

    public Self mouseWheelListener(MouseWheelListener l) {
        getSwingView().addMouseWheelListener(l);
        return (Self) this;
    }

    public Self background(Color c) {
        getSwingView().setBackground(c);
        return (Self) this;
    }

    public Self bounds(int x, int y, int width, int height) {
        getSwingView().setBounds(x, y, width, height);
        return (Self) this;
    }

    public Self bounds(Rectangle r) {
        getSwingView().setBounds(r);
        return (Self) this;
    }

    public Self componentOrientation(ComponentOrientation o) {
        getSwingView().setComponentOrientation(o);
        return (Self) this;
    }

    public Self cursor(Cursor cursor) {
        getSwingView().setCursor(cursor);
        return (Self) this;
    }

    public Self dropTarget(DropTarget dt) {
        getSwingView().setDropTarget(dt);
        return (Self) this;
    }

    public Self enabled(boolean b) {
        getSwingView().setEnabled(b);
        return (Self) this;
    }

    public Self focusable(boolean focusable) {
        getSwingView().setFocusable(focusable);
        return (Self) this;
    }

    public Self focusTraversalKeysEnabled(boolean focusTraversalKeysEnabled) {
        getSwingView().setFocusTraversalKeysEnabled(focusTraversalKeysEnabled);
        return (Self) this;
    }

    public Self foreground(Color c) {
        getSwingView().setForeground(c);
        return (Self) this;
    }

    public Self ignoreRepaint(boolean ignoreRepaint) {
        getSwingView().setIgnoreRepaint(ignoreRepaint);
        return (Self) this;
    }

    public Self locale(Locale l) {
        getSwingView().setLocale(l);
        return (Self) this;
    }

    public Self location(int x, int y) {
        getSwingView().setLocation(x, y);
        return (Self) this;
    }

    public Self maximumSize(int x, int y) {
        getSwingView().setMaximumSize(new Dimension(x, y));
        return (Self) this;
    }

    public Self minimumSize(int x, int y) {
        getSwingView().setMinimumSize(new Dimension(x, y));
        return (Self) this;
    }

    public Self name(String name) {
        SwingViewRegistry.add(name, getSwingView());
        getSwingView().setName(name);
        return (Self) this;
    }

    public Self preferredSize(int x, int y) {
        getSwingView().setPreferredSize(new Dimension(x, y));
        return (Self) this;
    }

    public Self size(int width, int height) {
        getSwingView().setSize(width, height);
        return (Self) this;
    }


    // ==== from java.awt.Container
    public Self containerListener(ContainerListener l) {
        getSwingView().addContainerListener(l);
        return (Self) this;
    }

    public Self propertyChangeListener(PropertyChangeListener listener) {
        getSwingView().addPropertyChangeListener(listener);
        return (Self) this;
    }

    public Self propertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSwingView().addPropertyChangeListener(propertyName, listener);
        return (Self) this;
    }

    public Self componentZOrder(Component comp, int index) {
        getSwingView().setComponentZOrder(comp, index);
        return (Self) this;
    }

    public Self focusCycleRoot(boolean focusCycleRoot) {
        getSwingView().setFocusCycleRoot(focusCycleRoot);
        return (Self) this;
    }

    public Self focusTraversalKeys(int id, Set<? extends AWTKeyStroke> keystrokes) {
        getSwingView().setFocusTraversalKeys(id, keystrokes);
        return (Self) this;
    }

    public Self focusTraversalPolicy(FocusTraversalPolicy policy) {
        getSwingView().setFocusTraversalPolicy(policy);
        return (Self) this;
    }

    public Self focusTraversalPolicyProvider(boolean provider) {
        getSwingView().setFocusTraversalPolicyProvider(provider);
        return (Self) this;
    }

    public Self font(Font f) {
        getSwingView().setFont(f);
        return (Self) this;
    }

    public Self layout(LayoutManager mgr) {
        getSwingView().setLayout(mgr);
        return (Self) this;
    }
}

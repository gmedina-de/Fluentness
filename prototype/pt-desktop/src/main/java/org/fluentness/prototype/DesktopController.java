package org.fluentness.prototype;

import org.fluentness.controller.AbstractDesktopController;

public class DesktopController extends AbstractDesktopController<DesktopView> {
    public DesktopController(DesktopView view) {
        super(view);
    }

//    private final UserRepository userRepository;
//
//    public DesktopController(DesktopView view, UserRepository userRepository) {
//        super(view);
//        this.userRepository = userRepository;
//
//        onClick(view.button1, this::onButtonClick);
//    }
//
//    private void onButtonClick() {
//        view.button1.setText("text");
//        User model = new User();
//        model.setUsername("test");
//        userRepository.insert(model);
//        for (User user : userRepository.selectAll()) {
//            System.out.println(user.getUsername());
//        }
//        view.table.addRow(2, "asdf", "asdfasdf");
//    }


}

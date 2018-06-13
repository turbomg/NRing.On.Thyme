package com.katamlek.nringthymeleaf.frontend;

import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.ui.*;

public class LoginView extends VerticalLayout {

    UserRepository userRepository;

    public LoginView(UserRepository userRepository) {
        this.userRepository = userRepository;
        addComponent(buildLoginScreen());
    }

    // Budowanie okna logowania
    public VerticalLayout buildLoginScreen() {

        VerticalLayout loginWindowLayout = new VerticalLayout();
        loginWindowLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        TextField email = new TextField("E-mail");
        email.setWidth("300px");
        TextField password = new TextField("Password");
        password.setWidth("300px");
        loginWindowLayout.addComponents(email, password);
        loginWindowLayout.addComponents(email, password);

        HorizontalLayout buttons = new HorizontalLayout();

        Button go = new Button("Go");
        go.addClickListener(e -> {
            String interceptedLogin = email.getValue();
            String interceptedPassword = password.getValue();
            verifyCredentials(interceptedLogin, interceptedPassword);
        });

        Button cancel = new Button("Cancel");
        cancel.addClickListener(e -> {
            email.setValue("");
            password.setValue("");
        });

        buttons.addComponent(go);
        buttons.addComponent(cancel);

        loginWindowLayout.addComponent(buttons);

        return loginWindowLayout;
    }

    // Login data validation
    private void verifyCredentials(String enteredEmail, String enteredPassword) {

        if ((userRepository.findByEmail(enteredEmail).isPresent()) && userRepository.findByEmail(enteredEmail).get().getPassword().equals(enteredPassword)) {
            // navigate to main view
            // UI.getCurrent.getNavigator.navigateTo()
            userRepository.findByEmail(enteredEmail).get().setLoggedIn(true);
        } else {
            Notification.show("Your credentials are wrong. Please try again or contact system administrator");
            userRepository.findByEmail(enteredEmail).get().setLoggedIn(false);
        }
    }
}

package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.User;
import com.katamlek.nringthymeleaf.domain.UserBranding;
import com.katamlek.nringthymeleaf.frontend.grids.UserGridView;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

/**
 * Used to enter new users/ edit existing users.
 * Accesible via UserGridView.
 */

@SpringComponent
@UIScope
@SpringView(name = UserForm.VIEW_NAME)
//todo form validation
//todo autosave
public class UserForm extends VerticalLayout implements View {
    public static final String VIEW_NAME = "user-form";
    Binder<User> userBinder = new Binder<>(User.class);

    UserRepository userRepository;
    // Currently edited user
    User user;

    public UserForm(UserRepository userRepository) {
        this.userRepository = userRepository;
        addComponents(buildUserForm());
    }

    public VerticalLayout buildUserForm() {

        // Vetical Layout
        VerticalLayout userFormVL = new VerticalLayout();

        // User form
        FormLayout userFL = new FormLayout();

        // Form fields
        // No display name - will use e-mail as login
        TextField name = new TextField("Name");
        TextField surname = new TextField("Surname");
        TextField initials = new TextField("Initials");
        TextField phoneNumber = new TextField("Phone number");
        TextField email = new TextField("E-mail");
        RadioButtonGroup<UserBranding> brandingRadioButtonGroup = new RadioButtonGroup<>("Branding", DataProvider.ofItems(UserBranding.values()));

        // Add fields to form
        userFL.addComponents(name, surname, initials, phoneNumber, email, brandingRadioButtonGroup);

        // Buttons
        HorizontalLayout userFormButtonsHL = new HorizontalLayout();

        // Buttons and listeners
        //todo close form
        //todo at listeners remove focus when cancel pressed

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            saveUser();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> {
            cancelUserChanges();
        });

        Button deleteButton = new Button("Delete user");
        deleteButton.addClickListener(e -> {
            deleteUser();
        });

        Button closeButton = new Button("Close form");
        closeButton.addClickListener(e -> {
            closeForm();
        });

       userFormButtonsHL.addComponents(saveButton, cancelButton, deleteButton);

        // Binder
     //   Binder<User> userBinder = new Binder<>(User.class);
        //    userBinder.bindInstanceFields(this);
        userBinder.bind(name, User::getName, User::setName);
        //todo set as required and so on
        userBinder.bind(email, User::getEmail, User::setEmail);

        // Put it all together
        userFormVL.addComponents(userFormButtonsHL, userFL);

        return userFormVL;
    }

    // Save method
    public void saveUser() {
        userRepository.save(user);
        UI.getCurrent().getNavigator().navigateTo("UserGridView");
    }

    // Cancel method
    public void cancelUserChanges() {
        //Popup
        Window window = new Window("Are you sure?");

        //Popup contents
        VerticalLayout confirmationVL = new VerticalLayout();
        confirmationVL.addComponent(new Label("If you continue the data will be lost."));

        // And buttons
        Button yesButton = new Button("Proceed");
        yesButton.addClickListener(e -> {
            window.close();
            UI.getCurrent().getNavigator().navigateTo(UserGridView.VIEW_NAME);
        });

        Button noButton = new Button("Take me back");
        noButton.addClickListener(e -> {
            window.close();
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
        confirmationVL.addComponent(buttonsLayout);

        window.setContent(confirmationVL);

        window.center();
        UI.getCurrent().addWindow(window);

    }

    // Delete method
    public void deleteUser() {
        //Popup
        Window window = new Window("Delete this user?");

        //Popup contents
        VerticalLayout confirmationVL = new VerticalLayout();
        confirmationVL.addComponent(new Label("There's no undo option"));

        // And buttons
        Button yesButton = new Button("Proceed");
        yesButton.addClickListener(e -> {
            userRepository.delete(user);
            UI.getCurrent().getNavigator().navigateTo(UserGridView.VIEW_NAME);
        });

        Button noButton = new Button("Take me back");
        noButton.addClickListener(e -> {
            window.close();
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
        confirmationVL.addComponent(buttonsLayout);

        window.setContent(confirmationVL);

        window.center();
        UI.getCurrent().addWindow(window);

    }

    // Edit/ add new method
    public void editUser(User user) {
        if (user != null) {
            userBinder.setBean(userRepository.findById(user.getId()).get());
            // set focus on name - drag form fields up to instance fields
        } else {
            userBinder.setBean(new User());
        }
    }

    // Closing form
    private void closeForm() {
        UI.getCurrent().getNavigator().navigateTo(UserGridView.VIEW_NAME);
    }


}

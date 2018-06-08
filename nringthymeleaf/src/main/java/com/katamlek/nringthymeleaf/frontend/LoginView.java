//package com.katamlek.nringthymeleaf.frontend;
//
//import com.vaadin.ui.*;
//import javafx.scene.control.ComboBox;
//
//import java.util.ArrayList;
//
//public class LoginView extends VerticalLayout {
//    public LoginView() {
//        addComponent(buildLoginScreen());
//    }
//
//    // Budowanie okna logowania
//    public VerticalLayout buildLoginScreen() {
//
//        VerticalLayout loginWindowLayout = new VerticalLayout();
//        loginWindowLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//
//        TextField login = new TextField("Login");
//        login.setWidth("300px");
//        TextField password = new TextField("Hasło");
//        password.setWidth("300px");
//        loginWindowLayout.addComponents(login, password);
//        ComboBox<String> branch = new ComboBox<>("Oddział");
//        branch.set(false);
//        branch.setItems("Oddział A", "Oddział B", "Oddział C");
//        branch.setWidth("300px");
//        loginWindowLayout.addComponents(login, password, branch);
//
//        HorizontalLayout buttons = new HorizontalLayout();
//
//        Button go = new Button("Zaloguj się");
//        go.addClickListener(e -> {
//            String interceptedLogin = login.getValue();
//            String interceptedPassword = password.getValue();
//            verifyCredentials(interceptedLogin, interceptedPassword);
//        });
//
//        Button cancel = new Button("Anuluj");
//        cancel.addClickListener(e -> {
//            login.setValue("");
//            password.setValue("");
//        });
//
//        buttons.addComponent(go);
//        buttons.addComponent(cancel);
//
//        loginWindowLayout.addComponent(buttons);
//
//        return loginWindowLayout;
//    }
//
//    // Napelnienie listy uzytkownikow danymi testowymi
//    private ArrayList<User> loadUserData() {
//        ArrayList<User> users = new ArrayList<>();
//        users.add(new User("kasia", "pirania"));
//        users.add(new User("basia", "szczupak"));
//        users.add(new User("ala", "wieloryb"));
//        users.add(new User("ewa", "sledz"));
//        users.add(new User("lenka", "karp"));
//
//        return users;
//    }
//
//    // Walidacja logowania
//    private void verifyCredentials(String enteredLogin, String enteredPassword) {
//        User incomingUser = new User(enteredLogin, enteredPassword);
//
////        if (loadUserData().contains(incomingUser)) {
////
////            UI.getCurrent().getNavigator().navigateTo("WrcGenericScreen");
////
////        } else if (!(loadUserData().contains(incomingUser))) {
////            com.vaadin.ui.Notification.show("Podano niepoprawne dane");
////        }
//
//        UI.getCurrent().getNavigator().navigateTo("WrcNewMessagesAndSystemNotificationsPage");
//    }
//}

package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.User;
import com.katamlek.nringthymeleaf.domain.UserRole;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.util.Arrays;

/**
 * System users list.
 */

@SpringView
@UIScope
public class UserGridView extends VerticalLayout implements View {
    private UserRepository userRepository;
    //  private UserForm userForm;
    private NavigationManager navigationManager;
    private Grid<User> userGrid;
    private GridCellFilter filter;

    public UserGridView(UserRepository userRepository, NavigationManager navigationManager) {
        this.userRepository = userRepository;
    //    this.userForm = userForm;
        this.navigationManager = navigationManager;
        addComponent(buildUserGridView());
        setMargin(false);
    }

    // Build the grid with users
    public Grid<User> buildUserGrid() {
        Grid<User> userGrid = new Grid<>(User.class);
        userGrid.setItems(Lists.newArrayList(userRepository.findAll()));

        // Setting visible colums according to specs
        userGrid.setColumns("initials", "name", "surname", "phoneNumber", "email", "userRole");

        // Setting column order
        userGrid.setColumnOrder("initials", "name", "surname", "phoneNumber", "email", "userRole");

        // Set columns hideable, reordering etc.
        userGrid.getColumns().forEach(column -> column.setSortable(true));
        userGrid.setColumnReorderingAllowed(true);
        userGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open user details for editing on double click
        userGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("User form is not ready yet.");
                // navigationManager.navigateTo(UserForm.class, event.getItem().getId());
            }
        });

        // Inline filtering
        this.filter = new GridCellFilter(userGrid);
        this.filter.setTextFilter("initials", true, true);
        this.filter.setTextFilter("name", true, true);
        this.filter.setTextFilter("surname", true, true);
        this.filter.setTextFilter("phoneNumber", true, true);
        this.filter.setTextFilter("email", true, true);

        CellFilterComponent<ComboBox<UserRole>> roleFilter = this.filter.setComboBoxFilter("userRole", UserRole.class, Arrays.asList(UserRole.values()));

        // Inline editor
        // userGrid.getEditor().setEnabled(true);

        // Extra columns: delete
        userGrid.addComponentColumn(this::buildDeleteButton);

        userGrid.setSizeFull();

        return userGrid;
    }

    // Build the buttons row: Add, Clear filters
    public HorizontalLayout buildUserButtons() {
        HorizontalLayout buttonsUserHL = new HorizontalLayout();

        Button addUserBtn = new Button("Add user"); // add new user
        addUserBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addUserBtn.setIcon(VaadinIcons.PLUS);
        addUserBtn.addClickListener(e -> Notification.show("The developer says she's working on it."));
//        addUserBtn.addClickListener(e -> {
//            navigationManager.navigateTo(UserForm.class); //todo
//        });

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        buttonsUserHL.addComponents(addUserBtn, clearAllFilters);
        return buttonsUserHL;
    }

    // Put all together
    public VerticalLayout buildUserGridView() {
        Label label = new Label("User List");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        VerticalLayout userGridViewVL = new VerticalLayout();
        userGridViewVL.setMargin(false);
        userGridViewVL.addComponents(label, buildUserButtons(), buildUserGrid());
        return userGridViewVL;
    }

    // Helpers
    private Button buildDeleteButton(User user) {
        Button deleteUButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteUButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteUButton.addClickListener(e -> {

            if (user.isLoggedIn() || user.isUnderEditing()) {
                Notification.show("The user is currently working or his data is being processed. Won't delete the data now.");
            } else {
                //Confirmation popup
                Window window = new Window("Do you really want to delete this customer?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    userRepository.delete(user);
                    userGrid.setItems(Lists.newArrayList(userRepository.findAll()));
                    // todo clear filters ??? MATEUSZ? JONO?
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);

            }
        });

        return deleteUButton;
    }

}

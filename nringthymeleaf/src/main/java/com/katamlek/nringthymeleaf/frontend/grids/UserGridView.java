package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.User;
import com.katamlek.nringthymeleaf.domain.UserRole;
import com.katamlek.nringthymeleaf.frontend.forms.UserForm;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
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
    private UserForm userForm;

    private GridCellFilter filter;

    public static final String VIEW_NAME = "user-grid-view";

    public UserGridView(UserRepository userRepository, UserForm userForm) {
        this.userRepository = userRepository;
        this.userForm = userForm;
        addComponent(buildUserGridView());
    }

    // Build the grid with cars
    public Grid<User> buildUserGrid() {
        // Setting items - only this booking cars, selected by booking id
        Grid<User> userGrid = new Grid<>(User.class);
        //todo how to pass the booking id parameter?
        userGrid.setItems(Lists.newArrayList(userRepository.findAll()));

        // Setting visible colums according to specs
        userGrid.getColumn("id").setHidden(true);
        userGrid.getColumn("password").setHidden(true);
        userGrid.getColumn("branding").setHidden(true);
        userGrid.getColumn("bookings").setHidden(true);
        userGrid.getColumn("customers").setHidden(true);
        userGrid.getColumn("events").setHidden(true);
        userGrid.getColumn("notes").setHidden(true);
        userGrid.getColumn("customerDocuments").setHidden(true);
        userGrid.getColumn("loggedIn").setHidden(true);

        // Setting column order
        userGrid.setColumnOrder("initials", "name", "surname", "phoneNumber", "email", "userRole");

        // Set columns hideable, reordering etc.
        userGrid.getColumns().forEach(column -> column.setSortable(true));
        userGrid.setColumnReorderingAllowed(true);
        userGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open user details for editing on double click
        userGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected user
                // todo NOT A NAVIGATOR ! New userEditForm(Long id);
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
        userGrid.getEditor().setEnabled(true);

        // Extra columns: edit, delete
        userGrid.addColumn(user -> "Edit", new ButtonRenderer(clickEvent -> {
            userForm.editUser((User) clickEvent.getItem());
        }));

        userGrid.addColumn(bookingCar -> "Delete", new ButtonRenderer(clickEvent -> {
            userRepository.delete((User) clickEvent.getItem());
        }));

        userGrid.setSizeFull();

        return userGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildUserButtons() {
        HorizontalLayout buttonsUserHL = new HorizontalLayout();

        Button addUserBtn = new Button("Add user"); // add new user
        addUserBtn.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(UserForm.VIEW_NAME);
                });

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        buttonsUserHL.addComponents(addUserBtn, clearAllFilters);
        return buttonsUserHL;
    }

    // Put all together
    public VerticalLayout buildUserGridView() {
        VerticalLayout userGridViewVL = new VerticalLayout();
        userGridViewVL.addComponents(buildUserButtons(), buildUserGrid());
        return userGridViewVL;
    }
}

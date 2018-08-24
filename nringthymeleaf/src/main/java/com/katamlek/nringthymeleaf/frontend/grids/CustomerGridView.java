package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.CarColor;
import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.domain.CustomerGroup;
import com.katamlek.nringthymeleaf.frontend.forms.CustomerForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.WelcomeView;
import com.katamlek.nringthymeleaf.repositories.CustomerNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
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
 * Customer listing used throughout the application.
 */
// todo this is an extra for RSR if no calendar on time

@SpringView
@UIScope
public class CustomerGridView extends VerticalLayout implements View {
    private CustomerRepository customerRepository;
    private CustomerNoteRepository customerNoteRepository;
    private NavigationManager navigationManager;
    private GridCellFilter filter;
    private Grid<Customer> customerGrid;

    public CustomerGridView(CustomerRepository customerRepository, CustomerNoteRepository customerNoteRepository,
                            NavigationManager navigationManager) {
        this.customerRepository = customerRepository;
        this.customerNoteRepository = customerNoteRepository;
        this.navigationManager = navigationManager;
        this.addComponent(buildCustomerGridView());
        setMargin(false);
    }

    // Build the grid with drivers
    public Grid<Customer> buildCustomerGrid() {

        customerGrid = new Grid<>(Customer.class);
        customerGrid.setItems(Lists.newArrayList(customerRepository.findAll()));

        // Setting visible colums according to specs
        customerGrid.setColumns("id", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");

        // Setting column order
        customerGrid.setColumnOrder("id", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");

        // Set columns hideable, reordering etc.
        customerGrid.getColumns().forEach(column -> column.setSortable(true));
        customerGrid.setColumnReorderingAllowed(true);
        customerGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open document details for editing on double click
        customerGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(CustomerForm.class, event.getItem().getId());
            }
        });

        // Inline filtering
        this.filter = new GridCellFilter(customerGrid);
        this.filter.setTextFilter("id", true, true);
        this.filter.setTextFilter("customerFirstName", true, true);
        this.filter.setTextFilter("customerLastName", true, true);
        CellFilterComponent<ComboBox<CustomerGroup>> customerGroupFilter = this.filter.setComboBoxFilter("customerGroup", CustomerGroup.class, Arrays.asList(CustomerGroup.values()));
        this.filter.setTextFilter("customerGeneralInformation", true, true);

        // Inline editor
        // customerGrid.getEditor().setEnabled(true);

        // Extra columns: delete, view documents, addDocument
        customerGrid.addComponentColumn(this::buildDeleteButton);
        customerGrid.addComponentColumn(this::buildAddDocumentButton);
        customerGrid.addComponentColumn(this::buildViewDocumentsButton);

        customerGrid.setSizeFull();

        return customerGrid;
    }

    // Helper methods
    private Button buildAddDocumentButton(Customer customer) {
        Button addDocButton = new Button(VaadinIcons.MINUS_CIRCLE);
        addDocButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        addDocButton.addClickListener(e -> Notification.show("Not yet")); //todo
        return addDocButton;
    }

    private Button buildDeleteButton(Customer customer) {
        Button deleteCButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCButton.addClickListener(e -> {

            if (customer.isUnderEditing()) {
                Notification.show("As someone is editing this customer now, I can't delete it.");
            } else {
                //Confirmation popup
                Window window = new Window("Do you really want to delete this customer?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    customerRepository.delete(customer);
                    customerGrid.setItems(Lists.newArrayList(customerRepository.findAll()));
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

        return deleteCButton;
    }

    private Button buildViewDocumentsButton(Customer customer) {
        Button viewDocButton = new Button(VaadinIcons.MINUS_CIRCLE);
        viewDocButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        viewDocButton.addClickListener(e -> Notification.show("Not yet")); //todo
        return viewDocButton;
    }

    // Build buttons row: Add, Clear filters
    public HorizontalLayout buildCustomerGridButtons() {
        HorizontalLayout buttonsHL = new HorizontalLayout();
        buttonsHL.setMargin(false);

        Button addCustomerBtn = new Button("Add customer");
        addCustomerBtn.addClickListener(e -> navigationManager.navigateTo(CustomerForm.class));
//        addCustomerBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        addCustomerBtn.setIcon(VaadinIcons.PLUS);
        addCustomerBtn.setWidth("200px");

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
//        clearAllFilters.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
//        clearAllFilters.setIcon(VaadinIcons.ERASER);
        clearAllFilters.setWidth("200px");

        Button backToDashboard = new Button("Back to dashboard");
        backToDashboard.addClickListener(e -> navigationManager.navigateTo(WelcomeView.class));
        backToDashboard.setWidth("200px");

        buttonsHL.addComponents(addCustomerBtn, clearAllFilters, backToDashboard);
        return buttonsHL;
    }

    // Put all together
    public VerticalLayout buildCustomerGridView() {
        Label label = new Label("Customer List");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        VerticalLayout customerGridViewVL = new VerticalLayout();
        customerGridViewVL.setMargin(false);
        customerGridViewVL.addComponents(label, buildCustomerGridButtons(), buildCustomerGrid());
        return customerGridViewVL;
    }

}

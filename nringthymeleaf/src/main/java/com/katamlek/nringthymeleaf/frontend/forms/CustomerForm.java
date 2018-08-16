package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CustomerGridView;
import com.katamlek.nringthymeleaf.frontend.grids.EventGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.AgentRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerDocumentRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;

import javax.xml.bind.Binder;
import javax.xml.soap.Text;

@UIScope
@SpringView
public class CustomerForm extends VerticalLayout implements View {
    private CustomerRepository customerRepository;
    private NavigationManager navigationManager;
    private AgentRepository agentRepository;
    private CustomerNoteRepository customerNoteRepository;
    private CustomerDocumentRepository customerDocumentRepository;

    public CustomerForm(CustomerRepository customerRepository, NavigationManager navigationManager, AgentRepository agentRepository, CustomerNoteRepository customerNoteRepository, CustomerDocumentRepository customerDocumentRepository) {
        this.customerRepository = customerRepository;
        this.navigationManager = navigationManager;
        this.agentRepository = agentRepository;
        this.customerNoteRepository = customerNoteRepository;
        this.customerDocumentRepository = customerDocumentRepository;

        addComponent(buildCustomerForm());
        setMargin(false);
    }

    // Window label & buttons
    private Label customerFormL = new Label("Customer Form");
    private Button saveAll;
    private Button cancelAll;
    private Button backToList;

    // Details section
    private TextField firstNameTF;
    private TextField lastNameTF;
    private TextField addressTF;
    private TextField phoneTF;
    private TextField emailTF;
    private CheckBox newsletterCB;
    private ComboBox<Agent> agentCB;
    private Grid<CustomerNote> customerNoteG;
    private Button addNoteB;
    private ComboBox<CustomerGroup> customerGroupCB;
    private TextField generalNoteTF; // i.e. customerGeneralInformation

    //Emergency contact section
    private TextField emergencyFirstNameTF;
    private TextField emergencyLastNameTF;
    private TextField emergencyPhoneTF;
    private TextField emergencyEmailTF;

    // Documents section
    private Grid<CustomerDocument> customerDocumentG;
    private Button addDocumentB;

    // History section
    private Grid<CustomerNote> historyG; // only the notes marked isHistory
    private Button addHistoryNoteB;

    // Binder
    private com.vaadin.data.Binder<Customer> customerBinder;

    public VerticalLayout buildDetailsSection() {
        VerticalLayout detailsVL = new VerticalLayout();
        detailsVL.setCaption("Customer details");
        detailsVL.setMargin(false);

        firstNameTF = new TextField("First name");
        lastNameTF = new TextField("Last name");
        HorizontalLayout namesHL = new HorizontalLayout(firstNameTF, lastNameTF);

        addressTF = new TextField("Address");
        phoneTF = new TextField("Phone");
        emailTF = new TextField("E-mail");
        HorizontalLayout contactDataHL = new HorizontalLayout(addressTF, phoneTF, emailTF);

        newsletterCB = new CheckBox("Newsletter");
        customerGroupCB = new ComboBox<>("Group");
        customerGroupCB.setItems(CustomerGroup.values());
        agentCB = new ComboBox<>("Agent");
        agentCB.setItems(Lists.newArrayList(agentRepository.findAll()));
        HorizontalLayout otherInfoHL = new HorizontalLayout(newsletterCB, customerGroupCB, agentCB);

        generalNoteTF = new TextField("General information");

        customerNoteG = new Grid<>(CustomerNote.class);

        customerNoteG.setColumns("enteredOn", "text");
        //todo set the binder and...      customerNoteG.setItems(customerNoteRepository.findCustomerNoteByCustomerAndHistoryNote(customerBinder.getBean(), false));

        customerNoteG.setColumnOrder("enteredOn", "text");

        customerNoteG.getColumns().forEach(column -> column.setSortable(true));
        customerNoteG.setColumnReorderingAllowed(true);
        customerNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // noteG.getEditor().setEnabled(true);

        // Extra button delete
        customerNoteG.addComponentColumn(this::deleteCustomerNoteButton);

        customerNoteG.setHeightByRows(4);
        customerNoteG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
        customerNoteG.setCaption("Customer notes");

        customerNoteG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Please be patient, will open the form soon.");
                // todo develop note form - see BookingNoteWindow, it shows how to do it
            }
        });


        addNoteB = new Button("Add customer note");
        addNoteB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addNoteB.setIcon(VaadinIcons.PLUS);
        addNoteB.addClickListener(e -> {
            Notification.show("Please be patient, will open the form soon.");
            //todo get the form and add the note;
        });

        detailsVL.addComponents(namesHL, contactDataHL, otherInfoHL, customerNoteG, addNoteB);

        return detailsVL;
    }

    public VerticalLayout buildEmergencyContactSection() {
        VerticalLayout emergencyVL = new VerticalLayout();
        emergencyVL.setCaption("Emergency contact");
        emergencyVL.setMargin(false);

        emergencyFirstNameTF = new TextField("First name");
        emergencyLastNameTF = new TextField("Last name");
        HorizontalLayout namesHL = new HorizontalLayout(emergencyFirstNameTF, emergencyLastNameTF);

        emergencyPhoneTF = new TextField("Phone");
        emergencyEmailTF = new TextField("E-mail");
        HorizontalLayout contactDataHL = new HorizontalLayout(emergencyPhoneTF, emergencyEmailTF);
        emergencyVL.addComponents(namesHL, contactDataHL);

        return emergencyVL;
    }

    public VerticalLayout buildDocumentsSection() {
        VerticalLayout documentsVL = new VerticalLayout();
        documentsVL.setCaption("Customer documents");
        documentsVL.setMargin(false);

        customerDocumentG = new Grid<>(CustomerDocument.class);
        customerDocumentG.setColumns("documentedEnteredOn", "user", "documentType");
        customerDocumentG.setColumnOrder("documentedEnteredOn", "user", "documentType");

//        customerDocumentG.setItems(customerDocumentRepository.findDistinctByCustomer(customerBinder.getBean()));

        customerDocumentG.getColumns().forEach(column -> column.setSortable(true));
        customerDocumentG.setColumnReorderingAllowed(true);
        customerDocumentG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // noteG.getEditor().setEnabled(true);

        // Extra button delete
        customerDocumentG.addComponentColumn(this::deleteCustomerDocumentButton);

        customerDocumentG.setHeightByRows(4);
        customerDocumentG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
      //  customerDocumentG.setCaption("Customer documents");

        customerDocumentG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Please be patient, will open the form soon.");
                // todo develop document form - see BookingNoteWindow
            }
        });

        addDocumentB = new Button("Add customer document");
        addDocumentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addDocumentB.setIcon(VaadinIcons.PLUS);
        addDocumentB.addClickListener(e -> {
            Notification.show("Please be patient, will open the form soon.");
            //todo get the form and add the note;
        });

        documentsVL.addComponents(customerDocumentG, addDocumentB);

        return documentsVL;
    }

    public VerticalLayout buildHistorySection() {
        VerticalLayout historyVL = new VerticalLayout();
        historyVL.setCaption("Customer history");
        historyVL.setMargin(false);

        historyG = new Grid<>(CustomerNote.class);
        historyG.setColumns("enteredOn", "text", "value");
        historyG.setColumnOrder("enteredOn", "text", "value");

        //todo set the binder and... IN ENTER VIEW!!!   historyG.setItems(customerNoteRepository.findCustomerNoteByCustomerAndHistoryNote(customerBinder.getBean(), true));

        historyG.getColumns().forEach(column -> column.setSortable(true));
        historyG.setColumnReorderingAllowed(true);
        historyG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // noteG.getEditor().setEnabled(true);

        // Extra button delete
        historyG.addComponentColumn(this::deleteCustomerNoteButton);

        historyG.setHeightByRows(4);
        historyG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
    //    historyG.setCaption("Customer history");

        historyG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Please be patient, will open the form soon.");
                // todo develop note form the same as for the CustomerNote
            }
        });


        addHistoryNoteB = new Button("Add customer history item");
        addHistoryNoteB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addHistoryNoteB.setIcon(VaadinIcons.PLUS);
        addHistoryNoteB.addClickListener(e -> {
            Notification.show("Please be patient, will open the form soon.");
            //todo get the form and add the note like a non-history note, just set isHistory to true on enter;
        });

        historyVL.addComponents(historyG, addHistoryNoteB);

        return historyVL;
    }

    // Put it all together
    public VerticalLayout buildCustomerForm() {
        VerticalLayout customerForm = new VerticalLayout();
        customerForm.addComponent(customerFormL);

        customerForm.addComponents(buildDetailsSection());
        customerForm.addComponents(buildEmergencyContactSection());
        customerForm.addComponents(buildDocumentsSection());
        customerForm.addComponents(buildHistorySection());

        customerBinder = new com.vaadin.data.Binder<>(Customer.class);

        // Binder
        customerBinder.bind(firstNameTF, "customerFirstName");
        customerBinder.bind(lastNameTF, "customerLastName");
        customerBinder.bind(addressTF, "customerAddress");
        customerBinder.bind(phoneTF, "customerPhoneNumber");
        customerBinder.bind(emailTF, "customerEmail");
        customerBinder.bind(newsletterCB, "customerNewsletter");
        customerBinder.bind(agentCB, "customerAgent");
        customerBinder.bind(customerGroupCB, "customerGroup");
        customerBinder.bind(generalNoteTF, "customerGeneralInformation");

        //Emergency contact section
        customerBinder.bind(emergencyFirstNameTF, "customerEmergencyFirstName");
        customerBinder.bind(emergencyLastNameTF, "customerEmergencyLastName");
        customerBinder.bind(emergencyPhoneTF, "customerEmergencyPhoneNumber");
        customerBinder.bind(emergencyEmailTF, "customerEmergcencyEmail");

        // Form buttons
        saveAll = new Button("Save");
        saveAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_BORDERLESS_COLORED);
        saveAll.setIcon(VaadinIcons.PENCIL);
        saveAll.addClickListener(e -> {
            try {
                customerRepository.save(customerBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                customerBinder.getBean().setUnderEditing(false);
                Notification.show("I saved your data.");
            }
        });

        cancelAll = new Button("Cancel");
        cancelAll.setDescription("Caution! Your data will be lost!");
        cancelAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        cancelAll.setIcon(VaadinIcons.ERASER);
        cancelAll.addClickListener(e -> {

            //Confirmation popup
            Window window = new Window("Do you really want to drop the changes this note?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Drop the form and take me back");
            yesButton.addClickListener(event1 -> {
                window.close();
                navigationManager.navigateTo(CustomerGridView.class); // todo or to the previous screen, ie Booking Form
            });

            Button noButton = new Button("Let's keep on working");
            noButton.addClickListener(event2 -> {
                window.close();
            });

            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
            confirmationVL.addComponent(buttonsLayout);

            window.setContent(confirmationVL);

            window.center();
            UI.getCurrent().addWindow(window);

        });

        backToList = new Button("Back to list");
        backToList.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        backToList.setIcon(VaadinIcons.ARROW_LEFT);
        backToList.addClickListener(e -> navigationManager.navigateTo(BookingGridView.class));

        HorizontalLayout buttonsHL = new HorizontalLayout(saveAll, cancelAll, backToList);

        addComponent(buttonsHL);

        customerForm.setMargin(false);

        return customerForm;
    }

    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String customerId = event.getParameters();
        if ("".equals(customerId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(customerId));
        }
    }

    // Called when user enters view from the list or adding a new customer
    public void enterView(Long id) {
        Customer customer;
        if (id == null) {
            // New
            customer = new Customer();

            customer.setUnderEditing(true);
            // todo more setters
        } else {
            customer = customerRepository.findById(id).get();
            if (customer.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
                navigationManager.navigateTo(CustomerGridView.class); //todo navigate to the previous view!!!
            } else {
                customer.setUnderEditing(true);
                customerDocumentG.setItems(customerDocumentRepository.findDistinctByCustomer(customerBinder.getBean()));
                if (customer == null) {
                    showNotFound();
                    return;
                }
            }
        }
        customerBinder.setBean(customer);
        firstNameTF.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Customer not found"));
    }

    // Helpers
    private Button deleteCustomerNoteButton(CustomerNote customerNote) {
        Button deleteCNButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCNButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCNButton.addClickListener(e -> {
            if (customerNote.isUnderEditing()) {
                Notification.show("Someone's working with this note. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this note?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    customerNoteRepository.delete(customerNote);
                    customerNoteG.setItems(Lists.newArrayList(customerNoteRepository.findAll()));//todo not all but by customer id
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
        return deleteCNButton;
    }

    private Button deleteCustomerDocumentButton(CustomerDocument customerDocument) {
        Button deleteCDButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCDButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCDButton.addClickListener(e -> {
            if (customerDocument.isUnderEditing()) {
                Notification.show("Someone's working with this document. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this document?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    customerDocumentRepository.delete(customerDocument);
                    customerDocumentG.setItems(Lists.newArrayList(customerDocumentRepository.findAll()));//todo not all but by customer id
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
        return deleteCDButton;
    }

}

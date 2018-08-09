package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.BookingDocument;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.BookingDocumentRepository;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringView
@UIScope
public class BookingDocumentForm extends VerticalLayout implements View {
    private BookingDocumentRepository bookingDocumentRepository;
    private Binder<BookingDocument> bookingDocumentBinder;
    private NavigationManager navigationManager;

    public BookingDocumentForm(BookingDocumentRepository bookingDocumentRepository, NavigationManager navigationManager) {
        this.bookingDocumentRepository = bookingDocumentRepository;
        this.navigationManager = navigationManager;
        setMargin(false);
        addComponent(buildBookingDocumentForm());
    }

    private DateField dateTF;
    private TextField documentDescriptionTF;
    private TextField documentNoteTF;
    private Button save;
    private Button cancel;

    public VerticalLayout buildBookingDocumentForm() {
        VerticalLayout bookingDocumentForm = new VerticalLayout();

        HorizontalLayout documentDataHL = new HorizontalLayout();

        //todo manual binding
        dateTF = new DateField("Date");
        documentDescriptionTF = new TextField("Description");
        documentNoteTF = new TextField("Notes");

        documentDataHL.addComponents(dateTF, documentDescriptionTF, documentNoteTF);


        // Save and cancel buttons
        save = new Button("Save document");
        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        save.addClickListener(e -> {
            try {
                bookingDocumentRepository.save(bookingDocumentBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                bookingDocumentBinder.getBean().setUnderEditing(false);
                Notification.show("Your data was saved");
                this.setVisible(false);
            }
        });

        cancel = new Button("Cancel");
        cancel.setDescription("Caution! Your data will be lost!");
        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        cancel.setIcon(VaadinIcons.ERASER);
        cancel.addClickListener(e -> {
            //Confirmation popup
            Window window = new Window("Do you really want to drop the changes?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Drop the form and take me back");
            yesButton.addClickListener(event1 -> {
                window.close();
                navigationManager.navigateTo(BookingGridView.class); //todo navigate to the previous screen - booking form
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

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        bookingDocumentForm.addComponents(documentDataHL, buttons);

        return bookingDocumentForm;
    }

    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String bookingDocumentId = event.getParameters();
        if ("".equals(bookingDocumentId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(bookingDocumentId));
        }
    }

    // Called when user enters view from the list or adding a new document
    public void enterView(Long id) {
        BookingDocument bookingDocument;
        if (id == null) {
            // New
            bookingDocument = new BookingDocument();

            bookingDocument.setUnderEditing(true);
            // todo more setters
        } else {
            bookingDocument = bookingDocumentRepository.findById(id).get();
            if (bookingDocument.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
            } else {
                bookingDocument.setUnderEditing(true);
                if (bookingDocument == null) {
                    showNotFound();
                    return;
                }
            }
        }
        bookingDocumentBinder.setBean(bookingDocument);
        // todo xx.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Document not found"));
    }
}

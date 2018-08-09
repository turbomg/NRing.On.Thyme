package com.katamlek.nringthymeleaf.frontend.windows;

import com.katamlek.nringthymeleaf.domain.BookingNote;
import com.katamlek.nringthymeleaf.domain.NoteStatus;
import com.katamlek.nringthymeleaf.repositories.BookingNoteRepository;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;

@SpringComponent
public class BookingNoteWindow extends Window implements View {

    private BookingNoteRepository bookingNoteRepository;

    public BookingNoteWindow(BookingNoteRepository bookingNoteRepository) {
        this.bookingNoteRepository = bookingNoteRepository;
        this.setContent(buildBookingNoteWindow());
        this.setCaption("Booking note");
        this.setClosable(true);
        this.setDraggable(true);
        this.center();
    }

    private TextField textTF;
    private ComboBox<NoteStatus> noteStatusCB;

    private Button save;
    private Button cancel;

    Binder<BookingNote> bookingNoteBinder = new Binder<>(BookingNote.class);

    private VerticalLayout buildBookingNoteWindow() {
        VerticalLayout bookingNoteForm = new VerticalLayout();
        textTF = new TextField("Note text");
        textTF.setSizeFull();
        textTF.setResponsive(true); //todo text wrap looks like crap
        noteStatusCB = new ComboBox<NoteStatus>("Note status");
        noteStatusCB.setItems(Arrays.asList(NoteStatus.values()));
        noteStatusCB.setEmptySelectionAllowed(false);

        bookingNoteBinder.forField(textTF).bind(BookingNote::getText, BookingNote::setText);
        bookingNoteBinder.forField(noteStatusCB).bind(BookingNote::getNoteStatus, BookingNote::setNoteStatus);

        save = new Button("Save note");
        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        save.addClickListener(e -> {
            try {
                bookingNoteRepository.save(bookingNoteBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                bookingNoteBinder.getBean().setUnderEditing(false);
                Notification.show("Your data was saved.");
                this.close();
            }
        });

        cancel = new Button("Cancel");
        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        cancel.addClickListener(e -> {
            this.close();
        });

        HorizontalLayout buttonsHL = new HorizontalLayout(save, cancel);

        bookingNoteForm.addComponents(noteStatusCB, textTF, buttonsHL);

        return bookingNoteForm;
    }

    // On enter
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String eventId = event.getParameters();
        if ("".equals(eventId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(eventId));
        }
    }

    // Called when user enters note from the list or adding a new note
    public void enterView(Long id) {
        BookingNote bookingNote;
        if (id == null) {
            // New
            bookingNote = new BookingNote();

            bookingNote.setNoteStatus(NoteStatus.VALID);
            bookingNote.setUnderEditing(true);
        } else {
            bookingNote = bookingNoteRepository.findById(id).get();
            if (bookingNote.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Try later."); //todo navigateTo
            } else {
                bookingNote.setUnderEditing(true);
                if (bookingNote == null) {
                    Notification.show("I don't have a note like that.");
                    return;
                }
            }
        }
        bookingNoteBinder.setBean(bookingNote);
        textTF.focus();
    }
}

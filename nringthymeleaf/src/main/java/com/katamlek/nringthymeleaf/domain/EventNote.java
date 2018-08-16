package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class EventNote extends Note {

    private boolean isInternal; // Determines if the note is an internal note

    @ManyToOne
    private Event event;

    @Override
    public String toString() {
        return "EventNote{" + "isInternal=" + isInternal + '}';
    }

    public boolean isInternal() {
        return isInternal;
    }

    public void setInternal(boolean internal) {
        isInternal = internal;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public Date getEnteredOn() {
        return super.getEnteredOn();
    }

    @Override
    public String getText() {
        return super.getText();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
    }

    @Override
    public void setEnteredOn(Date enteredOn) {
        super.setEnteredOn(enteredOn);
    }

    public void setEvent(Event event) {
        this.event = event;


    }
}

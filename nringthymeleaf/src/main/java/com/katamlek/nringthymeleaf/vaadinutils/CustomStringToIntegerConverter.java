package com.katamlek.nringthymeleaf.vaadinutils;

import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToIntegerConverter;

import java.util.Locale;

public class CustomStringToIntegerConverter extends StringToIntegerConverter {

    public CustomStringToIntegerConverter(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String convertToPresentation(Integer value, ValueContext context) {
        return value == null ? "" : this.getFormat((Locale)context.getLocale().orElse((Locale) null)).format(value);
    }
}

package com.katamlek.nringthymeleaf.vaadinutils;

import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.StringToBigDecimalConverter;

import java.math.BigDecimal;
import java.util.Locale;

public class CustomStringToBigDecimalConverter extends StringToBigDecimalConverter {

    public CustomStringToBigDecimalConverter(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String convertToPresentation(BigDecimal value, ValueContext context) {
        return value == null ? "" : this.getFormat((Locale)context.getLocale().orElse((Locale) null)).format(value);
    }
}

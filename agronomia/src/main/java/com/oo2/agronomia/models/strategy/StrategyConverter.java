package com.oo2.agronomia.models.strategy;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StrategyConverter implements AttributeConverter<PurchaseStrategy, String> {

    @Override
    public String convertToDatabaseColumn(PurchaseStrategy purchaseStrategy) {
        return purchaseStrategy.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public PurchaseStrategy convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        if (s.equals("personal")) {
            return new PersonalStrategy();
        } else if (s.equals("company")) {
            return new CompanyStrategy();
        }
        return null;
    }
}

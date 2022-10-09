package com.yuhtin.model;

/**
 * @author <a href="https://github.com/Yuhtin">Yuhtin</a>
 */
public enum ResultTypes {

    NOT_SELECTED(699, "Não selecionado"),
    NEW_OPORTUNITY(849, "Nova oportunidade"),
    SELECTED(1000, "Selecionado"),
    ;

    private final int maxValue;
    private final String message;

    ResultTypes(int maxValue, String message) {
        this.maxValue = maxValue;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ResultTypes getFromValue(double value) {
        for (ResultTypes resultTypes : values()) {
            if (resultTypes.maxValue >= value) {
                return resultTypes;
            }
        }

        return NOT_SELECTED;
    }

}

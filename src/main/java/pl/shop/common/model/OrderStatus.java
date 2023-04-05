package pl.shop.common.model;

public enum OrderStatus {
    NEW("Nowe"),
    PAID("Opłacone"),
    PROCESSING("Przetwarzane"),
    WAITING_FOR_DELIVERY("Czaeka na dostawę"),
    COMPLETED("Zrealizowane"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private String value;
    OrderStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

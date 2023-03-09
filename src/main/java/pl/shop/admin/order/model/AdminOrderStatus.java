package pl.shop.admin.order.model;

public enum AdminOrderStatus {
    NEW("Nowe"),
    PAID("Opłacone"),
    PROCESSING("Przetwarzane"),
    WAITING_FOR_DELIVERY("Czaeka na dostawę"),
    COMPLETED("Zrealizowane"),
    CANCELED("Anulowane"),
    REFUND("Zwrócone");

    private String value;
    AdminOrderStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

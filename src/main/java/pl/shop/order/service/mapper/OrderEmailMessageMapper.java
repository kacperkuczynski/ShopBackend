package pl.shop.order.service.mapper;

import pl.shop.order.model.Order;

import java.time.format.DateTimeFormatter;

public class OrderEmailMessageMapper {

    public static String createEmailMessage(Order order) {
        return "Twoje zamówienie o id: " + order.getId() +
                "\nData złożenia: " + order.getPlaceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                "\nWartość: " + order.getGrossValue() + " PLN " +
                "\n\n" +
                "\nPłatność: " + order.getPayment().getName() +
                (order.getPayment().getNote() != null ? "\n" + order.getPayment().getNote() : " ") +
                "\n\nDziękujemy za zakupy.";
    }
}

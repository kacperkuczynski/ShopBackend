package pl.shop.admin.order.service;

import pl.shop.common.model.OrderStatus;

public class AdminOrderEmailMessage {
    public static String createProcessingEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " jest przetwarzane." +
                "\nStatus został zmieniony na: " + newStatus.getValue() +
                "\nTwoje zamówienie jest przetwarzane przez naszych pracowników" +
                "\nPo skompletowaniu niezwłocznie przekażemy je do wysyłki" +
                "\n\n Pozdrawiamy"+
                "\n Sklep shop";
    }

    public static String createCompletedEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zrealizowane." +
                "\nStatus twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\n\n Dziękujemy za zakupy i zapraszamy ponownie" +
                "\n Sklep shop";
    }

    public static String createRefundEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie: " + id + " zostało zwrócone." +
                "\nStatus twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\n\n Pozdrawiamy" +
                "\n Sklep shop";
    }
}

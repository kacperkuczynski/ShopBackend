package pl.shop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shop.admin.order.model.AdminOrder;
import pl.shop.admin.order.model.AdminOrderStatus;
import pl.shop.admin.order.model.dto.AdminOrderStats;
import pl.shop.admin.order.repositor.AdminOrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminOrderStatsService {

    private final AdminOrderRepository orderRepository;

    public AdminOrderStats getStatistics() {
        LocalDateTime from = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime to = LocalDateTime.now();
        List<AdminOrder> orders = orderRepository.findAllByPlaceDateIsBetweenAndOrderStatus(
                from,
                to,
                AdminOrderStatus.COMPLETED
        );

        TreeMap<Integer, AdminOrderStatsValue> result = new TreeMap<>();
        for (int i = from.getDayOfMonth(); i < to.getDayOfMonth(); i++){
            result.put(i, aggregateValues(i, orders));
        }
        List<Long> ordersList = result.values().stream().map(v ->
                v.orders()).toList();
        List<BigDecimal> salesList = result.values().stream().map(v ->
                v.sales()).toList();

        return AdminOrderStats.builder()
                .label(result.keySet().stream().toList())
                .sale(result.values().stream().map(o -> o.sales).toList())
                .order(result.values().stream().map(o -> o.orders).toList())
                .ordersCount(ordersList.stream().reduce(Long::sum).orElse(0L))
                .salesSum(salesList.stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO))
                .build();

//        TreeMap<Integer, AdminOrderStatsValue> result =
//                IntStream.rangeClosed(from.getDayOfMonth(), to.getDayOfMonth())
//                        .boxed()
//                        .map(i -> aggregateValues(i, orders))
//                        .collect(toMap(
//                                value -> value,
//                                value -> value,
//                                (t, t2) -> {
//                                    throw new IllegalArgumentException();
//                                },
//                                TreeMap::new
//                        ));
//        return AdminOrderStats.builder()
//                .label(result.keySet().stream().toList())
//                .order(result.values().stream().map(v ->
//                        v.orders()).toList())
//                .sale(result.values().stream().map(v ->
//                        v.sales()).toList())
//                .build();


    }

    private AdminOrderStatsValue aggregateValues(Integer i, List<AdminOrder> orders) {
//        BigDecimal totalValue = BigDecimal.ZERO;
//        Long orderCount = 0L;
//        for (AdminOrder order : orders) {
//            if (i == order.getPlaceDate().getDayOfMonth()){
//                totalValue = totalValue.add(order.getGrossValue());
//                orderCount++;
//            }
//        AdminOrderStatsValue result = orders.stream()
//                .reduce(orderCount, ())
//        }
//        return new AdminOrderStatsValue(totalValue, orderCount);

        return orders.stream()
                .filter(adminOrder ->
                        adminOrder.getPlaceDate().getDayOfMonth() == i)
                .map(adminOrder -> adminOrder.getGrossValue())
                .reduce(new AdminOrderStatsValue(i, BigDecimal.ZERO, 0L),
                        (AdminOrderStatsValue o, BigDecimal v) ->
                                new AdminOrderStatsValue(i, o.sales().add(v), o.orders() + 1),
                        (o, o2) -> null
                        );
    }

    private record AdminOrderStatsValue(Integer days, BigDecimal sales, Long orders){}
}

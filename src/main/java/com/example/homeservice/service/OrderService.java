package com.example.homeservice.service;

import com.example.homeservice.enums.OrderStatus;
import com.example.homeservice.exception.NotFoundException;
import com.example.homeservice.model.Customer;
import com.example.homeservice.model.Order;
import com.example.homeservice.model.Specialist;
import com.example.homeservice.model.SubService;
import com.example.homeservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    public List<Order> findByCustomer(Customer customer){
        return orderRepository.findByCustomer(customer);
    }
    public Order save(Order order) {
//        if (orderRepository.findById(order.getId()).isPresent())
//            throw new InformationDuplicateException(
//                    order.getId() + " is duplicate"
//            );
       return orderRepository.save(order);
    }

    public void removeById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("order with id " + id + " not found")
        );
        orderRepository.delete(order);
    }

    public Order update(Order order, Long id) throws NotFoundException {
        Order foundedOrder = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("order with id " + id + " not found")
        );
        Optional.ofNullable(order.getAddress()).ifPresent(foundedOrder::setAddress);
        Optional.ofNullable(order.getDescription()).ifPresent(foundedOrder::setDescription);
        Optional.ofNullable(order.getCreatedAt()).ifPresent(foundedOrder::setCreatedAt);
        Optional.ofNullable(order.getCustomer()).ifPresent(foundedOrder::setCustomer);
        Optional.ofNullable(order.getStatus()).ifPresent(foundedOrder::setStatus);
        Optional.ofNullable(order.getSelectedSpecialist()).ifPresent(foundedOrder::setSelectedSpecialist);
        Optional.ofNullable(order.getOffers()).ifPresent(foundedOrder::setOffers);
        Optional.ofNullable(order.getSubService()).ifPresent(foundedOrder::setSubService);
        Optional.of(order.getFinalPrice()).ifPresent(foundedOrder::setFinalPrice);
        return orderRepository.save(foundedOrder);
    }

    @Transactional
    public void updateAddress(Long id, String newAddress) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.updateOrdersByAddress(newAddress, id);
        } else {
            throw new NotFoundException("order with id " + id + " not found");
        }
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderRepository.findById(id).orElse(null));
    }

    public List<Order> findAllById(Long id){
        return orderRepository.findAllById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByCustomerAndStatusIn(Customer customer, List<OrderStatus> orderStatuses){
        return orderRepository.findAllByCustomerAndStatusIn(customer, orderStatuses);
    }

    public List<Order> findAllBySubServiceAndStatus(SubService subService, OrderStatus orderStatus){
        return orderRepository.findAllBySubServiceAndStatus(subService, orderStatus);
    }
    public List<Order> findBySelectedSpecialist(Specialist specialist){
        return orderRepository.findBySelectedSpecialist(specialist);
    }
    public boolean validate(Order order) {
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Order> violation : violations) {
                System.out.println(violation.getMessage());
            }
        }
        return false;
    }
}

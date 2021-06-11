package com.libraryapp.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.*;

import com.libraryapp.services.checkout.CheckoutService;

@RestController
public class CheckoutController {

    @Inject
    CheckoutService checkoutService;

    @PostMapping("/customers/{customerId}/checkout")
    public void checkoutBooksByIdIn(
            @PathVariable Integer customerId,
            @RequestBody List<Integer> bookIds) {
         checkoutService.checkoutBooks(customerId, bookIds);
    }


    @PostMapping("/customers/{customerId}/return")
    public void returnBooksById(
            @PathVariable Integer customerId,
            @RequestBody List<Integer> bookIds) {
        checkoutService.returnBooks(customerId, bookIds);
    }

}

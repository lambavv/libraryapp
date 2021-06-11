package com.libraryapp.rest;

import com.libraryapp.domain.models.CustomerBookLinkModel;
import com.libraryapp.domain.models.CustomerModel;
import com.libraryapp.services.checkout.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerBookLinkController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping("/book/{bookId}/reservation")
    public CustomerBookLinkModel getCustomerBookLink(@PathVariable Integer bookId) {
        return checkoutService.getCustomerBookLink(bookId);
    }

    @GetMapping("/customer/{customerId}/reservation")
    public List<CustomerBookLinkModel> getCustomerBookLinks(@PathVariable Integer customerId) {
        return checkoutService.getCustomerBookLinks(customerId);
    }

    @GetMapping("/customers/book/{bookId}")
    public boolean customerBookLinkExistsByBookId(@PathVariable Integer bookId) {
        return checkoutService.existsBy(bookId);
    }
}

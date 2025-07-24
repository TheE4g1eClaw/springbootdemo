package com.solera.bootcamp.springbootdemo.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.solera.bootcamp.springbootdemo.Contracts.IService;
import com.solera.bootcamp.springbootdemo.Service.CrudForJPA;

@RestController
@RequestMapping("/API/v1/product")
public class ProductController {
    private final IService persist;
    public ProductController(CrudForJPA crudMethods) {
        this.persist = crudMethods;
    }

}

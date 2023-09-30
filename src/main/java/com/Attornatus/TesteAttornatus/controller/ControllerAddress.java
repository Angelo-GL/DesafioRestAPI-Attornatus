package com.Attornatus.TesteAttornatus.controller;

import com.Attornatus.TesteAttornatus.entities.Address;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class ControllerAddress {
    @Autowired
    private AddressService addressService;


    @PutMapping(value = "/{id}")
    public ResponseEntity<List<Address>> updateMainAddress(@PathVariable Long id, @RequestBody @Validated Person person){
        return ResponseEntity.ok().body(addressService.updateAddressMain(id, person));
    }
}

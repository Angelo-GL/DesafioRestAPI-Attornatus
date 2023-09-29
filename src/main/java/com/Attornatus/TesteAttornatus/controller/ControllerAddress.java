package com.Attornatus.TesteAttornatus.controller;

import com.Attornatus.TesteAttornatus.dto.MessageResponseDTO;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address")
public class ControllerAddress {
    @Autowired
    private AddressService addressService;


    @PutMapping(value = "/{id}")
    public MessageResponseDTO updateMainAddress(@PathVariable Long id, @RequestBody @Validated Person person){
        return addressService.updateAddressMain(id, person);

    }
}

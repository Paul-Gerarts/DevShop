package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto save(AddressDto addressDto);

    List<Address> findAll();
}

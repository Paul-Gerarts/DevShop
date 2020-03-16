package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.repositories.AddressRepository;
import be.syntra.devshop.DevshopBack.services.utilities.AddressMapperUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto save(AddressDto addressDto) {
        addressRepository.save(AddressMapperUtility.convertToAddress(addressDto));
        return addressDto;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}

package be.syntra.devshop.DevshopBack.services;

import be.syntra.devshop.DevshopBack.entities.Address;
import be.syntra.devshop.DevshopBack.models.AddressDto;
import be.syntra.devshop.DevshopBack.repositories.AddressRepository;
import be.syntra.devshop.DevshopBack.services.utilities.MapperUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;
    private MapperUtility mapperUtility;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtility mapperUtility) {
        this.addressRepository = addressRepository;
        this.mapperUtility = mapperUtility;
    }

    @Override
    public AddressDto save(AddressDto addressDto) {
        addressRepository.save(mapperUtility.convertToAddress(addressDto));
        return addressDto;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}

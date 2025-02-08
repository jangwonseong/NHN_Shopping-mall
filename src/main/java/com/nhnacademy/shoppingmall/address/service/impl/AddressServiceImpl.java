package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> getAddress(int addressId) {
        return addressRepository.findById(addressId);
    }

    @Override
    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressRepository.update(address);
    }

    @Override
    public void deleteAddress(int addressId) {
        addressRepository.deleteById(addressId);
    }
}

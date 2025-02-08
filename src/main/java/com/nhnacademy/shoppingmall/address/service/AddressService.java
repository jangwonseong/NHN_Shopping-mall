package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Optional<Address> getAddress(int addressId);
    List<Address> getAddressesByUserId(String userId);
    void saveAddress(Address address);
    void updateAddress(Address address);
    void deleteAddress(int addressId);
}

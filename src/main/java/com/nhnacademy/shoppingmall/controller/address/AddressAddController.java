package com.nhnacademy.shoppingmall.controller.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ServiceFactory;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/addAddress.do")
public class AddressAddController implements BaseController {
    private final AddressService addressService;

    public AddressAddController() {
        this.addressService = ServiceFactory.getInstance().getAddressService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login.do";
        }

        String streetAddress = req.getParameter("streetAddress");
        String postalCode = req.getParameter("postalCode");

        Address address = new Address();
        address.setUserId(user.getUserId());
        address.setStreetAddress(streetAddress);
        address.setPostalCode(postalCode);

        addressService.saveAddress(address);

        return "redirect:/mypage/address.do";
    }
}

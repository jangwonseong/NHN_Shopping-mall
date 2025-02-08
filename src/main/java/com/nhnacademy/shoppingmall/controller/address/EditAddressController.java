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

import java.util.Optional;


@RequestMapping(method = RequestMapping.Method.GET, value = "/mypage/editAddress.do")
public class EditAddressController implements BaseController {
    private final AddressService addressService;

    public EditAddressController() {
        this.addressService = ServiceFactory.getInstance().getAddressService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login.do";
        }

        int addressId = Integer.parseInt(req.getParameter("addressId"));
        Optional<Address> address = addressService.getAddress(addressId);

        if (address.isPresent()) {
            req.setAttribute("address", address.get());
            return "shop/mypage/edit_address";
        }

        return "redirect:/mypage/address.do";
    }
}

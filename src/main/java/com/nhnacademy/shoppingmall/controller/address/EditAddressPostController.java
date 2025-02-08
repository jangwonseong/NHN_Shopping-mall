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

// 주소 수정 컨트롤러
@RequestMapping(method = RequestMapping.Method.POST, value = "/mypage/editAddress.do")
public class EditAddressPostController implements BaseController {
    private final AddressService addressService;

    public EditAddressPostController() {
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
        String streetAddress = req.getParameter("streetAddress");
        String postalCode = req.getParameter("postalCode");

        Address address = new Address();
        address.setAddressId(addressId);
        address.setUserId(user.getUserId());
        address.setStreetAddress(streetAddress);
        address.setPostalCode(postalCode);

        addressService.updateAddress(address);

        return "redirect:/mypage/address.do";
    }
}
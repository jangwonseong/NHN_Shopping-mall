package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.cart.repository.CartItemRepository;
import com.nhnacademy.shoppingmall.cart.repository.impl.CartItemRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.service.CartItemService;
import com.nhnacademy.shoppingmall.cart.service.impl.CartItemServiceImpl;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.orderItem.repository.OrderItemRepository;
import com.nhnacademy.shoppingmall.orderItem.repository.impl.OrderItemRepositoryImpl;
import com.nhnacademy.shoppingmall.orderItem.service.OrderItemService;
import com.nhnacademy.shoppingmall.orderItem.service.impl.OrderItemServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserRegisterService;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserRegisterServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final AddressService addressService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CartItemService cartItemService;
    private final OrderService orderService;

    private ServiceFactory() {
        // 리포지토리 생성
        UserRepository userRepository = new UserRepositoryImpl();
        AddressRepository addressRepository = new AddressRepositoryImpl();
        ProductRepository productRepository = new ProductRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        CartItemRepository cartItemRepository = new CartItemRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderItemRepository orderItemRepository = new OrderItemRepositoryImpl();
        OrderItemService orderItemService = new OrderItemServiceImpl(orderItemRepository);

        // 서비스 생성 및 의존성 주입
        this.userRegisterService = new UserRegisterServiceImpl(userRepository);
        this.userService = new UserServiceImpl(userRepository, userRegisterService);
        this.addressService = new AddressServiceImpl(addressRepository);
        this.productService = new ProductServiceImpl(productRepository);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
        this.cartItemService = new CartItemServiceImpl(cartItemRepository, productService);
        this.orderService = new OrderServiceImpl(orderRepository, orderItemService);



        log.info("ServiceFactory initialized");
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserRegisterService getUserRegisterService() {
        return userRegisterService;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public CartItemService getCartItemService() {
        return cartItemService;
    }
    public OrderService getOrderService() {
        return orderService;
    }
}

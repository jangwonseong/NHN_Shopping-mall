@RequestMapping(method = RequestMapping.Method.POST, value = "/order/process.do")
public class ProcessOrderController implements BaseController {
    private final OrderService orderService;
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final PointService pointService;

    public ProcessOrderController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        this.orderService = serviceFactory.getOrderService();
        this.cartItemService = serviceFactory.getCartItemService();
        this.productService = serviceFactory.getProductService();
        this.pointService = serviceFactory.getPointService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartItemService.getCartItems(user.getUserId());

        try {
            // 1. 재고 및 포인트 확인
            int totalAmount = validateOrder(user, cartItems);

            // 2. 주문 생성
            String orderId = UUID.randomUUID().toString();
            List<OrderItem> orderItems = createOrderItems(orderId, cartItems);

            Order order = new Order(
                orderId,
                user.getUserId(),
                totalAmount,
                LocalDateTime.now(),
                Order.OrderStatus.COMPLETED,
                orderItems
            );

            // 3. 주문 처리 (트랜잭션 처리는 OrderService에서)
            orderService.placeOrder(order);

            // 4. 포인트 차감
            pointService.usePoints(user.getUserId(), totalAmount, orderId);

            // 5. 포인트 적립 요청 (비동기)
            RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute("requestChannel");
            requestChannel.add(new PointRequest(user.getUserId(), (int)(totalAmount * 0.1)));

            // 6. 장바구니 비우기
            clearCart(user.getUserId(), cartItems);

            return "redirect:/order/complete.do?orderId=" + orderId;
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            return "redirect:/cart/list.do";
        }
    }

    private int validateOrder(User user, List<CartItem> cartItems) {
        int totalAmount = 0;

        for (CartItem item : cartItems) {
            Product product = productService.getProduct(item.getProductId());
            if (product.getProductStock() < item.getQuantity()) {
                throw new IllegalStateException("상품 재고가 부족합니다: " + product.getProductName());
            }
            totalAmount += item.getQuantity() * product.getProductPrice();
        }

        if (user.getUserPoint() < totalAmount) {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        return totalAmount;
    }

    private List<OrderItem> createOrderItems(String orderId, List<CartItem> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            Product product = productService.getProduct(item.getProductId());
            OrderItem orderItem = new OrderItem(
                orderId,
                item.getProductId(),
                item.getQuantity(),
                product.getProductPrice()
            );
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private void clearCart(String userId, List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            cartItemService.removeFromCart(userId, item.getProductId());
        }
    }
}

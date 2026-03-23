package ch16

data class Product(val id: Long, val name: String, val price: Int, val stock: Int)

data class OrderItem(val product: Product, val quantity: Int) {
    val totalPrice: Int get() = product.price * quantity
}

data class Order(val id: Long, val items: List<OrderItem>) {
    val totalAmount: Int get() = items.sumOf { it. totalPrice }
}

interface ProductRepository {
    fun findById(id: Long): Product?
    fun updateStock(id: Long, newStock: Int): Boolean
}

class OrderService(private val productRepo: ProductRepository) {
    var orderID = 0L
    fun createdOrder(orderRequests: List<Pair<Long, Int>>): Result<Order> {
        if (orderRequests.isEmpty()) {
            return Result.failure(IllegalArgumentException("no order"))
        }
        val orderItems = orderRequests.map { (productId, quantity) ->
            val product = productRepo.findById(productId)
            if (product == null) {
                return Result.failure(NoSuchElementException("product not found for id=$productId"))
            }
            if (product.stock < quantity) {
                return Result.failure(IllegalArgumentException("out of stock id=$productId"))
            }
            if (quantity <= 0) {
                return Result.failure(IllegalArgumentException("quantity must be positive"))
            }
            val newStock = product.stock - quantity
            productRepo.updateStock(productId, newStock)

            OrderItem(product.copy(stock=newStock), quantity)
        }

        return Result.success(Order(orderID++, orderItems))
    }
}

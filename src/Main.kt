import ch10.applyDiscount
import ch10.averageOrderPriceByCategory
import ch10.orders
import ch10.totalOrderPriceByCustomer

fun main() {
    totalOrderPriceByCustomer()
    averageOrderPriceByCategory()

    val discounted = orders.applyDiscount(0.1) { it.amount >= 50000 }
    println(discounted)
}
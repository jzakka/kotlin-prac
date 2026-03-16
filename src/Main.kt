import ch10.applyDiscount
import ch10.averageOrderPriceByCategory
import ch10.orders
import ch10.totalOrderPriceByCustomer
import ch11.Repository
import ch11.User

fun main() {
    val userRepo = Repository<User>()
    userRepo.save(User(1, "Kim", 25))
    userRepo.save(User(2, "Lee", 30))
    userRepo.save(User(3, "Park", 22))

    println(userRepo.findById(1))           // User(id=1, name=Kim, age=25)
    println(userRepo.findAll().size)        // 3

    // 25세 이상 필터
    val adults = userRepo.filterBy { it.age >= 25 }
    println(adults)  // [User(1, Kim, 25), User(2, Lee, 30)]

    // 이름만 추출
    val names = userRepo.mapTo { it.name }
    println(names)  // [Kim, Lee, Park]

    userRepo.deleteById(2)
    println(userRepo.findAll().size)        // 2

}
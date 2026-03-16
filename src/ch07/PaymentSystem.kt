package ch07

class PaymentSystem {
    enum class PaymentMethod(val chargeRate: Double) {
        CREDIT_CARD(0.025), BANK_TRANSFER(0.005), CRYPTO(0.001);

        fun charge(price: Double): Double {
            return this.chargeRate * price
        }
    }

    sealed class Result {
        data class Success(val price: Double, val payment: PaymentMethod, val transactionID: Long) : Result()
        data class Failed(val message: String, val code: Int) : Result()
        data class Pending(val expectedTime: Long) : Result()

        fun handle() {
            when (this) {
                is Success -> println("$price was paid by ${payment.name}")
                is Failed -> println("[$code]: payment is failed: $message")
                is Pending -> println("loading...(expected elapse time $expectedTime seconds)")
            }
        }
    }

    object Cashier {
        var counter: Long = 0
        fun process(
            amount: Double,
//            amount: Money, // 검증 실패시 런타임 에러
            method: PaymentMethod,
        ): Result {
            if (amount <= 0) {
                return Result.Failed("Amount must be greater than zero", 240)
            }
            if (method == PaymentMethod.BANK_TRANSFER) {
                return Result.Pending(3)
            }
            return Result.Success(amount, method, counter++)
        }

    }

    @JvmInline
    value class Money(val amount: Double) {
        init {
            require(amount > 0) { "Amount must be positive" }
        }
    }
}
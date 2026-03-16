import ch07.PaymentSystem

fun main() {
    val c = PaymentSystem.Cashier

    val result = c.process(1000.0, PaymentSystem.PaymentMethod.CREDIT_CARD)
    result.handle()

    val result2 = c.process(5000.0, PaymentSystem.PaymentMethod.BANK_TRANSFER)
    result2.handle()

    val result3 = c.process(-100.0, PaymentSystem.PaymentMethod.BANK_TRANSFER)
    result3.handle()
}
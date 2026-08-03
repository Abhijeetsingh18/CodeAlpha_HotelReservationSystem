import java.util.Random;

public class PaymentSimulator {
    private static final Random RANDOM = new Random();

    public static class PaymentResult {
        public final boolean success;
        public final String message;
        public final String transactionId;

        public PaymentResult(boolean success, String message, String transactionId) {
            this.success = success;
            this.message = message;
            this.transactionId = transactionId;
        }
    }

    public static PaymentResult processPayment(String method, double amount) {
        if (amount <= 0) {
            return new PaymentResult(false, "Invalid amount", null);
        }
        boolean success = RANDOM.nextInt(100) < 95; // 95% success
        if (success) {
            String txnId = "TXN" + System.currentTimeMillis() + RANDOM.nextInt(1000);
            return new PaymentResult(true,
                    String.format("Payment of Rs.%.2f via %s successful.", amount, method), txnId);
        } else {
            return new PaymentResult(false,
                    "Payment declined by " + method + " gateway. Please try again.", null);
        }
    }


    public static PaymentResult processRefund(String originalTxnId, double amount) {
        String refundId = "RFND" + System.currentTimeMillis();
        return new PaymentResult(true,
                String.format("Refund of Rs.%.2f issued for transaction %s.", amount, originalTxnId),
                refundId);
    }
}
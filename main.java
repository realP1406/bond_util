import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {

    public static void main(String[] args) {

        List<Integer> payMonths = new ArrayList<>();

        payMonths.add(1);
        payMonths.add(4);
        payMonths.add(10);

        Bonds testBond = new Bonds(
            "Bond A",
            LocalDate.of(2030, 12, 31),
            15,
            payMonths,
            false
        );

        System.out.println(testBond.getNextPaymentDate());
    }
}

class Bonds {

    private String bondName;
    private LocalDate maturityDate;
    private int payDay;
    private List<Integer> payMonths = new ArrayList<>();
    private Boolean isCumulative;

    public Bonds(
        String bondName,
        LocalDate maturityDate,
        int payDay,
        List<Integer> payMonths,
        Boolean isCumulative
    ) {
        this.bondName = bondName;
        this.maturityDate = maturityDate;
        this.payDay = payDay;
        this.payMonths = payMonths;
        this.isCumulative = isCumulative;
    }

    public LocalDate getNextPaymentDate() {

        LocalDate today = LocalDate.now();
        LocalDate nextPaymentDate = null;

        if (isCumulative) {
            return maturityDate;
        } else {

            for (int month : payMonths) {

                LocalDate paymentDate =
                    LocalDate.of(today.getYear(), month, payDay);

                if (paymentDate.isAfter(today)) {
                    nextPaymentDate = paymentDate;
                    break;
                }
            }

            if (nextPaymentDate == null) {

                for (int month : payMonths) {

                    LocalDate paymentDate =
                        LocalDate.of(today.getYear() + 1, month, payDay);

                    if (paymentDate.isAfter(today)) {
                        nextPaymentDate = paymentDate;
                        break;
                    }
                }
            }
        }

        return nextPaymentDate;
    }
}
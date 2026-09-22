import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class main {

    public static void main(String[] args) {

        List<Integer> payMonths = new ArrayList<>();

        payMonths.add(1);
        payMonths.add(3);
        payMonths.add(4);
        payMonths.add(5);
        payMonths.add(6);
        payMonths.add(7);
        payMonths.add(8);
        payMonths.add(9);
        payMonths.add(10);
        payMonths.add(11);
        payMonths.add(12);

        Bonds testBond = new Bonds(
            "Bond A",
            LocalDate.of(2030, 12, 31),
            29,
            payMonths,
            false,
            1000,
            9.75
        );

        System.out.println("Next Payment Date: " + testBond.getNextPaymentDate());
        System.out.println("Last Payment Date: " + testBond.getLastPayDate());
        System.out.println("Next Payment Amount: " + testBond.getNextPaymentAmount());
    }
}

class Bonds {

    private String bondName;
    private LocalDate maturityDate;
    private int payDay;
    private List<Integer> payMonths = new ArrayList<>();
    private Boolean isCumulative;
    private int faceValue;
    private double couponRate;

    public Bonds(
        String bondName,
        LocalDate maturityDate,
        int payDay,
        List<Integer> payMonths,
        Boolean isCumulative,
        int faceValue,
        double couponRate
    ) {
        this.bondName = bondName;
        this.maturityDate = maturityDate;
        this.payDay = payDay;
        this.payMonths = payMonths;
        this.isCumulative = isCumulative;
        this.faceValue = faceValue;
        this.couponRate = couponRate;
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

    public LocalDate getLastPayDate() {
        
        LocalDate today = LocalDate.now();
        LocalDate lastPaymentDate = null;

        if (isCumulative) {
            return maturityDate;
        } else {

            for (int i = payMonths.size() - 1; i >= 0; i--) {

                int month = payMonths.get(i);
                LocalDate paymentDate =
                    LocalDate.of(today.getYear(), month, payDay);

                if (paymentDate.isBefore(today)) {
                    lastPaymentDate = paymentDate;
                    break;
                }
            }

            if (lastPaymentDate == null) {

                for (int i = payMonths.size() - 1; i >= 0; i--) {

                    int month = payMonths.get(i);
                    LocalDate paymentDate =
                        LocalDate.of(today.getYear() - 1, month, payDay);

                    if (paymentDate.isBefore(today)) {
                        lastPaymentDate = paymentDate;
                        break;
                    }
                }
            }
        }

        return lastPaymentDate;
    }

    public Double getNextPaymentAmount() {
        LocalDate nextPaymentDate = getNextPaymentDate();
        LocalDate lastPaymentDate = getLastPayDate();
        int diffInDays = (int) java.time.temporal.ChronoUnit.DAYS.between(lastPaymentDate, nextPaymentDate);
        return (faceValue * (couponRate) * diffInDays / 365.0);
    }
}
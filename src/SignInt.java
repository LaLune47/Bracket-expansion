import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInt {
    private final BigInteger value;
    private final int index;

    //constructor
    //public SignInt(int index, BigInteger value) {
    //    this.index = index;
    //    this.value = value;
    //}

    public SignInt(String input) { //"[+|-]000000", "[+|-]00123", "[+|-]00123(*+-...)，保证输入的input长这样
        int thisIndex = 0;
        String signIntPattern = "([+-]?[0-9]+).*";
        Pattern patternSignInt = Pattern.compile(signIntPattern);
        Matcher matcherSignInt = patternSignInt.matcher(input);
        if (matcherSignInt.matches()) {
            String signInt = matcherSignInt.group(1);
            boolean isPositive = true;
            String noSignInt;
            if (signInt.charAt(0) == '+' || signInt.charAt(0) == '-') {
                noSignInt = signInt.substring(1);
                if (signInt.charAt(0) == '-') {
                    isPositive = false;
                }
            } else {
                noSignInt = signInt;
            }
            thisIndex += signInt.length();
            noSignInt = noSignInt.replaceAll("^(0+)", "");
            if (noSignInt.equals("")) {
                noSignInt = "0";
            }
            String num = noSignInt;
            if (!isPositive) {
                num = "-" + num;
            }
            this.value = new BigInteger(num);
            this.index = thisIndex;
        } else {
            System.out.println("error in SignInt Constructor");
            this.value = BigInteger.ONE;
            this.index = thisIndex;
        }
    }

    //method
    public BigInteger getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}

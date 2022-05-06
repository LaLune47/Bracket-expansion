import java.math.BigInteger;

public class Power extends Factor {
    private BigInteger exp;//幂函数Power没有正负的field,只有指数值
    //private int index;//指向下一个未parse的index

    //constructor
    public Power(BigInteger exp) { //只提供指数exp，构造x^exp的Power
        super(0);
        this.exp = exp;
    }

    public Power(String input) { //保证input[0] == 'x'
        int thisIndex = 0;
        String normalPowerPattern = "(x\\*\\*)([+-]?[0-9]+).*";
        //Pattern patternNormalPower = Pattern.compile(normalPowerPattern);
        //Matcher matcherNormalPower = patternNormalPower.matcher(input);
        if (input.matches(normalPowerPattern)) {
            thisIndex += 3;
            SignInt num = new SignInt(input.substring(3));
            thisIndex += num.getIndex();
            this.exp = num.getValue();
            this.setIndex(thisIndex);
        } else if (input.charAt(0) == 'x') { //说明input == "x"
            thisIndex++;
            this.exp = BigInteger.ONE;
            this.setIndex(thisIndex);
        } else {
            System.out.println("error in power's constructor:missing 'x'");
        }
    }

    //method
    public BigInteger getExp() {
        return exp;
    }

    public Power deleteBracket() {
        return this;
    }

    public Power clone() {
        BigInteger exp = this.exp;
        return new Power(exp);
    }

    public String printFactor() {
        String res;
        if (exp.equals(BigInteger.ONE)) {
            res = "x";
        } else if (exp.equals(BigInteger.ZERO)) {
            res = "1";
        } else {
            res = "x**" + exp;
        }
        return res;
    }
    
    @Override
    public Poly toPoly() {
        Term term = new Term();
        term.addFactor(this);
        Poly poly = new Poly();
        poly.addTerm(term);
        return poly;
    }
}

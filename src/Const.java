import java.math.BigInteger;

public class Const extends Factor {
    private final BigInteger value;
    //private int index;

    //constructor
    public Const(BigInteger value) {
        super(0);
        this.value = value;
    }

    public Const(String input) {
        int thisIndex = 0;//parse SignInt
        SignInt num = new SignInt(input);
        thisIndex += num.getIndex();
        this.value = num.getValue();
        this.setIndex(thisIndex);
    }

    // method
    public BigInteger getValue() {
        return value;
    }

    public Const deleteBracket() {
        return this;
    }

    public Const clone() {
        return this;
    }

    public String printFactor() {
        if (value.equals(BigInteger.ZERO)) {
            return "0";
        }
        return value.toString();
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

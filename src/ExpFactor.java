import java.math.BigInteger;
import java.util.ArrayList;

public class ExpFactor extends Factor {
    private Poly poly;  //不一定含括号的
    private BigInteger exp;
    //private int index;

    //constructor
    public ExpFactor() {
    }

    // for clone and deleteBracket
    public ExpFactor(Poly poly, BigInteger exp) {
        super(0);
        this.poly = poly;
        this.exp = exp;
    }

    public ExpFactor(String input, ArrayList<SelfFunc> funcList) { //可以保证input的string一定长(表达式)这样
        int thisIndex = 0;
        if (input.charAt(0) != '(') {
            System.out.println("error in expFactor : missing (");
        }
        thisIndex++;
        Poly subPoly = new Poly(input.substring(thisIndex),funcList);
        this.poly = subPoly.deleteBracket();                          //嵌套括号的解决
        //System.out.println(subPoly.printPoly());
        thisIndex += subPoly.getIndex();
        if (input.charAt(thisIndex) != ')') {
            System.out.println("error in expFactor : missing )");
        }
        thisIndex++;
        if (thisIndex + 1 < input.length()) {
            if (input.charAt(thisIndex) == '*' && input.charAt(thisIndex + 1) == '*') {
                // 处理指数
                thisIndex += 2; //skip **
                SignInt num = new SignInt(input.substring(thisIndex));
                thisIndex += num.getIndex();
                this.exp = num.getValue();
            } else { //无指数
                this.exp = BigInteger.ONE;
            }
        } else { //无指数
            this.exp = BigInteger.ONE;
        }
        this.setIndex(thisIndex);
    }

    //method
    public Poly getPoly() {
        return poly;
    }
    
    public ExpFactor deleteBracket() {
        int exp = this.exp.intValue();
        Poly expr = this.poly.clone();
        if (exp == 0) {
            Const one = new Const(BigInteger.ONE);
            Poly temp = one.toPoly();
            return new ExpFactor(temp,BigInteger.ONE);
        }
        for (int i = 0; i < exp - 1; i++) {
            expr = expr.multiplyPoly(poly);
        }
        //System.out.println(res.printFactor());
        return new ExpFactor(expr, BigInteger.ONE);
    }

    public ExpFactor clone() {
        Poly clonePoly = poly.clone();
        return new ExpFactor(clonePoly, this.exp);
    }

    public String printFactor() {
        String res;
        res = "(" + poly.printPoly() + ")";
        if (!exp.equals(BigInteger.ONE)) {
            res = "(" + res + ")" + "**" + exp;
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

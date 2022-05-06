import java.math.BigInteger;
import java.util.ArrayList;

public class Trig extends Factor {
    private final int type;         //sin = 0，cos = 1
    private final BigInteger exp;   //外层指数
    private final Factor inner;     //内部的Factor,直接用Factor来存
    
    //构造器
    public Trig(int type,BigInteger exp,Factor inner) {
        this.type = type;
        this.exp = exp;
        this.inner = inner;
    }
    
    public Trig(String input, ArrayList<SelfFunc> funcList) { //保证input[0] == 'c'||'s'
        int thisIndex = 0;
        
        if (input.charAt(0) == 's') {  //sin
            this.type = 0;
        } else {
            this.type = 1;
        }
        thisIndex += 4;
    
        FactorFactory facFactory = new FactorFactory(funcList);//new Factor使用工厂模式
        inner = facFactory.factorFactory(input.substring(4));
        thisIndex += this.inner.getIndex();
        thisIndex++;  //括号
    
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
        this.setIndex(thisIndex);   //Factor，因子解析时
    }
    
    @Override
    public Factor deleteBracket() {
        if (exp.equals(BigInteger.ZERO)) {
            return new Const(BigInteger.ONE);
        } else {
            int type = this.type;
            BigInteger exp = this.exp;
            Factor inner = this.inner;
            return new Trig(type,exp,inner.deleteBracket());
        }
    }
    
    @Override
    public Trig clone() {
        return new Trig(this.type,this.exp,this.inner);
    }
    
    @Override
    public String printFactor() {
        String res;
        if (type == 0) {
            //if (inner instanceof ExpFactor) {
            //    res = "sin" + inner.deleteBracket().printFactor();
            //} else {
            //    res = "sin(" + inner.deleteBracket().printFactor() + ")";
            //}
            res = "sin(" + inner.deleteBracket().printFactor() + ")";
        } else {
            //if (inner instanceof ExpFactor) {
            //    res = "cos" + inner.deleteBracket().printFactor();
            //} else {
            //    res = "cos(" + inner.deleteBracket().printFactor() + ")";
            //}
            res = "cos(" + inner.deleteBracket().printFactor() + ")";
        }
        
        if (!exp.equals(BigInteger.ONE)) {
            res = res +  "**" + exp;
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

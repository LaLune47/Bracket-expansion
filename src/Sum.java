import java.math.BigInteger;
import java.util.ArrayList;

public class Sum extends Factor implements Func {
    // sum(i, s, e, t)
    // 本次作业中循环变量只能是字符 i
    //private Const s;   //下限
    //private Const e;   //上限
    private final Poly poly;  //  ()+()+()的形式
    private String expression;
    //private Factor t;   // 求和 因子（按照形式化定义，就是所有因子）
    //按照最开始的想法存储求和因子t就是不行，因为含i的式子不能被读成Factor

    //构造器
    private Sum(Poly poly) {
        this.poly = poly;
    }
    
    public Sum(String input, ArrayList<SelfFunc> funcList) {
        int thisIndex = 0;
        thisIndex += 6;  // "sum(i,"
    
        SignInt num1 = new SignInt(input.substring(thisIndex));
        thisIndex += num1.getIndex() + 1; //","
    
        SignInt num2 = new SignInt(input.substring(thisIndex));
        thisIndex += num2.getIndex() + 1; //读到了t之前的","
        
        if (num1.getValue().compareTo(num2.getValue()) > 0) {   // 求和下限大于上限
            Const value = new Const(BigInteger.ZERO);
            this.poly = value.toPoly();
        } else {
            // 得到求和因子t  sum(i, s, e, t)
            String [] temp = input.split(",");
            String newInput = temp[3];       // newInput 是t加上一堆多余的东西
    
            int bracketNum = 1;  //左括号比右括号多的值
            int pos;
            for (pos = 0;pos < newInput.length();pos++) {
                if (newInput.charAt(pos) == '(') {
                    bracketNum++;
                } else if (newInput.charAt(pos) == ')') {
                    bracketNum--;
                }
                if (pos != 0 && bracketNum == 0) {
                    break;  //此时pos就是sum的右括号
                }
            }
            newInput = newInput.substring(0,pos);
    
            Re re = new Re(funcList);
            newInput = re.replaceFunc(newInput);
            ////////////////////////////////////////////////////////////////////////////
            thisIndex += newInput.length() + 1; //t的长度加括号
            this.setIndex(thisIndex);
            
            BigInteger cut = num2.getValue().add(num1.getValue().negate());
            
            Poly transit = new Poly();   //过渡
            StringBuilder expression = new StringBuilder();
            if (!newInput.contains("i")) {
                expression = new StringBuilder("(" + newInput + ")" + "*"
                        + (cut.add(BigInteger.ONE)));
                transit = new Poly(expression.toString(),funcList);
            }
            else {
                for ( ;cut.compareTo(BigInteger.ZERO) >= 0;cut = cut.add(BigInteger.ONE.negate())) {
                    String index = String.valueOf(num2.getValue().add(cut.negate()));
    
                    String tempInput = newInput.replace("i","(" + index + ")");
                    tempInput = tempInput.replace("s" + "(" + index + ")" + "n","sin");
        
                    if (tempInput.charAt(0) != '(') {
                        tempInput = "(" + tempInput + ")";
                    }
                    expression.append(tempInput);
                    ExpFactor expFactor = new ExpFactor(tempInput,funcList);
                    if (cut.compareTo(BigInteger.ZERO) != 0) {
                        expression.append("+");
                    }
                    Term oneFactor = new Term(expFactor);
                    transit.addTerm(oneFactor);
                }
            }
            expression = new StringBuilder("(" + expression + ")");
            this.expression = expression.toString();
            this.poly = transit;
        }
    }
    
    // 函数替换成 poly,sum函数的展开
    //public Poly replace() {
    //}
    // 这个函数直接内嵌到 构造器里面吧, 因为i的缘故
    
    @Override
    public ExpFactor deleteBracket() {
        Poly finalPoly = this.poly.deleteBracket();  // 求和式去括号
        return new ExpFactor(finalPoly,BigInteger.ONE);
    }

    @Override
    public Sum clone() {
        return new Sum(this.poly);
    }

    @Override
    public String printFactor() {
        return poly.deleteBracket().printPoly();
    }

    @Override
    public Poly toPoly() {
        return this.poly.deleteBracket();
    }
    
    @Override
    public String getExpression() {
        return this.expression;
    }
}

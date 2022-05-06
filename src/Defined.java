import java.util.ArrayList;

public class Defined extends Factor implements Func { // 自定义函数
    private final ExpFactor expFactor;
    private String expression;  // 用来生成表达式因子的最终替换成果，用于嵌套
    
    public Defined(ExpFactor expFactor) {
        this.expFactor = expFactor;
    }
    
    public Defined(String input, ArrayList<SelfFunc> funcList) {
        int thisIndex = 2;  // "f("
        
        String name = input.substring(0,1);
        SelfFunc func = null;   // 被调用的函数
        for (SelfFunc item: funcList) {
            if (item.getName().equals(name)) {
                func = item;
                break;
            }
        }
        
        int bracketNum = 0;  //左括号比右括号多的值
        String [] actual = {"","",""};       // 扫描一次的过程同时获得实参
        int pos;
        int number = 0;  //形参个数
        StringBuilder argument = new StringBuilder();
        for (pos = 1;pos < input.length();pos++) {
            if (input.charAt(pos) == '(') {
                bracketNum++;
            } else if (input.charAt(pos) == ')') {
                bracketNum--;
                if (bracketNum == 0) {  // 最后一个形参
                    actual[number] = argument.toString();
                    number++;
                }
            }

            if (bracketNum == 1 && input.charAt(pos) == ',') {
                actual[number] = argument.toString();
                argument = new StringBuilder();
                number++;
                continue;
            }
            if (pos != 1) {
                argument.append(input.charAt(pos));
            }
            
            if (pos != 1 && bracketNum == 0) {
                break;  //此时pos就是f（形参）的右括号
            }
        }
        String inner = input.substring(2,pos);
        thisIndex += inner.length();
        thisIndex++;    // ")"
        this.setIndex(thisIndex);
    
        String expression = func.getExpression();   //现在是代换前的函数体
        
        // 对func,替换实参之前先进行一次字符串转化   +  代换
        Re re = new Re(funcList);
        for (int i = 0;i < number;i++) {
            actual[i] = re.replaceFunc(actual[i]);
            expression = expression.replace(func.getVar().get(i),"(" + actual[i] + ")");
        }
        
        expression = "(" + expression + ")";
        
        this.expression = expression;
        this.expFactor = new ExpFactor(expression,funcList).deleteBracket();
    }
    
    @Override
    public ExpFactor deleteBracket() {
        return this.expFactor.deleteBracket();
    }

    @Override
    public Defined clone() {
        return new Defined(this.expFactor);
    }

    @Override
    public String printFactor() {
        return this.expFactor.deleteBracket().printFactor();
    }
    
    @Override
    public Poly toPoly() {
        Term term = new Term();
        term.addFactor(this);
        Poly poly = new Poly();
        poly.addTerm(term);
        return poly;
    }
    
    @Override
    public String getExpression() {
        return this.expression;
    }
}

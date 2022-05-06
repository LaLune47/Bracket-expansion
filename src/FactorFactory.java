import java.util.ArrayList;

public class FactorFactory {
    private final ArrayList<SelfFunc> funcList;
    
    public FactorFactory(ArrayList<SelfFunc> funcList) {
        this.funcList = funcList;
    }
    
    public Factor factorFactory(String input) {
        //从input中得到type
        String type = "";
        if (input.charAt(0) == '(') {
            type = "expFactor";//表达式因子
        } else if (input.charAt(0) == '+' || input.charAt(0) == '-' ||
                (input.charAt(0) >= '0' && input.charAt(0) <= '9')) {
            type = "Const";//常数因子
        } else if (input.charAt(0) == 'x') {
            type = "Power";//幂函数
        } else if (input.charAt(0) == 's' && input.charAt(1) == 'u') {
            type = "Sum";//求和函数
        } else if (input.charAt(0) == 'f' || input.charAt(0) == 'g' || input.charAt(0) == 'h') {
            type = "Defined";
        } else if (input.charAt(0) == 's' || input.charAt(0) == 'c') {
            type = "Trig";
        }
        
        switch (type) {
            case "Const":
                return new Const(input);
            case "expFactor":
                return new ExpFactor(input,funcList);
            case "Power":
                return new Power(input);
            case "Sum":
                return new Sum(input,funcList);
            case "Defined":
                return new Defined(input,funcList);
            case "Trig":
                return new Trig(input,funcList);
            default:
                return new Const("0");
        }
    }
}

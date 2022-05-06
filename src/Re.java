import java.util.ArrayList;

public class Re {
    private final ArrayList<SelfFunc> funcList;
    
    public Re(ArrayList<SelfFunc> funcList) {
        this.funcList = funcList;
    }
    
    public String replaceFunc(String input) {
        FactorFactory facFactory = new FactorFactory(funcList);
        String out;
        
        if ("fgh".indexOf(input.charAt(0)) != -1 || input.contains("sum")) {
            Factor factor = facFactory.factorFactory(input);
            Func toReplace = (Func) factor;
            out = toReplace.getExpression();
        } else {
            out = input;
        }
        return out;
    }
}

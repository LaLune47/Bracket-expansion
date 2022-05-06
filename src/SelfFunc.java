import java.util.ArrayList;

public class SelfFunc { //自定义函数
    private final String name;  //函数名
    private final ArrayList<String> var;  //变量表
    private final String expression;  //函数表达式
    
    public SelfFunc(String input) {
        //f(x,y,z)=x+y**2+z**3
        String str = input.replaceAll("\\s*", "");
        name = str.substring(0,1);

        String [] temp = str.split("=");
        String test =  temp[0].replaceAll("[fgh]","");
        test = test.replace("(","");
        test = test.replace(")","");
        test = test.replace(",","");
        ArrayList<String> tran = new ArrayList<>();
        for (int i = 0;i < test.length();i++) {
            tran.add(test.substring(i,i + 1));
        }
        var = tran;
        
        expression = temp[1];
    }

    public String getName() {
        return this.name;
    }
    
    //public int getFuncNum() {
    //    return var.size();
    //}
    
    public ArrayList<String> getVar() {
        return var;
    }
    
    public String getExpression() {
        return expression;
    }
}

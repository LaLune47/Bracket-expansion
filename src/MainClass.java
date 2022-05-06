import com.oocourse.spec3.ExprInput;
import com.oocourse.spec3.ExprInputMode;
import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        // 实例化一个ExprInput类型的对象scanner
        // 由于是一般读入模式，所以我们实例化时传递的参数为ExprInputMode.NormalMode
        ExprInput scanner = new ExprInput(ExprInputMode.NormalMode);
    
        // 获取自定义函数个数
        int cnt = scanner.getCount();
    
        // 读入并存储自定义函数
        ArrayList<SelfFunc> funcList = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            String input = scanner.readLine();
            SelfFunc func = new SelfFunc(input);
            funcList.add(func);
        }
    
        // 读入最后一行表达式
        String testOrigin = scanner.readLine();
    
        // 表达式括号展开相关的逻辑
        String test = testOrigin.replaceAll("\\s*", "");   // 直接排除所有空白项影响
        test = test.replaceAll("(\\+-\\+)|(-\\+\\+)|(\\+\\+-)","-");
        test = test.replaceAll("(--\\+)|(-\\+-)|(\\+--)","+");
        test = test.replaceAll("(--)|(\\+\\+)","+");
        test = test.replaceAll("(\\+-)|(-\\+)","-");
        
        Poly polynomial = new Poly(test,funcList); //   内嵌了，自定义函数的代换
    
        Poly resPoly = polynomial.deleteBracket();
        
        String res = resPoly.printPoly();  // todo 各种输出优化，合并 1     // todo 合并同类型优化
        System.out.println(res);
    }
}
import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private final ArrayList<Factor> factorList = new ArrayList<>();
    private int index;//记录parse该Term后，parse到了input string的index。指向下一个未parse的字符

    //constructor
    public Term() {
        this.index = 0;
    }

    // only one factor
    public Term(Factor factor) {
        factorList.add(factor);
        index = 0;
    }

    //public Term(int index, ArrayList<Factor> factorList) {
    //    this.index = index;
    //    this.factorList = factorList;
    //}

    //method
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    public int getFacNum() {
        return this.factorList.size();
    }

    public static Term parseTerm(String input,ArrayList<SelfFunc> funcList) {
        int thisIndex = 0;//指向下一个未parse的字符
        Term res = new Term();//初始化？
        FactorFactory facFactory = new FactorFactory(funcList);//new Factor使用工厂模式
        Factor abbreviatedFactor;//第一个因子若为const，被省略了
        // 项的特殊形式：出现在第一个因子中，要给FactorList补Const。、
        if (input.charAt(0) == '+') {
            //abbreviatedFactor = new Const(BigInteger.ONE);
            //res.addFactor(abbreviatedFactor);
            thisIndex++;
        } else if (input.charAt(0) == '-') {
            BigInteger mone = BigInteger.valueOf(-1);
            abbreviatedFactor = new Const(mone);
            res.addFactor(abbreviatedFactor);
            thisIndex++;
        }
        Factor firstFactor;
        firstFactor = facFactory.factorFactory(input.substring(thisIndex));
        res.addFactor(firstFactor);
        thisIndex += firstFactor.getIndex();    // getIndex有问题，往下读的时候没有加
        while (thisIndex < input.length()) { //该Term不止一项
            if (input.charAt(thisIndex) == '*') {
                thisIndex++;
                Factor nextFactor = facFactory.factorFactory(input.substring(thisIndex));
                res.addFactor(nextFactor);
                thisIndex += nextFactor.getIndex();
            } else { //parseTerm 完成
                break;
            }
        }
        res.setIndex(thisIndex);   //你的指数
        return res;
    }

    // 项的展开，重点在这
    // 遍历每一项
    public Poly deleteBracket() {
        Poly res = new Poly();
        for (Factor fitem : this.factorList) {
            Factor cleanFactor = fitem.deleteBracket(); // 展开因子
            if (res.getNumTerm() == 0) {
                res = new Poly(cleanFactor);//
            } else {
                if (cleanFactor instanceof ExpFactor) {
                    res = res.multiplyPoly(((ExpFactor) cleanFactor).getPoly());
                } else {
                    Poly nextPoly = new Poly(cleanFactor);
                    res = res.multiplyPoly(nextPoly);
                }
            }
        }
        return res;
    }

    public void addFactor(Factor newFactor) {
        this.factorList.add(newFactor);
    }

    public ArrayList<Factor> getFactorList() {
        return factorList;
    }

    public Term clone() {
        Term cloneTerm = new Term();
        cloneTerm.setIndex(0);
        for (Factor factor : factorList) {
            Factor cloneFactor = factor.clone();
            cloneTerm.addFactor(cloneFactor);
        }
        return cloneTerm;
    }

    // term1*term2
    public Term multiplyTerm(Term other) {
        Term res = this.clone();
        for (Factor factor : other.getFactorList()) {
            res.addFactor(factor);
        }
        return res;
    }

    // 打印结果
    public String printTerm() {
        StringBuilder res = new StringBuilder();
        Term optTerm = this.mergeTerm();
        //Term optTerm = this;
        for (int i = 0; i < optTerm.factorList.size(); i++) {
            Factor temp = optTerm.factorList.get(i);
            if (i == 0) {
                if (temp.printFactor().equals("1") && optTerm.factorList.size() > 1) {
                    //等于1，直接跳过
                } else if (temp.printFactor().equals("-1") && optTerm.factorList.size() > 1) {
                    res.append("-");
                } else {
                    res.append(temp.printFactor());
                }
            } else {
                if (optTerm.factorList.get(0).printFactor().equals("1")
                        || optTerm.factorList.get(0).printFactor().equals("-1")) {
                    res.append(temp.printFactor());
                } else {
                    res.append("*").append(temp.printFactor());
                }
            }
        }
        return res.toString();
    }

    // 在拆了括号后的Term
    public Term mergeTerm() {
        Term newTerm = new Term();                 //化简后将项放这里
        BigInteger constValue = BigInteger.ONE;    // 系数
        BigInteger powerExp = BigInteger.ZERO;     // 幂函数指数

        for (Factor fitem : factorList) {
            if (fitem instanceof Const) {
                BigInteger fitemValue = ((Const) fitem).getValue();
                constValue = constValue.multiply(fitemValue);
            } else if (fitem instanceof Power) {
                BigInteger fitemExp = ((Power) fitem).getExp();
                powerExp = powerExp.add(fitemExp);
            } else {
                newTerm.addFactor(fitem.deleteBracket());          // 表达式项,还有其他项  ， 无法合并，直接乘进去
            }
        }
        
        if (constValue.equals(BigInteger.ZERO)) {         // 系数为不为0
            Term specialTerm = new Term();
            Const tempConst = new Const(BigInteger.ZERO);
            specialTerm.addFactor(tempConst);
            return specialTerm;          // 提前结束
        }
    
        if (!powerExp.equals(BigInteger.ZERO)) {      //如果指数不为零，加入合并的power项，否则不加入
            Power tempPower = new Power(powerExp);
            newTerm.addFactor(tempPower);
        } //先把所有非系数项弄掉
        
        if (!(constValue.equals(BigInteger.ONE) && newTerm.getFacNum() != 0)) {
            Const tempConst = new Const(constValue);
            newTerm.addFactor(tempConst);
        }
        return newTerm;
    }
}

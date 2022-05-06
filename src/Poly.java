import java.math.BigInteger;
import java.util.ArrayList;

@SuppressWarnings("checkstyle:EmptyLineSeparator")
public class Poly {
    private ArrayList<Term> termList = new ArrayList<>();//我的原来思路是用hashmap，为了方便记录每个Term的指数
    private int index;//指向下一个未parse的字符

    //constructor
    public Poly() {
    }

    // only one power or const factor, not ExpFactor
    public Poly(Factor factor) {
        if (factor instanceof ExpFactor) {
            this.termList = ((ExpFactor) factor).getPoly().clone().getTermList();
        } else {
            Term term = new Term(factor);
            termList.add(term);
        }
        index = 0;
    }

    public Poly(String input,ArrayList<SelfFunc> funcList) {
        boolean firstTermOp = true;
        int index = 0;//指向下一个未parse的字符
        if (input.charAt(0) == '+') {
            firstTermOp = true;
            index++;
        } else if (input.charAt(0) == '-') {
            firstTermOp = false;
            index++;
        }
        Term firstTerm = Term.parseTerm(input.substring(index),funcList);
        if (!firstTermOp) {
            Const addFirstTerm = new Const(BigInteger.valueOf(-1));
            firstTerm.addFactor(addFirstTerm);
        }
        index += firstTerm.getIndex();
        this.addTerm(firstTerm);
        while (index < input.length()) {
            if (input.charAt(index) == '+' || input.charAt(index) == '-') {
                boolean nextTermOp = input.charAt(index) == '+';
                index++;
                Term nextTerm = Term.parseTerm(input.substring(index),funcList);
                if (!nextTermOp) {
                    Const addNextTerm = new Const(BigInteger.valueOf(-1));
                    nextTerm.addFactor(addNextTerm);
                }
                index += nextTerm.getIndex();
                this.addTerm(nextTerm);
            } else { //parsePoly 完成
                break;
            }
        }
        this.index = index;
    }

    //method
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = 0;
    }

    public ArrayList<Term> getTermList() {
        return termList;
    }
    
    public Poly deleteBracket() {
        Poly res = new Poly();
        for (Term titem : this.termList) {
            Poly newPoly = titem.deleteBracket();//消除term的括号后会返回一个子poly
            for (Term item2 : newPoly.getTermList()) {
                res.addTerm(item2);
            }
        }
        res.setIndex(0);
        return res;
    }

    public Poly multiplyPoly(Poly other) {
        Poly res = new Poly();
        for (int i = 0; i < this.getNumTerm(); i++) {
            Term termi = this.termList.get(i).clone();
            //System.out.println("termi : " + termi.printTerm());
            for (int j = 0; j < other.getNumTerm(); j++) {
                Term termj = other.termList.get(j).clone();
                //System.out.println("termj : " + termj.printTerm());
                Term newTerm = termi.multiplyTerm(termj);
                //System.out.println("newTerm : " + newTerm.printTerm());
                res.addTerm(newTerm);
            }
        }
        return res;
    }

    public void addTerm(Term newTerm) {
        this.termList.add(newTerm);
    }

    public int getNumTerm() {
        return this.termList.size();
    }

    public Poly clone() {
        Poly clonePoly = new Poly();
        clonePoly.setIndex(0);
        for (Term cloneTerm : this.termList) {
            clonePoly.addTerm(cloneTerm);
        }
        return clonePoly;
    }
    
    //把自己this print 出来
    public String printPoly() {
        StringBuilder res = new StringBuilder();
        boolean isFirstTermToPrint = true;
        for (Term temp : termList) {
            if (isFirstTermToPrint) {
                res.append(temp.printTerm());
                isFirstTermToPrint = false;
            } else {
                String resNextTerm = temp.printTerm();
                if (!resNextTerm.equals("")) {
                    if (resNextTerm.charAt(0) == '+' || resNextTerm.charAt(0) == '-') {
                        res.append(resNextTerm);
                    } else {
                        res.append("+").append(resNextTerm);
                    }
                }
            }
        }
        return res.toString();
    }

}

public abstract class Factor {
    //工厂模式
    private int index;//指向下一个未parse的index

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //constructor
    public Factor() {
        this.index = 0;
    }

    public Factor(int index) {
        this.index = index;
    }

    //abstract method
    public abstract Factor deleteBracket();

    public abstract Factor clone();

    public abstract String printFactor();
    
    public abstract Poly toPoly();

}

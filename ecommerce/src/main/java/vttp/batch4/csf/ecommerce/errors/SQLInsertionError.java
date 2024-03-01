package vttp.batch4.csf.ecommerce.errors;

public class SQLInsertionError extends Exception {
    public SQLInsertionError(){
        super();
    }

    public SQLInsertionError(String msg){
        super(msg);
    }
}

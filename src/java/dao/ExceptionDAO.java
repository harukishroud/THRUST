/*
    SGR ALPHA - DAO PACKAGE
    File: EXCEPTIONDAO.JAVA | Last Major Update: 30.04.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package dao;

public class ExceptionDAO extends Exception {
    
    public ExceptionDAO(){}
    
    public ExceptionDAO(String arg){
        super(arg);
    }
    public ExceptionDAO(Throwable arg) {
        super(arg);
    }
    public ExceptionDAO(String arg, Throwable arg1){
        super(arg, arg1);
    }
    
    
}

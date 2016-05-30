package br.com.senacrs.consultorio.dao.impl_BD;
/**
 *
 * @author lhries
 */
public class BDException extends RuntimeException {

    public BDException(String s) {
        super(s);
    }

    public BDException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public BDException(Throwable throwable) {
        super(throwable);
    }
}

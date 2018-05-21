package logic.utils;

/**
 * Interface for implementing the visitor pattern.
 *
 * @author Diego Ortego Prieto
 */
public interface Visitable {

    void accept(Visitor visitor);

}

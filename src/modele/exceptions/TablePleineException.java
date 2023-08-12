package modele.exceptions;

public class TablePleineException extends Exception{
    public TablePleineException()
    {
        super();
    }

    public TablePleineException(String message)
    {
        super(message);
    }
}

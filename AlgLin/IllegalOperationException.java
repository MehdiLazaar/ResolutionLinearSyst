package AlgLin;

public class IllegalOperationException extends Exception {
	
	public IllegalOperationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Erreur : "+getMessage();
    }
    


}

package AlgLin;

public class IrregularSysLinException extends Exception {
		
			public IrregularSysLinException(String message) {
		        super(message);
		    }

		    @Override
		    public String toString() {
		        return "Erreur : Le système est irrégulier lors de sa résolution. Message d'erreur : " + getMessage();
		    }

}

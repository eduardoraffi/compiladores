package compilador;

import java.util.Vector;

import compilador.Errors.ErrorType;

public class SymbolTable {
	Vector<Symbol> mSymbolVector;

	public SymbolTable() {
		mSymbolVector = new Vector<Symbol>();
	}

	public int getSize() {
		return mSymbolVector.size();
	}

	public void addSymbol(Symbol simbolo) throws Exception {
		if (simbolo == null)
			Errors.generalError(ErrorType.INVALID_SYMBOL);

		mSymbolVector.add(simbolo);
	}

	public Symbol getSymbol(int pos) throws Exception {
		if (pos < 0 || pos >= mSymbolVector.size())
			Errors.generalError(pos, ErrorType.INVALID_SYMBOL_POSITION);

		return mSymbolVector.get(pos);
	}

	public Symbol getSymbol(Token token) throws Exception {
		Token aux;
		
		for (int i = (mSymbolVector.size() - 1); i >= 0; i--) {
			aux = mSymbolVector.get(i).getToken();
			if (aux.getLexem().equals(token.getLexem()) && aux.getSymbol().equals(token.getSymbol())) {
				return mSymbolVector.get(i);
			}
		}

		Errors.generalError(token.getLine(), ErrorType.MISSING_SYMBOL);
		return null;
	}

	public Symbol getSymbol(Symbol symbol) throws Exception {
		
		for (int i = mSymbolVector.size() - 1; i >= 0; i--) {
			if (mSymbolVector.get(i).equals(symbol)) {
				return mSymbolVector.get(i);
			}
		}

		Errors.generalError(symbol.getToken().getLine(), ErrorType.MISSING_SYMBOL);
		return null;
	}

	public void deleteSymbol(int pos) throws Exception {
		if (pos < 0 || pos >= mSymbolVector.size())
			Errors.generalError(pos, ErrorType.INVALID_SYMBOL_POSITION);

		mSymbolVector.remove(pos);
	}

	public void deleteSymbol(Token token) throws Exception {
		Token aux;
		
		for (int i = (mSymbolVector.size() - 1); i >= 0; i--) {
			aux = mSymbolVector.get(i).getToken();
			if (aux.getLexem().equals(token.getLexem()) && aux.getSymbol().equals(token.getSymbol())) {
				mSymbolVector.remove(i);
			}
		}
		Errors.generalError(token.getLine(), ErrorType.MISSING_SYMBOL);
	}

	public void deleteSymbol(Symbol symbol) throws Exception {
		Symbol aux;
		for (int i = (mSymbolVector.size() - 1); i >= 0; i--) {
			aux = mSymbolVector.get(i);
			if (aux.getToken().getLexem().equals(symbol.getToken().getLexem())
					&& aux.getToken().getSymbol().equals(symbol.getToken().getSymbol())) {
				mSymbolVector.remove(i);
			}
		}

		Errors.generalError(symbol.getToken().getLine(), ErrorType.MISSING_SYMBOL);
	}

	public boolean checkIfSymbolExists(Symbol symbol) {
		for (int i = 0; i < mSymbolVector.size(); i++) {
			if (mSymbolVector.get(i).equals(symbol))
				return true;
		}
		return false;
	}

	public boolean checkIfRotinaExists(Symbol symbol) {
		@SuppressWarnings("unused")
		GenericType genericType;
		int level;
		int i = (mSymbolVector.size() - 1);
		do {
			genericType = mSymbolVector.get(i).getType();
			level = mSymbolVector.get(i).getLevel();
			if (mSymbolVector.get(i).equals(symbol))
				return true;

			i--;
		} while (level <= symbol.getLevel() && i >= 0);
		return false;
	}
}

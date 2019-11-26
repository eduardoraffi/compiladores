package compilador;

import compilador.Errors.ErrorType;

public class Symbol {
	private GenericType mGenericType;
	private Token mToken;
	private int mLevel;

	public Symbol(GenericType genericType, Token token) throws Exception {
		if (genericType == null)
			Errors.generalError(ErrorType.INVALID_TYPE);

		if (token == null)
			Errors.generalError(ErrorType.INVALID_TOKEN);

		this.mGenericType = genericType;
		this.mToken = token;
	}

	public Symbol(Token token) throws Exception {
		if (token == null)
			Errors.generalError(ErrorType.INVALID_TOKEN);

		this.mGenericType = null;
		this.mToken = token;
	}

	public void setLevel(int actualLevel) throws Exception {
		if (actualLevel < 0)
			throw new Exception("Nivel invalido");

		mLevel = actualLevel;
	}

	public int getLevel() {
		return mLevel;
	}

	public Token getToken() {
		return mToken;
	}

	public void setType(GenericType genericType) throws Exception {
		if (genericType == null)
			throw new Exception("Tipo do simbolo invalido");

		mGenericType = genericType;
	}

	public GenericType getType() {
		return mGenericType;
	}

	@Override
	public boolean equals(Object genericObject) {
		if (genericObject.getClass() == this.getClass()) {
			Symbol symbol = (Symbol) genericObject;
			if (symbol.getToken().getSymbol().equals(this.mToken.getSymbol())
					&& symbol.getToken().getLexem().equals(this.mToken.getLexem())) {
				return true;
			}
		}
		return false;
	}
}
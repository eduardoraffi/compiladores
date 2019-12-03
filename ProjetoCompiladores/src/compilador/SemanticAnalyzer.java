package compilador;

import compilador.Errors.ErrorType;

public class SemanticAnalyzer {
	private SymbolTable mSymbolTable;
	private VerificaExpressao mVerificaExpressao;
	private int mLabel = 0;
	private int mStackPosition = 1;
	private int mLevel = 0;

	public SemanticAnalyzer() {
		mSymbolTable = new SymbolTable();
		mVerificaExpressao = new VerificaExpressao();
	}

	public int getNivel() {
		return mLevel;
	}

	public int getLabel() {
		int i = mLabel;
		mLabel++;
		return i;
	}

	public boolean pesquisaDuplicVarTabela(Token token) throws Exception {
		Symbol symbol = new Symbol(token);
		symbol.setLevel(mLevel);
		return mSymbolTable.checkIfRotinaExists(symbol);
	}
	
	public boolean pesquisaDeclVarTabela(Token token) throws Exception {
		Symbol symbol = new Symbol(token);
		if (mSymbolTable.checkIfSymbolExists(symbol)) {
			GenericType genericType = mSymbolTable.getSymbol(symbol).getType();
			if (genericType.getClass() == DeclaredVar.class)
				return true;
		}
		return false;
	}
	
	public boolean pesquisaDeclVarFunc(Token token) throws Exception {
		Symbol symbol = new Symbol(token);
		if (mSymbolTable.checkIfSymbolExists(symbol)) {
			GenericType type = mSymbolTable.getSymbol(symbol).getType();
			if (!type.getType().equals(Constants.L_PROCEDIMENTO))
				return true;
		}
		return false;
	}
	
	public boolean pesquisaDeclProc(Token token) throws Exception {
		Symbol symbol = new Symbol(token);
		return mSymbolTable.checkIfSymbolExists(symbol);
	}
	
	public void insertInSymbolTable(Symbol symbol) throws Exception {
		symbol.setLevel(mLevel);
		if (symbol.getType() != null) {
			switch (symbol.getType().getType()) {
			case Constants.L_PROCEDIMENTO:
				symbol.getType().setInfo(mLabel);
				mLabel++;
			case Constants.L_PROGRAMA:
				mLevel++;
				break;
			default:
				Errors.semanticError(symbol.getToken().getLine(), ErrorType.INVALID_TYPE);
			}
		}
		mSymbolTable.addSymbol(symbol);
	}

	public void insertGenericTypeInSymbolTable(GenericType genericType) throws Exception {//Alloc variaveis declaradas
		int i = mSymbolTable.getSize() - 1;
		int posInit = mStackPosition;
		int alloc = 0;
		while (i > 0 && mSymbolTable.getSymbol(i).getType() == null) {
			GenericType type = new DeclaredVar(genericType.getType(), mStackPosition);
			mSymbolTable.getSymbol(i).setType(type);
			mStackPosition++;
			alloc++;
			i--;
		}
		CodeGenerator.getInstance().generateCommand(Constants.CG_ALLOC, posInit, alloc);
	}

	public void insertFuncTypeInSymbolTable(String type) throws Exception {
		int i = mSymbolTable.getSize() - 1;
		if (mSymbolTable.getSymbol(i).getType() != null)
			Errors.generalError(ErrorType.TYPE_ALREADY_PRESENT);

		mSymbolTable.getSymbol(i).setType(new Rotina(type, mLabel));
		mLabel++;
		mLevel++;
	}

	public GenericType getSymbolType(Token token) throws Exception {
		Symbol symbol = new Symbol(token);
		return mSymbolTable.getSymbol(symbol).getType();
	}

	public void finalizaEscopo() throws Exception {
		Symbol aux;
		int count = mSymbolTable.getSize() - 1;
		int dealloc = 0;
		while (count >= 0) {
			aux = mSymbolTable.getSymbol(count);
			if (aux != null) {
				if (aux.getType().getClass() == DeclaredVar.class) {
					dealloc++;
				}
				if (mLevel > aux.getLevel()) {
					break;
				}
			}
			mSymbolTable.deleteSymbol(count);
			count--;
		}
		mStackPosition = mStackPosition - dealloc;
		CodeGenerator.getInstance().generateCommand(Constants.CG_DALLOC, mStackPosition, dealloc);
		mLevel--;
	}

	//Verifica expressão
	public void comecaExpressao() throws Exception {
		mVerificaExpressao.comecaExpressao();
	}

	public boolean booleanExp() {
		return mVerificaExpressao.getIfExpressionIsBoolean();
	}

	public void adicionaOperadorNaExpressao(Token token) throws Exception {
		mVerificaExpressao.adicionaOperadorNaExpressao(token);
	}

	public void adicionaInvNaExpressao(Token token) throws Exception {
		Token tokenAux = new Token(Constants.S_INVERTER, token.getLexem(), token.getLine());
		mVerificaExpressao.adicionaOperadorNaExpressao(tokenAux);
	}

	public void adicionaFatorNaExpressao(Token token) throws Exception {
		if (token.getSymbolId() == Constants.IDENTIFICADOR) {
			Symbol symbol = mSymbolTable.getSymbol(token);
			mVerificaExpressao.adicionaFatorNaExpressao(symbol);
		} else {
			mVerificaExpressao.adicionaFatorNaExpressao(token);
		}
	}
	
	public void terminaExpressao() throws Exception {
		mVerificaExpressao.terminaExpressao();
	}	
}
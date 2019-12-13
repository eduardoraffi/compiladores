package compilador;

import java.util.Vector;

import compilador.Errors.ErrorType;

public class VerificaExpressao {

	@SuppressWarnings("rawtypes")
	private Vector<PosFixa> mPosFixa;
	private Vector<Token> mStack;
	private CodeGenerator mCodeGenerator;

	private boolean isBooleanExpression = false;
	private boolean isFunctionReturn;

	public VerificaExpressao(CodeGenerator codeGenerator) {
		mCodeGenerator = codeGenerator;
		mPosFixa = null;
		mStack = null;
	}

	private void geraExp() throws Exception {
		int pos = 0;
		while (pos < mPosFixa.size()) {
			if (mPosFixa.get(pos).getClass() == ExpOp.class) {
				ExpOp op = (ExpOp) mPosFixa.get(pos);
				switch (op.getExp().getSymbolId()) {
				case Constants.INVERTER:
					mCodeGenerator.generateCommand(Constants.CG_INV);
					break;
				case Constants.MAIOR:
					mCodeGenerator.generateCommand(Constants.CG_CMA);
					break;
				case Constants.MAIORIG:
					mCodeGenerator.generateCommand(Constants.CG_CMAQ);
					break;
				case Constants.IG:
					mCodeGenerator.generateCommand(Constants.CG_CEQ);
					break;
				case Constants.MENOR:
					mCodeGenerator.generateCommand(Constants.CG_CME);
					break;
				case Constants.MENORIG:
					mCodeGenerator.generateCommand(Constants.CG_CMEQ);
					break;
				case Constants.DIF:
					mCodeGenerator.generateCommand(Constants.CG_CDIF);
					break;
				case Constants.MAIS:
					mCodeGenerator.generateCommand(Constants.CG_ADD);
					break;
				case Constants.MENOS:
					mCodeGenerator.generateCommand(Constants.CG_SUB);
					break;
				case Constants.MULT:
					mCodeGenerator.generateCommand(Constants.CG_MULT);
					break;
				case Constants.DIV:
					mCodeGenerator.generateCommand(Constants.CG_DIVI);
					break;
				case Constants.E:
					mCodeGenerator.generateCommand(Constants.CG_AND);
					break;
				case Constants.OU:
					mCodeGenerator.generateCommand(Constants.CG_OR);
					break;
				case Constants.NAO:
					mCodeGenerator.generateCommand(Constants.CG_NEG);
					break;
				default:
					Errors.generalError(ErrorType.GENERATE_EXPRESSION);
					break;
				}
			} else if (mPosFixa.get(pos).getClass() == ExpNum.class) {
				ExpNum num = (ExpNum) mPosFixa.get(pos);
				if (num.getExp().getSymbolId() == Constants.NUMERO) {
					mCodeGenerator.generateCommand(Constants.CG_LDC, num.getExp().getLexem());
				} else {
					if (num.getExp().getSymbolId() == Constants.VERDADEIRO) {
						mCodeGenerator.generateCommand(Constants.CG_LDC, 1);
					} else {
						mCodeGenerator.generateCommand(Constants.CG_LDC, 0);
					}
				}
			} else {
				ExpSimb sim = (ExpSimb) mPosFixa.get(pos);
				if (sim.getExp().getType().getClass() == Rotina.class) {
					mCodeGenerator.generateCommand(Constants.CG_CALL,
							Constants.CG_LABEL + "" + sim.getExp().getType().getInfo());
					mCodeGenerator.generateCommand(Constants.CG_LDV, 0);
				} else {
					if(!isFunctionReturn)
					mCodeGenerator.generateCommand(Constants.CG_LDV, sim.getExp().getType().getInfo());
				}
			}
			pos++;
		}
		isBooleanExpression = expType();
	}

	private boolean expType() throws Exception {
		Vector<Integer> tipo = new Vector<Integer>();
		int pos = 0;
		while (pos < mPosFixa.size()) {
			if (mPosFixa.get(pos).getClass() == ExpOp.class) {
				ExpOp op = (ExpOp) mPosFixa.get(pos);
				int tipoPos = tipo.size() - 1;
				switch (op.getExp().getSymbolId()) {
				case Constants.INVERTER:
					if (tipo.get(tipoPos) != 0) {
						Errors.generalError(ErrorType.EXPRESSION);
					}
					break;

				case Constants.MAIOR:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.MAIORIG:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.IG:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.MENOR:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.MENORIG:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.DIF:
					checkType(tipo, tipoPos, 2);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.MAIS:
					checkType(tipo, tipoPos, 1);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 0);
					break;

				case Constants.MENOS:
					checkType(tipo, tipoPos, 1);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 0);

					break;

				case Constants.MULT:
					checkType(tipo, tipoPos, 1);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 0);

					break;

				case Constants.DIV:
					checkType(tipo, tipoPos, 1);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 0);

					break;

				case Constants.E:
					checkType(tipo, tipoPos, 0);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);
					break;

				case Constants.OU:
					checkType(tipo, tipoPos, 0);
					tipo.remove(tipoPos);
					tipoPos--;
					tipo.set(tipoPos, 1);

					break;

				case Constants.NAO:
					if (tipo.get(tipoPos) == 0) {
						Errors.generalError(ErrorType.EXPRESSION);
					}
				}
			} else // number or identifier
			{
				if (mPosFixa.get(pos).getClass() == ExpNum.class) {
					ExpNum num = (ExpNum) mPosFixa.get(pos);
					if (num.getExp().getSymbolId() == Constants.NUMERO) {
						tipo.add(0);// numbers are int -> add int expression
					} else {
						tipo.add(1);
					}
				} else {
					ExpSimb sim = (ExpSimb) mPosFixa.get(pos);
					if (sim.getExp().getType().getType().equals(Constants.L_INTEIRO)
							|| sim.getExp().getType().getType().equals(Constants.L_FUNC_INT)) {
						tipo.add(0);
					} else {
						tipo.add(1);
					}
				}
			}
			pos++;
		}
		if (tipo.get(0) == 0)
			return false;

		return true;
	}

	void checkType(Vector<Integer> tipo, int tipoPos, int validator) throws Exception {
		if (validator == 2)// Check if different types
		{
			if ((tipo.get(tipoPos) == 0 && tipo.get(tipoPos - 1) == 1)
					|| (tipo.get(tipoPos) == 1 && tipo.get(tipoPos - 1) == 0)) {
				Errors.generalError(ErrorType.EXPRESSION);
			}
			return;
		}
		if (validator == 1) // Check if both are type boolean (for arithmetic, only int types are allowed
		{
			if (tipo.get(tipoPos) == 1 || tipo.get(tipoPos - 1) == 1) {
				Errors.generalError(ErrorType.EXPRESSION);
			}
			return;
		}
		if (validator == 0) // Check if both are type int (and, or, not must be boolean expressions)
		{
			if (tipo.get(tipoPos) == 0 || tipo.get(tipoPos - 1) == 0) {
				Errors.generalError(ErrorType.EXPRESSION);
			}
			return;
		}
	}

	@SuppressWarnings("rawtypes")
	public void comecaExpressao() throws Exception {
		if (mPosFixa != null)
			throw new Exception("Starting an expression without finishing another one.");

		mPosFixa = new Vector<PosFixa>();
		mStack = new Vector<Token>();
	}

	public void adicionaFatorNaExpressao(Symbol symbol) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adding in expression without starting another one");

		mPosFixa.add(new ExpSimb(symbol));
	}

	public void adicionaFatorNaExpressao(Token token) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adding in expression without starting another one");

		mPosFixa.add(new ExpNum(token));
	}

	public void adicionaOperadorNaExpressao(Token token) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adding in expression without starting another one");

		if (token.getSymbolId() == Constants.ABRE_PARENTESES) {
			mStack.add(token);
		} else {
			if (token.getSymbolId() == Constants.FECHA_PARENTESES) {
				int posPilha = mStack.size() - 1;
				Token aux = mStack.get(posPilha);
				while (mStack.get(posPilha).getSymbolId() != Constants.ABRE_PARENTESES) {
					aux = mStack.remove(posPilha);
					mPosFixa.add(new ExpOp(aux));
					posPilha--;
					if (posPilha < 0)
						Errors.generalError(token.getLine(), ErrorType.MISSING_CLOSING_PARENTHESES);
				}
				mStack.remove(posPilha);
			} else {
				int posPilha = mStack.size() - 1;
				while (!isToAddOperador(token)) {
					mPosFixa.add(new ExpOp(mStack.remove(posPilha)));
				}
				mStack.add(token);
			}
		}
	}

	private boolean isToAddOperador(Token token) throws Exception {
		if (mStack == null)
			return false;
		if (mStack.size() == 0) {
			return true;
		} else {
			int stackPosition = (mStack.size() - 1);

			return (priorityOrder(token.getSymbolId()) > priorityOrder(mStack.get(stackPosition).getSymbolId()) ? true
					: false);
		}
	}

	private int priorityOrder(int i) throws Exception {
		if (i == Constants.NAO || i == Constants.INVERTER)
			return 7;

		if (i == Constants.MULT || i == Constants.DIV)
			return 6;

		if (i == Constants.MAIS || i == Constants.MENOS)
			return 5;

		if (i == Constants.MAIOR || i == Constants.MAIORIG || i == Constants.MENOR || i == Constants.MENORIG)
			return 4;

		if (i == Constants.IG || i == Constants.DIF)
			return 3;

		if (i == Constants.E)
			return 2;

		if (i == Constants.OU)
			return 1;

		if (i == Constants.ABRE_PARENTESES || i == Constants.FECHA_PARENTESES)
			return 0;

		throw new Exception("priorityOrder(): " + i + "Invalid symbol");
	}

	public void terminaExpressao(boolean isFunctionReturn) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adding in expression without starting another one");

		this.isFunctionReturn = isFunctionReturn;
		
		int i = mStack.size() - 1;
		while (i >= 0) {
			mPosFixa.add(new ExpOp(mStack.remove(i)));
			i--;
		}
		geraExp();
		mPosFixa = null;
	}

	public boolean getIfExpressionIsBoolean() {
		return isBooleanExpression;
	}

	// Classes to aux posFixa formation
	private interface PosFixa<Generic> {
		public Generic getExp();

		public void setExp(Generic genericType);
	}

	private class ExpSimb implements PosFixa<Symbol> {

		private Symbol mSymbol;

		public ExpSimb(Symbol symbol) {
			setExp(symbol);
		}

		@Override
		public Symbol getExp() {
			return mSymbol;
		}

		@Override
		public void setExp(Symbol symbol) {
			mSymbol = symbol;
		}
	}

	private class ExpOp implements PosFixa<Token> {

		private Token mToken;

		public ExpOp(Token token) {
			setExp(token);
		}

		public Token getExp() {
			return mToken;
		}

		public void setExp(Token token) {
			mToken = token;
		}
	}

	private class ExpNum implements PosFixa<Token> {

		private Token mToken;

		public ExpNum(Token token) {
			setExp(token);
		}

		public Token getExp() {
			return mToken;
		}

		public void setExp(Token token) {
			mToken = token;
		}
	}
}

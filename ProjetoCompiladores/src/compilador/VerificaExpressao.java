package compilador;

import java.util.Vector;

import compilador.Errors.ErrorType;

public class VerificaExpressao {

	private interface vetorExp<Generic> {
		public Generic getExp();

		public void setExp(Generic t);
	}

	private class ExpSimb implements vetorExp<Symbol> {

		private Symbol simbolo;

		public ExpSimb(Symbol s) {
			setExp(s);
		}

		@Override
		public Symbol getExp() {
			return simbolo;
		}

		@Override
		public void setExp(Symbol t) {
			simbolo = t;
		}
	}

	private class ExpOp implements vetorExp<Token> {

		private Token token;

		public ExpOp(Token t) {
			setExp(t);
		}

		public Token getExp() {
			return token;
		}

		public void setExp(Token t) {
			token = t;
		}
	}

	private class ExpNum implements vetorExp<Token> {

		private Token token;

		public ExpNum(Token t) {
			setExp(t);
		}

		public Token getExp() {
			return token;
		}

		public void setExp(Token t) {
			token = t;
		}
	}

	// Pós fixa
	private Vector<vetorExp> mPosFixa;
	// Pilha de operadores
	private Vector<Token> mStack;

	private boolean isBooleanExpression = false;

	public VerificaExpressao() {
		mPosFixa = null;
	}

	private boolean isToAddOperador(Token token) throws Exception {
		if (mStack == null)
			return false;
		if (mStack.size() == 0) {
			return true;
		} else {
			int pilhaPos = (mStack.size() - 1);
			return precedencia(token.getSymbolId(), mStack.get(pilhaPos).getSymbolId());
		}
	}

	private boolean precedencia(int fator, int pilha) throws Exception {
		if (codigoDePrecedencia(fator) > codigoDePrecedencia(pilha))
			return true;

		return false;
	}

	private int codigoDePrecedencia(int i) throws Exception {
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

		throw new Exception("codigo inválido");
	}

	private void geraExp() throws Exception {
		int pos = 0;
		while (pos < mPosFixa.size()) {
			if (mPosFixa.get(pos).getClass() == ExpOp.class) {
				ExpOp op = (ExpOp) mPosFixa.get(pos);
				switch (op.getExp().getSymbolId()) {
				case Constants.INVERTER:
					CodeGenerator.getInstance().generateCommand(Constants.CG_INV);
					break;
				case Constants.MAIOR:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CMA);
					break;
				case Constants.MAIORIG:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CMAQ);
					break;
				case Constants.IG:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CEQ);
					break;
				case Constants.MENOR:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CME);
					break;
				case Constants.MENORIG:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CMEQ);
					break;
				case Constants.DIF:
					CodeGenerator.getInstance().generateCommand(Constants.CG_CDIF);
					break;
				case Constants.MAIS:
					CodeGenerator.getInstance().generateCommand(Constants.CG_ADD);
					break;
				case Constants.MENOS:
					CodeGenerator.getInstance().generateCommand(Constants.CG_SUB);
					break;
				case Constants.MULT:
					CodeGenerator.getInstance().generateCommand(Constants.CG_MULT);
					break;
				case Constants.DIV:
					CodeGenerator.getInstance().generateCommand(Constants.CG_DIVI);
					break;
				case Constants.E:
					CodeGenerator.getInstance().generateCommand(Constants.CG_AND);
					break;
				case Constants.OU:
					CodeGenerator.getInstance().generateCommand(Constants.CG_OR);
					break;
				case Constants.NAO:
					CodeGenerator.getInstance().generateCommand(Constants.CG_NEG);
					break;
				default:
					Errors.generalError(ErrorType.GENERATE_EXPRESSION);
					;
				}
			} else {
				if (mPosFixa.get(pos).getClass() == ExpNum.class) {
					ExpNum num = (ExpNum) mPosFixa.get(pos);
					if (num.getExp().getSymbolId() == Constants.NUMERO) {
						CodeGenerator.getInstance().generateCommand(Constants.CG_LDC, num.getExp().getLexem());
					} else {
						if (num.getExp().getSymbolId() == Constants.VERDADEIRO) {
							CodeGenerator.getInstance().generateCommand(Constants.CG_LDC, 1);
						} else {
							CodeGenerator.getInstance().generateCommand(Constants.CG_LDC, 0);
						}
					}
				} else {
					ExpSimb sim = (ExpSimb) mPosFixa.get(pos);
					if (sim.getExp().getType().getClass() == Rotina.class) {
						CodeGenerator.getInstance().generateCommand(Constants.CG_CALL,
								Constants.CG_LABEL + "" + sim.getExp().getType().getInfo());
						CodeGenerator.getInstance().generateCommand(Constants.CG_LDV, 0);
					} else {
						CodeGenerator.getInstance().generateCommand(Constants.CG_LDV, sim.getExp().getType().getInfo());
					}
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

	public void comecaExpressao() throws Exception {
		if (mPosFixa != null)
			throw new Exception("começando uma expressao sem terminar uma");

		mPosFixa = new Vector<vetorExp>();
		mStack = new Vector<Token>();
	}

	public void adicionaFatorNaExpressao(Symbol symbol) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adicionando na expressao sem começar uma");

		mPosFixa.add(new ExpSimb(symbol));
	}

	public void adicionaFatorNaExpressao(Token token) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adicionando na expressao sem começar uma");

		mPosFixa.add(new ExpNum(token));
	}

	public void adicionaOperadorNaExpressao(Token token) throws Exception {
		if (mPosFixa == null)
			throw new Exception("adicionando na expressao sem começar uma");

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
						throw new Exception("Fecha parentesis nao encontrado. Expresao invalida");
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

	public void terminaExpressao() throws Exception {
		if (mPosFixa == null)
			throw new Exception("terminando uma expressao sem começar uma");

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
}

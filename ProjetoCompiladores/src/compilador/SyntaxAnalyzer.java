package compilador;

import java.util.Vector;
import compilador.Constants;
import compilador.Errors.ErrorType;

public class SyntaxAnalyzer {
	private Token mToken;
	private Vector<Token> mTokenVector;

	private int actualVectorIndex = 0;
	private LexicalAnalyzer mLexicalAnalyzer;
	private SemanticAnalyzer mSemanticAnalyzer;
	private boolean isFunctionAttribuition = false;
	private CodeGenerator mCodeGenerator;

	public SyntaxAnalyzer(String filePath) throws Exception {
		mLexicalAnalyzer = new LexicalAnalyzer(filePath);
		mCodeGenerator = new CodeGenerator();
		mTokenVector = mLexicalAnalyzer.pegaTokens();
		mToken = mTokenVector.get(actualVectorIndex);
		mSemanticAnalyzer = new SemanticAnalyzer(mCodeGenerator);

		System.out.println(
				"Simbolo: " + mToken.getSymbol() + " Lexema: " + mToken.getLexem() + "  linha: " + mToken.getLine());

		if (mToken.getSymbolId() == Constants.PROGRAMA) {
			getNextToken();
			mCodeGenerator.generateCommand(Constants.CG_START);

			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				mSemanticAnalyzer.insertInSymbolTable(new Symbol(new Rotina(Constants.L_PROGRAMA), mToken));
				getNextToken();

				if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
					analisaBloco(null);

					if (mToken.getSymbolId() == Constants.PONTO) {
						mSemanticAnalyzer.finalizaEscopo(false);
						mCodeGenerator.generateCommand(Constants.CG_HALT);
						System.out.println("Sucesso");

					} else
						Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_DOT);
				} else
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
			} else
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
		} else
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_PROGRAM_IDENTIFIER);
	}

	private void getNextToken() throws Exception {
		if (actualVectorIndex <= mTokenVector.size()) {
			actualVectorIndex++;
			mToken = mTokenVector.get(actualVectorIndex);
			System.out.println("Simbolo: " + mToken.getSymbol() + " Lexema: " + mToken.getLexem() + "  linha: "
					+ mToken.getLine());
		} else
			Errors.semanticError(mToken.getLine(), ErrorType.TOKEN_VECTOR_END);
	}

	private void analisaBloco(Token token) throws Exception {
		getNextToken();
		analisaEtVariaveis();
		analisaSubRotinas();
		analisaComandos(token);
	}

	private void analisaEtVariaveis() throws Exception {
		if (mToken.getSymbolId() == Constants.VAR) {
			getNextToken();
			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				while (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
					analisaVariaveis();
					if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
						getNextToken();
					} else {
						Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
					}
				}
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
			}
		}
	}

	private void analisaVariaveis() throws Exception {
		do {
			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				mSemanticAnalyzer.insertInSymbolTable(new Symbol(mToken));
				getNextToken();

				if ((mToken.getSymbolId() == Constants.VIRGULA) || (mToken.getSymbolId() == Constants.DOISPONTOS)) {
					if (mToken.getSymbolId() == Constants.VIRGULA) {
						getNextToken();
						if (mToken.getSymbolId() == Constants.DOISPONTOS) {
							Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_COLON);
						}
					}
				} else {
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_COMMA_OR_SEMICOLON);
				}
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
			}
		} while (mToken.getSymbolId() != Constants.DOISPONTOS);
		getNextToken();
		analisaTipo();
	}

	private void analisaTipo() throws Exception {
		if ((mToken.getSymbolId() != Constants.INTEIRO) && (mToken.getSymbolId() != Constants.BOOLEANO)) {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_ATTRIBUTION_TYPE);
		}
		mSemanticAnalyzer.insertGenericTypeInSymbolTable(new DeclaredVar(mToken.getLexem()));
		getNextToken();
	}

	private void analisaComandos(Token func) throws Exception {
		if (mToken.getSymbolId() == Constants.INICIO) {
			getNextToken();
			analisaComandoSimples(func);
			while (mToken.getSymbolId() != Constants.FIM) {
				if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
					getNextToken();
					if (mToken.getSymbolId() != Constants.FIM) {
						analisaComandoSimples(func);
					}
				} else {
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
				}
			}
			if (func != null) {
				if (mSemanticAnalyzer.getSymbolType(func).getType().equals(Constants.L_FUNC_INT)) {
					if (!isFunctionAttribuition) {
						Errors.semanticError(mToken.getLine(), ErrorType.LAST_COMMAND_ATTRIBUTION_FAILURE);
					}
					isFunctionAttribuition = true;
				}
			}
			getNextToken();
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_INICIO);
		}
	}

	private void analisaComandoSimples(Token token) throws Exception {
		switch (mToken.getSymbolId()) {
		case Constants.IDENTIFICADOR:
			analisaAtribChProcedimento();
			break;
		case Constants.SE:
			analisaSe(token);
			break;
		case Constants.ENQUANTO:
			analisaEnquanto(token);
			break;
		case Constants.LEIA:
			analisaLeia();
			break;
		case Constants.ESCREVA:
			analisaEscreva();
			break;
		default:
			analisaComandos(token);
			break;
		}
	}

	private void analisaAtribChProcedimento() throws Exception {
		Token token = mToken;
		getNextToken();
		if (mToken.getSymbolId() == Constants.ATRIBUICAO) {
			if (mSemanticAnalyzer.pesquisaDeclVarFunc(token)) {
				analisaAtribuicao(token);
			} else {
				Errors.semanticError(mToken.getLine(), ErrorType.INVALID_VAR);
			}

		} else {
			analisaChamadaProcedimento(token);
		}
	}

	private void analisaLeia() throws Exception {
		getNextToken();
		if (mToken.getSymbolId() == Constants.ABRE_PARENTESES) {
			getNextToken();
			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				if (!mSemanticAnalyzer.pesquisaDeclVarTabela(mToken)) {
					Errors.semanticError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER2);
				}
				mCodeGenerator.generateCommand(Constants.CG_RD);
				mCodeGenerator.generateCommand(Constants.CG_STR, mSemanticAnalyzer.getSymbolType(mToken).getInfo());

				getNextToken();
				if (mToken.getSymbolId() == Constants.FECHA_PARENTESES) {
					getNextToken();
				} else {
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_CLOSING_PARENTHESES);
				}
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
			}
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_OPENNING_PARENTHESES);
		}

	}

	private void analisaEscreva() throws Exception {
		getNextToken();
		if (mToken.getSymbolId() == Constants.ABRE_PARENTESES) {
			getNextToken();
			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				if (!mSemanticAnalyzer.pesquisaDeclVarFunc(mToken)) {
					Errors.semanticError(mToken.getLine(), ErrorType.UNKNOWN_IDENTIFIER);
				} else {
					if (mSemanticAnalyzer.getSymbolType(mToken).getClass() == Rotina.class) {
						mCodeGenerator.generateCommand(Constants.CG_CALL,
								Constants.CG_LABEL + "" + mSemanticAnalyzer.getSymbolType(mToken).getInfo());
						mCodeGenerator.generateCommand(Constants.CG_LDV, 0);
					} else {
						mCodeGenerator.generateCommand(Constants.CG_LDV,
								mSemanticAnalyzer.getSymbolType(mToken).getInfo());
					}
					mCodeGenerator.generateCommand(Constants.CG_PRINT);
				}

				getNextToken();
				if (mToken.getSymbolId() == Constants.FECHA_PARENTESES) {
					getNextToken();
				} else {
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_CLOSING_PARENTHESES);
				}
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
			}
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_OPENNING_PARENTHESES);
		}
	}

	private void analisaEnquanto(Token token) throws Exception {
		int auxrot1 = mSemanticAnalyzer.getLabel();
		int auxrot2 = mSemanticAnalyzer.getLabel();

		// Label principal do while
		mCodeGenerator.generateLabel(auxrot1);

		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		mSemanticAnalyzer.terminaExpressao(false);

		if (mToken.getSymbolId() == Constants.FACA) {
			mCodeGenerator.generateCommand(Constants.CG_JMPF, Constants.CG_LABEL + "" + auxrot2);
			getNextToken();
			analisaComandoSimples(token);

			mCodeGenerator.generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + auxrot2);
			mCodeGenerator.generateLabel(auxrot2);
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.EXPRESSION);
		}
	}

	private void analisaSe(Token token) throws Exception {
		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		mSemanticAnalyzer.terminaExpressao(false);
		if (mSemanticAnalyzer.booleanExp()) {
			int labelSenao = mSemanticAnalyzer.getLabel();
			int labelSe = mSemanticAnalyzer.getLabel();
			mCodeGenerator.generateCommand(Constants.CG_JMPF, Constants.CG_LABEL + "" + labelSenao);
			if (mToken.getSymbolId() == Constants.ENTAO) {
				getNextToken();
				analisaComandoSimples(token);
				mCodeGenerator.generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + labelSe);
				mCodeGenerator.generateLabel(labelSenao);
				if (mToken.getSymbolId() == Constants.SENAO) {
					getNextToken();
					analisaComandoSimples(token);
				}
				mCodeGenerator.generateLabel(labelSe);
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_ENTAO);
			}
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.INVALID_EXPRESSION_BOOLEAN2);
		}
	}

	private void analisaSubRotinas() throws Exception {
		int auxrot = 0;
		int flag = 0;
		if ((mToken.getSymbolId() == Constants.PROCEDIMENTO) || (mToken.getSymbolId() == Constants.FUNCAO)) {
			flag = 1;
			auxrot = mSemanticAnalyzer.getLabel();
			mCodeGenerator.generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + auxrot);
		}
		while ((mToken.getSymbolId() == Constants.PROCEDIMENTO) || (mToken.getSymbolId() == Constants.FUNCAO)) {
			if (mToken.getSymbolId() == Constants.PROCEDIMENTO) {
				analisaDeclaracaoProcedimento();
			} else {
				analisaDeclaracaoFuncao();
			}
			if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
				getNextToken();
			} else {
				Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
			}
		}
		if (flag == 1) {
			mCodeGenerator.generateLabel(auxrot);
		}
	}

	private void analisaDeclaracaoProcedimento() throws Exception {
		getNextToken();
		if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
			if (!mSemanticAnalyzer.pesquisaDeclProc(mToken)) {

				mSemanticAnalyzer.insertInSymbolTable(new Symbol(new Rotina(Constants.L_PROCEDIMENTO), mToken));
				mCodeGenerator.generateLabel(mSemanticAnalyzer.getSymbolType(mToken).getInfo());

				getNextToken();
				if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
					analisaBloco(null);
				} else {
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
				}
			} else {
				Errors.semanticError(mToken.getLine(), ErrorType.INVALID_IDENTIFIER_MISSING_VAR);
			}
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
		}

		mSemanticAnalyzer.finalizaEscopo(false);
		mCodeGenerator.generateCommand(Constants.CG_RETURN);
	}

	private void analisaDeclaracaoFuncao() throws Exception {
		getNextToken();
		if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
			if (!mSemanticAnalyzer.pesquisaDeclVarFunc(mToken)) {
				Token func = mToken;
				mSemanticAnalyzer.insertInSymbolTable(new Symbol(func));
				getNextToken();
				if (mToken.getSymbolId() == Constants.DOISPONTOS) {
					getNextToken();
					if ((mToken.getSymbolId() == Constants.INTEIRO) || (mToken.getSymbolId() == Constants.BOOLEANO)) {

						if (mToken.getSymbolId() == Constants.INTEIRO) {
							mSemanticAnalyzer.insertFuncTypeInSymbolTable(Constants.L_FUNC_INT);
						} else
							mSemanticAnalyzer.insertFuncTypeInSymbolTable(Constants.L_FUNC_BOOL);

						mCodeGenerator.generateLabel(mSemanticAnalyzer.getSymbolType(func).getInfo());
						getNextToken();
						if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
							analisaBloco(func);
						} else {
							Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_SEMICOLON);
						}
					} else
						Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_ATTRIBUTION_TYPE);
				} else
					Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_COLON);
			} else {
				Errors.semanticError(mToken.getLine(), ErrorType.INVALID_IDENTIFIER_DECLARED_FUNCTION);
			}
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_IDENTIFIER);
		}

		mSemanticAnalyzer.finalizaEscopo(true);
	}

	private void analisaExpressao() throws Exception {
		analisaExpressaoSimples();
		if ((mToken.getSymbolId() == Constants.MAIOR) || (mToken.getSymbolId() == Constants.MAIORIG)
				|| (mToken.getSymbolId() == Constants.IG) || (mToken.getSymbolId() == Constants.MENOR)
				|| (mToken.getSymbolId() == Constants.MENORIG) || (mToken.getSymbolId() == Constants.DIF)) {
			mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
			getNextToken();
			analisaExpressaoSimples();
		}
	}

	private void analisaExpressaoSimples() throws Exception {
		if ((mToken.getSymbolId() == Constants.MAIS) || (mToken.getSymbolId() == Constants.MENOS)) {
			mSemanticAnalyzer.adicionaInvNaExpressao(mToken);
			getNextToken();
		}
		analisaTermo();
		while ((mToken.getSymbolId() == Constants.MAIS) || (mToken.getSymbolId() == Constants.MENOS)
				|| (mToken.getSymbolId() == Constants.OU)) {
			mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
			getNextToken();
			analisaTermo();
		}
	}

	private void analisaTermo() throws Exception {
		analisaFator();
		while ((mToken.getSymbolId() == Constants.MULT) || (mToken.getSymbolId() == Constants.DIV)
				|| (mToken.getSymbolId() == Constants.E)) {
			mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
			getNextToken();
			analisaFator();
		}
	}

	private void analisaFator() throws Exception {
		if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
			if (mSemanticAnalyzer.pesquisaDeclVarFunc(mToken)) {
				if (mSemanticAnalyzer.getSymbolType(mToken).getClass() == Rotina.class)
					analisaChamadaFuncao();
				else {
					mSemanticAnalyzer.adicionaFatorNaExpressao(mToken);
					getNextToken();
				}
			} else {
				Errors.semanticError(mToken.getLine(), ErrorType.INVALID_IDENTIFIER);
			}
		} else {
			if (mToken.getSymbolId() == Constants.NUMERO) {
				mSemanticAnalyzer.adicionaFatorNaExpressao(mToken);
				getNextToken();
			} else {
				if (mToken.getSymbolId() == Constants.NAO) {
					mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
					getNextToken();
					analisaFator();
				} else {
					if (mToken.getSymbolId() == Constants.ABRE_PARENTESES) {
						mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
						getNextToken();
						analisaExpressao();
						if (mToken.getSymbolId() == Constants.FECHA_PARENTESES) {
							mSemanticAnalyzer.adicionaOperadorNaExpressao(mToken);
							getNextToken();
						} else {
							Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_CLOSING_PARENTHESES);
						}
					} else {
						if (mToken.getSymbolId() == Constants.VERDADEIRO || mToken.getSymbolId() == Constants.FALSO) {
							mSemanticAnalyzer.adicionaFatorNaExpressao(mToken);
							getNextToken();
						} else {
							Errors.syntaxError(mToken.getLine(), ErrorType.MISSING_VERDADEIRO_OR_FALSO);
						}
					}
				}
			}
		}
	}

	private void analisaChamadaProcedimento(Token t) throws Exception {
		if (mSemanticAnalyzer.pesquisaDeclProc(t)) {
			mCodeGenerator.generateCommand(Constants.CG_CALL,
					Constants.CG_LABEL + "" + mSemanticAnalyzer.getSymbolType(t).getInfo());
		} else {
			Errors.semanticError(mToken.getLine(), ErrorType.INVALID_IDENTIFIER_UNKNOWN_PROCEDURE);
		}
	}

	private void analisaChamadaFuncao() throws Exception {
		mSemanticAnalyzer.adicionaFatorNaExpressao(mToken);
		getNextToken();
	}

	private void analisaAtribuicao(Token t) throws Exception {
		boolean isFunction = false;
		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		if (mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_INT)
				|| mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_BOOL)) {
			isFunction = true;
		}
		mSemanticAnalyzer.terminaExpressao(isFunction);
		if (mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_INTEIRO)
				|| mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_BOOLEANO)) {
			if (mSemanticAnalyzer.booleanExp()
					&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_BOOLEANO)) {
				mCodeGenerator.generateCommand(Constants.CG_STR, mSemanticAnalyzer.getSymbolType(t).getInfo());
			} else {
				if (!mSemanticAnalyzer.booleanExp()
						&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_INTEIRO)) {
					mCodeGenerator.generateCommand(Constants.CG_STR, mSemanticAnalyzer.getSymbolType(t).getInfo());
				} else {
					Errors.semanticError(mToken.getLine(), ErrorType.INVALID_EXPRESSION_BOOL);
				}
			}
		} else {
			if (mSemanticAnalyzer.booleanExp()
					&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_BOOL)) {
				isFunctionAttribuition = true;
			} else {
				if (!mSemanticAnalyzer.booleanExp()
						&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_INT)) {
					isFunctionAttribuition = true;
				} else {
					Errors.semanticError(mToken.getLine(), ErrorType.INVALID_EXPRESSION_INT);
				}
			}
		}
	}
}
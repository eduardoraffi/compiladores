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

	public SyntaxAnalyzer(String caminhoArquivo) throws Exception {
		mLexicalAnalyzer = new LexicalAnalyzer(caminhoArquivo);
		mTokenVector = mLexicalAnalyzer.pegaTokens();
		mToken = mTokenVector.get(actualVectorIndex);
		mSemanticAnalyzer = new SemanticAnalyzer();

		System.out.println(
				"Simbolo: " + mToken.getSymbol() + " Lexema: " + mToken.getLexem() + "  linha: " + mToken.getLine());

		if (mToken.getSymbolId() == Constants.PROGRAMA) {
			getNextToken();
			CodeGenerator.getInstance().generateCommand(Constants.CG_START);
			CodeGenerator.getInstance().generateCommand(Constants.CG_ALLOC, 0, 1);

			if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
				mSemanticAnalyzer.insertInSymbolTable(new Symbol(new Rotina(Constants.L_PROGRAMA), mToken));
				getNextToken();

				if (mToken.getSymbolId() == Constants.PONTO_VIRGULA) {
					analisaBloco(null);

					if (mToken.getSymbolId() == Constants.PONTO) {
						mSemanticAnalyzer.finalizaEscopo();
						CodeGenerator.getInstance().generateCommand(Constants.CG_DALLOC, 0, 1);
						CodeGenerator.getInstance().generateCommand(Constants.CG_HALT);
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

	private void analisaBloco(Token func) throws Exception {
		getNextToken();
		analisaEtVariaveis();
		analisaSubRotinas();
		analisaComandos(func);
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

	private void analisaComandoSimples(Token func) throws Exception {
		switch (mToken.getSymbolId()) {
		case Constants.IDENTIFICADOR:
			analisaAtribChProcedimento();
			break;
		case Constants.SE:
			analisaSe(func);
			break;
		case Constants.ENQUANTO:
			analisaEnquanto(func);
			break;
		case Constants.LEIA:
			analisaLeia();
			break;
		case Constants.ESCREVA:
			analisaEscreva();
			break;
		default:
			analisaComandos(func);
			break;
		}
	}

	private void analisaAtribChProcedimento() throws Exception {
		Token t = mToken;
		getNextToken();
		if (mToken.getSymbolId() == Constants.ATRIBUICAO) {
			if (mSemanticAnalyzer.pesquisaDeclVarFunc(t)) {
				analisaAtribuicao(t);
			} else {
				Errors.semanticError(mToken.getLine(), ErrorType.INVALID_VAR);
			}

		} else {
			analisaChamadaProcedimento(t);
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
				CodeGenerator.getInstance().generateCommand(Constants.CG_RD);
				CodeGenerator.getInstance().generateCommand(Constants.CG_STR,
						mSemanticAnalyzer.getSymbolType(mToken).getInfo());

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
						CodeGenerator.getInstance().generateCommand(Constants.CG_CALL,
								Constants.CG_LABEL + "" + mSemanticAnalyzer.getSymbolType(mToken).getInfo());
						CodeGenerator.getInstance().generateCommand(Constants.CG_LDV, 0);
					} else {
						CodeGenerator.getInstance().generateCommand(Constants.CG_LDV,
								mSemanticAnalyzer.getSymbolType(mToken).getInfo());
					}
					CodeGenerator.getInstance().generateCommand(Constants.CG_PRINT);
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

	private void analisaEnquanto(Token rotina) throws Exception {
		int auxrot1 = mSemanticAnalyzer.getLabel();
		int auxrot2 = mSemanticAnalyzer.getLabel();

		// Label principal do while
		CodeGenerator.getInstance().generateLabel(auxrot1);

		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		mSemanticAnalyzer.terminaExpressao();

		if (mToken.getSymbolId() == Constants.FACA) {
			CodeGenerator.getInstance().generateCommand(Constants.CG_JMPF, Constants.CG_LABEL + "" + auxrot2);
			getNextToken();
			analisaComandoSimples(rotina);

			CodeGenerator.getInstance().generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + auxrot2);
			CodeGenerator.getInstance().generateLabel(auxrot2);
		} else {
			Errors.syntaxError(mToken.getLine(), ErrorType.EXPRESSION);
		}
	}

	private void analisaSe(Token rotina) throws Exception {
		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		mSemanticAnalyzer.terminaExpressao();
		if (mSemanticAnalyzer.booleanExp()) {
			// Está errado
			int labelSenao = mSemanticAnalyzer.getLabel();
			int labelSe = mSemanticAnalyzer.getLabel();
			CodeGenerator.getInstance().generateCommand(Constants.CG_JMPF, Constants.CG_LABEL + "" + labelSenao);
			if (mToken.getSymbolId() == Constants.ENTAO) {
				getNextToken();
				analisaComandoSimples(rotina);
				CodeGenerator.getInstance().generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + labelSe);
				CodeGenerator.getInstance().generateLabel(labelSenao);
				if (mToken.getSymbolId() == Constants.SENAO) {
					getNextToken();
					analisaComandoSimples(rotina);
				}
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
			CodeGenerator.getInstance().generateCommand(Constants.CG_JUMP, Constants.CG_LABEL + "" + auxrot);
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
			CodeGenerator.getInstance().generateLabel(auxrot);
		}
	}

	private void analisaDeclaracaoProcedimento() throws Exception {
		getNextToken();
		if (mToken.getSymbolId() == Constants.IDENTIFICADOR) {
			if (!mSemanticAnalyzer.pesquisaDeclProc(mToken)) {

				mSemanticAnalyzer.insertInSymbolTable(new Symbol(new Rotina(Constants.L_PROCEDIMENTO), mToken));
				CodeGenerator.getInstance().generateLabel(mSemanticAnalyzer.getSymbolType(mToken).getInfo());

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

		mSemanticAnalyzer.finalizaEscopo();
		CodeGenerator.getInstance().generateCommand(Constants.CG_RETURN);
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

						CodeGenerator.getInstance().generateLabel(mSemanticAnalyzer.getSymbolType(func).getInfo());
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

		mSemanticAnalyzer.finalizaEscopo();
		CodeGenerator.getInstance().generateCommand(Constants.CG_RETURN);
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
			CodeGenerator.getInstance().generateCommand(Constants.CG_CALL,
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
		getNextToken();
		mSemanticAnalyzer.comecaExpressao();
		analisaExpressao();
		mSemanticAnalyzer.terminaExpressao();
		if (mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_INTEIRO)
				|| mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_BOOLEANO)) {
			if (mSemanticAnalyzer.booleanExp()
					&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_BOOLEANO)) {
				CodeGenerator.getInstance().generateCommand(Constants.CG_STR,
						mSemanticAnalyzer.getSymbolType(t).getInfo());
			} else {
				if (!mSemanticAnalyzer.booleanExp()
						&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_INTEIRO)) {
					CodeGenerator.getInstance().generateCommand(Constants.CG_STR,
							mSemanticAnalyzer.getSymbolType(t).getInfo());
				} else {
					Errors.semanticError(mToken.getLine(), ErrorType.INVALID_EXPRESSION_BOOL);
				}
			}
		} else {
			if (mSemanticAnalyzer.booleanExp()
					&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_BOOL)) {
				isFunctionAttribuition = true;
				CodeGenerator.getInstance().generateCommand(Constants.CG_STR, 0);
			} else {
				if (!mSemanticAnalyzer.booleanExp()
						&& mSemanticAnalyzer.getSymbolType(t).getType().equals(Constants.L_FUNC_INT)) {
					isFunctionAttribuition = true;
					CodeGenerator.getInstance().generateCommand(Constants.CG_STR, 0);
				} else {
					Errors.semanticError(mToken.getLine(), ErrorType.INVALID_EXPRESSION_INT);
				}
			}
		}
	}
}
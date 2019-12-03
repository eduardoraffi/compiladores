package compilador;

import java.util.Vector;
import compilador.Errors.ErrorType;

public class LexicalAnalyzer {
	Token mToken;
	Vector<Token> vetorDeTokens;
	private int line = 1;
	private int leituraDoArquivo = 0;
	private char caracterLido;
	private FileReader arquivo;

	public LexicalAnalyzer(String nomeDoArquivo) throws Exception {
		mToken = null;
		vetorDeTokens = new Vector<Token>();
		arquivo = new FileReader(nomeDoArquivo);
		readAllFile();
	}

	private void readCharacter() {
		leituraDoArquivo = arquivo.leituraCaracter();
		if (leituraDoArquivo != -1) {
			caracterLido = (char) leituraDoArquivo;
		}
	}

	private void readAllFile() throws Exception {
		readCharacter();
		while (leituraDoArquivo != -1) {
			if (caracterLido == Constants.C_ABRE_CHAVE) {
				while (caracterLido != Constants.C_FECHA_CHAVE) {
					if (leituraDoArquivo == -1) {
						Errors.lexycalError(line, ErrorType.MISSING_COMMENT_CLOSE_BRACE);
					}
					if (caracterLido == Constants.C_PULA_LINHA || caracterLido == Constants.C_PULA_LINHA_MAC) {
						line++;
					}
					readCharacter();
				}
				readCharacter();
				continue;
			}
			if (Character.isWhitespace(caracterLido)) {
				if (caracterLido == Constants.C_PULA_LINHA) {
					line++;
				}
				readCharacter();
				continue;
			}
			if (caracterLido == Constants.C_BARRA) {
				readCharacter();
				if (caracterLido == Constants.C_ESTRELA) {
					do {
						readCharacter();
						if (caracterLido == Constants.C_ESTRELA) {
							readCharacter();
							if (caracterLido == Constants.C_BARRA) {
								readCharacter();
								break;
							}
						}
					} while (leituraDoArquivo != -1);
				}
			}
			mToken = pegaToken();
			if (mToken == null) {
				Errors.lexycalError(line, ErrorType.UNKNOWN_WORD);
			}
			vetorDeTokens.add(mToken);
		}
	}

	public Token pegaToken() throws Exception {
		if (Character.isDigit(caracterLido)) {
			return trataDigito();
		}
		if (Character.isAlphabetic(caracterLido)) {
			return trataIdentificadorEPalavraReservada();
		}
		if (caracterLido == Constants.C_DOIS_PONTOS) {
			return trataAtribuicao();
		}
		if (caracterLido == Constants.C_MAIS || caracterLido == Constants.C_MENOS
				|| caracterLido == Constants.C_ESTRELA) {
			return trataOperadorAritmetico();
		}
		if (caracterLido == Constants.C_MAIOR || caracterLido == Constants.C_IGUAL
				|| caracterLido == Constants.C_EXCLAMACAO || caracterLido == Constants.C_MENOR) {
			return trataOperadorRelacional();
		}
		return trataPontuacao();
	}

	private Token trataDigito() throws Exception {
		String num = "";
		do {
			num = num + caracterLido;
			readCharacter();
		} while (Character.isDigit(caracterLido));
		return new Token(Constants.S_NUMERO, num, line);
	}

	public Vector<Token> pegaTokens() {
		return vetorDeTokens;
	}

	public Token pegaToken(int i) throws Exception {
		if (vetorDeTokens.size() < (i - 1) || (i - 1) < 0) {
			Errors.lexycalError(line, ErrorType.INVALID_INDEX);
			throw new Exception("Lexycal Problem: Invalid token index");
		}
		return vetorDeTokens.get(i);
	}

	private Token trataIdentificadorEPalavraReservada() throws Exception {
		String lexema = "";
		do {
			lexema = lexema + caracterLido;
			readCharacter();
		} while (Character.isDigit(caracterLido) || Character.isAlphabetic(caracterLido));

		switch (lexema) {
		case Constants.L_PROGRAMA:
			return new Token(Constants.S_PROGRAMA, lexema, line);
		case Constants.L_SE:
			return new Token(Constants.S_SE, lexema, line);
		case Constants.L_ENTAO:
			return new Token(Constants.S_ENTAO, lexema, line);
		case Constants.L_SENAO:
			return new Token(Constants.S_SENAO, lexema, line);
		case Constants.L_ENQUANTO:
			return new Token(Constants.S_ENQUANTO, lexema, line);
		case Constants.L_FACA:
			return new Token(Constants.S_FACA, lexema, line);
		case Constants.L_INICIO:
			return new Token(Constants.S_INICIO, lexema, line);
		case Constants.L_FIM:
			return new Token(Constants.S_FIM, lexema, line);
		case Constants.L_ESCREVA:
			return new Token(Constants.S_ESCREVA, lexema, line);
		case Constants.L_LEIA:
			return new Token(Constants.S_LEIA, lexema, line);
		case Constants.L_VAR:
			return new Token(Constants.S_VAR, lexema, line);
		case Constants.L_INTEIRO:
			return new Token(Constants.S_INTEIRO, lexema, line);
		case Constants.L_BOOLEANO:
			return new Token(Constants.S_BOOLEANO, lexema, line);
		case Constants.L_VERDADEIRO:
			return new Token(Constants.S_VERDADEIRO, lexema, line);
		case Constants.L_FALSO:
			return new Token(Constants.S_FALSO, lexema, line);
		case Constants.L_PROCEDIMENTO:
			return new Token(Constants.S_PROCEDIMENTO, lexema, line);
		case Constants.L_FUNCAO:
			return new Token(Constants.S_FUNCAO, lexema, line);
		case Constants.L_DIV:
			return new Token(Constants.S_DIV, lexema, line);
		case Constants.L_E:
			return new Token(Constants.S_E, lexema, line);
		case Constants.L_OU:
			return new Token(Constants.S_OU, lexema, line);
		case Constants.L_NAO:
			return new Token(Constants.S_NAO, lexema, line);
		default:
			return new Token(Constants.S_IDENTIFICADOR, lexema, line);
		}
	}

	private Token trataAtribuicao() throws Exception {
		String atrib = "" + caracterLido;
		readCharacter();
		if (caracterLido == Constants.C_IGUAL) {
			atrib = atrib + Constants.C_IGUAL;
			readCharacter();
			return new Token(Constants.S_ATRIBUICAO, atrib, line);
		}
		return new Token(Constants.S_DOISPONTOS, atrib, line);
	}

	private Token trataOperadorAritmetico() throws Exception {
		String atrib = "" + caracterLido;
		if (caracterLido == Constants.C_MAIS) {
			readCharacter();
			return new Token(Constants.S_MAIS, atrib, line);
		} else if (caracterLido == Constants.C_MENOS) {
			readCharacter();
			return new Token(Constants.S_MENOS, atrib, line);
		} else {
			readCharacter();
			return new Token(Constants.S_MULT, atrib, line);
		}
	}

	private Token trataOperadorRelacional() throws Exception {
		String operador = "" + caracterLido;
		if (caracterLido == Constants.C_IGUAL) {
			readCharacter();
			return new Token(Constants.S_IG, operador, line);
		}
		if (caracterLido == Constants.C_MAIOR) {
			readCharacter();
			if (caracterLido == '=') {
				operador = operador + caracterLido;
				readCharacter();
				return new Token(Constants.S_MAIORIG, operador, line);
			}
			return new Token(Constants.S_MAIOR, operador, line);
		}
		if (caracterLido == Constants.C_MENOR) {
			readCharacter();
			if (caracterLido == Constants.C_IGUAL) {
				operador = operador + caracterLido;
				readCharacter();
				return new Token(Constants.S_MENORIG, operador, line);
			}
			return new Token(Constants.S_MENOR, operador, line);
		}
		if (caracterLido == Constants.C_EXCLAMACAO) {
			readCharacter();
			if (caracterLido == Constants.C_IGUAL) {
				operador = operador + caracterLido;
				readCharacter();
				return new Token(Constants.S_DIF, operador, line);
			}
		}
		return null;
	}

	private Token trataPontuacao() throws Exception {
		String atrib = "" + caracterLido;
		if (caracterLido == Constants.C_PONTO_VIRGULA) {
			readCharacter();
			return new Token(Constants.S_PONTO_VIRGULA, atrib, line);
		}
		if (caracterLido == Constants.C_ABRE_PARENTESES) {
			readCharacter();
			return new Token(Constants.S_ABRE_PARENTESES, atrib, line);
		}
		if (caracterLido == Constants.C_FECHA_PARENTESES) {
			readCharacter();
			return new Token(Constants.S_FECHA_PARENTESES, atrib, line);
		}
		if (caracterLido == Constants.C_VIRGULA) {
			readCharacter();
			return new Token(Constants.S_VIRGULA, atrib, line);
		}
		if (caracterLido == Constants.C_PONTO) {
			readCharacter();
			return new Token(Constants.S_PONTO, atrib, line);
		}
		return null;
	}
}

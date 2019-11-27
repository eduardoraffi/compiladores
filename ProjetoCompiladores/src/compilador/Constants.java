package compilador;

public class Constants 
{

	private Constants() 
	{
		//donotbuildthisclass
	}
	
	//paths
	public static final String PATH_CODE_GEN		= "D:\\Documents\\Faculdade\\Faculdade2019\\Compiladores\\Geracao_Codigo\\code.txt";
	
	//Chars
	public static final Character C_ABRE_CHAVE 		= '{';
	public static final Character C_FECHA_CHAVE 	= '}';
	public static final Character C_EXCLAMACAO 		= '!';
	public static final Character C_PULA_LINHA 		= '\n';
	public static final Character C_PULA_LINHA_MAC	= '\r';
	public static final Character C_BARRA 			= '/';
	public static final Character C_MAIS 			= '+';
	public static final Character C_MENOS 			= '-';
	public static final Character C_ESTRELA 		= '*';
	public static final Character C_MAIOR 			= '>';
	public static final Character C_MENOR 			= '<';
	public static final Character C_IGUAL 			= '=';
	public static final Character C_DOIS_PONTOS 	= ':';
	public static final Character C_VIRGULA 		= ',';
	public static final Character C_PONTO 			= '.';
	public static final Character C_PONTO_VIRGULA	= ';';
	public static final Character C_ABRE_PARENTESES	= '(';
	public static final Character C_FECHA_PARENTESES= ')';

	//Token
	public static final String S_INVERTER 			= "sinv";
	public static final String S_PROGRAMA 			= "sprograma";
	public static final String S_INICIO 			= "sinicio";
	public static final String S_FIM 				= "sfim";
	public static final String S_PROCEDIMENTO	 	= "sprocedimento";
	public static final String S_FUNCAO 			= "sfuncao";
	public static final String S_SE 				= "sse";
	public static final String S_ENTAO 				= "sentao";
	public static final String S_SENAO 				= "ssenao";
	public static final String S_ENQUANTO 			= "senquanto";
	public static final String S_FACA 				= "sfaca";
	public static final String S_ATRIBUICAO 		= "satribuicao";
	public static final String S_ESCREVA 			= "sescreva";
	public static final String S_LEIA 				= "sleia";
	public static final String S_VAR 				= "svar";
	public static final String S_INTEIRO 			= "sinteiro";
	public static final String S_BOOLEANO 			= "sbooleano";
	public static final String S_IDENTIFICADOR 		= "sidentificador";
	public static final String S_NUMERO 			= "snumero";
	public static final String S_PONTO 				= "sponto";
	public static final String S_PONTO_VIRGULA 		= "sponto_virgula";
	public static final String S_VIRGULA 			= "svirgula";
	public static final String S_ABRE_PARENTESES 	= "sabre_parenteses";
	public static final String S_FECHA_PARENTESES 	= "sfecha_parenteses";
	public static final String S_MAIOR 				= "smaior";
	public static final String S_MAIORIG 			= "smaiorig";
	public static final String S_IG 				= "sig";
	public static final String S_MENOR 				= "smenor";
	public static final String S_MENORIG 			= "smenorig";
	public static final String S_DIF 				= "sdif";
	public static final String S_MAIS 				= "smais";
	public static final String S_MENOS 				= "smenos";
	public static final String S_MULT 				= "smult";
	public static final String S_DIV 				= "sdiv";
	public static final String S_E					= "se";
	public static final String S_OU 				= "sou";
	public static final String S_NAO 				= "snao";
	public static final String S_DOISPONTOS 		= "sdoispontos";
	public static final String S_VERDADEIRO 		= "sverdadeiro";
	public static final String S_FALSO 				= "sfalso";
	
	public static final int INVERTER 				= 0;
	public static final int PROGRAMA 				= 1;
	public static final int INICIO 					= 2;
	public static final int FIM 					= 3;
	public static final int PROCEDIMENTO	 		= 4;
	public static final int FUNCAO 					= 5;
	public static final int SE 						= 6;
	public static final int ENTAO 					= 7;
	public static final int SENAO 					= 8;
	public static final int ENQUANTO 				= 9;
	public static final int FACA 					= 10;
	public static final int ATRIBUICAO 				= 11;
	public static final int ESCREVA 				= 12;
	public static final int LEIA 					= 13;
	public static final int VAR 					= 14;
	public static final int INTEIRO 				= 15;
	public static final int BOOLEANO 				= 16;
	public static final int IDENTIFICADOR 			= 17;
	public static final int NUMERO 					= 18;
	public static final int PONTO 					= 19;
	public static final int PONTO_VIRGULA 			= 20;
	public static final int VIRGULA 				= 21;
	public static final int ABRE_PARENTESES 		= 22;
	public static final int FECHA_PARENTESES 		= 23;
	public static final int MAIOR 					= 24;
	public static final int MAIORIG 				= 25;
	public static final int IG 						= 26;
	public static final int MENOR 					= 27;
	public static final int MENORIG 				= 28;
	public static final int DIF 					= 29;
	public static final int MAIS 					= 30;
	public static final int MENOS					= 31;
	public static final int MULT 					= 32;
	public static final int DIV 					= 33;
	public static final int E						= 34;
	public static final int OU 						= 35;		 
	public static final int NAO 					= 36;
	public static final int DOISPONTOS 				= 37; 
	public static final int VERDADEIRO 				= 38;
	public static final int FALSO					= 39;

	//Lexemas
	public static final String L_INVERTER 			= "inv";
	public static final String L_PROGRAMA 			= "programa";
	public static final String L_INICIO 			= "inicio";
	public static final String L_FIM 				= "fim";
	public static final String L_PROCEDIMENTO	 	= "procedimento";
	public static final String L_FUNCAO 			= "funcao";
	public static final String L_SE 				= "se";
	public static final String L_ENTAO 				= "entao";
	public static final String L_SENAO 				= "senao";
	public static final String L_ENQUANTO 			= "enquanto";
	public static final String L_FACA 				= "faca";
	public static final String L_ATRIBUICAO 		= "atribuicao";
	public static final String L_ESCREVA 			= "escreva";
	public static final String L_LEIA 				= "leia";
	public static final String L_VAR 				= "var";
	public static final String L_INTEIRO 			= "inteiro";
	public static final String L_BOOLEANO 			= "booleano";
	public static final String L_IDENTIFICADOR 		= "identificador";
	public static final String L_NUMERO 			= "numero";
	public static final String L_PONTO 				= "ponto";
	public static final String L_PONTO_VIRGULA 		= "ponto_virgula";
	public static final String L_VIRGULA 			= "virgula";
	public static final String L_ABRE_PARENTESES 	= "abre_parenteses";
	public static final String L_FECHA_PARENTESES 	= "fecha_parenteses";
	public static final String L_MAIOR 				= "maior";
	public static final String L_MAIORIG 			= "maiorig";
	public static final String L_IG 				= "ig";
	public static final String L_MENOR 				= "menor";
	public static final String L_MENORIG 			= "menorig";
	public static final String L_DIF 				= "dif";
	public static final String L_MAIS 				= "mais";
	public static final String L_MENOS 				= "menos";
	public static final String L_MULT 				= "mult";
	public static final String L_DIV 				= "div";
	public static final String L_E					= "e";
	public static final String L_OU 				= "ou";
	public static final String L_NAO 				= "nao";
	public static final String L_DOISPONTOS 		= "doispontos";
	public static final String L_VERDADEIRO 		= "verdadeiro";
	public static final String L_FALSO 				= "falso";	
	public static final String L_FUNC_BOOL			= "funcBool";	
	public static final String L_FUNC_INT			= "funcInt";	
	
	//Code gen
	public static final String CG_START 			= "START";
    public static final String CG_ALLOC 			= "ALLOC";
    public static final String CG_DALLOC 			= "DALLOC";
    public static final String CG_HALT 				= "HLT";
    public static final String CG_LABEL				= "L";
    public static final String CG_LDC 				= "LDC";
    public static final String CG_LDV 				= "LDV";
    public static final String CG_ADD 				= "ADD";
    public static final String CG_SUB				= "SUB";
    public static final String CG_MULT 				= "MULT";
    public static final String CG_DIVI 				= "DIVI";
    public static final String CG_INV 				= "INV";
    public static final String CG_AND 				= "AND";
    public static final String CG_OR 				= "OR";
    public static final String CG_NEG 				= "NEG";
    public static final String CG_CME 				= "CME";
    public static final String CG_CMA 				= "CMA";
    public static final String CG_CEQ				= "CEQ";
    public static final String CG_CDIF 				= "CDIF";
    public static final String CG_CMEQ 				= "CMEQ";
    public static final String CG_CMAQ 				= "CMAQ";
    public static final String CG_STR				= "STR";
    public static final String CG_JUMP 				= "JMP";
    public static final String CG_JMPF 				= "JMPF";
    public static final String CG_NULL 				= "NULL";
    public static final String CG_RD 				= "RD";
    public static final String CG_PRINT				= "PRN";
    public static final String CG_CALL 				= "CALL";
    public static final String CG_RETURN 			= "RETURN";
}
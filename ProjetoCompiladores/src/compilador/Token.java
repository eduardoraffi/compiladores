package compilador;

public class Token 
{
    private final String mSymbol;
    private final String mLexem;
    private final int mLine;
    
    public Token(String symbol, String lexem, int line) throws Exception
    {
    	if(symbol == null) throw new Exception("Symbol is null");
        if(lexem == null) throw new Exception("Lexem is null");
        mSymbol = symbol;
        mLexem = lexem;
        mLine = line;
    }
       
    public String getSymbol()
    {
        return mSymbol;
    }
     
    public String getLexem()
    {
        return mLexem;
    }
    
    public int getLine()
    {
        return mLine;
    }
    
    public String toString()
    {
        return "Simbol\t: "+mSymbol+"\nLexem\t: "+mLexem+"\nCode\t: " + getSymbolId() + "\nLine\t: "+mLine;
    }
    
    public static int getSymbolId(String symbol)
    {
        switch (symbol)
        {
	        case Constants.S_INVERTER:
	             return 0;   
	        case Constants.S_PROGRAMA:
	            return 1;
	        case Constants.S_INICIO:
	            return 2;
	        case Constants.S_FIM:
	            return 3;
	        case Constants.S_PROCEDIMENTO:
	            return 4;
	        case Constants.S_FUNCAO:
	            return 5;
	        case Constants.S_SE:
	            return 6;
	        case Constants.S_ENTAO:
	            return 7;
	        case Constants.S_SENAO:
	            return 8;
	        case Constants.S_ENQUANTO:
	            return 9;
	        case Constants.S_FACA:
	            return 10;
	        case Constants.S_ATRIBUICAO:
	            return 11;
	        case Constants.S_ESCREVA:
	            return 12;
	        case Constants.S_LEIA:
	            return 13;
	        case Constants.S_VAR:
	            return 14;
	        case Constants.S_INTEIRO:
	            return 15;
	        case Constants.S_BOOLEANO:
	            return 16;
	        case Constants.S_IDENTIFICADOR:
	            return 17;
	        case Constants.S_NUMERO:
	            return 18;
	        case Constants.S_PONTO:
	            return 19;
	        case Constants.S_PONTO_VIRGULA:
	            return 20;
	        case Constants.S_VIRGULA:
	            return 21;
	        case Constants.S_ABRE_PARENTESES:
	            return 22;
	        case Constants.S_FECHA_PARENTESES:
	            return 23;
	        case Constants.S_MAIOR:
	            return 24;
	        case Constants.S_MAIORIG:
	            return 25;
	        case Constants.S_IG:
	            return 26;
	        case Constants.S_MENOR:
	            return 27;
	        case Constants.S_MENORIG:
	            return 28;
	        case Constants.S_DIF:
	            return 29;
	        case Constants.S_MAIS:
	            return 30;
	        case Constants.S_MENOS:
	            return 31;
	        case Constants.S_MULT:
	            return 32;
	        case Constants.S_DIV:
	            return 33;
	        case Constants.S_E:
	            return 34;
	        case Constants.S_OU:
	            return 35;
	        case Constants.S_NAO:
	            return 36;
	        case Constants.S_DOISPONTOS:
	            return 37;
	        case Constants.S_VERDADEIRO:
	            return 38;
	        case Constants.S_FALSO:
	            return 39;
	        default:
	        	return 0;
        }
    }
    
     public int getSymbolId()
     {
    	 switch (this.mSymbol)
         {
 	        case Constants.S_INVERTER:
 	        	return 1;   
 	        case Constants.S_PROGRAMA:
 	            return 1;
 	        case Constants.S_INICIO:
 	            return 2;
 	        case Constants.S_FIM:
 	            return 3;
 	        case Constants.S_PROCEDIMENTO:
 	            return 4;
 	        case Constants.S_FUNCAO:
 	            return 5;
 	        case Constants.S_SE:
 	            return 6;
 	        case Constants.S_ENTAO:
 	            return 7;
 	        case Constants.S_SENAO:
 	            return 8;
 	        case Constants.S_ENQUANTO:
 	            return 9;
 	        case Constants.S_FACA:
 	            return 10;
 	        case Constants.S_ATRIBUICAO:
 	            return 11;
 	        case Constants.S_ESCREVA:
 	            return 12;
 	        case Constants.S_LEIA:
 	            return 13;
 	        case Constants.S_VAR:
 	            return 14;
 	        case Constants.S_INTEIRO:
 	            return 15;
 	        case Constants.S_BOOLEANO:
 	            return 16;
 	        case Constants.S_IDENTIFICADOR:
 	            return 17;
 	        case Constants.S_NUMERO:
 	            return 18;
 	        case Constants.S_PONTO:
 	            return 19;
 	        case Constants.S_PONTO_VIRGULA:
 	            return 20;
 	        case Constants.S_VIRGULA:
 	            return 21;
 	        case Constants.S_ABRE_PARENTESES:
 	            return 22;
 	        case Constants.S_FECHA_PARENTESES:
 	            return 23;
 	        case Constants.S_MAIOR:
 	            return 24;
 	        case Constants.S_MAIORIG:
 	            return 25;
 	        case Constants.S_IG:
 	            return 26;
 	        case Constants.S_MENOR:
 	            return 27;
 	        case Constants.S_MENORIG:
 	            return 28;
 	        case Constants.S_DIF:
 	            return 29;
 	        case Constants.S_MAIS:
 	            return 30;
 	        case Constants.S_MENOS:
 	            return 31;
 	        case Constants.S_MULT:
 	            return 32;
 	        case Constants.S_DIV:
 	            return 33;
 	        case Constants.S_E:
 	            return 34;
 	        case Constants.S_OU:
 	            return 35;
 	        case Constants.S_NAO:
 	            return 36;
 	        case Constants.S_DOISPONTOS:
 	            return 37;
 	        case Constants.S_VERDADEIRO:
 	            return 38;
 	        case Constants.S_FALSO:
 	            return 39;
 	        default:
 	        	return 0;
         }
     }
}
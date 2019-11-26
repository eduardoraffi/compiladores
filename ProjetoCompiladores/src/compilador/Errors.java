package compilador;

public class Errors 
{
    public Errors()
    {
    	//Do not build this class
    }
    
	public static void lexycalError (int errorLine, ErrorType errorType) throws Exception
    {
        throw new Exception("Lexycal error on " + errorLine + ": " + errorDescription(errorType));
    }
    
    public static void syntaxError (int errorLine, ErrorType errorType) throws Exception
    {
        throw new Exception("Syntax error on " + errorLine + ": " + errorDescription(errorType));
    }
    
    public static void semanticError (int errorLine, ErrorType errorType) throws Exception
    {
        throw new Exception("Semantic error on " + errorLine + ": " + errorDescription(errorType));
    }
    
    public static void generalError (ErrorType errorType) throws Exception
    {
    	throw new Exception("General error: " + errorDescription(errorType));
    }
    public static void generalError (int errorLine, ErrorType errorType) throws Exception
    {
    	throw new Exception("General error: " + errorDescription(errorType));
    }
    
   private static String errorDescription (ErrorType errorType)
   {
	   String errorDescription = "";
       switch(errorType)
       {
           case MISSING_DOT:
               errorDescription =  "Missing '.'";
               break;
           case MISSING_SEMICOLON:
               errorDescription =  "Missing ';'";
               break;
           case MISSING_IDENTIFIER:
               errorDescription =  "Missing identifier";
               break;
           case MISSING_PROGRAM_IDENTIFIER:
               errorDescription =  "Missing identifier 'programa'";
               break;
           case MISSING_COLON:
               errorDescription =  "Missing ':'";
               break;
           case MISSING_COMMA_OR_SEMICOLON:
               errorDescription =  "Missing ',' ou ':'";
               break;
           case MISSING_ATTRIBUTION_TYPE:
               errorDescription =  "Missing type attribution";
               break;
           case MISSING_INICIO:
               errorDescription =  "Missing 'inicio'";
               break;
           case MISSING_OPENNING_PARENTHESES:
               errorDescription =  "Missing '('";
               break;
           case MISSING_CLOSING_PARENTHESES:
               errorDescription =  "Missing ')'";
               break;
           case EXPRESSION:
               errorDescription =  "Expression error";
               break;
           case MISSING_ENTAO:
               errorDescription =  "Missing 'entao'";
               break;
           case MISSING_VERDADEIRO_OR_FALSO:
               errorDescription =  "Missing 'verdeiro' or 'falso'";
               break;
           case MISSING_COMMENT_CLOSE_BRACE:
               errorDescription =  "Missing '}'";
               break;
           case UNKNOWN_WORD:
               errorDescription =  "Unknown word";
               break;
           case INVALID_INDEX:
               errorDescription =  "Invalid index";
               break;
           case TOKEN_VECTOR_END:
               errorDescription =  "Token array has ended";
               break;
           case DUPLICATED_FUNCTION_ATTRIBUTION:
               errorDescription =  "Duplicated function attribution";
               break;
           case LAST_COMMAND_ATTRIBUTION_FAILURE:
               errorDescription =  "Attribution failure. Last command wasn\'t function attribution";
               break;
           case INVALID_VAR:
               errorDescription =  "Invalid variable";
               break;
           case INVALID_EXPRESSION_BOOL:
               errorDescription =  "Invalid bool expression type";
               break;
           case INVALID_EXPRESSION_INT:
               errorDescription =  "Invalid int expression type";
               break;
           case MISSING_IDENTIFIER2:
               errorDescription =  "Undeclared var";
               break;
           case UNKNOWN_IDENTIFIER:
               errorDescription =  "Unknown identifier";
               break;
           case INVALID_EXPRESSION_BOOLEAN:
               errorDescription =  "Invalid expression. Expression has to be boolean type";
               break;
           case INVALID_EXPRESSION_BOOLEAN2:
               errorDescription =  "Invalid expression. Expression has to be boolean type";
               break;
           case INVALID_IDENTIFIER_MISSING_VAR:
               errorDescription =  "Invalid identifier. Missing variable declaration";
               break;
           case INVALID_IDENTIFIER_DECLARED_FUNCTION:
               errorDescription =  "Invalid identifier. Function already exists";
               break;
           case INVALID_IDENTIFIER:
               errorDescription =  "Invalid identifier";
               break;
           case INVALID_IDENTIFIER_UNKNOWN_PROCEDURE:
               errorDescription =  "Invalid identifier. Unknown procedure";
           case UNKNOWN_SYMBOL:
        	   errorDescription =  "Unknown symbol";
               break;
           case MISSING_SYMBOL:
        	   errorDescription =  "Missing symbol";
        	   break;
           case TYPE_ALREADY_PRESENT:
        	   errorDescription =  "Last symbol already has type";
        	   break;
           case INVALID_TYPE:
        	   errorDescription =  "Invalid type";
        	   break;
           case INVALID_TOKEN:
        	   errorDescription =  "Empty token object";
        	   break;
           case INVALID_SYMBOL:
        	   errorDescription =  "Empty symbol object";
        	   break;
           case INVALID_SYMBOL_POSITION:
        	   errorDescription =  "Invalid symbol position";
        	   break;
           case GENERATE_EXPRESSION:
        	   errorDescription =  "Failure on expression generate";
        	   break;
           default:
               errorDescription =  "Unknown error";
       }
       
       return errorDescription;
   }
   
   public static enum ErrorType
	{
		MISSING_DOT,							//1
		MISSING_SEMICOLON,						//2
		MISSING_IDENTIFIER,						//3
		MISSING_PROGRAM_IDENTIFIER,				//4
		MISSING_COLON,							//5
		MISSING_COMMA_OR_SEMICOLON,				//6
		MISSING_ATTRIBUTION_TYPE,				//7
		MISSING_INICIO,							//8
		MISSING_OPENNING_PARENTHESES,			//9
		MISSING_CLOSING_PARENTHESES,			//10
		EXPRESSION,								//11
		MISSING_ENTAO,							//12
		MISSING_VERDADEIRO_OR_FALSO,			//13
		MISSING_COMMENT_CLOSE_BRACE,			//14
		UNKNOWN_WORD,							//15
		INVALID_INDEX,							//16
		TOKEN_VECTOR_END,						//17
		DUPLICATED_FUNCTION_ATTRIBUTION,		//18
		LAST_COMMAND_ATTRIBUTION_FAILURE,		//19
		INVALID_VAR,							//20
		INVALID_EXPRESSION_BOOL,				//21
		INVALID_EXPRESSION_INT,					//22
		MISSING_IDENTIFIER2,					//23
		UNKNOWN_IDENTIFIER,						//24
		INVALID_EXPRESSION_BOOLEAN,				//25
		INVALID_EXPRESSION_BOOLEAN2,			//26
		INVALID_IDENTIFIER_MISSING_VAR,			//27
		INVALID_IDENTIFIER_DECLARED_FUNCTION,	//28
		INVALID_IDENTIFIER,						//29
		INVALID_IDENTIFIER_UNKNOWN_PROCEDURE,	//30
	   	UNKNOWN_SYMBOL,							//31
	    MISSING_SYMBOL,							//32
	    INVALID_TYPE,							//32
	    TYPE_ALREADY_PRESENT,
	    INVALID_TOKEN,
	    INVALID_SYMBOL,
	    INVALID_SYMBOL_POSITION,
	    GENERATE_EXPRESSION;
	}
}

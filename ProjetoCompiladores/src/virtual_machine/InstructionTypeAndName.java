package virtual_machine;

public enum InstructionTypeAndName {
	ADD(0), SUB(0), MULT(0), DIVI(0), INV(0), AND(0), OR(0), NEG(0), CME(0), CMA(0), CEQ(0), CDIF(0), CMEQ(0), CMAQ(0),
	START(0), HLT(0), NULL(0), RD(0), PRN(0), RETURN(0), LDC(1), LDV(1), STR(1), JMP(1), JMPF(1), CALL(1), ALLOC(2),
	DALLOC(2), RETURNF(2);

	private int mCode;

	private InstructionTypeAndName(int code) {
		mCode = code;
	}

	private int getType() {
		return mCode;
	}

	public static int getInstructionType(InstructionTypeAndName p) {
		return p.getType();
	}

	public static InstructionTypeAndName getInstructionName(String instructionName) throws Exception {
		switch (instructionName.toUpperCase()) {
		case Constants.CG_ADD:
			return ADD;
		case Constants.CG_SUB:
			return SUB;
		case Constants.CG_MULT:
			return MULT;
		case Constants.CG_DIVI:
			return DIVI;
		case Constants.CG_INV:
			return INV;
		case Constants.CG_AND:
			return AND;
		case Constants.CG_OR:
			return OR;
		case Constants.CG_NEG:
			return NEG;
		case Constants.CG_CME:
			return CME;
		case Constants.CG_CMA:
			return CMA;
		case Constants.CG_CEQ:
			return CEQ;
		case Constants.CG_CDIF:
			return CDIF;
		case Constants.CG_CMEQ:
			return CMEQ;
		case Constants.CG_CMAQ:
			return CMAQ;
		case Constants.CG_START:
			return START;
		case Constants.CG_HALT:
			return HLT;
		case Constants.CG_RD:
			return RD;
		case Constants.CG_PRINT:
			return PRN;
		case Constants.CG_LDC:
			return LDC;
		case Constants.CG_LDV:
			return LDV;
		case Constants.CG_STR:
			return STR;
		case Constants.CG_JUMP:
			return JMP;
		case Constants.CG_JMPF:
			return JMPF;
		case Constants.CG_CALL:
			return CALL;
		case Constants.CG_ALLOC:
			return ALLOC;
		case Constants.CG_DALLOC:
			return DALLOC;
		case Constants.CG_RETURN:
			return RETURN;
		case Constants.CG_RETURNF:
			return RETURNF;
		case Constants.CG_NULL:
			return NULL;
		default:
			throw new Exception("Invalid instruction");
		}
	}
}

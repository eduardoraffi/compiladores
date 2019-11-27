package virtual_machine;

import java.util.*;
import javax.swing.JOptionPane;

public class InstructionProcessor {

	private Vector<Instruction> mInstructionVector = null;
	private VMStack mStack = null;
	private boolean isEndOfStack = false;
	private int newValue = 0;
	private String output = "";
	int mInstruction = 0;

	public InstructionProcessor(String arquivo) throws Exception {
		mInstructionVector = new VMFileReader(arquivo).parsearPalavras();
		mInstruction = 0;
	}

	public Vector<Instruction> getInstrucoes() {
		return mInstructionVector;
	}

	public VMStack getStack() {
		return mStack;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput() {
		output = null;
	}

	public boolean isStackReachEnd() {
		if (isEndOfStack)
			return true;

		return false;
	}

	public void runInstruction() {
		if (mInstruction < 0 || mInstruction < mInstructionVector.size()) {
			runInstruction(mInstructionVector.get(mInstruction));
			mInstruction++;
		}
	}

	private void runInstruction(Instruction instrucao) {
		int type = InstructionTypeAndName.getInstructionType(instrucao.getInstrucao());
		switch (type) {
		case 0:
			runSimpleExpression((SimpleInstruction) instrucao);
			break;
		case 1:
			runComposedExpression((ComposedInstruction) instrucao);
			break;
		case 2:
			runDoubleComposedExpression((DoubleComposedInstruction) instrucao);
			break;
		default:
			break;
		}
	}

	private void runSimpleExpression(SimpleInstruction instrucao) {
		switch (instrucao.getInstrucao()) {
		case ADD:
			add();
			break;
		case SUB:
			sub();
			break;
		case MULT:
			mult();
			break;
		case DIVI:
			divi();
			break;
		case INV:
			inv();
			break;
		case AND:
			and();
			break;
		case OR:
			or();
			break;
		case NEG:
			neg();
			break;
		case CME:
			cme();
			break;
		case CMA:
			cma();
			break;
		case CEQ:
			ceq();
			break;
		case CDIF:
			cdif();
			break;
		case CMEQ:
			cmeq();
			break;
		case CMAQ:
			cmaq();
			break;
		case START:
			start();
			break;
		case HLT:
			hlt();
			break;
		case NULL:
			nul();
			break;
		case RD:
			rd();
			break;
		case PRN:
			prn();
			break;
		case RETURN:
			ret();
			break;

		default:
			break;
		}

	}

	private void add() {
		// M[s-1] := M[s-1] + M[s]; s := s-1
		newValue = mStack.getValue(2) + mStack.getValue(1);
		mStack.setValue(2, newValue);
		mStack.pop();
	}

	private void sub() {
		// M[s-1]:=M[s-1] - M[s]; s:=s - 1
		newValue = mStack.getValue(2) - mStack.getValue(1);
		mStack.setValue(2, newValue);
		mStack.pop();
	}

	private void mult() {
		// M[s-1]:=M[s-1] * M[s]; s:=s - 1
		newValue = mStack.getValue(2) * mStack.getValue(1);
		mStack.setValue(2, newValue);
		mStack.pop();
	}

	private void divi() {
		// M[s-1]:=M[s-1] div M[s]; s:=s - 1
		newValue = mStack.getValue(2) / mStack.getValue(1);
		mStack.setValue(2, newValue);
		mStack.pop();
	}

	private void inv() {
		// M[s]:= -M[s]
		newValue = -mStack.getValue(1);
		mStack.setValue(1, newValue);
	}

	private void and() {
		// se M [s-1] = 1 e M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2) == 1) && (mStack.getValue(1) == 1)) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void or() {
		// se M[s-1] = 1 ou M[s] = 1 então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2) == 1) || (mStack.getValue(1) == 1)) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void neg() {
		// M[s]:= 1-M[s]
		newValue = 1 - mStack.getValue(1);
		mStack.setValue(mStack.tamPilha() - 1, newValue);
	}

	private void cme() {
		// se M[s-1] < M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) < (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void cma() {
		// se M[s-1] > M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) > (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void ceq() {
		// se M[s-1] = M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) == (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void cdif() {
		// se M[s-1] != M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) != (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void cmeq() {
		// se M[s-1] <= M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) <= (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void cmaq() {
		// se M[s-1] >= M[s] então M[s-1]:=1 senão M[s-1]:=0; s:=s - 1
		if ((mStack.getValue(2)) >= (mStack.getValue(1))) {
			mStack.setValue(2, 1);
		} else {
			mStack.setValue(2, 0);
		}
		mStack.pop();
	}

	private void start() {
		// S:=-1
		mStack = new VMStack();
	}

	private void hlt() {
		isEndOfStack = true;
	}

	private void nul() {
		// nada a se fazer.
	}

	private void rd() {
		// S:=s+1; M[s]:=proxima entra
		String value = JOptionPane.showInputDialog(null, "Valor:", "Entrada de valor", JOptionPane.QUESTION_MESSAGE);
		newValue = Integer.parseInt(value);
		mStack.push(mStack.tamPilha(), newValue);
	}

	private void prn() {
		// M[s];s:=s-1;
		output = String.valueOf(mStack.getValue(1));
		mStack.pop();
	}

	private void ret() {
		mInstruction = (mStack.getValue(1)) - 1;
		mStack.pop();
	}

	private void runComposedExpression(ComposedInstruction instruction) {
		switch (instruction.getInstrucao()) {
		case LDC:
			ldc(instruction.getParameter());
			break;
		case LDV:
			ldv(instruction.getParameter());
			break;
		case STR:
			str(instruction.getParameter());
			break;
		case JMP:
			jmp(instruction.getParameter());
			break;
		case JMPF:
			jmpf(instruction.getParameter());
			break;
		case CALL:
			call(instruction.getParameter());
			break;
		default:
			break;
		}
	}

	private void ldc(int param) {
		// S:=s+1 ; M[s]:=k
		mStack.push(mStack.tamPilha(), param);
	}

	private void ldv(int param) {
		// S:=s+1 ; M[s]:=M[n]
		mStack.pushPos(param);
	}

	private void str(int param) {
		// M[n]:=M[s]; s;=s-1;
		mStack.setFixedValue(param, mStack.getValue(1));
		mStack.pop();
	}

	private void jmp(int param) {
		// desviar sempre instrucao:=param
		mInstruction = param;
	}

	private void jmpf(int param) {
		// desviar caso falso se M[s]=0, entao i:=att1, senao i:=i+1
		newValue = mStack.getValue(1);
		if (newValue == 0)
			mInstruction = param;
		mStack.pop();
	}

	private void call(int param) {
		mStack.push(mStack.tamPilha(), mInstruction + 1);
		mInstruction = param;
	}

	private void runDoubleComposedExpression(DoubleComposedInstruction instruction) {
		switch (instruction.getInstrucao()) {
		case ALLOC:
			alloc(instruction.getParameter1(), instruction.getParameter2());
			break;
		case DALLOC:
			dalloc(instruction.getParameter1(), instruction.getParameter2());
			break;
		default:
			break;
		}
	}

	private void alloc(int p1, int p2) {
		// {s:=s + 1; M[s]:=M[m+k]}
		for (int k = 0; k < (p2); k++) {
			mStack.push(mStack.tamPilha(), 0);
			mStack.setValue(1, mStack.getFixedValue(p1 + k));
		}
	}

	private void dalloc(int p1, int p2) {
		// {M[m+k]:=M[s]; s:=s - 1}
		for (int k = (p2 - 1); k >= 0; k--) {
			mStack.setFixedValue(p1 + k, mStack.getValue(1));
			mStack.pop();
		}
	}
}

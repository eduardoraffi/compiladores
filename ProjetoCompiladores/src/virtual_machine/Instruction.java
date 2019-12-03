package virtual_machine;

public class Instruction {

	protected InstructionTypeAndName mInstructionTypeAndName;
	protected int mLine;

	public Instruction(InstructionTypeAndName instructionTypeAndName, int line) throws Exception {
		mInstructionTypeAndName = instructionTypeAndName;
		if (line < 0) {
			throw new Exception("line cannot be lower than zero");
		}
		mLine = line;
	}

	public InstructionTypeAndName getInstrucao() {
		return mInstructionTypeAndName;
	}

	public int getLinha() {
		return mLine;
	}

	@Override
	public String toString() {
		return "\nInstruction: " + mInstructionTypeAndName.name() + "\nLine: " + mLine + "\n";
	}
}
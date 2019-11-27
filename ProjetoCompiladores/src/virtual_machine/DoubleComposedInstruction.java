package virtual_machine;

public class DoubleComposedInstruction extends Instruction {
	private int mParameter1;
	private int mParameter2;

	public DoubleComposedInstruction(InstructionTypeAndName instructionTypeAndName, int line, int parameter1,
			int parameter2) throws Exception {
		super(instructionTypeAndName, line);
		if (InstructionTypeAndName.getInstructionType(instructionTypeAndName) != 2) {
			throw new Exception("instrucao de outro tipo");
		}
		if (parameter1 < 0) {
			throw new Exception("p1 nao pode ser menor que zero");
		}
		if (parameter2 < 0) {
			throw new Exception("p2 nao pode ser menor que zero");
		}

		mParameter1 = parameter1;
		mParameter2 = parameter2;
	}

	public int getParameter1() {
		return mParameter1;
	}

	public int getParameter2() {
		return mParameter2;
	}

	@Override
	public String toString() {
		return "\nInstruction: " + mInstructionTypeAndName.name() + "\nParameter1: " + mParameter1 + "\nParameter2: "
				+ mParameter2 + "\nLine: " + mLine + "\n";
	}
}
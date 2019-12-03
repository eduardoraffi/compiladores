package virtual_machine;

public class ComposedInstruction extends Instruction {
	private int mParameter;

	public ComposedInstruction(InstructionTypeAndName instructionTypeAndName, int line, int parameter)
			throws Exception {
		super(instructionTypeAndName, line);
		if (InstructionTypeAndName.getInstructionType(instructionTypeAndName) != 1) {
			throw new Exception("Different instruction types!");
		}

		if (parameter < 0) {
			throw new Exception("parameter cannot be lower than zero");
		}
		mParameter = parameter;
	}

	int getParameter() {
		return mParameter;
	}

	@Override
	public String toString() {
		return "\nInstruction: " + mInstructionTypeAndName.name() + "\nParameter: " + mParameter + "\nLine:" + mLine
				+ "\n";
	}
}
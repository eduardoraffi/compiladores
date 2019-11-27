package virtual_machine;

public class SimpleInstruction extends Instruction {

	public SimpleInstruction(InstructionTypeAndName instructionTypeAndName, int line) throws Exception {
		super(instructionTypeAndName, line);
		if (InstructionTypeAndName.getInstructionType(instructionTypeAndName) != 0) {
			throw new Exception("instrucao de outro tipo");
		}
	}
}

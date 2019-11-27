package virtual_machine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class VMFileReader {

	private enum InstructionType {
		SIMPLE(0), COMPOSED(1), DOUBLE_COMPOSED(2);

		private final int code;

		private InstructionType(int code) {
			this.code = code;
		}

		public int toInt() {
			return code;
		}
	}

	private Vector<String> mContentFile;

	public VMFileReader(String path) {
		mContentFile = new Vector<String>();
		BufferedReader bufferedReader = null;
		File file = new File(path);
		if (file.exists()) {
			try {
				FileReader fileReader = new FileReader(path);
				bufferedReader = new BufferedReader(fileReader);
				String actualLine;

				while ((actualLine = bufferedReader.readLine()) != null) {
					mContentFile.add(actualLine);

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bufferedReader != null)
						bufferedReader.close();
				} catch (IOException e) {
					System.err.printf("Erro na leitura do arquivo: %s.\n", e.getMessage());
				}
			}
		}
	}

	private static int numeroDeAtributos(String instructionType) {
		switch (instructionType) {
		case Constants.CG_LDC:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_LDV:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_STR:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_JUMP:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_JMPF:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_CALL:
			return InstructionType.COMPOSED.toInt();
		case Constants.CG_ALLOC:
			return InstructionType.DOUBLE_COMPOSED.toInt();
		case Constants.CG_DALLOC:
			return InstructionType.DOUBLE_COMPOSED.toInt();
		default:
			return InstructionType.SIMPLE.toInt(); // any different code return 0 -> simple type instruction
		}
	}

	public Vector<Instruction> parsearPalavras() throws Exception {
		Vector<Instruction> instructionVector = new Vector<Instruction>();
		Vector<Label> labels = new Vector<Label>();
		int parameter1 = 0;
		int parameter2 = 0;

		for (int i = 0; i < mContentFile.size(); i++) {
			String[] l = mContentFile.get(i).split("\t");
			String toRes = "";
			if (!l[0].equals("")) {
				labels.add(new Label(i, l[0]));
			}
			for (int j = 1; j < l.length; j++) {
				toRes += l[j];
				toRes += "\t";
				mContentFile.set(i, toRes);
			}
		}

		for (int i = 0; i < mContentFile.size(); i++) {
			String[] result = mContentFile.get(i).split("\t");
			if (numeroDeAtributos(result[0]) == 0) {
				SimpleInstruction e = new SimpleInstruction(InstructionTypeAndName.getInstructionName(result[0]), i);
				instructionVector.add(e);
			}

			if (numeroDeAtributos(result[0]) == 1) {
				if (result[0].equals(Constants.CG_JUMP) || result[0].equals(Constants.CG_JMPF)
						|| result[0].equals(Constants.CG_CALL)) {
					int j;
					for (j = 0; j < labels.capacity(); j++) {
						if (labels.get(j).getLabel().equals(result[1]))
							break;
					}
					if (j == labels.capacity())
						throw new Exception("label invalido");

					parameter1 = labels.get(j).getLinha();
				} else {
					parameter1 = Integer.parseInt(result[1]);
				}
				ComposedInstruction e = new ComposedInstruction(InstructionTypeAndName.getInstructionName(result[0]), i,
						parameter1);
				instructionVector.add(e);
			}

			if (numeroDeAtributos(result[0]) == 2) {
				String[] aux = result[1].split(",");
				parameter1 = Integer.parseInt(aux[0]);
				parameter2 = Integer.parseInt(aux[1]);
				DoubleComposedInstruction e = new DoubleComposedInstruction(
						InstructionTypeAndName.getInstructionName(result[0]), i, parameter1, parameter2);
				instructionVector.add(e);
			}
		}
		return instructionVector;
	}
}
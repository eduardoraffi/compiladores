package compilador;

import java.io.File;
import java.util.Formatter;

public class CodeGenerator {

	private File mFile;
	private Formatter mFormmater;

	public CodeGenerator() throws Exception {
		try {
			mFile = new File(Constants.PATH_CODE_GEN);
			if (mFile.exists())
				mFile.delete();

			mFile.createNewFile();
			mFormmater = new Formatter(mFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateLabel(int labelNum) {
		mFormmater.format(Constants.CG_LABEL + labelNum + "\tNULL\n");
		mFormmater.flush();
	}

	public void generateCommand(String operator) {
		mFormmater.format("\t" + operator + "\n");
		mFormmater.flush();
	}

	public void generateCommand(String operator, String firstArg) {
		mFormmater.format("\t" + operator + "\t" + firstArg + "\n");
		mFormmater.flush();
	}

	public void generateCommand(String operator, int firstArg) {
		mFormmater.format("\t" + operator + "\t" + firstArg + "\n");
		mFormmater.flush();
	}

	public void generateCommand(String operator, String firstArg, String secondArg) {
		mFormmater.format("\t" + operator + "\t" + firstArg + "," + secondArg + "\n");
		mFormmater.flush();
	}

	public void generateCommand(String operator, int firstArg, int secondArg) {
		mFormmater.format("\t" + operator + "\t" + firstArg + "," + secondArg + "\n");
		mFormmater.flush();
	}

	public void close() {
		mFormmater.close();
	}
}
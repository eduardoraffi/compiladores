package virtual_machine;

public class Label {

	private int mLine;
	private String mLabel;

	public Label(int line, String label) throws Exception {
		if (line < 0)
			throw new Exception("linha inválida");
		if (label.equals(""))
			throw new Exception("linha inválida");

		this.mLine = line;
		this.mLabel = label;
	}

	public int getLinha() {
		return mLine;
	}

	public String getLabel() {
		return mLabel;
	}
}

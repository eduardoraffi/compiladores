package virtual_machine;

public class Label {

	private int mLine;
	private String mLabel;

	public Label(int line, String label) throws Exception {
		if (line < 0)
			throw new Exception("Invalid line. Must be greater than zero.");
		if (label.equals(""))
			throw new Exception("Invalid label. label is empty.");

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

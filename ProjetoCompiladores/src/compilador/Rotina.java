package compilador;

public class Rotina implements GenericType {

	private int mLabel;
	private final String mType;

	public Rotina(String type) throws Exception {
		if (type == null)
			throw new Exception("tipo invalido");

		mType = type;
	}

	public Rotina(String type, int label) throws Exception {
		if (type == null)
			throw new Exception("tipo invalido");

		mType = type;
		mLabel = label;
	}

	@Override
	public String getType() {
		return mType;
	}

	@Override
	public int getInfo() {
		return mLabel;
	}

	@Override
	public void setInfo(int info) {
		mLabel = info;
	}

}
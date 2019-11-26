package compilador;

import compilador.Errors.ErrorType;

public class DeclaredVar implements GenericType {

	private int mStackPosition;
	private String mType;

	public DeclaredVar(String type) throws Exception {
		if (type == null)
			Errors.generalError(ErrorType.INVALID_TYPE);

		mType = type;
	}

	public DeclaredVar(String type, int pos) throws Exception {
		if (type == null)
			Errors.generalError(ErrorType.INVALID_TYPE);

		mType = type;
		mStackPosition = pos;
	}

	@Override
	public String getType() {
		return mType;
	}

	@Override
	public int getInfo() {
		return mStackPosition;
	}

	@Override
	public void setInfo(int pos) {
		mStackPosition = pos;
	}

}
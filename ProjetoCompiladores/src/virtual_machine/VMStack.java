package virtual_machine;

import java.util.Vector;

public class VMStack {

	public int mPosition = 0;
	private Vector<StackContent> mStackContentVector = new Vector<StackContent>();
	private StackContent mStackContent = new StackContent();

	public VMStack() {
		this.mPosition = 0;
	}

	public boolean pilhaVazia() {
		if (this.mPosition < 1) {
			return true;
		} else
			return false;
	}

	public int tamPilha() {
		if (!pilhaVazia()) {
			return mStackContentVector.size();
		} else
			return 0;
	}

	public void push(int address, int value) {
		mStackContent = new StackContent();
		mStackContent.setAdress(address);
		mStackContent.setValor(value);
		mStackContentVector.add(mStackContent);
		this.mPosition++;
	}

	public void pushPos(int address) {
		mPosition++;
		mStackContent = new StackContent();
		mStackContent.setAdress(mStackContentVector.size());
		mStackContent.setValor(mStackContentVector.get(address).mValue);
		mStackContentVector.add(mStackContent);
	}

	public StackContent pop() {
		mPosition--;
		return mStackContentVector.remove(mStackContentVector.size() - 1);
	}

	public int getEnd(int pos) {
		return mStackContentVector.get(mStackContentVector.size() - pos).getAdress();
	}

	public int getValue(int pos) {
		return mStackContentVector.get((mStackContentVector.size() - pos)).getValor();
	}

	public int getFixedValue(int pos) {
		return mStackContentVector.get(pos).getValor();
	}

	public void setValue(int pos, int value) {
		mStackContent = new StackContent();
		int address = mStackContentVector.get(mStackContentVector.size() - pos).getAdress();
		mStackContent.setAdress(address);
		mStackContent.setValor(value);
		mStackContentVector.set(mStackContentVector.size() - pos, mStackContent);
	}

	public void setFixedValue(int pos, int value) {
		mStackContent = new StackContent();
		mStackContent.setAdress(pos);
		mStackContent.setValor(value);
		mStackContentVector.set(pos, mStackContent);
	}

}

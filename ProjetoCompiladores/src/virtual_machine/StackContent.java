package virtual_machine;

public class StackContent {

	public int mAddress;
	public int mValue;

	public StackContent() {
		this.mValue = 0;
		this.mAddress = 0;
	}

	public StackContent(int address, int value) {
		this.mValue = value;
		this.mAddress = address;
	}

	public int getValor() {
		return mValue;
	}

	public void setValor(int valor) {
		this.mValue = valor;
	}

	public int getAdress() {
		return mAddress;
	}

	public void setAdress(int adress) {
		this.mAddress = adress;
	}

}

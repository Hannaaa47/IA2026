package ExamenDiagnostico;

public class Nodo {
	private int valor;
	private Nodo der, izq;

	public Nodo(int valor, Nodo der, Nodo izq) {
		this.valor = valor;
		this.der = der;
		this.izq = izq;
	}
	
	public Nodo(int valor) {
		this.valor = valor;
	}


	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Nodo getDer() {
		return der;
	}

	public void setDer(Nodo der) {
		this.der = der;
	}

	public Nodo getIzq() {
		return izq;
	}

	public void setIzq(Nodo izq) {
		this.izq = izq;
	}
	
	
	
}	

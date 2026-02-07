package ExamenDiagnostico;

public class Principal {
	public static void main(String args[]) {
		Arbol a = new Arbol();

		System.out.println(a.vacio());
		
		a.insertar(5);
		a.insertar(1);
		a.insertar(2);
		a.insertar(6);
		a.insertar(4);
		a.insertar(3);
		a.insertar(9);
		a.insertar(10);
		a.insertar(8);
		a.insertar(7);
		
		System.out.println(a.vacio());
		
		a.imprimirInorden();
	}
}

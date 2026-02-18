package ExamenDiagnostico;

public class Arbol {
	private Nodo raiz = null;
	
	public Arbol () {
	}
	
	public void insertar(int valor) {
		Nodo nuevo = new Nodo(valor);
		
		if (vacio()) {
			raiz = nuevo;
		} else {
			Nodo aux = raiz;
			while (aux != null) {
                if (aux.getDer() == null && aux.getIzq() == null) {
                    if (valor > aux.getValor()) {
                        aux.setDer(nuevo);
                        aux = null;
                    } else {
                        aux.setIzq(nuevo);
                        aux = null;
                    }
                } else if (valor > aux.getValor() && aux.getDer() == null) {
                    aux.setDer(nuevo);
                    aux = null;
                } else if (valor < aux.getValor() && aux.getIzq() == null) {
                    aux.setIzq(nuevo);
                    aux = null;
                } else {
                    if (valor > aux.getValor()) {
                        aux = aux.getDer();
                    } else {
                        aux = aux.getIzq();
                    }
                }
 
            }
		}
	}
	
	public boolean vacio() {
		return raiz == null;
	}
	
	public Nodo buscarNodo(int nombre) {
		Nodo aux = raiz;
        Nodo resultado = null;
 
        while (aux != null) {
            if (aux.getValor() == nombre) {
                resultado = aux;
                aux = null;
            } else if (aux.getDer() == null && aux.getIzq() == null) {
                aux = null;
            } else if (nombre > aux.getValor() && aux.getDer() != null) {
                aux = aux.getDer();
            } else if (nombre < aux.getValor() && aux.getIzq() != null) {
                aux = aux.getIzq();
            } else {
                aux = null;
            }
 
        }
        
        if (resultado != null) {
            return resultado;
        }else {
            return null;
        }
	}   
	
	

    private void imprimirInorden (Nodo act) {
        if (act != null)
        {    
            imprimirInorden (act.getIzq());
            System.out.print(act.getValor() + " ");
            imprimirInorden (act.getDer());
        }
    }

    public void imprimirInorden () {
        imprimirInorden (raiz);
        System.out.println();
    }


}

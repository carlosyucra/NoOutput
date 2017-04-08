package games.bcar.nooutput.Recurso;

/**
 * Created by bcar on 05/03/17.
 */

public class PuntoAdyacente  {
    PuntoD actual;
    PuntoD derecha;
    PuntoD izquierda;
    PuntoD arriba;
    PuntoD abajo;
    public PuntoAdyacente(PuntoD punto){
        this.actual = punto;
    }

    public PuntoD getActual() {
        return actual;
    }

    public void setActual(PuntoD actual) {
        this.actual = actual;
    }

    public PuntoD getDerecha() {
        return derecha;
    }

    public void setDerecha(PuntoD derecha) {
        this.derecha = derecha;
    }

    public PuntoD getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(PuntoD izquierda) {
        this.izquierda = izquierda;
    }

    public PuntoD getArriba() {
        return arriba;
    }

    public void setArriba(PuntoD arriba) {
        this.arriba = arriba;
    }

    public PuntoD getAbajo() {
        return abajo;
    }

    public void setAbajo(PuntoD abajo) {
        this.abajo = abajo;
    }
}

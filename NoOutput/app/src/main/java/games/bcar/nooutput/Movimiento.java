package games.bcar.nooutput;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by bcar on 04/03/17.
 */

public class Movimiento extends View {
    int STOP = 0;
    int DERECHA = 1;
    int IZQUIERDA = 2;
    int ABAJO = 3;
    int ARRIBA = 4;
    int estadoActual = STOP;

    Thread apretando;
    public Movimiento(Context context){
        super(context);
        init();
    }
    public Movimiento(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    public Movimiento(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
    }
    Point inicial= new Point();
    boolean demo = false;
    Laberinto laberinto;
    public void apretando(){
        apretando= new Thread(new Runnable() {
            @Override
            public void run() {
                laberinto = (Laberinto) findViewById(R.id.laberinto);
                while (true) {
                    if(((Laberinto) findViewById(R.id.laberinto)).player.estadoActual==STOP)
                        ((Laberinto) findViewById(R.id.laberinto)).player.cambioDireccion(estadoActual);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        apretando.start();
    }
    public int direccion(int x, int y){
        int difX = inicial.x-x;
        int difY = inicial.y-y;

        if(Math.abs(difX)<10 && Math.abs(difY)<10){
            return -1;
        }

        if(Math.abs(difX)>Math.abs(difY)){//IZQUIERDA O DERECHA
            if(inicial.x>x){
               // System.out.println("IZQUIERDA");
                estadoActual = IZQUIERDA;
                return IZQUIERDA;
            }else{
                //System.out.println("DERECHA");
                estadoActual = DERECHA;
                return DERECHA;
            }
        }else{//ARRIBA ABAJO
            if(inicial.y>y){
                //System.out.println("ARRIBA");
                estadoActual = ARRIBA;
                return ARRIBA;
            }else{
                //System.out.println("ABAJO");
                estadoActual = ABAJO;
                return ABAJO;
            }
        }
    }
    int acumulado;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(laberinto.pintarDemo==true)
                    System.exit(0);
                //Aqui guardas en una variable privada de clase las coordenadas del primer toque:
                inicial.set((int)event.getX(),(int)event.getY());
                estadoActual = -1;
                acumulado = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                acumulado++;
                if(acumulado>3){
                    int nuevo = direccion((int)event.getX(),(int)event.getY());
                    if(nuevo != -1 && nuevo!=estadoActual){
                        inicial.set((int)event.getX(),(int)event.getY());
                        estadoActual = nuevo;
                        acumulado=0;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                estadoActual = -1;
                break;
        }
        return true;
    }
}

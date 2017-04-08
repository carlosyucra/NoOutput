package games.bcar.nooutput;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import games.bcar.nooutput.Recurso.PuntoD;

/**
 * Created by bcar on 07/03/17.
 */

public class Control extends Thread{
    Laberinto laberinto;
    Paint fondo;
    Paint texto;
    int ancho,alto;
    int numero;
    int salidaX,salidaY;

    boolean dibujarSalida;
    Bitmap salida;
    Bitmap aviso;
    Bitmap aviso1;
    Bitmap aviso2;
    int avisoPosX;

    public Control(Laberinto laberinto){
        this.laberinto = laberinto;
        this.alto = laberinto.altoPantalla;
        this.ancho = laberinto.anchoPantalla;
        //CARGAR SALIDA
        salida = imagenScale(laberinto.LX,laberinto.LX*2,R.drawable.salida);

        setIncial();
        fondo = new Paint();
        fondo.setARGB(120,255,255,255);
        fondo.setStrokeWidth(3);
        fondo.setStyle(Paint.Style.FILL);

        texto = new Paint();
        texto.setColor(Color.BLACK);
        texto.setStrokeWidth(5);
        texto.setTextSize(laberinto.LY*2);

        //Para avisos
        aviso1 = imagenScale(ancho/2,laberinto.LY*2,R.drawable.aviso1);
        aviso2 = imagenScale(ancho/2,laberinto.LY*2,R.drawable.aviso2);
        aviso = aviso1;
        avisoPosX = alto+5;
    }
    public void setIncial(){
        numero = 30;
    }
    public Bitmap imagenScale(int ancho,int alto, int id){
        Bitmap tmp = BitmapFactory.decodeResource(laberinto.getResources(),id);
        tmp = Bitmap.createScaledBitmap(tmp,ancho,alto,true);
        return tmp;
    }
    public String convertString(int id){
        //LO QUE HACE EL PROGRAMA ES DEVOLVER UN NUMERO DE ACUERDO CON LA PALABRA, a=0,b=1,c=2,...
        String[] ids={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
                ,"a1","b1","c1","d1","e1","f1","g1","h1","i1","j1","k1","l1","m1","n1","o1","p1","q1","r1","s1","t1","u1","v1","w1","x1","y1","z1"
                ,"a2","b2","c2","d2","e2","f2","g2","h2","i2","j2","k2","l2","m2","n2","o2","p2","q2","r2","s2","t2","u2","v2","w2","x2","y2","z2"
                ,"a3","b3","c3","d3","e3","f3","g3","h3","i3","j3","k3","l3","m3","n3","o3","p3","q3","r3","s3","t3","u3","v3","w3","x3","y3","z3"};
        return ids[id];
    }
    public int azar( int max){
        return (int) (Math.random()*max);
    }
    boolean dibujarAviso=false;
    public void mostrarAviso(){
        dibujarAviso = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int estado = 1;
                while(laberinto.ejecutarJuego){
                    try {
                        if(estado==1) {
                            if (avisoPosX >= alto - laberinto.LY * 2+5)
                                avisoPosX--;
                            else {
                                estado = 2;
                            }
                        }else if(estado==2){
                            Thread.sleep(3000);
                            estado = 3;
                        }else if(estado==3){
                            if(avisoPosX<alto){
                                avisoPosX++;
                            }else{
                                estado = 4;
                            }
                        }else if(estado == 4){
                            break;
                        }
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dibujarAviso=false;
            }
        }).start();
    }
    public void dibujara(Canvas canvas){
        canvas.drawCircle(ancho/2,laberinto.LY,laberinto.LY+laberinto.LY/2,fondo);
        canvas.drawText(""+numero,ancho/2-laberinto.LY,laberinto.LY*2,texto);
        if(dibujarSalida==true)
            canvas.drawBitmap(salida,salidaX,salidaY,null);
        if(dibujarAviso==true){
            canvas.drawBitmap(aviso,10,avisoPosX,null);
        }

    }
    public void run(){
        while (laberinto.ejecutarJuego){
            try {
                numero--;
                Thread.sleep(1000);
                if(numero<=0){
                    PuntoD punto = laberinto.vertices.get(azar(laberinto.vertices.size()-1));
                    salidaX = punto.getX()*laberinto.LX-laberinto.LX/2;
                    salidaY = punto.getY()*laberinto.LY-laberinto.LX/2;
                    dibujarSalida = true;
                    aviso = aviso2;
                    setIncial();
                }
                if(numero==28){
                    mostrarAviso();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
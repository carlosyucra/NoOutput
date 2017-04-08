package games.bcar.nooutput;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import games.bcar.nooutput.Recurso.Mapas;
import games.bcar.nooutput.Recurso.MatrizAdyacente;
import games.bcar.nooutput.Recurso.NodoD;
import games.bcar.nooutput.Recurso.PuntoD;

/**
 * Created by bcar on 03/03/17.
 */

public class Laberinto extends View {
    int anchoPantalla;
    int altoPantalla;
    boolean dibujar;
    Bitmap laberinto1;
    Bitmap laberinto2;
    Control control;
    Paint paint;  //Para las lineas
    static PlayerV2 player;

    //Enemigos
    ArrayList<Enemy> enemigos = new ArrayList<>();

    //Parametros para dibujo
    int LX,LY;  //Es para el movimiento de los personajes
    public ArrayList<PuntoD> vertices=new ArrayList<PuntoD>();
    public ArrayList<NodoD> nodos=new ArrayList<NodoD>();
    MatrizAdyacente adyacentes;
    int velocidad;
    boolean ejecutarJuego = true;

    int nivel;

    public Laberinto(Context context){
        super(context);
        init();
    }
    public Laberinto(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    public Laberinto(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        dibujar = false;
        SharedPreferences prefe=super.getContext().getSharedPreferences("datos",Context.MODE_PRIVATE);
        velocidad =prefe.getInt("velocidad",2);
        nivel = 1;
        paint =new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        llenarGrafo();
        musica();
    }
    public void musica(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer media = MediaPlayer.create(getContext(),R.raw.musicajuego);
                media.setLooping(true);
                media.start();
            }
        }).start();
    }
    public void llenarGrafo(){
        llenarVertices(Mapas.getMapa(nivel)[0]);
        llenarNodos(Mapas.getMapa(nivel)[1]);
        adyacentes = new MatrizAdyacente(vertices,nodos);
    }
    public void postInit(){

        anchoPantalla = JuegoActivity.pantalla.x;
        altoPantalla = JuegoActivity.pantalla.y;
        LX = JuegoActivity.pantalla.x/16/2;
        LY = JuegoActivity.pantalla.y/12/2;

        //CARGAR LOS LABERINTOS
        laberinto1 = imagenScale(anchoPantalla, altoPantalla, R.drawable.laberinto1);
        laberinto2 = imagenScale(anchoPantalla, altoPantalla, R.drawable.laberinto2);

        //CARGAR LOS PERSONAJES
        player = new PlayerV2(this);

        dibujar = true; //PERMITE QUE SE DIBUJE LOS CAMBIOS EN PANTALLA

        player.hilo();
        repintar();
        control = new Control(this);
        control.start();
        juegoNiveles();


    }
    public void nextNivel(){
        nivel++;
        juegoNiveles();
    }
    public void juegoNiveles(){
        if(nivel==1){
            enemigos.clear();
            enemigos.add(new Enemy(this,"m"));
            enemigos.get(0).start();
        }else if(nivel==2){
            dibujar = false;
            enemigos.clear();
            enemigos.add(new Enemy(this,"p"));
            enemigos.add(new Enemy(this,"u"));
            control.dibujarSalida = false;
            control.aviso = control.aviso1;
            control.numero = 30;
            enemigos.get(0).tiempo=17;
            enemigos.get(0).start();
            enemigos.get(1).start();
            dibujar = true;
        }else if(nivel==3){
            fin();
        }
    }
    //BORRAR SOLO DEMOSTRACION¡¡
    static boolean pintarDemo = false;
    boolean gano = false;
    Bitmap demostracion;
    public void fin(){
        /*for(Enemy e:enemigos)e.stop();
        enemigos.clear();*/
        if(gano==false)
            demostracion = imagenScale(anchoPantalla,altoPantalla,R.drawable.perdiste);
        else
            demostracion = imagenScale(anchoPantalla,altoPantalla,R.drawable.ganaste);
        pintarDemo = true;
    }

    public Bitmap imagenScale(int ancho,int alto, int id){
        Bitmap tmp = BitmapFactory.decodeResource(getResources(),id);
        tmp = Bitmap.createScaledBitmap(tmp,ancho,alto,true);
        return tmp;
    }
    public void llenarVertices(String vert) {
        vertices.clear();
        String todo = vert;
        try {
            while (!todo.equals("")) {
                int priComa = todo.indexOf(",");
                int segComa = todo.indexOf(",", priComa+1);
                int punComa = todo.indexOf(";");
                String id=todo.substring(0,priComa);
                int px=Integer.parseInt(todo.substring(priComa+1,segComa));
                int py=Integer.parseInt(todo.substring(segComa+1,punComa));
                vertices.add(new PuntoD(id, px, py));
                todo = todo.substring(punComa+1, todo.length());
            }
        } catch (Exception e) {
            System.out.println("Error llenarVertices: " + e.getMessage()+" "+e);
            System.out.println("Recordar que el laberinto es de forma a,2,5;b,4,5;...;");
            System.exit(0);
        }
    }
    public void llenarNodos(String nodo) {
        nodos.clear();
        String todo = nodo;
        try{
            while(!todo.equals("")){
                int priComa = todo.indexOf(",");
                int punComa = todo.indexOf(";");
                String pri = todo.substring(0,priComa);
                String seg = todo.substring(priComa+1,punComa);
                nodos.add(new NodoD(vertices.get(convertID(pri)),vertices.get(convertID(seg))));
                todo=todo.substring(punComa+1,todo.length());
            }
        }catch(Exception e){
            System.out.println("Error llenarNodos: "+e.getMessage()+" "+e);
            System.out.println("Recordar que los nodos son de la forma a,b;c,a;d,e;...;");
            System.exit(0);
        }
    }
    public int convertID(String id){
        //LO QUE HACE EL PROGRAMA ES DEVOLVER UN NUMERO DE ACUERDO CON LA PALABRA, a=0,b=1,c=2,...
        String[] ids={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
                ,"a1","b1","c1","d1","e1","f1","g1","h1","i1","j1","k1","l1","m1","n1","o1","p1","q1","r1","s1","t1","u1","v1","w1","x1","y1","z1"
                ,"a2","b2","c2","d2","e2","f2","g2","h2","i2","j2","k2","l2","m2","n2","o2","p2","q2","r2","s2","t2","u2","v2","w2","x2","y2","z2"
                ,"a3","b3","c3","d3","e3","f3","g3","h3","i3","j3","k3","l3","m3","n3","o3","p3","q3","r3","s3","t3","u3","v3","w3","x3","y3","z3"};
        for(int i=0;i<ids.length;i++){
            if(ids[i].equals(id)){
                return i;
            }
        }
        System.out.println("Error en convertID -1");
        return -1;
    }
    public void repaint() {
        this.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }
    public void repintar() {
        //do stuff that was in your original constructor...
        new Thread(new Runnable() {
            @Override
            public void run() {
                //player.derecha();
                while (ejecutarJuego) {
                    repaint();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected void onDraw(Canvas canvas) {
        if(!dibujar)return;
        if(pintarDemo){
            canvas.drawBitmap(demostracion,0,0,null);
            return;
        }
        canvas.drawBitmap(laberinto1,0,0,null);

        //Prueba
        for(NodoD n:nodos){
            canvas.drawLine(n.getPunto1().getX()*LX, n.getPunto1().getY()*LY, n.getPunto2().getX()*LX, n.getPunto2().getY()*LY,paint);
        }
        player.dibujar(canvas);
        for(Enemy e:enemigos){
            e.dibujar(canvas);
        }
        control.dibujara(canvas);
    }
}

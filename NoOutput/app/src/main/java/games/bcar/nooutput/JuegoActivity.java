package games.bcar.nooutput;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

public class JuegoActivity extends AppCompatActivity {

    public static Point pantalla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_juego);
        init();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ((Laberinto)findViewById(R.id.laberinto)).postInit();
        ((Movimiento)findViewById(R.id.movimiento)).apretando();
    }
    public void init(){
        Display display = this.getWindowManager().getDefaultDisplay();
        pantalla = new Point();
        display.getSize(pantalla);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ((Laberinto)findViewById(R.id.laberinto)).ejecutarJuego = false;
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
package games.bcar.nooutput;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Activity_configuracion extends AppCompatActivity {
    SeekBar seekBar;
    TextView textViewSeekBar;
    Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_configuracion);
        init();
    }
    public void init() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewSeekBar = (TextView) findViewById(R.id.textView);
        guardar = (Button)findViewById(R.id.guardar);
        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
        textViewSeekBar.setText(prefe.getInt("velocidad",2)+"");
        seekBar.setProgress(40*prefe.getInt("velocidad",2));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //private Toast toastStart = Toast.makeText(Activity_configuracion.this, getText(R.string.start), Toast.LENGTH_SHORT);
            //private Toast toastStop = Toast.makeText(Activity_configuracion.this, getText(R.string.stop), Toast.LENGTH_SHORT);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //la Seekbar siempre empieza en cero, si queremos que el valor mínimo sea otro podemos modificarlo
                if(progress<40){
                    textViewSeekBar.setText(1+"");
                }else if(progress<80){
                    textViewSeekBar.setText(2+"");
                }else if(progress<120){
                    textViewSeekBar.setText(3+"");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutar();
            }
        });
    }
    public void ejecutar() {
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putInt("velocidad", Integer.parseInt(textViewSeekBar.getText().toString()));
        editor.commit();
        finish();
    }
}

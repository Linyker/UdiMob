package pdsi2.udimob.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Linyker on 16/01/2015.
 */
public class ActivityNotification extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView txt=new TextView(this);

        txt.setText("Nesta tela virá as dicas de utilização do programa");
        setContentView(txt);

    }
}

package pdsi2.udimob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Linyker on 01/12/2014.
 */
public class ActivityEnviarEmail extends Activity{

    private String email_contato,de,para,assunto,mensagem;
    private EditText editDe,editPara,editAssunto,editMensagem;
    private Button botao_enviar,botao_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_email);

        //editDe = (EditText) findViewById(R.id.editDe);
        editPara = (EditText) findViewById(R.id.editPara);
        editAssunto = (EditText) findViewById(R.id.editAssunto);
        editMensagem = (EditText) findViewById(R.id.editMensagem);

        botao_enviar = (Button) findViewById(R.id.botao_enviar);
        botao_cancelar = (Button) findViewById(R.id.botao_cancelar);

        Intent i = getIntent();
        email_contato = i.getStringExtra("email_contato");
        editPara.setText(email_contato);


        botao_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                assunto = String.valueOf(editAssunto.getText());
                mensagem = String.valueOf(editMensagem.getText());
                para = String.valueOf(editPara.getText());
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{para});
                email.putExtra(Intent.EXTRA_SUBJECT, assunto);
                email.putExtra(Intent.EXTRA_TEXT,mensagem);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Escolha por onde quer enviar :"));

            }
        });




    }
}

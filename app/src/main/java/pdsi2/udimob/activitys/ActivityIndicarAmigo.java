package pdsi2.udimob.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pdsi2.udimob.R;

/**
 * Created by Linyker on 02/12/2014.
 */
public class ActivityIndicarAmigo extends Activity {

    private EditText editPara,editAssunto,editMensagem;
    private Button botao_enviar;
    private String para,assunto,mensagem;
    private String imobiliaria,endereco,email,telefone,bairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indicar_amigo);

        editPara = (EditText) findViewById(R.id.editPara);
        editAssunto = (EditText) findViewById(R.id.editAssunto);
        editMensagem = (EditText) findViewById(R.id.editMensagem);

        botao_enviar = (Button) findViewById(R.id.botao_enviar);

        Intent i = getIntent();
        imobiliaria = i.getStringExtra("imobiliaria");
        endereco = i.getStringExtra("endereco");
        email = i.getStringExtra("email");
        telefone = i.getStringExtra("telefone");
        bairro = i.getStringExtra("bairro");

        mensagem = "Olá!\n\nA imobiliária "+imobiliaria+" está anunciando um imovél.\nAchei que você teria interesse :)" +
                "\nO imóvel fica na "+endereco+" no bairro "+bairro+"" +
                "\n\n Segue os contatos da imobiliária : " +
                "\n Email: "+email+
                "\n Telefone: "+telefone;

        assunto = "Casa no bairro " + bairro;

        editMensagem.setText(mensagem);
        editAssunto.setText(assunto);
        botao_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                para = String.valueOf(editPara.getText());

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] {para});
                email.putExtra(Intent.EXTRA_SUBJECT,assunto);
                email.putExtra(Intent.EXTRA_TEXT,mensagem);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Escolha por onde quer enviar :"));
            }
        });

    }
}

package pdsi2.udimob.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import pdsi2.udimob.R;

/**
 * Created by Linyker on 16/01/2015.
 */
public class ActivityNotification extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.manual);

        TextView introducao = (TextView) findViewById(R.id.txtIntroducao);
        TextView conteudo = (TextView) findViewById(R.id.txtConteudo);
        TextView conteudo2 = (TextView) findViewById(R.id.txtConteudo2);

        introducao.setText(
                "   UdiMob é um aplicativo voltado para anuncios de venda e aluguel de imóveis " +
                "na cidade de Uberlândia-MG, nele é possível encontrar diversos tipos de casas e apartamentos " +
                "prontos para serem vendidos ou alugados\n\n    O UdiMob é simples e de facil utilização, o seu visual nada" +
                " poluído ajuda no entendimento e no manuseio durante a navegação no aplicativo");

        conteudo.setText("   O aplicativo exige que algumas funcionalidades do smartphone estejam habilitadas, como " +
                "Conexão com a internet e o GPS, caso você se esqueça de habilitar o aplicativo te enviará um alerta " +
                "com um redirecionamento para as páginas especificas para habilitar\n\n" +
                "   Com essas funcionalidades prontas, o aplicativo está pronto para uso, então vamos lá! \n\n" +
                "" +
                "   A primeira tela do aplicativo te fornece a opção de Venda ou Aluguel de um imóvel, simples não?\n\n" +
                "   Após ser feita a escolha aparecerá a tela com a lista de todos imóveis disponiveis na cidade," +
                "Se você preferir, você pode utilizar a busca acima da lista feita por bairro para fazer uma busca mais " +
                "focada. \n\n   Você não lembra exatamente o nome bairro que quer ? Não se preocupe, a busca te ajuda nisso," +
                "conforme você vai digitando ela te retorna as opções de bairros da cidade para te ajudar, não é legal ?\n\n" +
                "   Depois que você escolheu o bairro, basta apenas clicar no botão voltar do celular para sair do campo de busca " +
                "que o UdiMob te retornará os móveis, então agora é só clicar nele que você irá para a proxima tela dados mais" +
                " detalhados dos imóveis.\n\n" +
                "   Nessa tela você terá todas as informações disponibilizadas do imóvel, como endereço, telefone, descrição, entre outras.\n\n" +
                "   Você irá reparar que essa tela não contém nenhum botão, então como eu vou entrar em contato com o dono? Como vou ver o " +
                "imóvel no mapa? É bem simples, essas opções e outras estão no menu do celular, basta apenas você clicar no menu do seu " +
                "celular que você terá acesso a todas essas funcionalidades, segue o modelo do menu em alguns aparelhos :\n\n");



        conteudo2.setText("   Esse menu terá varias opções, como enviar e-mail, ligar para o proprietário, indicar o imóvel para um amigo" +
                " e ver a localização do imóvel no mapa, nessa tela de localização do imóvel, você terá além da visualização, dois opcionais," +
                " você poderá calcular a sua distancia do móvel e além disso você terá também a possibilidade de traçar uma rota da sua " +
                "posição até aonde o móvel se encontra.\n\n\n Agora é só você desfrutar do aplicativo !  =)");

    }
}

package pdsi2.udimob.conexao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import pdsi2.udimob.dto.Imovel;

public class ImovelRest {

	private static final String URL_WS = "http://192.168.100.24:8080/WebServiceREST/imovel/";

	public List<Imovel> getListaImovel() throws Exception {

		String[] resposta = new WebServiceCliente().get(URL_WS + "buscarTodosGSON");

		if (resposta[0].equals("200")) {
			Gson gson = new Gson();
			ArrayList<Imovel> listaImovel = new ArrayList<Imovel>();
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

			for (int i = 0; i < array.size(); i++) {
				listaImovel.add(gson.fromJson(array.get(i), Imovel.class));
			}
			return listaImovel;
		} else {
			throw new Exception(resposta[1]);
		}
	}
}

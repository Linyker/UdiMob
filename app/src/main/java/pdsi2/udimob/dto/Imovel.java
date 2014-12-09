package pdsi2.udimob.dto;

/**
 * Created by Linyker on 17/11/2014.
 */

public class Imovel {

    private String bairro;
    private String cidade;
    private String imagem_url;
    private String descricao;
    private String proprietario;
    private String email;
    private String telefone;
    private String endereco;

    public Imovel(String bairro, String cidade, String imagem_url, String descricao, String proprietario, String email, String telefone, String endereco) {
        this.bairro = bairro;
        this.cidade = cidade;
        this.imagem_url = imagem_url;
        this.descricao = descricao;
        this.proprietario = proprietario;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }



    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}

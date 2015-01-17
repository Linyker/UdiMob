package pdsi2.udimob.dto;

/**
 * Created by Linyker on 17/11/2014.
 */

public class Imovel {

    private String nome;

    private Integer idImovel;

    private Integer tipoImovel;

    private String usuario;

    private String logradouro;

    private Integer numero;

    private String bairro;

    private String descricaoImovel;

    private Double preco;

    private String email;

    private String telefone;

    private String imagem_url;

    public Imovel(String nome, Integer idImovel, Integer tipoImovel, String usuario, String logradouro, Integer numero, String bairro, String descricaoImovel, Double preco, String email, String telefone, String imagem_url) {
        this.nome = nome;
        this.idImovel = idImovel;
        this.tipoImovel = tipoImovel;
        this.usuario = usuario;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.descricaoImovel = descricaoImovel;
        this.preco = preco;
        this.email = email;
        this.telefone = telefone;
        this.imagem_url = imagem_url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
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

    public Integer getIdImovel() {
        return idImovel;
    }

    public void setIdImovel(Integer idImovel) {
        this.idImovel = idImovel;
    }

    public Integer getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(Integer tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricaoImovel() {
        return descricaoImovel;
    }

    public void setDescricaoImovel(String descricaoImovel) {
        this.descricaoImovel = descricaoImovel;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}

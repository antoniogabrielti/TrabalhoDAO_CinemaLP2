package dominio;

import java.util.Date;

public class Secao implements Comparable<Secao> {
    private Sala sala;
    private Date dataHora;
    private Filme filme;
    private int qtdDisponivel;
    private int id;

    public Secao(Sala sala, Date dataHora, Filme filme) {
        this.sala = sala;
        this.dataHora = dataHora;
        this.filme = filme;
        this.qtdDisponivel=sala.getCapacidade();
    }

    public Secao(Sala sala, Date hora, Filme f, int qtd, int idsecao) {
        this.sala = sala;
        this.dataHora = hora;
        this.filme = f;
        this.qtdDisponivel=qtd;
        this.id=idsecao;
    }


    public int getId() {
        return id;
    }

    
    public Sala getSala() {
        return sala;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public Filme getFilme() {
        return filme;
    }

    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdComprada) {
        this.qtdDisponivel = qtdDisponivel-qtdComprada;
    }
       

    @Override
    public int compareTo(Secao OutraSecao) {
        return (this.getDataHora().compareTo(OutraSecao.getDataHora()));
    }
     
}

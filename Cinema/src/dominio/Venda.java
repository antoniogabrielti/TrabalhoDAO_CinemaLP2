
package dominio;

import java.util.Date;

public class Venda implements Comparable<Venda> {
    private int idvenda;
    private Date DataHora;
    private Secao secao;
    private int qtdVendido;

    public Venda(Secao S, int qtdVendido,Date DataHora){
        this.secao=S;
        this.qtdVendido=qtdVendido;
        this.DataHora=DataHora;
    }
    public Venda(Date horario, Secao S, int qtdVendido,int id){
        this.DataHora=horario;
        this.secao=S;
        this.qtdVendido=qtdVendido;
        this.idvenda=id;
    }

    public int getIdvenda() {
        return idvenda;
    }
    
    public Secao getSecao() {
        return secao;
    }

    public int getQtdVendido() {
        return qtdVendido;
    }

    public Date getDataHora() {
        return DataHora;
    }

    public void setDataHora(Date DataHora) {
        this.DataHora = DataHora;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public void setQtdVendido(int qtdVendido) {
        this.qtdVendido = qtdVendido;
    }
    

    @Override
    public int compareTo(Venda OutraVenda) {
       return (this.getDataHora().compareTo(OutraVenda.getDataHora())); 
    }
}

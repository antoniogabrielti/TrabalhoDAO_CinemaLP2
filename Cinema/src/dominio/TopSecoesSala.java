
package dominio;

public class TopSecoesSala implements Comparable<TopSecoesSala> {
    private Sala sala;
    private Integer qtdVezesUtilizada;

    public TopSecoesSala(Sala S,int qtd) {
        this.qtdVezesUtilizada = new Integer(qtd);
        this.sala=S;
    }

    public Sala getSala() {
        return sala;
    }

    public Integer getQtdVezesUtilizada() {
        return qtdVezesUtilizada;
    }
    
    
    @Override
    public int compareTo(TopSecoesSala Top) {
        return (this.getQtdVezesUtilizada().compareTo(Top.getQtdVezesUtilizada()));
    }
}

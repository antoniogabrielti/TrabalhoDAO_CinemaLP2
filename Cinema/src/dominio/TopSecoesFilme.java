
package dominio;

public class TopSecoesFilme implements Comparable<TopSecoesFilme> {
    private Filme filme;
    private Integer qtdVezesVisto;

    public TopSecoesFilme(Filme F,int qtd) {
        this.qtdVezesVisto = new Integer(qtd);
        this.filme=F;
    }

    public Filme getFilme() {
        return filme;
    }

    public Integer getQtdVezesVisto() {
        return qtdVezesVisto;
    }
    
    @Override
    public int compareTo(TopSecoesFilme Top) {
        return (this.getQtdVezesVisto().compareTo(Top.getQtdVezesVisto()));
    }
}

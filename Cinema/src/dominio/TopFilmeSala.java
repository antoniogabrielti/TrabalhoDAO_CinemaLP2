
package dominio;

public class TopFilmeSala<T> implements Comparable<TopFilmeSala> {
    private T dado;
    private Integer qtdVendida;

    public TopFilmeSala(T dado,int qtd) {
        this.qtdVendida = new Integer(qtd);
        this.dado=dado;
    }

    public T getDado() {
        return dado;
    }

    public Integer getQtdVendida() {
        return qtdVendida;
    }
    
    
    @Override
    public int compareTo(TopFilmeSala Top) {
        return (this.getQtdVendida().compareTo(Top.getQtdVendida()));
    }
}

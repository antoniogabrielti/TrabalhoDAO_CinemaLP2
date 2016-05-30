
package dominio;

public class Filme implements Comparable<Filme> {
    private int codigo;
    private String nome;
    private String genero;
    private String sinopse;

    public Filme(int codigo, String nome, String genero, String sinopse) {
        this.codigo = codigo;
        this.nome = nome;
        this.genero = genero;
        this.sinopse = sinopse;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    @Override
    public int compareTo(Filme OutroFilme) {
        return (this.getNome().compareTo(OutroFilme.getNome()));
    }
    
    
}

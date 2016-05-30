
package repositorio;

import dominio.Filme;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioFilme {
    private List<Filme> filmes;

    public RepositorioFilme() {
        filmes = new ArrayList<Filme>();
    }

    public boolean addFilme(Filme F) {
       return filmes.add(F);
    }

    public List<Filme> getListaFilmes() {
        Collections.sort(filmes);
        return filmes;
    }
   public Filme buscarFilmePorCod(int codigo) {
        for (Filme F : filmes) {
            if (F.getCodigo()==codigo) {
                return F;
           }
        }
        return null;
    }
   public Filme buscarFilmePorNome(String Nome){
       for(Filme Filme : getListaFilmes()){
           if(Filme.getNome().equalsIgnoreCase(Nome)){
               return Filme;
           }
       }
       return null;
   }
}
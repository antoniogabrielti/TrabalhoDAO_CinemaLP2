
package dao;
import dominio.Filme;
import java.util.List;


public interface FilmeDao extends Dao<Filme>{
    public Filme buscarFilmePorCod(int codigo);
    public List<Filme> buscarFilmePorNome(String Nome);
 }


package dao;
import java.util.List;


public interface Dao<T> {
    public void salvar(T dominio);
    public void deletar(T dominio);
    public void atualizar(T dominio);
    public List<T> listar();
}

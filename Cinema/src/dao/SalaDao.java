
package dao;
import dominio.Sala;
import java.util.List;


public interface SalaDao extends Dao<Sala>{
    public Sala buscarSalaPorNumero(int numero);
    public List<Sala> buscarSalaPorCapacidade(int capacidade);
 }

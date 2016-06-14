
package dao;
import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import java.util.Date;
import java.util.List;


public interface SecaoDao extends Dao<Secao>{
    public List<Secao> buscarSecaoPorFilme(Filme f);
    public List<Secao> buscarSecaoPorHorario(Date hora);
    public List<Secao> buscarSecaoPorSala(Sala s);
    public Secao buscarSecaoPorID(int ID);
 }

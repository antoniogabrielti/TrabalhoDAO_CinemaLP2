
package dao;

import dominio.Filme;
import dominio.Sala;
import dominio.TopFilmeSala;
import dominio.Venda;
import java.util.Date;
import java.util.List;

public interface VendaDao extends Dao<Venda> {
    public List<Venda> buscarVendaPorFilme(Filme f);
    public List<Venda> buscarVendaPorSala(Sala s);
    public List<Venda> buscarVendaPorHorario(Date h);
    public Venda buscarVendaPorID(int ID);
    public TopFilmeSala TopSalaOrFilme(int opcao);
}

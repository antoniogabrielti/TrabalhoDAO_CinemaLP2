
package dao_impldb;

import dao.VendaDao;
import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import dominio.TopFilmeSala;
import dominio.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import negocio.SalaNegocio;
import negocio.SecaoNegocio;
import viewUI.UIUtil;

public class VendaDaoBd extends DaoBd<Venda> implements VendaDao {

    @Override
    public void salvar(Venda venda) {
         
        try {
            String sql = "INSERT INTO venda VALUES (?,?,?)";

         
            conectarObtendoId(sql);
            java.sql.Date dataSql = new java.sql.Date(venda.getDataHora().getTime());
            comando.setDate(1, dataSql);
            comando.setInt(2, venda.getSecao().getId());
            comando.setInt(3, venda.getQtdVendido());
           
            
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Venda no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Venda venda) {
        try {
            String sql = "DELETE FROM venda WHERE idvenda = ?";

            conectar(sql);
            comando.setInt(1, venda.getIdvenda());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Venda no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void atualizar(Venda venda) {
        try {
            String sql = "UPDATE venda SET datavenda=?, idsecao=?,qtdvendido=?"
                    + "WHERE idvenda=?";

            conectar(sql);
            java.sql.Date dataSql = new java.sql.Date(venda.getDataHora().getTime());
            comando.setDate(1, dataSql);
            comando.setInt(2, venda.getSecao().getId());
            comando.setInt(3, venda.getQtdVendido());
            comando.setInt(4, venda.getIdvenda());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Venda no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Venda> listar() {
        List<Venda> listaVenda = new ArrayList<>();

        String sql = "select * from venda;";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                java.sql.Date dataSql = resultado.getDate("datavenda");
                java.util.Date datavenda = new java.util.Date(dataSql.getTime());
                int idsecao = resultado.getInt("idsecao");
                int qtdvendido = resultado.getInt("qtdvendido");
                int idvenda = resultado.getInt("idvenda");

                
                SecaoNegocio SecN = new SecaoNegocio();
                Secao SC = SecN.procurarSecaoPorID(idsecao);
                
                            
                listaVenda.add(new Venda(datavenda,SC , qtdvendido, idvenda));
            

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as secoes do Banco de Dados!");
            throw new RuntimeException(ex);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } finally {
            fecharConexao();
        }

        return (listaVenda);
    }

    @Override
    public List<Venda> buscarVendaPorFilme(Filme f) {
        List<Venda> VendasFilme = new ArrayList<>();
        List<Venda> TodasVendas = this.listar();
        if(!TodasVendas.isEmpty()){
        for(Venda v : TodasVendas){
            if(v.getSecao().getFilme().getCodigo()==f.getCodigo()){
                VendasFilme.add(v);
            }
          }
        }
        return VendasFilme;
    }

    @Override
    public List<Venda> buscarVendaPorSala(Sala s) {
        List<Venda> VendasSala = new ArrayList<>();
        List<Venda> TodasVendas = this.listar();
        if(!TodasVendas.isEmpty()){
            for(Venda v : TodasVendas){
                if(v.getSecao().getSala().getNumero()==s.getNumero()){
                    VendasSala.add(v);
                }
            }
        }
        return VendasSala;
    }

    @Override
    public List<Venda> buscarVendaPorHorario(Date h) {
        List<Venda> VendaHorario = new ArrayList<>();
        List<Venda> TodasVendas = this.listar();
        if(!TodasVendas.isEmpty()){
            for(Venda v : TodasVendas){
                if(v.getDataHora().equals(h)){
                    VendaHorario.add(v);
                }
            }
        }
        return VendaHorario;
    }

    @Override
    public Venda buscarVendaPorID(int ID) {

        
        try {
            String sql = "select * from venda where idvenda=?;";
            
            conectar(sql);
            comando.setInt(1, ID);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                java.sql.Date dataSql = resultado.getDate("datavenda");
                java.util.Date datavenda = new java.util.Date(dataSql.getTime());
                int idsecao = resultado.getInt("idsecao");
                int qtdvendido = resultado.getInt("qtdvendido");
                int idvenda = resultado.getInt("idvenda");

                
                SecaoNegocio SecN = new SecaoNegocio();
                Secao SC = SecN.procurarSecaoPorID(idsecao);
                
                            
                return(new Venda(datavenda,SC , qtdvendido, idvenda));
            

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as secoes do Banco de Dados!");
            throw new RuntimeException(ex);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public TopFilmeSala TopSalaOrFilme(int opcao) {
        try {
            String sql=null;
            if(opcao==1){
                sql = "select sum(totalvenda) qtd,idfilme from TopFilme group by idfilme order by sum(totalvenda) desc limit 1;";
            }else{
                sql = "select sum(totalvenda) qtd,idsala from TopSala group by idsala order by sum(totalvenda) desc limit 1;";
            }
            conectar(sql);
         
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                if(opcao==1){
                    int qtdVendido = resultado.getInt("qtd");
                    int idfilme = resultado.getInt("idfilme");
                    FilmeNegocio FN = new FilmeNegocio();
                    Filme f = FN.procurarFilmePorCod(idfilme);
                    return(new TopFilmeSala<Filme>(f,qtdVendido));
                }else{
                    int qtdVendido = resultado.getInt("qtd");
                    int idsala = resultado.getInt("idsala");
                    SalaNegocio SN = new SalaNegocio();
                    List<Sala> salas = SN.listar();
                    Sala s=null;
                    for(Sala sal : salas){
                        if(sal.getId()==idsala){
                            s=sal;
                        }
                    }
                    return(new TopFilmeSala<Sala>(s,qtdVendido));
                }
                
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as secoes do Banco de Dados!");
            throw new RuntimeException(ex);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } finally {
            fecharConexao();
        }

        return (null);
    }
    
}

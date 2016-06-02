package dao_impldb;



import dao.SecaoDao;
import dominio.Filme;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dominio.Sala;
import dominio.Secao;
import java.util.Date;


public class SecaoDaoBd extends DaoBd<Secao> implements SecaoDao {

    @Override
    public void salvar(Secao secao) {
     
        try {
            String sql = "INSERT INTO secao VALUES (?,?,?,?)";

         
            conectarObtendoId(sql);
            comando.setInt(1, secao.getSala().getId());
            java.sql.Date horaSql = new java.sql.Date(secao.getDataHora().getTime());
            comando.setDate(2, horaSql);
            comando.setInt(3, secao.getFilme().getCodigo());
            comando.setInt(4, secao.getQtdDisponivel());
            
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Secao no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }
 

    @Override
    public void deletar(Secao secao) {
                try {
            String sql = "DELETE FROM secao WHERE idsecao = ?";

            conectar(sql);
            comando.setInt(1, secao.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Secao no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
  }

    @Override
    public void atualizar(Secao secao) {
        try {
            String sql = "UPDATE secao SET idsala=?, horario=?,cod=?,qtddisponivel=?"
                    + "WHERE idsala=?";

            conectar(sql);
            comando.setInt(1, secao.getSala().getId());
            java.sql.Date horaSql = new java.sql.Date(secao.getDataHora().getTime());
            comando.setDate(2, horaSql);
            comando.setInt(3, secao.getFilme().getCodigo());
            comando.setInt(4, secao.getQtdDisponivel());
            comando.setInt(5, secao.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Secao no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Secao> buscarSecaoPorFilme(Filme f) {
      List<Secao> listaSecao = new ArrayList<>();
        String sql = "SELECT S.numero,S.capacidade,S.idsala,SC.horario"
                + ",SC.qtddisponivel,SC.idsecao FROM secao SC join sala S on SC.idsala=S.idsala and SC.cod = ?";

        try {
            conectar(sql);
            comando.setInt(1, f.getCodigo());

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int numero = resultado.getInt("numero");
                int capacidade = resultado.getInt("capacidade");
                int idsala = resultado.getInt("idsala");
                java.sql.Date dataSql = resultado.getDate("horario");
                java.util.Date hora = new java.util.Date(dataSql.getTime());
                int qtd = resultado.getInt("qtddisponivel");
                int idsecao = resultado.getInt("idsecao");
                Sala sala = new Sala(numero,capacidade,idsala);           
                Secao s = new Secao(sala,hora,f,qtd,idsecao);    
                
                listaSecao.add(s);
                
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as Secoes pelo filme no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaSecao);
    }
    

    @Override
    public List<Secao> buscarSecaoPorSala(Sala s) {
      List<Secao> listaSecao = new ArrayList<>();
        String sql = "SELECT F.nome,F.genero,F.sinopse,F.cod,SC.horario"
                + ",SC.qtddisponivel,SC.idsecao FROM secao SC join filme F on F.cod=SC.cod and SC.idsala = ?";

        try {
            conectar(sql);
            comando.setInt(1, s.getId());

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
                int cod = resultado.getInt("cod");
                
                Filme f = new Filme(cod, nome, genero, sinopse);
     
                java.sql.Date dataSql = resultado.getDate("horario");
                java.util.Date hora = new java.util.Date(dataSql.getTime());
                int qtd = resultado.getInt("qtddisponivel");
                int idsecao = resultado.getInt("idsecao");           
                
                listaSecao.add(new Secao(s,hora,f,qtd,idsecao));
                
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as Secoes pela sala no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaSecao);
    }
    
    @Override
    public List<Secao> buscarSecaoPorHorario(Date hora) {
      List<Secao> listaSecao = new ArrayList<>();
        String sql = "select F.nome,F.genero,F.sinopse,F.cod,S.numero,S.capacidade,S.idsala,SC.horario,"
                + "SC.qtddisponivel,SC.idsecao from secao SC join sala S\n" +
                "on SC.idsala=S.idsala join filme F on SC.cod=F.cod and SC.horario= '?' ;";

        try {
            conectar(sql);
            java.sql.Date horario = new java.sql.Date(hora.getTime());
            comando.setDate(1, horario);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
                int cod = resultado.getInt("cod");
                
                Filme f = new Filme(cod,nome,genero,sinopse);
                
                int numero = resultado.getInt("numero");
                int capacidade = resultado.getInt("capacidade");
                int id = resultado.getInt("idsala");
                              
                Sala s = new Sala(numero, capacidade,id);


                int qtd = resultado.getInt("qtddisponivel");
                int idsecao = resultado.getInt("idsecao");
                
                
                listaSecao.add(new Secao(s, hora, f, qtd, idsecao));
                
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Secao pelo filme no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaSecao);
    }
    
    @Override
    public List<Secao> listar() {
        List<Secao> listaSecao = new ArrayList<>();

        String sql = "select F.nome,F.genero,F.sinopse,F.cod,S.numero,S.capacidade,S.idsala,SC.horario,"
                + "SC.qtddisponivel,SC.idsecao from secao SC join sala S\n" +
                "on SC.idsala=S.idsala join filme F on SC.cod=F.cod;";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
                int cod = resultado.getInt("cod");
                
                Filme f = new Filme(cod,nome,genero,sinopse);
                
                int numero = resultado.getInt("numero");
                int capacidade = resultado.getInt("capacidade");
                int id = resultado.getInt("idsala");
                              
                Sala s = new Sala(numero, capacidade,id);

                java.sql.Date dataSql = resultado.getDate("horario");
                java.util.Date hora = new java.util.Date(dataSql.getTime());
                int qtd = resultado.getInt("qtddisponivel");
                int idsecao = resultado.getInt("idsecao");
                
                
                listaSecao.add(new Secao(s, hora, f, qtd, idsecao));
            

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar as secoes do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaSecao);
    }
}

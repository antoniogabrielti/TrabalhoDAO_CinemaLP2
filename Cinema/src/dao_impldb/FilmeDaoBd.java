package dao_impldb;

import dao.FilmeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dominio.Filme;


public class FilmeDaoBd extends DaoBd<Filme> implements FilmeDao {

    @Override
    public void salvar(Filme filme) {
     
        try {
            String sql = "INSERT INTO filme VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, filme.getNome());
            comando.setString(2, filme.getGenero());
            comando.setString(3, filme.getSinopse());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Filme no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }
 

    @Override
    public void deletar(Filme filme) {
                try {
            String sql = "DELETE FROM filme WHERE cod = ?";

            conectar(sql);
            comando.setInt(1, filme.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Filme no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
  }

    @Override
    public void atualizar(Filme filme) {
        try {
            String sql = "UPDATE filme SET nome=?, genero=?, sinopse=? "
                    + "WHERE cod=?";

            conectar(sql);
            comando.setString(1, filme.getNome());
            comando.setString(2, filme.getGenero());
            comando.setString(3, filme.getSinopse());
            comando.setInt(4, filme.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Filme no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public Filme buscarFilmePorCod(int codigo) {
        String sql = "SELECT * FROM filme WHERE cod = ?";

        try {
            conectar(sql);
            comando.setInt(1, codigo);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
              
                Filme f = new Filme(codigo, nome, genero, sinopse);

                return f;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Filme pelo codigo no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    

    @Override
    public List<Filme> buscarFilmePorNome(String Nome) {
     List<Filme> listaFilmes = new ArrayList<>();
        String sql = "SELECT * FROM filme WHERE lower(nome) LIKE lower(?)";

        try {
            conectar(sql);
            comando.setString(1, "%" + Nome + "%");
            ResultSet resultado = comando.executeQuery();
            
           while (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
                int cod = resultado.getInt("cod");
                
           
                Filme f = new Filme(cod, nome, genero, sinopse);

                listaFilmes.add(f);
            
          }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Filme pelo Nome no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
        return listaFilmes;
    }

    @Override
    public List<Filme> listar() {
        List<Filme> listaFilmes = new ArrayList<>();

        String sql = "SELECT * FROM filme";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                String nome = resultado.getString("nome");
                String genero = resultado.getString("genero");
                String sinopse = resultado.getString("sinopse");
                int cod = resultado.getInt("cod");
           
                Filme f = new Filme(cod, nome, genero, sinopse);

                listaFilmes.add(f);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os filmes do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaFilmes);
    }
}

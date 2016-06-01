package dao_impldb;


import dao.SalaDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dominio.Sala;


public class SalaDaoBd extends DaoBd<Sala> implements SalaDao {

    @Override
    public void salvar(Sala sala) {
     
        try {
            String sql = "INSERT INTO sala VALUES (?,?)";

         
            conectarObtendoId(sql);
            comando.setInt(1, sala.getNumero());
            comando.setInt(2, sala.getCapacidade());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Sala no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }
 

    @Override
    public void deletar(Sala sala) {
                try {
            String sql = "DELETE FROM sala WHERE idsala = ?";

            conectar(sql);
            comando.setInt(1, sala.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Sala no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
  }

    @Override
    public void atualizar(Sala sala) {
        try {
            String sql = "UPDATE sala SET numero=?, capacidade=?"
                    + "WHERE idsala=?";

            conectar(sql);
            comando.setInt(1, sala.getNumero());
            comando.setInt(2, sala.getCapacidade());
            comando.setInt(3, sala.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Sala no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public Sala buscarSalaPorNumero(int numero) {
        String sql = "SELECT * FROM sala WHERE numero = ?";

        try {
            conectar(sql);
            comando.setInt(1, numero);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int numeroSala = resultado.getInt("numero");
                int capacidade = resultado.getInt("capacidade");
                int id = resultado.getInt("idsala");
                              
                Sala s = new Sala(numeroSala, capacidade,id);

                return s;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Sala pelo codigo no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }
    

    @Override
    public List<Sala> buscarSalaPorCapacidade(int capacidade) {
     List<Sala> listaSalas = new ArrayList<>();
        String sql = "SELECT * FROM sala WHERE capacidade >= ?";

        try {
            conectar(sql);
            comando.setInt(1, capacidade);
            ResultSet resultado = comando.executeQuery();
            
           while (resultado.next()) {
                int numero = resultado.getInt("numero");
                int capacidadeSala = resultado.getInt("capacidade");
                int id = resultado.getInt("idsala");
                              
                Sala s = new Sala(numero, capacidadeSala,id);

                listaSalas.add(s);
            
          }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Sala pelo Nome no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
        return listaSalas;
    }

    @Override
    public List<Sala> listar() {
        List<Sala> listaSala = new ArrayList<>();

        String sql = "SELECT * FROM sala";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int numero = resultado.getInt("numero");
                int capacidade = resultado.getInt("capacidade");
                int id = resultado.getInt("idsala");
                              
                Sala s = new Sala(numero, capacidade,id);

                listaSala.add(s);
            

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os filmes do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaSala);
    }
}

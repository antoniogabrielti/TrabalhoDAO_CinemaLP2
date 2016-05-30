package br.com.senacrs.consultorio.dao.impl_BD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.senacrs.consultorio.dominio.Paciente;
import br.com.senacrs.consultorio.dao.PacienteDao;

public class PacienteDaoBd extends DaoBd<Paciente> implements PacienteDao {
  

    //Metodo salvar: trabalhar com data e recebe o id auto-increment 
    //e já relaciona no objeto paciente (recebido por parâmetro)
    //Caso queira retornar, só retornar id.
    @Override
    public void salvar(Paciente paciente) {
        int id = 0;
        try {
            String sql = "INSERT INTO paciente (rg, nome, datanascimento) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, paciente.getRg());
            comando.setString(2, paciente.getNome());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            java.sql.Date dataSql = new java.sql.Date(paciente.getDataNascimento().getTime());
            comando.setDate(3, dataSql);
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                paciente.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar paciente no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public void deletar(Paciente paciente) {
        try {
            String sql = "DELETE FROM paciente WHERE id = ?";

            conectar(sql);
            comando.setInt(1, paciente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar paciente no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

    }

    @Override
    public void atualizar(Paciente paciente) {
        try {
            String sql = "UPDATE paciente SET rg=?, nome=?, datanascimento=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, paciente.getRg());
            comando.setString(2, paciente.getNome());
            //Trabalhando com data: lembrando dataUtil -> dataSql
            java.sql.Date dataSql = new java.sql.Date(paciente.getDataNascimento().getTime());
            comando.setDate(3, dataSql);
            comando.setInt(4, paciente.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar paciente no Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> listaPacientes = new ArrayList<>();

        String sql = "SELECT * FROM paciente";

        try {
            conectar(sql);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                java.sql.Date dataSql = resultado.getDate("datanascimento");
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Paciente pac = new Paciente(id, rg, nome, dataUtil);

                listaPacientes.add(pac);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (listaPacientes);
    }

    @Override
    public Paciente procurarPorId(int id) {
        String sql = "SELECT * FROM paciente WHERE id = ?";

        try {
            conectar(sql);
            comando.setInt(1, id);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                java.sql.Date dataSql = resultado.getDate("datanascimento");
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Paciente pac = new Paciente(id, rg, nome, dataUtil);

                return pac;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo id do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public Paciente procurarPorRg(String rg) {
        String sql = "SELECT * FROM paciente WHERE rg = ?";

        try {
            conectar(sql);
            comando.setString(1, rg);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                java.sql.Date dataSql = resultado.getDate("datanascimento");
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Paciente pac = new Paciente(id, rg, nome, dataUtil);

                return pac;

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o paciente pelo rg do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    }

    @Override
    public List<Paciente> procurarPorNome(String nome) {
        List<Paciente> listaPacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente WHERE nome LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + nome + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("rg");
                String nomeX = resultado.getString("nome");
                //Trabalhando com data: lembrando dataSql -> dataUtil
                java.sql.Date dataSql = resultado.getDate("datanascimento");
                java.util.Date dataUtil = new java.util.Date(dataSql.getTime());

                Paciente pac = new Paciente(id, rg, nomeX, dataUtil);

                listaPacientes.add(pac);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os pacientes pelo nome do Banco de Dados!");
            throw new RuntimeException(ex);
        } finally {
            fecharConexao();
        }
        return (listaPacientes);
    }
}

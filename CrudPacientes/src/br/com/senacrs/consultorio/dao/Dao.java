package br.com.senacrs.consultorio.dao;

import br.com.senacrs.consultorio.dominio.Paciente;
import java.util.List;

/**
 *
 * @author lhries
 */

//A ideia da interface Dao é que padronizar todos os métodos do CRUD da aplicação.
public interface Dao<T> {
    public void salvar(T dominio);
    public void deletar(T paciente);
    public void atualizar(T paciente);
    public List<T> listar();
    public T procurarPorId(int id);
}

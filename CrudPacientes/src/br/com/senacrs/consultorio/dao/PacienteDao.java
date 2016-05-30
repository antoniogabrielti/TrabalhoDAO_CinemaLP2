
package br.com.senacrs.consultorio.dao;

import java.util.List;
import br.com.senacrs.consultorio.dominio.Paciente;

/**
 *
 * @author lhries
 */
//Além dos métodos do Crud padronizado na interface Dao, dois metodos sao obrigatorios.
public interface PacienteDao extends Dao<Paciente>{
    public Paciente procurarPorRg(String rg);
    public List<Paciente> procurarPorNome(String nome);
    
    
}

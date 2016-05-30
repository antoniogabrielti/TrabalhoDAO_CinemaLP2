package br.com.senacrs.consultorio.negocio;

import br.com.senacrs.consultorio.dominio.Paciente;
import br.com.senacrs.consultorio.dao.PacienteDao;
import br.com.senacrs.consultorio.dao.impl_BD.PacienteDaoBd;
import java.util.List;

public class PacienteNegocio {

    private PacienteDao pacienteDao;

    public PacienteNegocio() {
        pacienteDao = new PacienteDaoBd();
    }

    public void salvar(Paciente p) throws NegocioException {
        this.validarCamposObrigatorios(p);
        this.validarRgExistente(p);
        pacienteDao.salvar(p);
    }

    public List<Paciente> listar() {
        return (pacienteDao.listar());
    }

    public void deletar(Paciente paciente) throws NegocioException {
        if (paciente == null || paciente.getRg() == null) {
            throw new NegocioException("Paciente nao existe!");
        }
        pacienteDao.deletar(paciente);
    }

    public void atualizar(Paciente paciente) throws NegocioException {
        if (paciente == null || paciente.getRg() == null) {
            throw new NegocioException("Paciente nao existe!");
        }
        this.validarCamposObrigatorios(paciente);
        pacienteDao.atualizar(paciente);
    }

    public Paciente procurarPorRg(String rg) throws NegocioException {
        if (rg == null || rg.isEmpty()) {
            throw new NegocioException("Campo RG nao informado");
        }
        Paciente paciente = pacienteDao.procurarPorRg(rg);
        if (paciente == null) {
            throw new NegocioException("Paciente nao encontrado");
        }
        return (paciente);
    }

    public List<Paciente> procurarPorNome(String nome) throws NegocioException {
        if (nome == null || nome.isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
        return(pacienteDao.procurarPorNome(nome));
    }

    public boolean pacienteExiste(String rg) {
        Paciente paciente = pacienteDao.procurarPorRg(rg);
        return (paciente != null);
    }

    private void validarCamposObrigatorios(Paciente p) throws NegocioException {
        if (p.getRg() == null || p.getRg().isEmpty()) {
            throw new NegocioException("Campo RG nao informado");
        }

        if (p.getNome() == null || p.getNome().isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
    }

    private void validarRgExistente(Paciente p) throws NegocioException {
        if (pacienteExiste(p.getRg())) {
            throw new NegocioException("RG ja existente");
        }
    }

}

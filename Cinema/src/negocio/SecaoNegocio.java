package negocio;


import dao.SecaoDao;
import dao_impldb.SalaDaoBd;
import dao_impldb.SecaoDaoBd;
import dominio.Sala;
import dominio.Secao;
import java.util.List;

public class SecaoNegocio {

    private SecaoDao secaoDao;

    public SecaoNegocio() {
        secaoDao = new SecaoDaoBd();
    }

    public void salvar(Secao s) throws NegocioException {
        this.validarCamposObrigatorios(s);
        this.validarNumExistente(s);
        secaoDao.salvar(s);
    }

    public List<Sala> listar() {
        return (salaDao.listar());
    }

    public void deletar(Sala s) throws NegocioException {
        if (s == null || s.getId() <= 0) {
            throw new NegocioException("Sala nao existe!");
        }
        salaDao.deletar(s);
    }

    public void atualizar(Sala s) throws NegocioException {
        if (s == null || s.getId() <= 0) {
            throw new NegocioException("Sala nao existe!");
        }
        this.validarCamposObrigatorios(s);
        salaDao.atualizar(s);
    }

    public Sala procurarSalaPorNumero(int num) throws NegocioException {
        if (num <= 0) {
            throw new NegocioException("Numero de Sala Invalido");
        }
        Sala sala = salaDao.buscarSalaPorNumero(num);

        return (sala);
    }

    public List<Sala> procurarPorCapacidade(int tamanho) throws NegocioException {
        if (tamanho<=0) {
            throw new NegocioException("Capacidade Necessaria Para Sala Invalida");
        }
        return(salaDao.buscarSalaPorCapacidade(tamanho));
    }

    public boolean SalaExiste(int num) {
        Sala sala = salaDao.buscarSalaPorNumero(num);
        return (sala != null);
    }

    private void validarCamposObrigatorios(Secao s) throws NegocioException {
         if (s.getFilme() == null || s.getFilme().getNome().isEmpty()) {
            throw new NegocioException("Filme nao informado");
        }
        if(s.getSala() == null || s.getSala().getNumero()>0){
            throw new NegocioException("Sala nao informada");
        }
    }

    private void validarNumExistente(Sala s) throws NegocioException {
        if (SalaExiste(s.getNumero())) {
            throw new NegocioException("Este Numero de Sala ja existe");
        }
    }

}

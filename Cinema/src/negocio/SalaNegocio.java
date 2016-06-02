package negocio;

import dao.SalaDao;
import dao_impldb.SalaDaoBd;
import dominio.Sala;
import java.util.List;

public class SalaNegocio {

    private SalaDao salaDao;

    public SalaNegocio() {
        salaDao = new SalaDaoBd();
    }

    public void salvar(Sala s) throws NegocioException {
        this.validarCamposObrigatorios(s);
        this.validarNumExistente(s);
        salaDao.salvar(s);
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

    private void validarCamposObrigatorios(Sala s) throws NegocioException {
         if (s == null || s.getNumero()<=0) {
            throw new NegocioException("Campo numero incorreto");
        }
        if(s == null || s.getCapacidade()<20){
            throw new NegocioException("Campo capacidade tem que ser maior ou igual a 20(capacidade minima)");
        }
    }

    private void validarNumExistente(Sala s) throws NegocioException {
        if (SalaExiste(s.getNumero())) {
            throw new NegocioException("Este Numero de Sala ja existe");
        }
    }

}

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
        salaDao.salvar(s);
    }

    public List<Sala> listar() {
        return (salaDao.listar());
    }

    public void deletar(Sala s) throws NegocioException {
        if (s == null || s.getId() < 0) {
            throw new NegocioException("Filme nao existe!");
        }
        filmeDao.deletar(filme);
    }

    public void atualizar(Filme filme) throws NegocioException {
        if (filme == null || filme.getCodigo() < 0) {
            throw new NegocioException("Filme nao existe!");
        }
        this.validarCamposObrigatorios(filme);
        filmeDao.atualizar(filme);
    }

    public Filme procurarFilmePorCod(int cod) throws NegocioException {
        if (cod < 0) {
            throw new NegocioException("Codigo Invalido");
        }
        Filme filme = filmeDao.buscarFilmePorCod(cod);

        return (filme);
    }

    public List<Filme> procurarPorNomeFilme(String nome) throws NegocioException {
        if (nome == null || nome.isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
        return(filmeDao.buscarFilmePorNome(nome));
    }

    public boolean FilmeExiste(int cod) {
        Filme filme = filmeDao.buscarFilmePorCod(cod);
        return (filme != null);
    }

    private void validarCamposObrigatorios(Sala s) throws NegocioException {
         if (s == null || s.getNumero()>=0) {
            throw new NegocioException("Campo numero incorreto");
        }
        if(s == null || s.getCapacidade()>=20){
            throw new NegocioException("Campo capacidade tem que ser maior ou igual a 20(capacidade minima)");
        }
    }

    private void validarCodExistente(Filme f) throws NegocioException {
        if (FilmeExiste(f.getCodigo())) {
            throw new NegocioException("Codigo ja existente");
        }
    }

}

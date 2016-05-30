package negocio;

import dominio.Filme;
import dao.FilmeDao;
import dao_impldb.FilmeDaoBd;
import java.util.List;

public class FilmeNegocio {

    private FilmeDao filmeDao;

    public FilmeNegocio() {
        filmeDao = new FilmeDaoBd();
    }

    public void salvar(Filme f) throws NegocioException {
        this.validarCamposObrigatorios(f);
        filmeDao.salvar(f);
    }

    public List<Filme> listar() {
        return (filmeDao.listar());
    }

    public void deletar(Filme filme) throws NegocioException {
        if (filme == null || filme.getCodigo() < 0) {
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

    private void validarCamposObrigatorios(Filme f) throws NegocioException {
         if (f.getNome() == null || f.getNome().isEmpty()) {
            throw new NegocioException("Campo nome nao informado");
        }
        if(f.getGenero()== null || f.getGenero().isEmpty()){
            throw new NegocioException("Campo genero nao informado");
        }
        if(f.getSinopse()== null || f.getSinopse().isEmpty()){
            throw new NegocioException("Campo sinopse nao informado");
        }
    }

    private void validarCodExistente(Filme f) throws NegocioException {
        if (FilmeExiste(f.getCodigo())) {
            throw new NegocioException("Codigo ja existente");
        }
    }

}

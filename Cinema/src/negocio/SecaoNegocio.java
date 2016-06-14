package negocio;


import dao.SecaoDao;
import dao_impldb.SalaDaoBd;
import dao_impldb.SecaoDaoBd;
import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import java.util.Date;
import java.util.List;

public class SecaoNegocio {

    private SecaoDao secaoDao;

    public SecaoNegocio() {
        secaoDao = new SecaoDaoBd();
    }

    public void salvar(Secao s) throws NegocioException {
        this.validarCamposObrigatorios(s);
        this.validarSecaoExistente(s);
        secaoDao.salvar(s);
    }

    public List<Secao> listar() {
        return (secaoDao.listar());
    }

    public void deletar(Secao s) throws NegocioException {
        if (s == null || s.getId() <= 0) {
            throw new NegocioException("Secao nao existe!");
        }
        secaoDao.deletar(s);
    }

    public void atualizar(Secao s) throws NegocioException {
        if (s == null || s.getId() <= 0) {
            throw new NegocioException("Secao nao existe!");
        }
        this.validarCamposObrigatorios(s);
        this.validarSecaoExistente(s);
        secaoDao.atualizar(s);
    }

    public List<Secao> procurarSecaoPorFilme(Filme f) throws NegocioException {
        if (f == null || f.getNome().isEmpty()) {
            throw new NegocioException("Filme nao informado");
        }
        List<Secao> Secoes = secaoDao.buscarSecaoPorFilme(f);

        return (Secoes);
    }

       public List<Secao> procurarSecaoPorHorario(Date h) throws NegocioException {
        if (h == null) {
            throw new NegocioException("horario nao informado");
        }
        List<Secao> Secoes = secaoDao.buscarSecaoPorHorario(h);

        return (Secoes);
    }
       public Secao procurarSecaoPorID(int ID) throws NegocioException {
        if (ID<=0) {
            throw new NegocioException("ID da Secao e Incorreto!!!");
        }
        Secao s = secaoDao.buscarSecaoPorID(ID);

        return (s);
    }
       
       public List<Secao> procurarSecaoPorSala(Sala s) throws NegocioException {
        if(s == null || s.getNumero()<=0){
            throw new NegocioException("Sala nao informada");
        }
        List<Secao> Secoes = secaoDao.buscarSecaoPorSala(s);

        return (Secoes);
    }
    
    private void validarCamposObrigatorios(Secao s) throws NegocioException {
         if (s.getFilme() == null || s.getFilme().getNome().isEmpty()) {
            throw new NegocioException("Filme nao informado");
        }
        if(s.getSala() == null || s.getSala().getNumero()<=0){
            throw new NegocioException("Sala nao informada");
        }
    }

    private void validarSecaoExistente(Secao s) throws NegocioException {
        List<Secao> salasEncontradas = secaoDao.buscarSecaoPorSala(s.getSala());
        
        if (!salasEncontradas.isEmpty()) {
            for(Secao sec : salasEncontradas){
                if(sec.getDataHora().equals(s.getDataHora()) && sec.getId()!=s.getId()){
                    if(sec.getFilme().getCodigo()==s.getFilme().getCodigo()){
                        throw new NegocioException("Secao ja cadastrada, voce esta duplicando!!!");
                    }else{
                        throw new NegocioException("Ja ha uma secao nesta sala e horario!!!");
                    }
                    
                }
        }
            
        }
    }

}

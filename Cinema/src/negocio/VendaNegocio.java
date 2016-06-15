package negocio;



import dao.VendaDao;
import dao_impldb.SecaoDaoBd;
import dao_impldb.VendaDaoBd;
import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import dominio.TopFilmeSala;
import dominio.Venda;
import java.util.Date;
import java.util.List;

public class VendaNegocio {

    private VendaDao vendaDao;

    public VendaNegocio() {
        vendaDao = new VendaDaoBd();
    }

    public void salvar(Venda v) throws NegocioException {
        this.validarCamposObrigatorios(v);
        this.validarVendaExistente(v);
        vendaDao.salvar(v);
    }

    public List<Venda> listar() {
        return (vendaDao.listar());
    }

    public void deletar(Venda v) throws NegocioException {
        if (v == null || v.getIdvenda() <= 0) {
            throw new NegocioException("Venda nao existe!");
        }
        vendaDao.deletar(v);
    }

    public void atualizar(Venda v) throws NegocioException {
        if (v == null || v.getIdvenda() <= 0) {
            throw new NegocioException("Venda nao existe!");
        }
        this.validarCamposObrigatorios(v);
        vendaDao.atualizar(v);
    }
    
    public TopFilmeSala TopSalaOrFilme(int opcao) throws NegocioException{
        if(opcao!=1 && opcao!=2){
            throw new NegocioException("Opcao Invalida!!!");
        }
        return (vendaDao.TopSalaOrFilme(opcao));
    }

    public List<Venda> procurarVendaPorFilme(Filme f) throws NegocioException {
        if (f == null || f.getNome().isEmpty()) {
            throw new NegocioException("Filme nao informado");
        }
        List<Venda> Vendas = vendaDao.buscarVendaPorFilme(f);

        return (Vendas);
    }

       public List<Venda> procurarVendaPorHorario(Date h) throws NegocioException {
        if (h == null) {
            throw new NegocioException("horario nao informado");
        }
        List<Venda> Vendas = vendaDao.buscarVendaPorHorario(h);

        return (Vendas);
    }
       public Venda procurarVendaPorID(int ID) throws NegocioException {
        if (ID<=0) {
            throw new NegocioException("ID da Venda Incorreto!!!");
        }
        Venda v = vendaDao.buscarVendaPorID(ID);

        return (v);
    }
       
       public List<Venda> procurarVendaPorSala(Sala s) throws NegocioException {
        if(s == null || s.getNumero()<=0){
            throw new NegocioException("Sala nao informada");
        }
        List<Venda> Vendas = vendaDao.buscarVendaPorSala(s);

        return (Vendas);
    }
    
    private void validarCamposObrigatorios(Venda v) throws NegocioException {
         if (v.getSecao() == null || v.getSecao().getId()<=0) {
            throw new NegocioException("Secao nao informada");
        }
        if(v.getQtdVendido()<=0){  
            throw new NegocioException("Quantidade nao pode ser zero ou negativa!!!");
        }
        if(v.getQtdVendido() > v.getSecao().getQtdDisponivel()){
            throw new NegocioException("Quantidade ultrapassou capacidade da secao!!!");
        }
    }

    private void validarVendaExistente(Venda venda) throws NegocioException {
        List<Venda> vendasEncontradas = vendaDao.listar();
        
        if (!vendasEncontradas.isEmpty()) {
            for(Venda v : vendasEncontradas){
                if(v.getIdvenda()==venda.getIdvenda()){
                    throw new NegocioException("Venda ja cadastrada, voce esta duplicando!!!");
                }
            } 
        }
    }

}

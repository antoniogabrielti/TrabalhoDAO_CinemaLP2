
package repositorio;

import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import dominio.Venda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RepositorioVenda {
        private List<Venda> vendas;
     

    public RepositorioVenda() {
        vendas = new ArrayList<Venda>();
    }

    public boolean addVenda(Venda V) {
       return vendas.add(V);
    }

    public List<Venda> getListaVenda() {
        Collections.sort(vendas);
        return vendas;
    }
    public ArrayList<Venda> VendaPorFilme(Filme F){
        ArrayList<Venda> VendasDesteFilme = new ArrayList<Venda>();
            for(Venda V: vendas){
                if(V.getSecao().getFilme().getCodigo()==F.getCodigo()){
                    VendasDesteFilme.add(V);
                }
            }
        
        return VendasDesteFilme;
    }
        public ArrayList<Venda> VendaPorHora(Date H){
        ArrayList<Venda> VendasNesteHorario = new ArrayList<Venda>();
            for(Venda V: vendas){
                if(V.getSecao().getDataHora().equals(H)){
                    VendasNesteHorario.add(V);
                }
            }
        
        return VendasNesteHorario;
    }
        public ArrayList<Venda> VendaPorSala(Sala S){
        ArrayList<Venda> VendasNestaSala = new ArrayList<Venda>();
            for(Venda V: vendas){
                if(V.getSecao().getSala().equals(S)){
                    VendasNestaSala.add(V);
                }
            }
        
        return VendasNestaSala;
    }
        public ArrayList<Venda> VendaPorSecao(Secao Sc){
        ArrayList<Venda> VendasNestaSecao = new ArrayList<Venda>();
            for(Venda V: vendas){
                if(V.getSecao().equals(Sc)){
                    VendasNestaSecao.add(V);
                }
            }
        
        return VendasNestaSecao;
    }

}

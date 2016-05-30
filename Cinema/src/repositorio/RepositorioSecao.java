
package repositorio;

import dominio.Filme;
import dominio.Secao;
import dominio.TopSecoesFilme;
import dominio.TopSecoesSala;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RepositorioSecao {
 private List<Secao> secoes;
 RepositorioTopFilme TopFilme = new RepositorioTopFilme();
 RepositorioTopSala TopSalas = new RepositorioTopSala();
 
    public RepositorioSecao() {
        secoes = new ArrayList<Secao>();
    }

    public boolean addSecao(Secao S) {
       return secoes.add(S);
    }

    public List<Secao> getListaSecoes() {
        Collections.sort(secoes);
        return secoes;
    }   
    // mostrar os filmes com seus códigos usuario escolhe o filme
    public ArrayList<Secao> MostrarSecoesDeUmFilme(int cod){
        ArrayList<Secao> SecoesDesteFilme = new ArrayList<Secao>();
        for(Secao S: secoes){
            if(S.getFilme().getCodigo()==cod){
                SecoesDesteFilme.add(S);
            }
        }
        return SecoesDesteFilme;
    }
        // mostrar as salas com seus numeros usuario escolhe o numero da sala
    public ArrayList<Secao> MostrarSecoesEmUmaSala(int num){
        ArrayList<Secao> SecoesDesteSala = new ArrayList<Secao>();
        for(Secao S: secoes){
            if(S.getSala().getNumero()==num){
                SecoesDesteSala.add(S);
            }
        }
        return SecoesDesteSala;
    }
            // mostrar as salas com seus numeros usuario escolhe o numero da sala
    public ArrayList<Secao> MostrarSecoesEmUmaHora(Date dataHora){
        ArrayList<Secao> SecoesNestaHora = new ArrayList<Secao>();
        for(Secao S: secoes){
            if(S.getDataHora().equals(dataHora)){
                SecoesNestaHora.add(S);
            }
        }
        return SecoesNestaHora;
    }
        public List<TopSecoesFilme> RemoverFilmesRepetidos(RepositorioTopFilme Top){
        List<TopSecoesFilme> TopF;
        TopF=Top.getListaTopFilme();
        ArrayList<TopSecoesFilme> TopRepetidos = new ArrayList<TopSecoesFilme>();
        for(int i=0;i<Top.getListaTopFilme().size();i++){
          for(int j=i+1;j<Top.getListaTopFilme().size();j++){
               if(TopF.get(j).getFilme().getNome().equals(TopF.get(i).getFilme().getNome())){
                   TopRepetidos.add(TopF.get(j));
               }    
            }
        }
        int tam=TopRepetidos.size();
        int count=0;
        while(tam!=0){
            for(int i=0;i<TopF.size();i++){
                    if(TopRepetidos.get(count).getFilme().getNome().equals(TopF.get(i).getFilme().getNome())){
                    TopF.remove(i);
                }
                
            }
            count++;
            tam--;
         }

        return TopF;
    }
    public RepositorioTopFilme CriarListaFilmesMaisSecoes(){
        RepositorioFilme ListaFilmesSecoes = new RepositorioFilme();
        for(Secao S : secoes){
            ListaFilmesSecoes.addFilme(S.getFilme());
        }
        
        for(int i=0;i<ListaFilmesSecoes.getListaFilmes().size();i++){
            int count = Collections.frequency(ListaFilmesSecoes.getListaFilmes(), ListaFilmesSecoes.getListaFilmes().get(i));
            TopFilme.addTopFilme(new TopSecoesFilme(ListaFilmesSecoes.getListaFilmes().get(i),count));
        }
        return TopFilme;
    }
    public void FilmesEmMaisSecoes(){
        TopFilme=CriarListaFilmesMaisSecoes();
        List<TopSecoesFilme> FilmesTop = RemoverFilmesRepetidos(TopFilme);
        for(TopSecoesFilme Top : FilmesTop){
            System.out.println("Filme: "+Top.getFilme().getNome()+" /quantidade de seções com este filme ==> "+Top.getQtdVezesVisto());
        }
    }
            public List<TopSecoesSala> RemoverSalasRepetidas(RepositorioTopSala Top){
        List<TopSecoesSala> TopS;
        TopS=Top.getListaTopSalas();
        ArrayList<TopSecoesSala> TopRepetidos = new ArrayList<TopSecoesSala>();
        for(int i=0;i<Top.getListaTopSalas().size();i++){
          for(int j=i+1;j<Top.getListaTopSalas().size();j++){
               if(TopS.get(j).getSala().getNumero()== TopS.get(i).getSala().getNumero()){
                   TopRepetidos.add(TopS.get(j));
               }    
            }
        }
        int tam=TopRepetidos.size();
        int count=0;
        while(tam!=0){
            for(int i=0;i<TopS.size();i++){
                    if(TopRepetidos.get(count).getSala().getNumero()== TopS.get(i).getSala().getNumero()){
                    TopS.remove(i);
                }
                
            }
            count++;
            tam--;
         }

        return TopS;
    }
    public RepositorioTopSala CriarListaSalasMaisSecoes(){
        RepositorioSala ListaSalasSecoes = new RepositorioSala();
        for(Secao S : secoes){
            ListaSalasSecoes.addSala(S.getSala());
        }
        
        for(int i=0;i<ListaSalasSecoes.getListaSalas().size();i++){
            int count = Collections.frequency(ListaSalasSecoes.getListaSalas(), ListaSalasSecoes.getListaSalas().get(i));
            TopSalas.addTopSala(new TopSecoesSala(ListaSalasSecoes.getListaSalas().get(i),count));
        }
        return TopSalas;
    }
    public void SalasEmMaisSecoes(){
        TopSalas=CriarListaSalasMaisSecoes();
        List<TopSecoesSala> SalasTop = RemoverSalasRepetidas(TopSalas);
        for(TopSecoesSala Top : SalasTop){
            System.out.println("Sala Nº "+Top.getSala().getNumero()+" /quantidade de seções nesta sala ==> "+Top.getQtdVezesUtilizada());
        }
    }
}

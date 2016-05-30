package viewUI;

import dominio.Filme;
import dominio.Secao;
import dominio.Venda;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import repositorio.RepositorioFilme;
import repositorio.RepositorioSala;
import repositorio.RepositorioSecao;
import repositorio.RepositorioVenda;
import util.Console;
import util.DateUtil;
import view.menu.VendaMenu;

class VendaUI {
    private RepositorioSecao listaSecao;
    private RepositorioVenda listaVenda;
    private RepositorioFilme Filmes;
    private RepositorioSala Salas;
    public VendaUI(RepositorioSecao listaSecao,RepositorioVenda listaVenda,RepositorioFilme Filmes,RepositorioSala Salas) {
        this.listaSecao=listaSecao;
        this.listaVenda=listaVenda;
        this.Filmes=Filmes;
        this.Salas=Salas;
    }
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(VendaMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case VendaMenu.OP_CADASTRAR:
                    cadastrarVenda();
                    break;
                case VendaMenu.OP_LISTAR:
                    mostrarVendas();
                    break;
                case VendaMenu.OP_BUSCAFILME:
                    buscaVendaPorFilme();
                    break;
                case VendaMenu.OP_BUSCADATAHORA:
                    buscaVendaPorDataHora();
                    break;
                case VendaMenu.OP_BUSCASALA:
                    buscaVendaPorSala();
                    break;
                case VendaMenu.OP_VOLTAR:
                    System.out.println("Retornando ao menu principal..");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != VendaMenu.OP_VOLTAR);
    }

    private void cadastrarVenda() {
        boolean comprou = false;
        do{
                        System.out.println("-------------------------------------\n");
        System.out.println(String.format("%-10s", "NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|QUANTIDADE DISPONIVEL") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
        for (Secao S : listaSecao.getListaSecoes()) {
            if(S.getQtdDisponivel()>0){
            String horario = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horario) + "\t"
                    + String.format("%-20s", "|" + S.getQtdDisponivel()+" ingressos") + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
            }
        }
        Secao SecaoEscolhida=null;
        int Num_Sala = Console.scanInt("Informe o numero da sala da secao escolhida: ");
        String dataHora = Console.scanString("informe a hora da secao que deseja comprar no formato (hh:mm):");
            Date horario=null;
            try {
               horario = DateUtil.stringToHour(dataHora);
               List<Secao> SecaoEncontrada=listaSecao.MostrarSecoesEmUmaSala(Num_Sala);
               if(!SecaoEncontrada.isEmpty()){
                   for(Secao S : SecaoEncontrada){
                       String hora = DateUtil.hourToString(S.getDataHora());
                       if(hora.equals(dataHora)){
                           SecaoEscolhida=S;
                       }
                  }
              }
            } catch (ParseException ex) {
                System.out.println("Data ou hora no formato inválido!");
            }
            if(SecaoEscolhida!=null){
                int qtdCompra=0;
                do{
                    qtdCompra = Console.scanInt("Digite a quantidade de ingressos para a compra:");
                    if(qtdCompra<=SecaoEscolhida.getQtdDisponivel() && qtdCompra>0){
                        Date DataHoraCompra = new Date();
                        listaVenda.addVenda(new Venda(SecaoEscolhida,qtdCompra,DataHoraCompra));
                        SecaoEscolhida.setQtdDisponivel(qtdCompra);
                        comprou=true;
                        System.out.println("Sua compra foi realizada com sucesso!!!");
                    }else{
                        if(qtdCompra<=0){
                            System.out.println("Quantidade Invalida!!!");
                        }else{
                         System.out.println("Quantidade ultrapassa o número de ingressos disponiveis!!!");
                        }
                    }
                }while(comprou==false);
            }else{
                System.out.println("Voce informou uma secao que não existe em nosso catalogo!!!");
            }
        }while(comprou==false);
    }

    private void mostrarVendas() {
                System.out.println("-------------- Relatorio de Vendas ---------------\n");
        System.out.println(String.format("%-10s", "DATA/HORA DA COMPRA") + "\t"
                + String.format("%-20s", "|NOME DO FILME") + "\t"
                + String.format("%-20s", "|SALA DA SECAO") + "\t"
                + String.format("%-20s", "|HORARIO DA SECAO") + "\t"
                + String.format("%-20s", "|QUANTIDADE COMPRADO"));
        for (Venda V : listaVenda.getListaVenda()) {
            String DataHoraCompra = DateUtil.dateHourToString(V.getDataHora());
            String HoraSecao = DateUtil.hourToString(V.getSecao().getDataHora());
            System.out.println(String.format("%-10s", DataHoraCompra) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + HoraSecao) + "\t"
                    + String.format("%-20s", "|" + V.getQtdVendido()));
        }
    }

    private void buscaVendaPorFilme() {
        System.out.println("######## Busca de Vendas por Filme ############");
        FilmeUI listaFilmes = new FilmeUI(Filmes);
        listaFilmes.mostrarFilmes();
        int cod_filme=Console.scanInt("Informe o codigo do filme que deseja pesquisar as Vendas:");
        List<Venda> VendasEncontrada=listaVenda.VendaPorFilme(Filmes.buscarFilmePorCod(cod_filme));
        if(!VendasEncontrada.isEmpty()){
                 System.out.println(String.format("%-10s", "DATA/HORA DA COMPRA") + "\t"
                + String.format("%-20s", "|NOME DO FILME") + "\t"
                + String.format("%-20s", "|SALA DA SECAO") + "\t"
                + String.format("%-20s", "|HORARIO DA SECAO") + "\t"
                + String.format("%-20s", "|QUANTIDADE COMPRADO"));
        for (Venda V : VendasEncontrada) {
            String DataHoraCompra = DateUtil.dateHourToString(V.getDataHora());
            String HoraSecao = DateUtil.hourToString(V.getSecao().getDataHora());
            System.out.println(String.format("%-10s", DataHoraCompra) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + HoraSecao) + "\t"
                    + String.format("%-20s", "|" + V.getQtdVendido()));
        }
        }else{
            System.out.println("Nenhuma Venda foi encontrada para este filme!!!");
        }
    }

    private void buscaVendaPorDataHora() {
        System.out.println("######## Busca de Vendas por Data e Hora ############");
        String Data_Hora=Console.scanString("Informe a data e hora das vendas que deseja filtrar (dd/MM/aaaa HH:mm):");
         Date horario=null;
            try {
               horario = DateUtil.stringToHour(Data_Hora);
               List<Venda> VendasEncontrada=listaVenda.VendaPorHora(horario);
              if(!VendasEncontrada.isEmpty()){
                 System.out.println(String.format("%-10s", "DATA/HORA DA COMPRA") + "\t"
                + String.format("%-20s", "|NOME DO FILME") + "\t"
                + String.format("%-20s", "|SALA DA SECAO") + "\t"
                + String.format("%-20s", "|HORARIO DA SECAO") + "\t"
                + String.format("%-20s", "|QUANTIDADE COMPRADO"));
             for (Venda V : VendasEncontrada) {
                    String DataHoraCompra = DateUtil.dateHourToString(V.getDataHora());
                    String HoraSecao = DateUtil.hourToString(V.getSecao().getDataHora());
                    System.out.println(String.format("%-10s", DataHoraCompra) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + HoraSecao) + "\t"
                    + String.format("%-20s", "|" + V.getQtdVendido()));
                   }
            }else{
            System.out.println("Nenhuma Venda foi encontrada neste horario!!!");
            }  
        } catch (ParseException ex) {
                System.out.println("Data ou hora no formato inválido!");
        }
    }

    private void buscaVendaPorSala() {
        System.out.println("######## Busca de Vendas por Sala ############");
        SalaUI listaSalas = new SalaUI(Salas);
        listaSalas.mostrarSalas();
        int Num_Sala=Console.scanInt("Informe o numero da sala que deseja pesquisar as Vendas:");
        List<Venda> VendasEncontrada=listaVenda.VendaPorSala(Salas.buscarSalasPorNumero(Num_Sala));
        if(!VendasEncontrada.isEmpty()){
                 System.out.println(String.format("%-10s", "DATA/HORA DA COMPRA") + "\t"
                + String.format("%-20s", "|NOME DO FILME") + "\t"
                + String.format("%-20s", "|SALA DA SECAO") + "\t"
                + String.format("%-20s", "|HORARIO DA SECAO") + "\t"
                + String.format("%-20s", "|QUANTIDADE COMPRADO"));
        for (Venda V : VendasEncontrada) {
            String DataHoraCompra = DateUtil.dateHourToString(V.getDataHora());
            String HoraSecao = DateUtil.hourToString(V.getSecao().getDataHora());
            System.out.println(String.format("%-10s", DataHoraCompra) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + V.getSecao().getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + HoraSecao) + "\t"
                    + String.format("%-20s", "|" + V.getQtdVendido()));
        }
        }else{
            System.out.println("Nenhuma Venda foi encontrada para esta sala!!!");
        }
    }
}


package viewUI;

import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import repositorio.RepositorioFilme;
import repositorio.RepositorioSala;
import repositorio.RepositorioSecao;
import util.Console;
import util.DateUtil;
import view.menu.SecaoMenu;

class SecaoUI {
private RepositorioFilme Filmes;
private RepositorioSala Salas;
private RepositorioSecao listaSecao;
    public SecaoUI(RepositorioFilme listaFilmes, RepositorioSala listaSalas, RepositorioSecao listaSecao) {
        this.Filmes=listaFilmes;
        this.Salas=listaSalas;
        this.listaSecao=listaSecao;
    }
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(SecaoMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case SecaoMenu.OP_CADASTRAR:
                    cadastrarSecao();
                    break;
                case SecaoMenu.OP_LISTAR:
                    mostrarSecoes();
                    break;
                case SecaoMenu.OP_BUSCAFILME:
                    buscaSecoesPorFilme();
                    break;
                case SecaoMenu.OP_BUSCADATAHORA:
                    buscaSecoesPorDataHora();
                    break;
                case SecaoMenu.OP_BUSCASALA:
                    buscaSecoesPorSala();
                    break;
                case SecaoMenu.OP_FILMESTOP:
                    MostraFilmesEmMaisSecoes();
                    break;
                case SecaoMenu.OP_SALASTOP:
                    MostraSalasEmMaisSecoes();
                    break;
                case SecaoMenu.OP_VOLTAR:
                    System.out.println("Retornando ao menu principal..");
                    break;
                default:
                    System.out.println("Opção inválida..");
            }
        } while (opcao != SecaoMenu.OP_VOLTAR);
    } 

    private void cadastrarSecao() {
         System.out.println("========= Cadastro de Secao ============");
        SalaUI sala = new SalaUI(Salas);
        sala.mostrarSalas();
        int numeroSala = Console.scanInt("Informe o numero da sala escolhida:");
        String dataHora = Console.scanString("informe a hora da seção neste formato (hh:mm):");
            Date horario=null;
            boolean AchouSecao=false;
            try {
               horario = DateUtil.stringToHour(dataHora);
               List<Secao> SecaoEncontrada=listaSecao.MostrarSecoesEmUmaSala(numeroSala);
               ArrayList<String> DatasDeSecoes = new ArrayList<String>();
               if(!SecaoEncontrada.isEmpty()){
                   for(Secao S : SecaoEncontrada){
                       String hora = DateUtil.hourToString(S.getDataHora());
                       DatasDeSecoes.add(hora);
                  }
                  
                  for(String HorasSecao : DatasDeSecoes){
                      if(HorasSecao.equals(dataHora)){
                          AchouSecao=true;
                   }
               }
             }
            } catch (ParseException ex) {
                System.out.println("Data ou hora no formato inválido!");
            }
            if(AchouSecao==false && horario!=null){
                Sala Sala = Salas.buscarSalasPorNumero(numeroSala);
                FilmeUI filmescadastrados = new FilmeUI(Filmes);
                filmescadastrados.mostrarFilmes();
                int codigo = Console.scanInt("Informe o codigo do filme para a secao:");
                Filme FilmeEscolhido = Filmes.buscarFilmePorCod(codigo);
                listaSecao.addSecao(new Secao(Sala,horario,FilmeEscolhido));
                System.out.println("Secao Cadastrada com Sucesso!!!");
            }else{
                System.out.println("Ja existe uma secao na mesma sala e horario informado!!!");
            }
    }

    private void mostrarSecoes() {
                System.out.println("------------- Relatorio de Secoes ----------------\n");
        System.out.println(String.format("%-10s", "NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
        for (Secao S : listaSecao.getListaSecoes()) {
            String horario = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horario) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
        }
    }

    private void buscaSecoesPorFilme() {
        System.out.println("######## Busca de Secoes por Filme ############");
        FilmeUI listaFilmes = new FilmeUI(Filmes);
        listaFilmes.mostrarFilmes();
        int cod_filme=Console.scanInt("Informe o codigo do filme que deseja pesquisar as secoes:");
        List<Secao> SecoesEncontrada=listaSecao.MostrarSecoesDeUmFilme(cod_filme);
        if(!SecoesEncontrada.isEmpty()){
            System.out.println("<< Secoes com este filme >>");
            System.out.println(String.format("%-10s", "NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
         for (Secao S : SecoesEncontrada) {
            String horario = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horario) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
         }
        }else{
            System.out.println("Nenhuma Secao foi encontrada para este filme!!!");
        }
    }

    private void buscaSecoesPorDataHora() {
                System.out.println("######## Busca de Secoes pelo Horario ############");
        String hora_secao=Console.scanString("Informe o horario para busca das secoes, no formato(HH:mm):");
        Date horario=null;
            try {
               horario = DateUtil.stringToHour(hora_secao);
               
            } catch (ParseException ex) {
                System.out.println("Data ou hora no formato inválido!");
            }
        List<Secao> SecoesEncontrada=listaSecao.MostrarSecoesEmUmaHora(horario);
        if(!SecoesEncontrada.isEmpty()){
            System.out.println("<< Secoes no horario das "+hora_secao+">>");
            System.out.println(String.format("%-10s", "NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
         for (Secao S : SecoesEncontrada) {
            String horaEncontrada = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horaEncontrada) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
         }
        }else{
            System.out.println("Nenhuma Secao foi encontrada neste horario!!!");
        }
    }

    private void buscaSecoesPorSala() {
        System.out.println("######## Busca de Secoes por Sala ############");
        SalaUI listaSalas = new SalaUI(Salas);
        listaSalas.mostrarSalas();
        int num_sala=Console.scanInt("Informe o numero da sala que deseja pesquisar as secoes:");
        List<Secao> SecoesEncontrada=listaSecao.MostrarSecoesEmUmaSala(num_sala);
        if(!SecoesEncontrada.isEmpty()){
            System.out.println("<< Secoes com esta sala >>");
            System.out.println(String.format("%-10s", "NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
         for (Secao S : SecoesEncontrada) {
            String horario = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horario) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
         }
        }else{
            System.out.println("Nenhuma Secao foi encontrada para esta sala!!!");
        }  
    }

    private void MostraFilmesEmMaisSecoes() {
        System.out.println("************ Relatório de filmes - Numero de Secoes ***********");
        listaSecao.FilmesEmMaisSecoes();
    }

    private void MostraSalasEmMaisSecoes() {
        System.out.println("************ Relatório de Salas - Numero de Secoes ***********");
        listaSecao.SalasEmMaisSecoes();
    }
}

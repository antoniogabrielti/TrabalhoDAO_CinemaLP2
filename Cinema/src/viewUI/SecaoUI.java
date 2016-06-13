
package viewUI;

import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import negocio.SalaNegocio;
import negocio.SecaoNegocio;
import util.Console;
import util.DateUtil;
import view.menu.SecaoMenu;

class SecaoUI {
    private SecaoNegocio secaoNegocio;

    public SecaoUI() {
        secaoNegocio = new SecaoNegocio();
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
                case SecaoMenu.OP_ATUALIZASECAO:
                    //AtualizarSecao();
                    break;
                case SecaoMenu.OP_DELETESECAO:
                    //ExcluirSecao();
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
         String dataHora = Console.scanString("informe a hora da seção neste formato (hh:mm):");
            Date horario=null;
            try {
               horario = DateUtil.stringToHour(dataHora);
            
 
                SalaUI sala = new SalaUI();
                sala.mostrarSalas();
                int numeroSala = Console.scanInt("Informe o numero da sala escolhida:");
                SalaNegocio salas = new SalaNegocio();
                Sala Sala = salas.procurarSalaPorNumero(numeroSala);
                if(Sala==null){
                    System.out.println("Sala Invalida!!!");
                    return;
                }else{
                    FilmeUI filmescadastrados = new FilmeUI();
                    filmescadastrados.mostrarFilmes();
                    int codigo = Console.scanInt("Informe o codigo do filme para a secao:");
                    FilmeNegocio Filmes = new FilmeNegocio();
                    Filme FilmeEscolhido = Filmes.procurarFilmePorCod(codigo);
                    if(FilmeEscolhido==null){
                        System.out.println("Filme Invalido!!!");
                        return;
                    }else{
                        secaoNegocio.salvar(new Secao(Sala,horario,FilmeEscolhido));
                        System.out.println("Secao Cadastrada com Sucesso!!!");
                    }   
                }
            } catch (ParseException ex) {
                System.out.println("hora no formato inválido!");
                return;
            } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
        }
    }
                
    public boolean mostrarSecoes() {
        List<Secao> listaSecoes = secaoNegocio.listar();
        return(this.verSecoes(listaSecoes));    
        }
    
        public boolean verSecoes(List<Secao> listaSecoes){
        System.out.println("------------ Relatorio de Secoes -----------------\n");
        if (listaSecoes.isEmpty()) {
            System.out.println("Secoes nao encontradas!");
            return true;
        }else{
            System.out.println(String.format("%-10s", "ID") + "\t"
                + String.format("%-20s", "|SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|CAPACIDADE"));
            for (Secao s : listaSecoes) {
                String horario = DateUtil.hourToString(s.getDataHora());
                if(s.getQtdDisponivel()>0 && !horario.isEmpty() && horario!=null){
                     System.out.println(String.format("%-10s", s.getId()) + "\t"
                      + String.format("%-20s", "|" + s.getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + s.getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + s.getQtdDisponivel() + " Lugares Disponiveis."));
                }
            
               
              }
            return false;
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

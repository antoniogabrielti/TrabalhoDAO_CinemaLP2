package viewUI;

import repositorio.RepositorioFilme;
import repositorio.RepositorioSala;
import repositorio.RepositorioSecao;
import repositorio.RepositorioVenda;
import util.Console;
import view.menu.MainMenu;



/**
 *
 * @author lhries
 */
public class MainUI {
    private RepositorioFilme listaFilmes;
    private RepositorioSala listaSalas;
    private RepositorioSecao listaSecao;
    private RepositorioVenda listaVenda;

    public MainUI() {
        listaFilmes = new RepositorioFilme();
        listaSalas = new RepositorioSala();
        listaSecao = new RepositorioSecao();
        listaVenda = new RepositorioVenda();
    }

    public void executar() {
        int opcao = 0;
        do {
            System.out.println(MainMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case MainMenu.OP_FILMES:
                   new FilmeUI().executar();
                    break;
                case MainMenu.OP_SALAS:
                   new SalaUI(listaSalas).executar();
                    break;
                case MainMenu.OP_SECOES:
                   new SecaoUI(listaFilmes,listaSalas,listaSecao).executar();
                    break;
                case MainMenu.OP_VENDA:
                  new VendaUI(listaSecao,listaVenda,listaFilmes,listaSalas).executar();
                    break;
                case MainMenu.OP_SAIR:
                    System.out.println("Aplicação finalizada!!!");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != MainMenu.OP_SAIR);
    }
}

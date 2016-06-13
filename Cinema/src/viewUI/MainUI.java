package viewUI;


import negocio.NegocioException;
import util.Console;
import view.menu.MainMenu;

public class MainUI {
    
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
                   new SalaUI().executar();
                    break;
                case MainMenu.OP_SECOES:
                   new SecaoUI().executar();
                    break;
                case MainMenu.OP_VENDA:
                  //new VendaUI().executar();
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

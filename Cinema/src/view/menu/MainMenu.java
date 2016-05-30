package view.menu;

public class MainMenu {

    public static final int OP_FILMES = 1;
    public static final int OP_SALAS = 2;
    public static final int OP_SECOES = 3;
    public static final int OP_VENDA = 4;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Menu Filmes\n"
                + "2- Menu Salas\n"
                + "3- Menu Secoes\n"
                + "4- Menu Venda\n"
                + "0- Sair da Aplicação"
                + "\n--------------------------------------");
    }
}

package view.menu;

public class SalaMenu {
    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_BUSCANUM = 3;
    public static final int OP_BUSCACAPACIDADE = 4;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar sala\n"
                + "2- Listar salas\n"
                + "3- Buscar sala por numero\n"
                + "4- Buscar salas por capacidade\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }        
}

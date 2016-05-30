package view.menu;

public class FilmeMenu {
    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_BUSCANOME = 3;
    public static final int OP_BUSCACOD = 4;
    public static final int OP_DELETARFILME = 5;
    public static final int OP_ATUALIZARFILME = 6;
    public static final int OP_VOLTAR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar filme\n"
                + "2- Listar filmes\n"
                + "3- Buscar filme por nome\n"
                + "4- Buscar filme por codigo\n"
                + "5- Deletar um filme\n"
                + "6- Atualizar um filme\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }        
}

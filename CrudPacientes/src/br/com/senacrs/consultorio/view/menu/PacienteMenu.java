package br.com.senacrs.consultorio.view.menu;

public class PacienteMenu {
    public static final int OP_CADASTRAR = 1;
    public static final int OP_DELETAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_LISTAR = 4;
    public static final int OP_CONSULTAR = 5;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar Pacientes\n"
                + "2- Deletar Paciente\n"
                + "3- Atualizar dados do Paciente\n"
                + "4- Listar Pacientes\n"
                + "5- Consultar Pacientes por Nome\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }    
}

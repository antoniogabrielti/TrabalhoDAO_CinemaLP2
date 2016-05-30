package br.com.senacrs.consultorio.view;

import java.text.ParseException;
import br.com.senacrs.consultorio.dominio.Paciente;
import br.com.senacrs.consultorio.negocio.NegocioException;
import br.com.senacrs.consultorio.negocio.PacienteNegocio;
import br.com.senacrs.consultorio.util.Console;
import br.com.senacrs.consultorio.util.DateUtil;
import br.com.senacrs.consultorio.view.menu.PacienteMenu;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author lhries
 */
public class PacienteUI {

    private PacienteNegocio pacienteNegocio;

    public PacienteUI() {
        pacienteNegocio = new PacienteNegocio();
    }

    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(PacienteMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case PacienteMenu.OP_CADASTRAR:
                        cadastrarPaciente();
                        break;
                    case PacienteMenu.OP_DELETAR:
                        deletarPaciente();
                        break;
                    case PacienteMenu.OP_ATUALIZAR:
                        atualizarPaciente();
                        break;
                    case PacienteMenu.OP_LISTAR:
                        mostrarPacientes();
                        break;
                    case PacienteMenu.OP_CONSULTAR:
                        consultarPacientesPorNome();
                        break;
                    case PacienteMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != PacienteMenu.OP_SAIR);
    }

    private void cadastrarPaciente() {
        String rg = Console.scanString("RG: ");
        String nome = Console.scanString("Nome: ");
        String dataString = Console.scanString("Data de Nascimento: ");
        try {
            pacienteNegocio.salvar(new Paciente(rg, nome, DateUtil.stringToDate(dataString)));
            System.out.println("Paciente " + nome + " cadastrado com sucesso!");
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } catch (ParseException ex) {
            UIUtil.mostrarErro("Formato de Data inválido!");
        }
    }

    public void mostrarPacientes() {
        List<Paciente> listaPacientes = pacienteNegocio.listar();
        this.mostrarPacientes(listaPacientes);
    }

    private void deletarPaciente() {
        String rg = Console.scanString("RG do paciente a ser deletado: ");
        try {
            Paciente pac = pacienteNegocio.procurarPorRg(rg);
            this.mostrarPaciente(pac);
            if (UIUtil.getConfirmacao("Realmente deseja excluir esse paciente?")) {
                pacienteNegocio.deletar(pac);
                System.out.println("Paciente deletado com sucesso!");
            } else {
                System.out.println("Operacao cancelada!");
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

    private void atualizarPaciente() {
        String rg = Console.scanString("RG do paciente a ser alterado: ");
        try {
            Paciente pac = pacienteNegocio.procurarPorRg(rg);
            this.mostrarPaciente(pac);

            System.out.println("Digite os dados do paciente que quer alterar [Vazio caso nao queira]");
            String nome = Console.scanString("Nome: ");
            String dataString = Console.scanString("Data de Nascimento: ");
            if (!nome.isEmpty()) {
                pac.setNome(nome);
            }
            if (!dataString.isEmpty()) {
                pac.setDataNascimento(DateUtil.stringToDate(dataString));
            }

            pacienteNegocio.atualizar(pac);
            System.out.println("Paciente " + nome + " atualizado com sucesso!");
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } catch (ParseException ex) {
            UIUtil.mostrarErro("Formato de Data inválido!");
        }
    }

    private void consultarPacientesPorNome() {
        String nome = Console.scanString("Nome: ");
        try {
            List<Paciente> listaPac = pacienteNegocio.procurarPorNome(nome);
            this.mostrarPacientes(listaPac);
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }

    }

    private void mostrarPaciente(Paciente p) {
        System.out.println("-----------------------------");
        System.out.println("Paciente");
        System.out.println("RG: " + p.getRg());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Data de Nascimento: "
                + DateUtil.dateToString(p.getDataNascimento()));
        System.out.println("-----------------------------");
    }

    private void mostrarPacientes(List<Paciente> listaPacientes) {
        if (listaPacientes.isEmpty()) {
            System.out.println("Pacientes nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "RG") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|DATA DE NASCIMENTO"));
            for (Paciente paciente : listaPacientes) {
                System.out.println(String.format("%-10s", paciente.getRg()) + "\t"
                        + String.format("%-20s", "|" + paciente.getNome()) + "\t"
                        + String.format("%-20s", "|" + DateUtil.dateToString(paciente.getDataNascimento())));
            }
        }
    }
}

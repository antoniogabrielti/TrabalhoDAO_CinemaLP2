package viewUI;

import dominio.Sala;
import java.util.List;
import negocio.NegocioException;
import negocio.SalaNegocio;
import util.Console;
import view.menu.SalaMenu;


class SalaUI {
    private SalaNegocio salaNegocio;

    public SalaUI() {
        salaNegocio = new SalaNegocio();
    }
    
        public void executar() {
        int opcao = 0;
        do {
            System.out.println(SalaMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case SalaMenu.OP_CADASTRAR:
                    cadastrarSala();
                    break;
                case SalaMenu.OP_LISTAR:
                    mostrarSalas();
                    break;
                case SalaMenu.OP_BUSCANUM:
                    buscaSalaPorNumero();
                    break;
                case SalaMenu.OP_BUSCACAPACIDADE:
                    buscaSalaPorCapacidade();
                    break;
                case SalaMenu.OP_ATUALIZASALA:
                    atualizarSala();
                    break;
                case SalaMenu.OP_DELETASALA:
                    deletaSala();
                    break;
                case SalaMenu.OP_VOLTAR:
                    System.out.println("Retornando ao menu principal..");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != SalaMenu.OP_VOLTAR);
    }  

    private void cadastrarSala() {
        System.out.println("===== Cadastro de Salas ======");
        int num = Console.scanInt("Digite o numero da Sala:");
        
        try {
            if (salaNegocio.SalaExiste(num)) {
                System.out.println("Sala com esse numero já existe, já cadastrada!!!");
            } else {
                int capacidade = Console.scanInt("Informe a capacidade dessa sala [sendo a capacidade minima 20]:");
                salaNegocio.salvar(new Sala(num,capacidade));
                System.out.println("Sala "+num+" cadastrada com sucesso!!!"); 
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

        public boolean mostrarSalas() {
        List<Sala> listaSala = salaNegocio.listar();
        return(this.verSalas(listaSala));    
        }
        public boolean verSalas(List<Sala> listaSala){
        System.out.println("------------ Relatorio de Salas -----------------\n");
        if (listaSala.isEmpty()) {
            System.out.println("Salas nao encontradas!");
            return true;
        }else{
            System.out.println(String.format("%-10s", "NUMERO") + "\t"
                + String.format("%-20s", "|CAPACIDADE"));
            for (Sala s : listaSala) {
                System.out.println(String.format("%-10s", s.getNumero()) + "\t"
                  + String.format("%-20s", "|" + s.getCapacidade()));
              }
            return false;
        }
    }

    private void buscaSalaPorNumero() {
        System.out.println("######## Busca de Sala pelo Numero ############");
        int numero = Console.scanInt("Informe o numero da sala para a busca:");
        try {
            Sala salaEncontrada = salaNegocio.procurarSalaPorNumero(numero);
             if(salaEncontrada!=null){
            System.out.println(String.format("%-10s", "NUMERO") + "\t"
                + String.format("%-20s", "|CAPACIDADE"));
            System.out.println(String.format("%-10s", salaEncontrada.getNumero()) + "\t"
                    + String.format("%-20s", "|" + salaEncontrada.getCapacidade()));
            
        }else{
            System.err.println("Nenhuma Sala foi encontrada com este numero!!!");
        }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

    private void buscaSalaPorCapacidade() {
        System.out.println("######## Busca de Salas por Capacidade ############");
        int capacidade = Console.scanInt("Informe a capacidade da qual necessita que a sala tenha:");
        try {
            List<Sala> SalasEncontradas = salaNegocio.procurarPorCapacidade(capacidade);
            if(!SalasEncontradas.isEmpty()){
            System.out.println(String.format("%-10s", "NUMERO") + "\t"
                + String.format("%-20s", "|CAPACIDADE"));
            for (Sala s : SalasEncontradas) {
                System.out.println(String.format("%-10s", s.getNumero()) + "\t"
                     + String.format("%-20s", "|" + s.getCapacidade()));
            }
        }else{
            System.err.println("Nenhuma sala comporta esta capacidade informada!!!");
        }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

    private void deletaSala() {
               try {
            if(this.mostrarSalas()){
                System.out.println("Nao ha Salas cadastradas!!!");
                return;
            }else{
               int num = Console.scanInt("Digite o numero da sala no qual deseja excluir:");
               Sala s = salaNegocio.procurarSalaPorNumero(num);
               if (s!=null && UIUtil.getConfirmacao("Realmente deseja excluir essa sala?")) {
                     salaNegocio.deletar(s);
                 System.out.println("Sala deletada com sucesso!");
                } else {
                   if(s==null){
                       System.out.println("Numero incorreto nenhuma sala encontrada!!!");
                       return;
                   }
                System.out.println("Operacao cancelada!");
                }
           }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

    private void atualizarSala() {
        try {
            if(this.mostrarSalas()){
                System.out.println("Nao ha Salas cadastradas!!!");
                return;
            }else{
               int num = Console.scanInt("Digite o numero da sala na qual deseja alterar os dados:");
               Sala s = salaNegocio.procurarSalaPorNumero(num);
                if(s!=null){
                    System.out.println("-----------------------------");
                    System.out.println("Sala");
                    System.out.println("Numero: " + s.getNumero());
                    System.out.println("Capacidade: " + s.getCapacidade());
                    System.out.println("-----------------------------");
                    System.out.println("Digite os dados que deseja alterar na sala [Vazio caso nao queira]");
                    int numAlterado,capacidade;
            do{
                    numAlterado = Console.scanInt("Numero: ");
            }while(!TestaNumeroEditar(numAlterado,s));
            do{
                capacidade = Console.scanInt("Capacidade: ");
                if(capacidade<20){
                    System.out.println("A capacidade minima das salas sao de 20!!!");
                }else{
                    s.setCapacidade(capacidade);
                    break;
                }
            }while(capacidade<20);
            
            salaNegocio.atualizar(s);
            System.out.println("Sala " + s.getNumero() + " atualizada com sucesso!");
                }else{
                    System.out.println("Numero de sala invalida!!!");
                    return;
                }
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }
    public boolean TestaNumeroEditar(int numAlterado,Sala s){
          if (numAlterado!=s.getNumero() && numAlterado>0) {
             if(!salaNegocio.SalaExiste(numAlterado))
                  s.setNumero(numAlterado);
                  return true;
             }else{
                  System.out.println("Esse numero de Sala ja esta cadastrado!!!");
                  return false;
             }
          }
    }



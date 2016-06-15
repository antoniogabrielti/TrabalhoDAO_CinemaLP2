
package viewUI;

import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import negocio.SalaNegocio;
import negocio.SecaoNegocio;
import util.Console;
import util.DateUtil;
import view.menu.SecaoMenu;

class SecaoUI {
    private SecaoNegocio secaoNegocio;
    private FilmeUI filmesCadastrados;
    private FilmeNegocio filmeNegocio;
    private SalaUI salasCadastradas;
    private SalaNegocio salaNegocio;

    public SecaoUI() {
        secaoNegocio = new SecaoNegocio();
        filmesCadastrados = new FilmeUI();
        filmeNegocio = new FilmeNegocio();
        salasCadastradas = new SalaUI();
        salaNegocio = new SalaNegocio();
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
                    AtualizarSecao();
                    break;
                case SecaoMenu.OP_DELETESECAO:
                    ExcluirSecao();
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
                        
                salasCadastradas.mostrarSalas();
                int numeroSala = Console.scanInt("Informe o numero da sala escolhida:");
                Sala Sala = salaNegocio.procurarSalaPorNumero(numeroSala);
                if(Sala==null){
                    System.out.println("Sala Invalida!!!");
                    return;
                }else{
                    filmesCadastrados.mostrarFilmes();
                    int codigo = Console.scanInt("Informe o codigo do filme para a secao:");
                    Filme FilmeEscolhido = filmeNegocio.procurarFilmePorCod(codigo);
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
                if(!horario.isEmpty() && horario!=null){
                    String disponivel=null;
                    if(s.getQtdDisponivel()>0){
                        disponivel=s.getQtdDisponivel()+" Lugares Disponiveis.";
                    }else{
                        disponivel="ESGOTADA";
                    }
                     System.out.println(String.format("%-10s", s.getId()) + "\t"
                      + String.format("%-20s", "|" + s.getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + s.getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + disponivel ));                     
                }
            
               
              }
            return false;
        }
    }

    private void buscaSecoesPorFilme() {
        System.out.println("######## Busca de Secoes por Filme ############");
        filmesCadastrados.mostrarFilmes();
        int cod_filme=Console.scanInt("Informe o codigo do filme que deseja pesquisar as secoes:");
       
        try {
           Filme filme = filmeNegocio.procurarFilmePorCod(cod_filme);
           if(filme!=null){
               List<Secao> SecoesEncontrada=secaoNegocio.procurarSecaoPorFilme(filme);
            if(!SecoesEncontrada.isEmpty()){
                System.out.println("<< Secoes com este filme >>");
                System.out.println(String.format("%-10s", "ID") + "\t"
                    + String.format("%-20s", "|NUMERO DA SALA") + "\t"
                    + String.format("%-20s", "|HORARIO") + "\t"
                    + String.format("%-20s", "|FILME") + "\t"
                    + String.format("%-20s", "|GENERO"));
            for (Secao S : SecoesEncontrada) {
                String horario = DateUtil.hourToString(S.getDataHora());
                    System.out.println(String.format("%-10s", S.getId()) + "\t"
                        + String.format("%-20s", "|" + S.getSala().getNumero()) + "\t"
                        + String.format("%-20s", "|" + horario) + "\t"
                        + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                        + String.format("%-20s", "|" + S.getFilme().getGenero()));
                }
            }else{
                System.out.println("Nenhuma Secao foi encontrada para este filme!!!");
            }
           }else{
               System.err.println("Codigo do filme incorreto!!!");
           }  
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
        }
    }

    private void buscaSecoesPorDataHora() {
                System.out.println("######## Busca de Secoes pelo Horario ############");
        String hora_secao=Console.scanString("Informe o horario para busca das secoes, no formato(HH:mm):");
        Date horario=null;
            try {
               horario = DateUtil.stringToHour(hora_secao);
             
            List<Secao> SecoesEncontrada=secaoNegocio.procurarSecaoPorHorario(horario);
        if(!SecoesEncontrada.isEmpty()){
            System.out.println("<< Secoes no horario das "+hora_secao+">>");
            System.out.println(String.format("%-10s", "ID") + "\t"
                + String.format("%-20s", "|NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
            for (Secao S : SecoesEncontrada) {
                String horaEncontrada = DateUtil.hourToString(S.getDataHora());
                System.out.println(String.format("%-10s", S.getId()) + "\t"
                    + String.format("%-20s", "|" + S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horaEncontrada) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
            }
        }else{
            System.out.println("Nenhuma Secao foi encontrada neste horario!!!");
        }
         } catch (ParseException ex) {
                System.out.println("hora no formato invalido!");
                return;
         } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
         }
    }

    private void buscaSecoesPorSala() {
        System.out.println("######## Busca de Secoes por Sala ############");
          this.salasCadastradas.mostrarSalas();
          int num_sala=Console.scanInt("Informe o numero da sala que deseja pesquisar as secoes:");
          try{
            Sala s = this.salaNegocio.procurarSalaPorNumero(num_sala);
            if(s==null){
                System.err.println("Numero de sala nao existe!!!");
                return;
            }
            List<Secao> SecoesEncontrada=secaoNegocio.procurarSecaoPorSala(s);
        if(!SecoesEncontrada.isEmpty()){
            System.out.println("<< Secoes com esta sala >>");
            System.out.println(String.format("%-10s", "ID") + "\t"
                + String.format("%-20s", "|NUMERO DA SALA") + "\t"
                + String.format("%-20s", "|HORARIO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|GENERO"));
         for (Secao S : SecoesEncontrada) {
            String horario = DateUtil.hourToString(S.getDataHora());
            System.out.println(String.format("%-10s", S.getId()) + "\t"
                    + String.format("%-20s", "|" + S.getSala().getNumero()) + "\t"
                    + String.format("%-20s", "|" + horario) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getNome()) + "\t"
                    + String.format("%-20s", "|" + S.getFilme().getGenero()));
         }
        }else{
            System.out.println("Nenhuma Secao foi encontrada para esta sala!!!");
        } 
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
         }
    }

    private void AtualizarSecao() {
         try {
            if(this.mostrarSecoes()){
                System.out.println("Nao ha Secoes cadastradas!!!");
                return;
            }else{
               int cod = Console.scanInt("Digite o ID da secao no qual deseja alterar os dados:");
               Secao s = secaoNegocio.procurarSecaoPorID(cod);
                if(s!=null){
                    String Hora = DateUtil.hourToString(s.getDataHora());
                    System.out.println("-----------------------------");
                    System.out.println("Secao");
                    System.out.println("ID Secao: " + s.getId());
                    System.out.println("Sala: " + s.getSala().getNumero());
                    System.out.println("Horario: " + Hora);
                    System.out.println("Codigo do Filme: " + s.getFilme().getCodigo());
                    System.out.println("Filme: "+s.getFilme().getNome());
                    System.out.println("-----------------------------");
                    System.out.println("Digite os dados que deseja alterar na secao [Vazio caso nao queira]");
            Sala sala=null;
            do{
                
            Integer numsala;
            this.salasCadastradas.mostrarSalas();
            String num = Console.scanString("numero de sala: ");
            if(num!=null && !num.isEmpty()){
                numsala = Integer.parseInt(num);
                if(numsala>0){
                 sala = this.salaNegocio.procurarSalaPorNumero(numsala);
                    if(sala==null){
                        System.out.println("Nenhuma Sala Encontrada!!!");
                    }
                }else{
                    sala=null;
                    System.err.println("Numero de Sala Invalido!!!");
                }
                }else{
                    numsala=s.getSala().getNumero();
                    sala=s.getSala();
                }
            }while(sala==null);
            Date horario=null;
            do{
                String hora_secao=Console.scanString("Horario: ");
                if(hora_secao!=null && !hora_secao.isEmpty()){
                    String textoString[] = hora_secao.split(":");
                if(textoString.length==2 && !textoString[0].equals(":")
                     && textoString[0].length()==2 && !textoString[1].equals(":") && textoString[1].length()==2){
                        horario=DateUtil.stringToHour(hora_secao);
                    }else{
                        System.err.println("hora no formato incorreto!!!");
                        horario=null;
                    }
                }else{
                    if(hora_secao.isEmpty()){
                        horario=s.getDataHora();
                    }
                }
            }while(horario==null);
            Filme f =null;
            do{
            this.filmesCadastrados.mostrarFilmes();
            Integer codFilme;
            
            String codstring = Console.scanString("codigo do filme: ");
            if(codstring!=null && !codstring.isEmpty()){
                codFilme = Integer.parseInt(codstring);
                if(codFilme>0){
                 f = this.filmeNegocio.procurarFilmePorCod(codFilme);
                         if(f==null){
                            System.out.println("Nenhuma Filme Encontrado!!!");
                        }
                    }else{
                       f=null;
                       System.err.println("Codigo do Filme Invalido!!!");
                    }
                }else{
                    codFilme=s.getFilme().getCodigo();
                    f=s.getFilme();
                }
            }while(f==null);
            secaoNegocio.atualizar(new Secao(sala,horario,f,s.getQtdDisponivel(), s.getId()));
                    System.out.println("Secao Atualizada Com Sucesso!!!");
                }else{
                    System.out.println("ID da Secao Nao Encontrado!!!");
                    return;
                }
            }
        } catch (ParseException ex) {
                System.out.println("hora no formato invalido!");
      
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
        }           
    }

    private void ExcluirSecao() {
        try{
            if(this.mostrarSecoes()){
                System.out.println("Nao ha Secoes cadastradas!!!");
                return;
            }else{
               int cod = Console.scanInt("Digite o ID da secao no qual deseja excluir:");
               Secao s = secaoNegocio.procurarSecaoPorID(cod);
                if(s!=null){
                    String Hora = DateUtil.hourToString(s.getDataHora());
                    System.out.println("-----------------------------");
                    System.out.println("Secao");
                    System.out.println("ID Secao: " + s.getId());
                    System.out.println("Sala: " + s.getSala().getNumero());
                    System.out.println("Horario: " + Hora);
                    System.out.println("Codigo do Filme: " + s.getFilme().getCodigo());
                    System.out.println("Filme: "+s.getFilme().getNome());
                    System.out.println("-----------------------------");
                    if(UIUtil.getConfirmacao("Realmente deseja excluir essa Secao?")) {
                     secaoNegocio.deletar(s);
                     System.out.println("Secao deletada com sucesso!");
                    } else {
                       System.out.println("Operacao cancelada!");
                    }
                }else{
                    System.out.println("ID da Secao Incorreto, nenhuma secao encontrada!!!");
                    return;
                }
           }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } 
    }
}



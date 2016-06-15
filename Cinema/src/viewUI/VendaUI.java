package viewUI;

import dominio.Filme;
import dominio.Sala;
import dominio.Secao;
import dominio.TopFilmeSala;
import dominio.Venda;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import negocio.SalaNegocio;
import negocio.SecaoNegocio;
import negocio.VendaNegocio;
import util.Console;
import util.DateUtil;
import view.menu.VendaMenu;

class VendaUI {
    private VendaNegocio vendaNegocio;
    private SecaoNegocio secaoNegocio;
    private SecaoUI secoesCadastradas;
    private FilmeUI filmesCadastrados;
    private FilmeNegocio filmeNegocio;
    private SalaUI salasCadastradas;
    private SalaNegocio salaNegocio;
    public VendaUI() {
        vendaNegocio= new VendaNegocio();
        secoesCadastradas=new SecaoUI();
        secaoNegocio = new SecaoNegocio();
        filmesCadastrados = new FilmeUI();
        filmeNegocio = new FilmeNegocio();
        salasCadastradas = new SalaUI();
        salaNegocio = new SalaNegocio();
    }
    public void executar() {
        int opcao = 0;
        do {
            System.out.println(VendaMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case VendaMenu.OP_CADASTRAR:
                    cadastrarVenda();
                    break;
                case VendaMenu.OP_LISTAR:
                    mostrarVendas();
                    break;
                case VendaMenu.OP_BUSCAFILME:
                    buscaVendaPorFilme();
                    break;
                case VendaMenu.OP_BUSCADATAHORA:
                    buscaVendaPorData();
                    break;
                case VendaMenu.OP_BUSCASALA:
                    buscaVendaPorSala();
                    break;
                case VendaMenu.OP_DELETAVENDA:
                    deletaVenda();
                    break;
                case VendaMenu.OP_ATUALIZAVENDA:
                    atualizaVenda();
                    break;
                case VendaMenu.OP_TOPSALAFILME:
                    relatorioTopSalaFilme();
                    break;
                case VendaMenu.OP_VOLTAR:
                    System.out.println("Retornando ao menu principal..");
                    break;
                default:
                    System.out.println("Opção inválida..");

            }
        } while (opcao != VendaMenu.OP_VOLTAR);
    }

private void cadastrarVenda() {
         System.out.println("========= Cadastro da Venda ============");
         try{
            if(this.secoesCadastradas.mostrarSecoes()){
                  System.out.println("Nao ha secoes cadastradas para venda!!!");
                  return;
            }else{
                Secao secaoEscolhida=null;
                do{
                 int idsecao = Console.scanInt("informe o ID da secao que deseja comprar ingresso:");
                 secaoEscolhida = this.secaoNegocio.procurarSecaoPorID(idsecao);
                 if(secaoEscolhida==null){
                     System.out.println("Nenhuma Secao encontrada com esse ID!!!");
                 }
                 if(secaoEscolhida.getQtdDisponivel()<=0){
                     System.err.println("Voce nao pode escolher uma secao ESGOTADA, informe uma secao com ingressos disponiveis");
                 }
                         
                }while(secaoEscolhida==null || secaoEscolhida.getQtdDisponivel()==0);
                int qtdCompra=-5;
                do{
                    qtdCompra= Console.scanInt("digite quantos ingressos para esta secao deseja comprar:");
                    if(qtdCompra<=0){
                        System.err.println("Quantidade nao pode ser zero ou negativa!!!");
                        qtdCompra=-5;
                    }
                    if(qtdCompra>secaoEscolhida.getQtdDisponivel()){
                        System.err.println("Quantidade ultrapassa a capacidade da secao!!!");
                        qtdCompra=-5;
                    }
                }while(qtdCompra==-5);
                Date dataCompra = new Date();
                vendaNegocio.salvar(new Venda(secaoEscolhida,qtdCompra,dataCompra));
                System.out.println("Venda Realizada com Sucesso!!!");
            }  

            } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
        }
    }
                
    public boolean mostrarVendas() {
        List<Venda> listaVendas = vendaNegocio.listar();
        return(this.verVendas(listaVendas));    
        }
    
        public boolean verVendas(List<Venda> listaVendas){
        System.out.println("------------ Relatorio de Vendas -----------------\n");
        if (listaVendas.isEmpty()) {
            System.out.println("Vendas nao encontradas!");
            return true;
        }else{
            System.out.println(String.format("%-10s", "DATA DA VENDA") + "\t"
                + String.format("%-20s", "|ID") + "\t"
                + String.format("%-20s", "|SALA") + "\t"
                + String.format("%-20s", "|HORARIO SECAO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|QUANTIDADE VENDIDA"));
            for (Venda v : listaVendas) {
                String horario = DateUtil.hourToString(v.getSecao().getDataHora());
                String data = DateUtil.dateToString(v.getDataHora());
                      System.out.println(String.format("%-10s", data) + "\t"
                      + String.format("%-20s", "|" + v.getIdvenda()) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + v.getQtdVendido() + " Ingressos."));
            }
            return false;
        }
    }

    private void buscaVendaPorFilme() {
        System.out.println("######## Busca de Vendas por Filme ############");
        filmesCadastrados.mostrarFilmes();
        int cod_filme=Console.scanInt("Informe o codigo do filme que deseja pesquisar as vendas:");
       
        try {
           Filme filme = filmeNegocio.procurarFilmePorCod(cod_filme);
           if(filme!=null){
               List<Venda> vendasEncontrada=vendaNegocio.procurarVendaPorFilme(filme);
            if(!vendasEncontrada.isEmpty()){
                System.out.println("<< Vendas com este filme >>");
                System.out.println(String.format("%-10s", "DATA DA VENDA") + "\t"
                + String.format("%-20s", "|ID") + "\t"
                + String.format("%-20s", "|SALA") + "\t"
                + String.format("%-20s", "|HORARIO SECAO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|QUANTIDADE VENDIDA"));
            for (Venda v : vendasEncontrada) {
                String horario = DateUtil.hourToString(v.getSecao().getDataHora());
                String data = DateUtil.dateToString(v.getDataHora());
                      System.out.println(String.format("%-10s", data) + "\t"
                      + String.format("%-20s", "|" + v.getIdvenda()) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + v.getQtdVendido() + " Ingressos."));
            }
            }else{
                System.out.println("Nenhuma Venda foi encontrada para este filme!!!");
            }
           }else{
               System.err.println("Codigo do filme incorreto!!!");
           }  
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
        }
    }

    private void buscaVendaPorData() {
                System.out.println("######## Busca de Venda pela Data ############");
        String dataVenda=Console.scanString("Informe a data para busca das vendas, no formato(dd/mm/aaaa):");
        Date dataBusca=null;
            try {
               dataBusca = DateUtil.stringToDate(dataVenda);
             
            List<Venda> vendaEncontrada=vendaNegocio.procurarVendaPorHorario(dataBusca);
        if(!vendaEncontrada.isEmpty()){
            System.out.println("<< Vendas na data "+dataVenda+">>");
                System.out.println(String.format("%-10s", "DATA DA VENDA") + "\t"
                + String.format("%-20s", "|ID") + "\t"
                + String.format("%-20s", "|SALA") + "\t"
                + String.format("%-20s", "|HORARIO SECAO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|QUANTIDADE VENDIDA"));
            for (Venda v : vendaEncontrada) {
                String horario = DateUtil.hourToString(v.getSecao().getDataHora());
                String data = DateUtil.dateToString(v.getDataHora());
                      System.out.println(String.format("%-10s", data) + "\t"
                      + String.format("%-20s", "|" + v.getIdvenda()) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + v.getQtdVendido() + " Ingressos."));
            }
        }else{
            System.out.println("Nenhuma Venda foi encontrada nesta data!!!");
        }
         } catch (ParseException ex) {
                System.out.println("data no formato invalido!");
                return;
         } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
         }
    }

    private void buscaVendaPorSala() {
        System.out.println("######## Busca de Vendas por Sala ############");
          this.salasCadastradas.mostrarSalas();
          int num_sala=Console.scanInt("Informe o numero da sala que deseja pesquisar as Vendas:");
          try{
            Sala s = this.salaNegocio.procurarSalaPorNumero(num_sala);
            if(s==null){
                System.err.println("Numero de sala nao existe!!!");
                return;
            }
            List<Venda> vendaEncontrada=vendaNegocio.procurarVendaPorSala(s);
        if(!vendaEncontrada.isEmpty()){
            System.out.println("<< Vendas com esta sala >>");
                System.out.println(String.format("%-10s", "DATA DA VENDA") + "\t"
                + String.format("%-20s", "|ID") + "\t"
                + String.format("%-20s", "|SALA") + "\t"
                + String.format("%-20s", "|HORARIO SECAO") + "\t"
                + String.format("%-20s", "|FILME") + "\t"
                + String.format("%-20s", "|QUANTIDADE VENDIDA"));
            for (Venda v : vendaEncontrada) {
                String horario = DateUtil.hourToString(v.getSecao().getDataHora());
                String data = DateUtil.dateToString(v.getDataHora());
                      System.out.println(String.format("%-10s", data) + "\t"
                      + String.format("%-20s", "|" + v.getIdvenda()) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getSala().getNumero()) + "\t"
                      + String.format("%-20s", "|" + horario) + "\t"
                      + String.format("%-20s", "|" + v.getSecao().getFilme().getNome()) + "\t"
                      + String.format("%-20s", "|" + v.getQtdVendido() + " Ingressos."));
            }
        }else{
            System.out.println("Nenhuma Venda foi encontrada para esta sala!!!");
        } 
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
            return;
         }
    }

    private void AtualizarVenda() {
         try {
            if(this.mostrarVendas()){
                System.out.println("Nao ha Vendas cadastradas!!!");
                return;
            }else{
               int cod = Console.scanInt("Digite o ID da venda na qual deseja alterar os dados:");
               Venda v = vendaNegocio.procurarVendaPorID(cod);
                if(v!=null){
                    String Hora = DateUtil.hourToString(v.getSecao().getDataHora());
                    String Data = DateUtil.dateToString(v.getDataHora());
                    System.out.println("-----------------------------");
                    System.out.println("Venda");
                    System.out.println("Data da Venda: " + Data);
                    System.out.println("Sala: " + v.getSecao().getSala().getNumero());
                    System.out.println("Horario: " + Hora);
                    System.out.println("Filme: "+v.getSecao().getFilme().getNome());
                    System.out.println("Quantidade Vendida: "+v.getQtdVendido());
                    System.out.println("-----------------------------");
                    System.out.println("Digite os dados que deseja alterar na venda [Vazio caso nao queira]");
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

    private void deletaVenda() {
        try{
            if(this.mostrarVendas()){
                System.out.println("Nao ha Vendas cadastradas!!!");
                return;
            }else{
               int cod = Console.scanInt("Digite o ID da venda na qual deseja excluir:");
               Venda v = vendaNegocio.procurarVendaPorID(cod);
                if(v!=null){
                    String Hora = DateUtil.hourToString(v.getSecao().getDataHora());
                    String Data = DateUtil.dateToString(v.getDataHora());
                    System.out.println("-----------------------------");
                    System.out.println("Venda");
                    System.out.println("ID Venda: " + v.getIdvenda());
                    System.out.println("Data da Venda: " + Data);
                    System.out.println("Sala: " + v.getSecao().getSala().getNumero());
                    System.out.println("Horario: " + Hora);
                    System.out.println("Filme: "+v.getSecao().getFilme().getNome());
                    System.out.println("Quantidade Vendida: "+v.getQtdVendido());
                    System.out.println("-----------------------------");
                    if(UIUtil.getConfirmacao("Realmente deseja excluir essa Venda?")) {
                     vendaNegocio.deletar(v);
                     System.out.println("Venda deletada com sucesso!");
                    } else {
                       System.out.println("Operacao cancelada!");
                    }
                }else{
                    System.out.println("ID da Venda nao existe, nenhuma venda encontrada!!!");
                    return;
                }
           }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } 
    }

    private void relatorioTopSalaFilme() {
        System.out.println("========= Relatorio Sala ou Filme mais Vendidos com Secoes ===========");
        boolean checkOp=false;
            try{
                do{
                    int opcao = Console.scanInt("Informe o relatorio que deseja visualizar, 1-Filme mais Vendido ou 2-Sala mais Vendida:");
                     if(opcao==1 || opcao==2){
                        checkOp=true;
                        if(opcao==1){
                            TopFilmeSala<Filme> Top = vendaNegocio.TopSalaOrFilme(opcao);
                            if(Top!=null){
                                System.out.println("-----------------------------");
                                System.out.println("FILME : "+Top.getDado().getNome());
                                System.out.println("Quantidade Total de Vendas: "+Top.getQtdVendida());
                                System.out.println("-----------------------------");
                            }else{
                                System.out.println("Nao ha vendas cadastradas!!!");
                            }
                        }else{
                            TopFilmeSala<Sala> Top = vendaNegocio.TopSalaOrFilme(opcao);
                            if(Top!=null){
                                System.out.println("-----------------------------");
                                System.out.println("SALA : "+Top.getDado().getNumero());
                                System.out.println("Quantidade Total de Vendas: "+Top.getQtdVendida());
                                System.out.println("-----------------------------");
                            }else{
                                System.out.println("Nao ha vendas cadastradas!!!");
                            }
                        }
                        
                     }else{
                        System.err.println("Opcao Invalida, Digite 1- para filme ou 2- para sala");
                     }
                 }while(checkOp=false);
            } catch (NegocioException ex) {
                UIUtil.mostrarErro(ex.getMessage());
            } 
     }
}

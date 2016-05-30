package viewUI;

import dominio.Filme;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.FilmeNegocio;
import negocio.NegocioException;
import repositorio.RepositorioFilme;
import util.Console;
import view.menu.FilmeMenu;

class FilmeUI {
    private FilmeNegocio filmeNegocio;

    public FilmeUI() {
        filmeNegocio = new FilmeNegocio();
    }

        public void executar() {
        int opcao = -1;
        do {
            System.out.println(FilmeMenu.getOpcoes());
            opcao = Console.scanInt("Digite sua opção:");
            switch (opcao) {
                case FilmeMenu.OP_CADASTRAR:
                    cadastrarFilmes();
                    break;
                case FilmeMenu.OP_LISTAR:
                    mostrarFilmes();
                    break;
                case FilmeMenu.OP_BUSCANOME:
                    buscaFilmePorNome();
                    break;
                case FilmeMenu.OP_BUSCACOD:
                    buscaFilmePorCod();
                    break;
                case FilmeMenu.OP_DELETARFILME:
                    deletarFilme();
                    break;
                case FilmeMenu.OP_ATUALIZARFILME:
                    atualizarFilme();
                    break;
                case FilmeMenu.OP_VOLTAR:
                    System.out.println("Retornando ao menu principal..");
                    break;
                default:
                    System.out.println("Opção inválida..");
           }
        } while (opcao != FilmeMenu.OP_VOLTAR);
    }

    private void cadastrarFilmes() {
        System.out.println("========= Cadastro de Filmes ============");
        int cod = Console.scanInt("Informe o codigo do Filme:");
        try {
            if (filmeNegocio.procurarFilmePorCod(cod)!=null) {
                System.err.println("Filme com esse codigo já existe, já cadastrado!!!");
            } else {
                String nome = Console.scanString("Digite o nome do Filme:");
                String genero = Console.scanString("Informe o genero do Filme:");
                String sinopse = Console.scanString("Digite uma sinopse para este Filme:");
                filmeNegocio.salvar(new Filme(cod,nome,genero,sinopse));
                System.out.println("Filme "+nome+" cadastrado com sucesso!!!"); 
            }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }
    
        public boolean mostrarFilmes() {
        List<Filme> listaFilmes = filmeNegocio.listar();
        return(this.verFilmes(listaFilmes));    
        }
        public boolean verFilmes(List<Filme> listaFilmes){
        System.out.println("-----------------------------\n");
        if (listaFilmes.isEmpty()) {
            System.out.println("Filmes nao encontrados!");
            return true;
        }else{
            System.out.println(String.format("%-10s", "CÓDIGO") + "\t"
                + String.format("%-20s", "|NOME") + "\t"
                + String.format("%-20s", "|GENERO") + "\t"
                + String.format("%-20s", "|SINOPSE"));
            for (Filme filme : listaFilmes) {
                System.out.println(String.format("%-10s", filme.getCodigo()) + "\t"
                    + String.format("%-20s", "|" + filme.getNome()) + "\t"
                    + String.format("%-20s", "|" + filme.getGenero()) + "\t"
                    + String.format("%-20s", "|" + filme.getSinopse()));
            }
            return false;
        }
    }

    private void buscaFilmePorNome() {
        System.out.println("######## Busca de Filme por Nome ############");
        String nome = Console.scanString("Informe o nome do filme para a busca:");
        try {
            List<Filme> FilmeEncontrado = filmeNegocio.procurarPorNomeFilme(nome);
                    if(FilmeEncontrado!=null){
            System.out.println(String.format("%-10s", "CÓDIGO") + "\t"
                + String.format("%-20s", "|NOME") + "\t"
                + String.format("%-20s", "|GENERO") + "\t"
                + String.format("%-20s", "|SINOPSE"));
            for (Filme filme : FilmeEncontrado) {
                System.out.println(String.format("%-10s", filme.getCodigo()) + "\t"
                    + String.format("%-20s", "|" + filme.getNome()) + "\t"
                    + String.format("%-20s", "|" + filme.getGenero()) + "\t"
                    + String.format("%-20s", "|" + filme.getSinopse()));
            }
        }else{
            System.err.println("Nenhum Filme foi encontrado com este nome!!!");
        }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }

    }

    private void buscaFilmePorCod() {
        System.out.println("######## Busca de Filme por Codigo ############");
        int cod = Console.scanInt("Informe o codigo do filme para a busca:");
        try {
            Filme FilmeEncontrado = filmeNegocio.procurarFilmePorCod(cod);
            if(FilmeEncontrado!=null){
            System.out.println(String.format("%-10s", "CÓDIGO") + "\t"
                + String.format("%-20s", "|NOME") + "\t"
                + String.format("%-20s", "|GENERO") + "\t"
                + String.format("%-20s", "|SINOPSE"));
            System.out.println(String.format("%-10s", FilmeEncontrado.getCodigo()) + "\t"
                    + String.format("%-20s", "|" + FilmeEncontrado.getNome()) + "\t"
                    + String.format("%-20s", "|" + FilmeEncontrado.getGenero()) + "\t"
                    + String.format("%-20s", "|" + FilmeEncontrado.getSinopse()));
        }else{
            System.err.println("Nenhum Filme foi encontrado com este codigo!!!");
        }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        }
    }

    private void deletarFilme() {
        try {
            if(this.mostrarFilmes()){
                System.out.println("Nao ha Filmes cadastrados!!!");
                return;
            }else{
               int cod = Console.scanInt("Digite o codigo do filme no qual deseja excluir:");
               Filme f = filmeNegocio.procurarFilmePorCod(cod);
            if (UIUtil.getConfirmacao("Realmente deseja excluir esse paciente?")) {
                filmeNegocio.deletar(f);
                System.out.println("Filme deletado com sucesso!");
            } else {
                System.out.println("Operacao cancelada!");
            }
           }
        } catch (NegocioException ex) {
            UIUtil.mostrarErro(ex.getMessage());
        } 
    }
}

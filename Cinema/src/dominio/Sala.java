package dominio;

public class Sala {
   private int numero;
   private int capacidade;
   private int id;

    public Sala(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
    }

    public Sala(int numero, int capacidade, int id) {
        this.numero=numero;
        this.capacidade=capacidade;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    
    public int getNumero() {
        return numero;
    }

    public int getCapacidade() {
        return capacidade;
    }
 
}
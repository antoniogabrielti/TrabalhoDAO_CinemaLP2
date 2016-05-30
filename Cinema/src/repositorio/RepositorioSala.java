
package repositorio;

import dominio.Sala;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioSala {
       private List<Sala> salas;

    public RepositorioSala() {
        salas = new ArrayList<Sala>();
    }

    public boolean addSala(Sala S) {
       return salas.add(S);
    }

    public List<Sala> getListaSalas() {
         return salas;
    }
   public Sala buscarSalasPorNumero(int numero) {
        for (Sala S : salas) {
            if (S.getNumero()==numero) {
                return S;
           }
        }
        return null;
    }
   public ArrayList<Sala> buscarSalasPorCapacidade(int Capacidade){
       ArrayList<Sala> SalasComEssaCapacidade = new ArrayList<Sala>();
       for(Sala S : getListaSalas()){
           if(S.getCapacidade()>=Capacidade){
               SalasComEssaCapacidade.add(S);
           }
       }
       return SalasComEssaCapacidade;
   } 
}

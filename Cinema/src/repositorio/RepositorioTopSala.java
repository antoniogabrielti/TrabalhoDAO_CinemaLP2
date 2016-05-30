
package repositorio;


import dominio.TopSecoesSala;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioTopSala {
     private List<TopSecoesSala> topsalas;

    public RepositorioTopSala() {
        topsalas = new ArrayList<TopSecoesSala>();
    }

    public boolean addTopSala(TopSecoesSala Top) {
       return topsalas.add(Top);
    }

    public List<TopSecoesSala> getListaTopSalas() {
        Collections.sort(topsalas,Collections.reverseOrder());
        return topsalas;
    }
}

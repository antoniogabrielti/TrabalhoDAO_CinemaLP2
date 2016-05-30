
package repositorio;

import dominio.TopSecoesFilme;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioTopFilme {
    private List<TopSecoesFilme> topfilmes;

    public RepositorioTopFilme() {
        topfilmes = new ArrayList<TopSecoesFilme>();
    }

    public boolean addTopFilme(TopSecoesFilme Top) {
       return topfilmes.add(Top);
    }

    public List<TopSecoesFilme> getListaTopFilme() {
        Collections.sort(topfilmes,Collections.reverseOrder());
        return topfilmes;
    }
}

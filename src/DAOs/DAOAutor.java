package DAOs;

import Entidades.Autor;
import java.util.ArrayList;
import java.util.List;

public class DAOAutor extends DAOGenerico<Autor> {

    public DAOAutor() {
        super(Autor.class);
    }

    public int autoIdAutor() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idAutor) FROM Autor e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Autor> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Autor e WHERE e.nomeAutor LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Autor> listById(int id) {
        return em.createQuery("SELECT e FROM Autor e WHERE e.idAutor = :id").setParameter("id", id).getResultList();
    }

    public List<Autor> listInOrderNome() {
        return em.createQuery("SELECT e FROM Autor e ORDER BY e.nomeAutor").getResultList();
    }

    public List<Autor> listInOrderId() {
        return em.createQuery("SELECT e FROM Autor e").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Autor> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdAutor()+ "-" + lf.get(i).getAutor());
        }
        return ls;
    }
     
   
    
}

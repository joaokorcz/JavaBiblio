package DAOs;

import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuario extends DAOGenerico<Usuario> {

    public DAOUsuario() {
        super(Usuario.class);
    }

    public int autoIdUsuario() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idUsuario) FROM Usuario e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Usuario> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Usuario e WHERE e.nomeUsuario LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Usuario> listById(int id) {
        return em.createQuery("SELECT e FROM Usuario e WHERE e.idUsuario = :id").setParameter("id", id).getResultList();
    }

    public List<Usuario> listInOrderNome() {
        return em.createQuery("SELECT e FROM Usuario e ORDER BY e.nomeUsuario").getResultList();
    }

    public List<Usuario> listInOrderId() {
        return em.createQuery("SELECT e FROM Usuario e ORDER BY e.idUsuario").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Usuario> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getNome()+ "- hehe");
        }
        return ls;
    }
}

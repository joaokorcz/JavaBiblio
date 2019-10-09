package DAOs;

import Entidades.Livroalugado;
import Entidades.LivroalugadoPK;
import java.util.ArrayList;
import java.util.List;

public class DAOLivroalugado extends DAOGenerico<Livroalugado> {

    public DAOLivroalugado() {
        super(Livroalugado.class);
    }
    
    //método para obter usando a chave composta
    public Livroalugado obter(LivroalugadoPK itensPedidoPK) {
        return em.find(Livroalugado.class, itensPedidoPK);
    }

    public int autoIdLivroalugado() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idLivroalugado) FROM Livroalugado e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Livroalugado> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Livroalugado e WHERE e.nomeLivroalugado LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Livroalugado> listById(int id) {
        return em.createQuery("SELECT e FROM Livroalugado e WHERE e.idLivroalugado = :id").setParameter("id", id).getResultList();
    }

    public List<Livroalugado> listInOrderNome() {
        return em.createQuery("SELECT e FROM Livroalugado e ORDER BY e.nomeLivroalugado").getResultList();
    }

    public List<Livroalugado> listInOrderId() {
        return em.createQuery("SELECT e FROM Livroalugado e").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Livroalugado> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getUsuario()+ "-" + lf.get(i).getLivro());
        }
        return ls;
    }
     
   
    
}

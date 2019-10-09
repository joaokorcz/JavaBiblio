/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author João Otavio
 */
@Entity
@Table(name = "editora")
@NamedQueries({
    @NamedQuery(name = "Editora.findAll", query = "SELECT e FROM Editora e")})
public class Editora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_editora")
    private Integer idEditora;
    @Basic(optional = false)
    @Column(name = "editora")
    private String editora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editoraIdEditora")
    private List<Livro> livroList;

    public Editora() {
    }

    public Editora(Integer idEditora) {
        this.idEditora = idEditora;
    }

    public Editora(Integer idEditora, String editora) {
        this.idEditora = idEditora;
        this.editora = editora;
    }

    public Integer getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(Integer idEditora) {
        this.idEditora = idEditora;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<Livro> getLivroList() {
        return livroList;
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList = livroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEditora != null ? idEditora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Editora)) {
            return false;
        }
        Editora other = (Editora) object;
        if ((this.idEditora == null && other.idEditora != null) || (this.idEditora != null && !this.idEditora.equals(other.idEditora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Editora[ idEditora=" + idEditora + " ]";
    }
    
}

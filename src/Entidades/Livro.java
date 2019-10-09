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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author João Otavio
 */
@Entity
@Table(name = "livro")
@NamedQueries({
    @NamedQuery(name = "Livro.findAll", query = "SELECT l FROM Livro l")})
public class Livro implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    private List<Livroalugado> livroalugadoList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_livro")
    private Integer idLivro;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "ano")
    private Integer ano;
    @JoinColumn(name = "autor_id_autor", referencedColumnName = "id_autor")
    @ManyToOne(optional = false)
    private Autor autorIdAutor;
    @JoinColumn(name = "editora_id_editora", referencedColumnName = "id_editora")
    @ManyToOne(optional = false)
    private Editora editoraIdEditora;
    @JoinColumn(name = "genero_id_genero", referencedColumnName = "id_genero")
    @ManyToOne(optional = false)
    private Genero generoIdGenero;

    public Livro() {
    }

    public Livro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public Livro(Integer idLivro, String titulo) {
        this.idLivro = idLivro;
        this.titulo = titulo;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Autor getAutorIdAutor() {
        return autorIdAutor;
    }

    public void setAutorIdAutor(Autor autorIdAutor) {
        this.autorIdAutor = autorIdAutor;
    }

    public Editora getEditoraIdEditora() {
        return editoraIdEditora;
    }

    public void setEditoraIdEditora(Editora editoraIdEditora) {
        this.editoraIdEditora = editoraIdEditora;
    }

    public Genero getGeneroIdGenero() {
        return generoIdGenero;
    }

    public void setGeneroIdGenero(Genero generoIdGenero) {
        this.generoIdGenero = generoIdGenero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLivro != null ? idLivro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.idLivro == null && other.idLivro != null) || (this.idLivro != null && !this.idLivro.equals(other.idLivro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Livro[ idLivro=" + idLivro + " ]";
    }

    public List<Livroalugado> getLivroalugadoList() {
        return livroalugadoList;
    }

    public void setLivroalugadoList(List<Livroalugado> livroalugadoList) {
        this.livroalugadoList = livroalugadoList;
    }
    
}

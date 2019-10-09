/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author João Otavio
 */
@Embeddable
public class LivroalugadoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "livro_id_livro")
    private int livroIdLivro;
    @Basic(optional = false)
    @Column(name = "usuario_id_usuario")
    private int usuarioIdUsuario;

    public LivroalugadoPK() {
    }

    public LivroalugadoPK(int livroIdLivro, int usuarioIdUsuario) {
        this.livroIdLivro = livroIdLivro;
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public int getLivroIdLivro() {
        return livroIdLivro;
    }

    public void setLivroIdLivro(int livroIdLivro) {
        this.livroIdLivro = livroIdLivro;
    }

    public int getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(int usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) livroIdLivro;
        hash += (int) usuarioIdUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LivroalugadoPK)) {
            return false;
        }
        LivroalugadoPK other = (LivroalugadoPK) object;
        if (this.livroIdLivro != other.livroIdLivro) {
            return false;
        }
        if (this.usuarioIdUsuario != other.usuarioIdUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.LivroalugadoPK[ livroIdLivro=" + livroIdLivro + ", usuarioIdUsuario=" + usuarioIdUsuario + " ]";
    }
    
}

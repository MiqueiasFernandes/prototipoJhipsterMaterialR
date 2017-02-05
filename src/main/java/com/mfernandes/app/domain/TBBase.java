package com.mfernandes.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TBBase.
 */
@Entity
@Table(name = "tb_base")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbbase")
public class TBBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idbase", nullable = false)
    private String idbase;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Long idusuario;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    private TBUsuario tBUsuario;

    @ManyToOne
    private TBProjeto tBProjeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdbase() {
        return idbase;
    }

    public TBBase idbase(String idbase) {
        this.idbase = idbase;
        return this;
    }

    public void setIdbase(String idbase) {
        this.idbase = idbase;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public TBBase idusuario(Long idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getUrl() {
        return url;
    }

    public TBBase url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TBUsuario getTBUsuario() {
        return tBUsuario;
    }

    public TBBase tBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
        return this;
    }

    public void setTBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
    }

    public TBProjeto getTBProjeto() {
        return tBProjeto;
    }

    public TBBase tBProjeto(TBProjeto tBProjeto) {
        this.tBProjeto = tBProjeto;
        return this;
    }

    public void setTBProjeto(TBProjeto tBProjeto) {
        this.tBProjeto = tBProjeto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBBase tBBase = (TBBase) o;
        if (tBBase.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBBase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBBase{" +
            "id=" + id +
            ", idbase='" + idbase + "'" +
            ", idusuario='" + idusuario + "'" +
            ", url='" + url + "'" +
            '}';
    }
}

package com.mfernandes.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TBModeloGenerico.
 */
@Entity
@Table(name = "tb_modelo_generico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "tbmodelogenerico")
public class TBModeloGenerico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "idmodelogenerico", nullable = false)
    private String idmodelogenerico;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Long idusuario;

    @Column(name = "script")
    private String script;

    @ManyToOne
    private TBUsuario tBUsuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdmodelogenerico() {
        return idmodelogenerico;
    }

    public TBModeloGenerico idmodelogenerico(String idmodelogenerico) {
        this.idmodelogenerico = idmodelogenerico;
        return this;
    }

    public void setIdmodelogenerico(String idmodelogenerico) {
        this.idmodelogenerico = idmodelogenerico;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public TBModeloGenerico idusuario(Long idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getScript() {
        return script;
    }

    public TBModeloGenerico script(String script) {
        this.script = script;
        return this;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public TBUsuario getTBUsuario() {
        return tBUsuario;
    }

    public TBModeloGenerico tBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
        return this;
    }

    public void setTBUsuario(TBUsuario tBUsuario) {
        this.tBUsuario = tBUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TBModeloGenerico tBModeloGenerico = (TBModeloGenerico) o;
        if (tBModeloGenerico.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tBModeloGenerico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TBModeloGenerico{" +
            "id=" + id +
            ", idmodelogenerico='" + idmodelogenerico + "'" +
            ", idusuario='" + idusuario + "'" +
            ", script='" + script + "'" +
            '}';
    }
}

package br.ciar.domain.ocorrencias;

import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "webconferencia")
@Configurable
public class WebConferencia extends Conferencia {

    @Column(name = "urlacesso")
    @Size(max = 255)
    private String urlAcesso;
    @Column(name = "urlgravacao")
    @Size(max = 255)
    private String urlGravacao;

    public String getUrlAcesso() {
        return this.urlAcesso;
    }

    public void setUrlAcesso(String urlAcesso) {
        this.urlAcesso = urlAcesso;
    }

    public String getUrlGravacao() {
        return this.urlGravacao;
    }

    public void setUrlGravacao(String urlGravacao) {
        this.urlGravacao = urlGravacao;
    }

    public boolean isAcessivel() {
        try {
            return !urlAcesso.trim().isEmpty();
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public boolean isGravacaoAcessivel() {
        try {
            return !urlGravacao.trim().isEmpty();
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static long countWebConferencias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM WebConferencia o", Long.class).getSingleResult();
    }

    public static List<WebConferencia> findAllWebConferencias() {
        return entityManager().createQuery("SELECT o FROM WebConferencia o", WebConferencia.class).getResultList();
    }

    public static WebConferencia findWebConferencia(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(WebConferencia.class, id);
    }

    public static List<WebConferencia> findWebConferenciaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM WebConferencia o", WebConferencia.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(getCliente()).append(", ");
        sb.append("Comentarios: ").append(getComentarios() == null ? "null" : getComentarios().size()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Fim: ").append(getFim()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Inicio: ").append(getInicio()).append(", ");
        sb.append("Local_ocorrencia: ").append(getLocalOcorrencia()).append(", ");
        sb.append("Mapa: ").append(getMapa()).append(", ");
        sb.append("Nome: ").append(getNome()).append(", ");
        sb.append("Review: ").append(getReview()).append(", ");
        sb.append("Urlacesso: ").append(getUrlAcesso()).append(", ");
        sb.append("Urlgravacao: ").append(getUrlGravacao());
        return sb.toString();
    }
}

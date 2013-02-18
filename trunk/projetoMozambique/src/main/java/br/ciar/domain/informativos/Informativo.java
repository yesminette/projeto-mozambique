package br.ciar.domain.informativos;

import br.ciar.domain.configuracoes.CFG;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Informativo implements Serializable {

    @PersistenceContext
    transient EntityManager entityManager;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_informativo")
    private Integer id;
    @Column(columnDefinition = "TEXT")
    private String conteudo;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private Boolean destaque;
    @Size(max = 255)
    private String titulo;
    @Column(name = "data_informativo")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dataInformativo;

    public Informativo() {
        destaque = false;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isDestaque() {
        return this.destaque;
    }

    public void setDestaque(Boolean destaque) {
        this.destaque = destaque;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataInformativo() {
        return this.dataInformativo;
    }

    public void setDataInformativo(Date data_informativo) {
        this.dataInformativo = data_informativo;
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Informativo attached = findInformativo(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        this.entityManager.clear();
    }

    @Transactional
    public Informativo merge() {
        if (this.entityManager == null) {
            this.entityManager = entityManager();
        }
        Informativo merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public static EntityManager entityManager() {
        EntityManager em = new Informativo().entityManager;
        if (em == null) {
            throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        }
        return em;
    }

    public static long countInformativos() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Informativo o", Long.class).getSingleResult();
    }

    public static List<Informativo> findAllInformativos() {
        return entityManager().createQuery("SELECT o FROM Informativo o", Informativo.class).getResultList();
    }

    public static List<Informativo> findAllInformativosOrdered(String campo, String ordem) {
        String ordenacao = (campo.isEmpty() || ordem.isEmpty()) ? " id ASC" : campo + " " + ordem;
        return entityManager().createQuery("SELECT o FROM Informativo o ORDER BY " + ordenacao, Informativo.class).getResultList();
    }

    public static Informativo findInformativo(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Informativo.class, id);
    }

    public static List<Informativo> findInformativoEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Informativo o", Informativo.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List getInformativos(String[] palavrasChave, int primeiroResultado, int maximoResultados) {
        String query = "SELECT o FROM Informativo o";

        if (palavrasChave.length > 0) {
            query += " WHERE o.id <> "+CFG.ID_CLIPPING+" AND ";

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.descricao) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.conteudo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.titulo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            // Remover o último OR que sobrou (ou se não teve nada, remover o Where)
            int aRemover = query.lastIndexOf("OR");
            if(aRemover < 0){
                aRemover = query.lastIndexOf("AND");
            }
            query = query.substring(0, aRemover);
        }

        query += " ORDER BY o.dataInformativo DESC";

        return entityManager().createQuery(query, Informativo.class).setFirstResult(primeiroResultado).setMaxResults(maximoResultados).getResultList();
    }

    public static long countInformativos(String[] palavrasChave) {
        String query = "SELECT COUNT(o) FROM Informativo o";

        if (palavrasChave.length > 0) {
            query += " WHERE o.id <> "+CFG.ID_CLIPPING+" AND ";

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.descricao) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.conteudo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            for (String palavra : palavrasChave) {
                if (!palavra.trim().isEmpty()) {
                    query += " lower(o.titulo) LIKE '%" + palavra.toLowerCase() + "%' ";
                    query += " OR ";
                }
            }

            // Remover o último OR que sobrou (ou se não teve nada, remover o Where)
            int aRemover = query.lastIndexOf("OR");
            if(aRemover < 0){
                aRemover = query.lastIndexOf("AND");
            }
            query = query.substring(0, aRemover);
        }
        return entityManager().createQuery(query, Long.class).getSingleResult();
    }

    public static List<Informativo> getDestaques(int limite) {
        return (List<Informativo>) entityManager().createQuery("SELECT o FROM Informativo o WHERE o.destaque = true ORDER BY o.dataInformativo DESC").setFirstResult(0).setMaxResults(limite).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Conteudo: ").append(getConteudo()).append(", ");
        sb.append("Data_informativo: ").append(getDataInformativo()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Destaque: ").append(isDestaque()).append(", ");
        sb.append("Titulo: ").append(getTitulo());
        return sb.toString();
    }
}

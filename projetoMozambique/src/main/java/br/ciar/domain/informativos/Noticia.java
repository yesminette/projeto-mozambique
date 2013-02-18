package br.ciar.domain.informativos;

import br.ciar.domain.configuracoes.CFG;
import java.util.List;
import javax.persistence.Entity;
import org.springframework.beans.factory.annotation.Configurable;
import javax.validation.constraints.Size;
import br.ciar.domain.informativos.fotografia.Galeria;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@Configurable
public class Noticia extends Informativo {

    @Size(max = 255)
    private String autor;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_galeria")
    private Galeria galeria;

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Galeria getGaleria() {
        return this.galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }

    public boolean temGaleria() {
        if (galeria != null) {
            if (galeria.getFotos() != null) {
                if (!galeria.getFotos().isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static long countNoticias() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Noticia o", Long.class).getSingleResult();
    }

    public static List<Noticia> findAllNoticias() {
        return entityManager().createQuery("SELECT o FROM Noticia o", Noticia.class).getResultList();
    }

    public static Noticia findNoticia(Integer id) {
        if (id == null) {
            return null;
        }
        return entityManager().find(Noticia.class, id);
    }

    public static List<Noticia> findNoticiaEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Noticia o", Noticia.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List getNoticias(String[] palavrasChave, int primeiroResultado, int maximoResultados) {
        String query = "SELECT o FROM Noticia o";

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
            if (aRemover < 0) {
                aRemover = query.lastIndexOf("AND");
            }
            query = query.substring(0, aRemover);
        }

        query += " ORDER BY o.dataInformativo DESC";

        return entityManager().createQuery(query, Noticia.class).setFirstResult(primeiroResultado).setMaxResults(maximoResultados).getResultList();
    }

    public static long countNoticias(String[] palavrasChave) {
        String query = "SELECT COUNT(o) FROM Noticia o";

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
            if (aRemover < 0) {
                aRemover = query.lastIndexOf("AND");
            }
            query = query.substring(0, aRemover);
        }
        return entityManager().createQuery(query, Long.class).getSingleResult();
    }

    public static List<Noticia> findNoticiasComGaleria(int maxResults) {
        return entityManager().createQuery("SELECT o FROM Noticia o WHERE o.galeria != null ORDER BY o.dataInformativo DESC", Noticia.class).setMaxResults(maxResults).getResultList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autor: ").append(getAutor()).append(", ");
        sb.append("Conteudo: ").append(getConteudo()).append(", ");
        sb.append("Data_informativo: ").append(getDataInformativo()).append(", ");
        sb.append("Descricao: ").append(getDescricao()).append(", ");
        sb.append("Destaque: ").append(isDestaque()).append(", ");
        sb.append("Galeria: ").append(getGaleria()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Titulo: ").append(getTitulo());
        return sb.toString();
    }
}

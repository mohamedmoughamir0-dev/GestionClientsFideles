import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String nom;
    private String email;
    private List<Achat> achats = new ArrayList<>();

    public Client(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Achat> getAchats() {
        return achats;
    }

    public void setAchats(List<Achat> achats) {
        this.achats = achats;
    }
    @Override
    public String toString() {
        return "Client [ID=" + id + ", Nom=" + nom + ", Email=" + email + "]";
    }

public double calculerTotal() throws HistoriqueAchatVideException {
    if (this.achats.isEmpty()) {
        throw new HistoriqueAchatVideException("Aucun achat trouvé pour ce client.");
    }

    return this.achats.stream()
            .mapToDouble(Achat::getMontant)
            .sum();
}
}
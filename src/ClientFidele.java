public class ClientFidele extends Client {
    private String niveauFidelite; // Bronze / Silver / Gold

    public ClientFidele(int id, String nom, String email, String niveauFidelite) {
        super(id, nom, email);
        this.niveauFidelite = niveauFidelite;
    }

    public String getNiveauFidelite() {
        return niveauFidelite;
    }

    public void setNiveauFidelite(String niveauFidelite) {
        this.niveauFidelite = niveauFidelite;
    }
    @Override
    public String toString() {
        double totalAchats = getAchats().stream().mapToDouble(Achat::getMontant).sum();
        return "ClientFidele [ID=" + getId() + ", Nom=" + getNom() + ", Email=" + getEmail() +
                ", Niveau=" + getNiveauFidelite() + ", TotalAchats=" + totalAchats + "€]";
    }

}

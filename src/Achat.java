import java.time.LocalDate;

public class Achat {
    private double montant;
    private LocalDate dateAchat;

    public Achat(double montant, LocalDate dateAchat) {
        this.montant = montant;
        this.dateAchat = dateAchat;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }
    @Override
    public String toString() {
        return "Achat [Montant=" + montant + "€, Date=" + dateAchat + "]";
    }
}

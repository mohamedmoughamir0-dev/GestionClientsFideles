import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Client {

    private int id;
    private String nom;
    private String email;
    private List<Achat> achats = new ArrayList<>();

    public Client( int id,String nom, String email) {
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
public void totalAchatParClient(Map<Integer, Client> clients)
{
    System.out.println("\n=== TOTAL DES ACHATS PAR CLIENT ===");
    clients.values().forEach(cl -> {
        double totalAchats = cl.getAchats().stream().mapToDouble(Achat::getMontant).sum();
        System.out.println(cl.getNom() + " a dépensé : " + totalAchats + "€");
    });
}


    public static List<Client>  trierClientDec(Map<Integer, Client> clients)
    { List<Client> sorted = clients.values().stream()
            .sorted((cl1, cl2) -> Double.compare(
                    cl2.getAchats().stream().mapToDouble(Achat::getMontant).sum(),
                    cl1.getAchats().stream().mapToDouble(Achat::getMontant).sum()))
            .collect(Collectors.toList());
        System.out.println("\n=== CLIENTS TRIÉS PAR ACHATS DÉCROISSANTS ===");
        sorted.forEach(System.out::println);
        return sorted;
    }
    // Filtrer les clients Gold (>500€)
public static List<Client> clientPlus500(Map<Integer, Client> clients)
{ List<Client> goldClients = clients.values().stream()
        .filter(cl -> cl.getAchats().stream().mapToDouble(Achat::getMontant).sum() > 500)
        .collect(Collectors.toList());
    System.out.println("\n=== CLIENTS GOLD ===");
    goldClients.forEach(System.out::println);
    return goldClients;
}
    public static List<Client> clientGold(Map<Integer, Client> clients) {
        List<Client> goldClients = clients.values().stream()
                .filter(cl -> cl instanceof ClientFidele)
                .filter(cl -> ((ClientFidele) cl).getNiveauFidelite().equalsIgnoreCase("GOLD"))
                .collect(Collectors.toList());

        System.out.println("\n=== CLIENTS GOLD ===");
        goldClients.forEach(System.out::println);

        return goldClients;
    }

    public static void exporterGoldJSON(Map<Integer, Client> clients, String filePath) {
        List<ClientFidele> golds = clients.values().stream()
                .filter(client -> client instanceof ClientFidele) // keep only fidèle clients
                .map(client -> (ClientFidele) client)             // cast to ClientFidele
                .filter(cf -> "gold".equals(cf.getNiveauFidelite()))
                .collect(Collectors.toList());


        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("[");
            for (int i = 0; i < golds.size(); i++) {
                ClientFidele cl = golds.get(i);

                String json = String.format(
                        "  { \"id\": %d, \"nom\": \"%s\", \"email\": \"%s\", \"niveau\": \"%s\" }",
                        cl.getId(), cl.getNom(), cl.getEmail(), cl.getNiveauFidelite()
                );

                writer.print(json);

                if (i < golds.size() - 1) writer.println(",");
            }
            writer.println("\n]");

            System.out.println("Export JSON terminé -> " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
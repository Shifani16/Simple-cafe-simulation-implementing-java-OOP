import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class History {
    private int nomorPesanan;
    private int qty;
    private double subtotal;
    private String kodePromo;
    private List<ShoppingCart> shoppingHistory;

    public History() {
        shoppingHistory = new ArrayList<>();
    }

    public void addToHistory(ShoppingCart cart) {
        shoppingHistory.add(cart);
    }

    public List<ShoppingCart> getHistory() {
        return shoppingHistory;
    }

    public History(int nomorPesanan, int qty, double subtotal, String kodePromo) {
        this.nomorPesanan = nomorPesanan;
        this.qty = qty;
        this.subtotal = subtotal;
        this.kodePromo = kodePromo;
    }

    public History(int nomorPesanan, int qty, double subtotal) {
        this.nomorPesanan = nomorPesanan;
        this.qty = qty;
        this.subtotal = subtotal;
    }

    public int getNomorPesanan() {
        return nomorPesanan;
    }

    public int getQty() {
        return qty;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getKodePromo() {
        return kodePromo;
    }
}

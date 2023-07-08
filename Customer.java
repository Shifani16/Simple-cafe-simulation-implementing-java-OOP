import java.util.Date;

abstract class Customer {
    public double saldoAwal;
    private ShoppingCart cart;
    private static int nomorPesanan = 1;
    private int nomor;

    public Customer(double saldoAwal) {
        this.saldoAwal = saldoAwal;
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public abstract String getType();

    public double getSaldoAwal() {
        return saldoAwal;
    }

    public void setSaldoAwal(double saldoAwal) {
        this.saldoAwal = saldoAwal;
    }

    public double getOngkir() {
        return 15000;
    }

    public int nomorPesanan() {
        return nomorPesanan++;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }
}

class CustomerMember extends Customer {
    private String namaMember;
    private Date tanggalRegis;
    public CustomerMember(String namaMember, Date tanggalRegis, double saldoAwal) {
        super(saldoAwal);
        this.namaMember = namaMember;
        this.tanggalRegis = tanggalRegis;
    }

    @Override
    public String getType() {
        return "MEMBER";
    }

    public String getNamaMember() {
        return namaMember;
    }

    public void setNamaMember(String namaMember) {
        this.namaMember = namaMember;
    }

    public Date getTanggalRegis() {
        return tanggalRegis;
    }

    public void setTanggalRegis(Date tanggalRegis) {
        this.tanggalRegis = tanggalRegis;
    }
}

class CustomerGuest extends Customer {
    public CustomerGuest(double saldoAwal) {
        super(saldoAwal);
    }

    @Override
    public String getType() {
        return "GUEST";
    }
}

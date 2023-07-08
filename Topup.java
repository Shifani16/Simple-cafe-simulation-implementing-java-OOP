abstract class Topup {
    public String IDPelanggan;
    public double saldoTopup;

    public Topup(String IDPelanggan, double saldoTopup) {
        this.saldoTopup = saldoTopup;
        this.IDPelanggan = IDPelanggan;
    }

    public String getIDPelanggan() {
        return IDPelanggan;
    }

    public void setIDPelanggan(String IDPelanggan) {
        this.IDPelanggan = IDPelanggan;
    }

    public double getSaldoTopup() {
        return saldoTopup;
    }

    public void setSaldoTopup(double saldoTopup) {
        this.saldoTopup = saldoTopup;
    }
}

public class CheckOut {
    private String idPelanggan;
    private boolean isCheckOut;

    CheckOut (String idPelanggan) {
        this.idPelanggan = idPelanggan;
        this.isCheckOut = false;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public boolean isCheckOut() {
        return isCheckOut;
    }

    public void setCheckOut(boolean checkOut) {
        isCheckOut = checkOut;
    }

}

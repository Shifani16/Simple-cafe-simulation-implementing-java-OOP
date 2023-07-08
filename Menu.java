abstract class Menu {
    public String namaMenu;
    public double hargaMenu;
    public String IDMenu;
    private int totalQty;
    private double totalHarga;

    public Menu(String IDMenu, String namaMenu, double hargaMenu) {
        this.namaMenu = namaMenu;
        this.hargaMenu = hargaMenu;
        this.IDMenu = IDMenu;
    }


    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getHargaMenu() {
        return hargaMenu;
    }

    public void setHargaMenu(double hargaMenu) {
        this.hargaMenu = hargaMenu;
    }

    public abstract String getType();

    public String getNamaMenu() {
        return namaMenu;
    }

    public String getIDMenu() {
        return IDMenu;
    }

    public void setIDMenu(String IDMenu) {
        this.IDMenu = IDMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }
}

class MenuMakanan extends Menu {

    public MenuMakanan(String IDMenu, String namaMenu, double hargaMenu) {
        super(IDMenu, namaMenu, hargaMenu);
    }

    @Override
    public String getType() {
        return "MAKANAN";
    }
}

class MenuMinuman extends Menu {
    private String customType;

    public MenuMinuman(String IDMenu, String namaMenu, double hargaMenu, String customType) {
        super(IDMenu, namaMenu, hargaMenu);
        this.customType = customType;
    }

    @Override
    public String getType() {
        return  "MINUMAN";
    }
}

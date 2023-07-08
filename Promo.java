import java.util.Date;
import java.util.Map;

public abstract class Promo implements Applicable, Comparable<Promo>{
    private String kodePromo;
    private Date tanggalAwal;
    private Date tanggalAkhir;
    private double persenPotongan;
    private double maksPotongan;
    private double minPembelian;
    private String jenisPromo;
    private double hargaDiskon;
    private boolean isApplied;

    public Promo(String kodePromo, Date tanggalAwal, Date tanggalAkhir, double persenPotongan, double maksPotongan, double minPembelian, String jenisPromo) {
        this.kodePromo = kodePromo;
        this.tanggalAwal = tanggalAwal;
        this.tanggalAkhir = tanggalAkhir;
        this.persenPotongan = persenPotongan;
        this.maksPotongan = maksPotongan;
        this.minPembelian = minPembelian;
        this.jenisPromo = jenisPromo;
    }

    public void setJenisPromo(String jenisPromo) {
        this.jenisPromo = jenisPromo;
    }

    public Promo(String kodePromo, double hargaDiskon) {
        this.kodePromo = kodePromo;
        this.hargaDiskon = hargaDiskon;
    }

    public Promo(String kodePromo, boolean isApplied) {
        this.kodePromo = kodePromo;
        this.isApplied = isApplied;

    }

    public void setKodePromo(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public String getKodePromo() {
        return kodePromo;
    }


    public Date getTanggalAwal() {
        return tanggalAwal;
    }

    public Date getTanggalAkhir() {
        return tanggalAkhir;
    }

    public double getPersenPotongan() {
        return persenPotongan;
    }

    public double getMaksPotongan() {
        return maksPotongan;
    }

    public double getMinPembelian() {
        return minPembelian;
    }
    public String getJenisPromo() {
        return jenisPromo;
    }

    public double getHargaDiskon() {
        return hargaDiskon;
    }

    public void setHargaDiskon(double hargaDiskon) {
        this.hargaDiskon = hargaDiskon;
    }

    public void isApplicable(String kodePromo) {
        this.kodePromo = kodePromo;
    }

    public boolean alreadyApplicable() {
        return true;
    }
}

class promoDisc extends Promo {

    private double hargaDiskon;
    private Map<String, Menu> menus;

    public promoDisc(String kodePromo, Date tanggalAwal, Date tanggalAkhir, double persenPotongan, double maksPotongan, double minPembelian, String jenisPromo) {
        super(kodePromo, tanggalAwal, tanggalAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
    }

    @Override
    public boolean isCustomerEligible(CustomerEligible x) {
        return false;
    }

    @Override
    public boolean isMinimumPriceEligible(OrderEligible x) {
        return false;
    }

    @Override
    public boolean isShippingFeeEligible(OrderEligible x) {
        return false;
    }

    @Override
    public int compareTo(Promo o) {
        return 0;
    }
}


class promoCashBack extends Promo {

    public promoCashBack(String kodePromo, Date tanggalAwal, Date tanggalAkhir, double persenPotongan, double maksPotongan, double minPembelian, String jenisPromo) {
        super(kodePromo, tanggalAwal, tanggalAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
    }

    @Override
    public boolean isCustomerEligible(CustomerEligible x) {
        return false;
    }

    @Override
    public boolean isMinimumPriceEligible(OrderEligible x) {
        return false;
    }

    @Override
    public boolean isShippingFeeEligible(OrderEligible x) {
        return false;
    }

    @Override
    public int compareTo(Promo o) {
        return 0;
    }
}

class promoDelivery extends Promo {

    public promoDelivery(String kodePromo, Date tanggalAwal, Date tanggalAkhir, double persenPotongan, double maksPotongan, double minPembelian, String jenisPromo) {
        super(kodePromo, tanggalAwal, tanggalAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
    }

    @Override
    public boolean isCustomerEligible(CustomerEligible x) {
        return false;
    }

    @Override
    public boolean isMinimumPriceEligible(OrderEligible x) {
        return false;
    }

    @Override
    public boolean isShippingFeeEligible(OrderEligible x) {
        return false;
    }

    @Override
    public int compareTo(Promo o) {
        return 0;
    }
}

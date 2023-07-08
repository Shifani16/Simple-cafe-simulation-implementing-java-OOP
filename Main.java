import javax.sound.midi.MidiMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import static java.time.temporal.ChronoUnit.DAYS;

/*
    Ahmad Nabih Baril Hilmy 225150207111085
    Nursyifa Devani Effendy 225150207111079
    Khansa Nabilah Sukendro 225150207111078
    Jeyhan Farrel Alfarisqi 225150207111082
    M. Fajar 22515020711084
 */
public class Main {
    private static String appliedPromoCode;
    private static Map<String, String> appliedPromos = new HashMap<>();

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        LinkedHashMap<String, Customer> cus = new LinkedHashMap<>();
        Map<String, Menu> mnu = new HashMap<>();
        Map<String, Promo> promo = new HashMap<>();
        Map<String, Map<String, Integer>> cart = new HashMap<>();
        LinkedHashMap<String, CheckOut> co = new LinkedHashMap<>();
        Map<String, List<History>> nomorPesanan = new HashMap<>();
        boolean exit = false;

        StringBuilder sb = new StringBuilder();

        while (!exit) {
            String input = sc.nextLine();
            sb.append(input).append("\n");
            if (input.equals("")) {
                exit = true;
            }
        }

        String[] inputs = sb.toString().split("\n");

        for (String inp : inputs) {
            String[] data = inp.split(" ");
            String command = data[0];

            switch (command) {
                case "CREATE":
                    String command2 = data[1];
                    switch (command2) {
                        case "MENU":
                            createMenu(mnu, inp);
                            break;
                        case "MEMBER":
                            createCustomer(cus, inp);
                            break;
                        case "GUEST":
                            createCustomer(cus, inp);
                            break;
                        case "PROMO":
                            createPromo(promo, inp);
                            break;
                    }
                    break;
                case "ADD_TO_CART":
                    addToCart(cus, mnu, cart, inp);
                    break;
                case "REMOVE_FROM_CART":
                    removeFromCart(cus, mnu, cart, inp);
                    break;
                case "APPLY_PROMO":
                    applyPromo(cus, mnu, promo, cart, inp);
                    break;
                case "TOPUP":
                    systemTopup(cus, inp);
                    break;
                case "CHECK_OUT":
                    checkOut(co, cus, mnu, cart, promo, inp);
                    break;
                case "PRINT":
                    systemPrint(nomorPesanan, cus, mnu, cart, promo, co, inp);
                    break;
                case "PRINT_HISTORY":
                    printHistory(nomorPesanan, cus, mnu, cart, promo, co, inp);
            }
        }
        sc.close();
    }

    public static void createCustomer(LinkedHashMap<String, Customer> customer, String input) throws ParseException {
        String[] data = input.split(" ");
        // index array data:
        //  0      1                  2
        //CREATE MEMBER A001|Ani|2023/05/15|1000000
        if (data[0].equals("CREATE")) {
            String[] split = data[2].split("\\|");
            String id = split[0];
            // index array split:
            //          0          1       2        3
            // CREATE MEMBER A001|Ani|2023/05/15|1000000

            if (data[1].equals("MEMBER")) {
                if (customer.containsKey(id)) {
                    System.out.println("CREATE MEMBER FAILED: " + id + " IS EXIST");
                } else {
                    String namaCus = split[1]; //split[1] isinya namaMakanan
                    String tanggalRegis = split[2]; //split[2] isinya tanggal
                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = formatter.parse(tanggalRegis);
                    double saldoAwal = Double.parseDouble(split[3]); //split[3] isinya saldo

                    Customer cusMem = new CustomerMember(namaCus, date, saldoAwal);
                    customer.put(id, cusMem); //menaruh id dan info menu ke hashmap
                    System.out.println("CREATE MEMBER SUCCESS: " + id + " " + namaCus);
                }
            }
            //CREATE GUEST A001|1000000
            else if (data[1].equals("GUEST")) {
                if (customer.containsKey(id)) {
                    System.out.println("CREATE GUEST FAILED: " + id);
                } else {
                    double saldoAwal = Double.parseDouble(split[1]);
                    // index split:
                    //         0            1
                    //CREATE GUEST A001|1000000
                    Customer cusG = new CustomerGuest(saldoAwal);
                    customer.put(id, cusG); //menaruh id dan info cus ke hashmap
                    System.out.println("CREATE GUEST SUCCESS: " + id + cusG.getSaldoAwal());
                }
            }
        }
    }

    public static void createMenu(Map<String, Menu> menu, String input) {
        // CREATE MENU MAKANAN M001|Nasi Goreng Spesial|20000
        String[] data = input.split(" "); //ngemisah antara CREATE, MENU, MAKANAN, M001|NASI, dsb
        //index split data :
        //    0     1    2         3       4           5
        // CREATE MENU MAKANAN M001|Nasi Goreng Spesial|20000
        if (data[0].equals("CREATE") && data[1].equals("MENU")) {
            //index split:
            //          0                       1            2
            // CREATE MENU MAKANAN M001|Nasi Goreng Spesial|20000
            String[] split = input.trim().split("\\|");
            String id = split[0].split(" ")[3]; //Ngambil id dari hasil split (ID di index 3)

            if (data[2].equals("MAKANAN")) { // index data[2] antara makanan/minuman
                if (menu.containsKey(id)) {
                    System.out.println("CREATE MENU FAILED: " + id + " IS EXIST");
                } else {
                    String namaMakanan = split[1]; //split[1] isinya namaMakanan
                    double hargaMakanan = Double.parseDouble(split[2]); //split [2] isinya harga

                    Menu menuMakan = new MenuMakanan(id, namaMakanan, hargaMakanan);
                    menu.put(id, menuMakan); //menaruh id dan info menu ke hashmap
                    System.out.println("CREATE MENU SUCCESS: " + id + " " + namaMakanan);
                }
                //CREATE MENU MINUMAN M002|Teh Panas|2000|M
            } else if (data[2].equals("MINUMAN")) { // index data[2] antara makanan/minuman
                if (menu.containsKey(id)) {
                    System.out.println("CREATE MENU FAILED: " + id + " IS EXIST");
                } else {
                    String namaMinuman = split[1]; //split[1] isinya namaMakanan
                    double hargaMinuman = Double.parseDouble(split[2]); //split[2] isinya harga
                    String customType = split[3]; //split[3] isinya customType

                    if (customType.equals("M") || customType.equals("L")) { //kalo customType isinya M atau L
                        Menu menuMinum = new MenuMinuman(id, namaMinuman, hargaMinuman, customType);
                        menu.put(id, menuMinum); //menaruh id dan info menu ke hashmap
                        System.out.println("CREATE MENU SUCCESS: " + id + " " + namaMinuman);
                    } else { //jika tidak
                        System.out.println("CREATE MENU ERROR");
                    }
                }
            } else {
                throw new RuntimeException("CREATE MENU ERROR:");
            }
        }
    }

    public static void addToCart(LinkedHashMap<String, Customer> customer, Map<String, Menu> menu, Map<String, Map<String, Integer>> cart, String input) {
        // ADD_TO_CART A001 M001 1
        String[] data = input.split(" ");
        String idPel = data[1];
        String idMenu = data[2];
        int qty = Integer.parseInt(data[3]);

        // IDPelanggan dan IDMenu harus sudah ada sebelumnya
        if (customer.containsKey(idPel) && menu.containsKey(idMenu)) {
            Customer customers = customer.get(idPel);
            Menu menus = menu.get(idMenu);


            ShoppingCart shoppingCart = customers.getCart();
            boolean isMenuExist = shoppingCart.isMenuExist(idMenu);

            if (isMenuExist) { // jika menu sebelumnya sudah ada
                shoppingCart.incrementQty(idMenu, qty); //menu ditambah dengan jumlah sebelumnya
                System.out.println("ADD_TO_CART SUCCESS: " + qty + " " + menus.getNamaMenu() + " IS INCREMENTED");
            } else { // jika sebelumnya belum ada
                shoppingCart.addToCart(menus, qty);
                System.out.println("ADD_TO_CART SUCCESS: " + qty + " " + menus.getNamaMenu() + " IS ADDED");
            }

            // untuk mengupdate hashmap cart
            Map<String, Integer> cartItem = cart.getOrDefault(idPel, new HashMap<>());
            cartItem.put(idMenu, shoppingCart.getQty(idMenu));
            cart.put(idPel, cartItem);
        } else {
            System.out.println("ADD_TO_CART FAILED: NON EXISTENT CUSTOMER OR MENU");
        }
    }

    public static void removeFromCart(LinkedHashMap<String, Customer> customer, Map<String, Menu> menu, Map<String, Map<String, Integer>> cart, String input) {
        // REMOVE_FROM_CART A001 M001 1
        String[] data = input.split(" ");
        String idPel = data[1];
        String idMenu = data[2];
        int qty = Integer.parseInt(data[3]);

        // idPelanggan dan idMenu harus sesuai dengan yang ada pada hashmap
        if (customer.containsKey(idPel) && menu.containsKey(idMenu)) {
            Customer customers = customer.get(idPel);
            Menu menus = menu.get(idMenu);

            ShoppingCart shoppingCart = customers.getCart();
            boolean isMenuExist = shoppingCart.isMenuExist(idMenu);

            // mengecek apakah idMenu terdapat pada hashmap
            if (isMenuExist) {
                int currentQty = shoppingCart.getQty(idMenu); //variabel ini sudah termasuk jumlah qty + incremented qty dari existing shopping cart

                // membandingkan qty yang didecrement dan jumlah qty yang ada
                if (currentQty > qty) {
                    shoppingCart.decrementQty(idMenu, qty);
                    System.out.println("REMOVE_FROM_CART SUCCESS: " + menus.getNamaMenu() + " QUANTITY IS DECREMENTED");
                } else if (currentQty == qty) {
                    shoppingCart.removeFromCart(idMenu);
                    System.out.println("REMOVE_FROM_CART SUCCESS: " + menus.getNamaMenu() + " IS REMOVED");
                }

                //mengupdate hashmap pada cart
                Map<String, Integer> cartItem = cart.get(idPel);
                if (cartItem != null) {
                    cartItem.put(idMenu, shoppingCart.getQty(idMenu));
                    cart.put(idPel, cartItem);
                }
            } else {
                System.out.println("REMOVE_FROM_CART FAILED: CUSTOMER OR MENU NOT FOUND");
            }
        } else {
            System.out.println("REMOVE_FROM_CART FAILED: CUSTOMER OR MENU NOT FOUND");
        }
    }

    public static void createPromo(Map<String, Promo> promo, String input) throws ParseException {
        // CREATE PROMO DISCOUNT ABC123|2023/05/01|2023/05/10|10|50000|100000
        String[] data = input.split(" ");
        if (data[0].equals("CREATE") && data[1].equals("PROMO")) {
            String[] split = data[3].split("\\|");
            String jenisPromo = data[2]; // Jenis promo diambil dari index 2
            String kodePromo = split[0]; // Ambil kode promo dari hasil split


            if (jenisPromo.equals("DISCOUNT") || jenisPromo.equals("CASHBACK") || jenisPromo.equals("DELIVERY")) {
                if (promo.containsKey(kodePromo)) {
                    System.out.println("CREATE PROMO " + jenisPromo + " FAILED: " + kodePromo + " IS EXIST");
                } else {
                    String tanggalAwal = split[1]; // Tanggal awal diambil dari index 1
                    String tanggalAkhir = split[2]; // Tanggal akhir diambil dari index 2
                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    Date dateAwal = formatter.parse(tanggalAwal);
                    Date dateAkhir = formatter.parse(tanggalAkhir);

                    String persenan = split[3];
                    String valuePersenan = persenan.substring(0, persenan.length() - 1).trim();
                    double persenPotongan = Double.parseDouble(valuePersenan); // Persen potongan diambil dari index 3
                    double maksPotongan = Double.parseDouble(split[4]); // Maksimum potongan diambil dari index 4
                    double minPembelian = Double.parseDouble(split[5]); // Minimum pembelian diambil dari index 5

                    if (jenisPromo.equals("DISCOUNT")) {
                        promoDisc promoDiskon = new promoDisc(kodePromo, dateAwal, dateAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
                        promo.put(kodePromo, promoDiskon);
                        System.out.println("CREATE PROMO " + jenisPromo + " SUCCESS: " + kodePromo);
                    } else if (jenisPromo.equals("CASHBACK")) {
                        promoCashBack promoCashback = new promoCashBack(kodePromo, dateAwal, dateAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
                        promo.put(kodePromo, promoCashback);
                        System.out.println("CREATE PROMO " + jenisPromo + " SUCCESS: " + kodePromo);
                    } else if (jenisPromo.equals("DELIVERY")) {
                        promoDelivery promoDelivery = new promoDelivery(kodePromo, dateAwal, dateAkhir, persenPotongan, maksPotongan, minPembelian, jenisPromo);
                        promo.put(kodePromo, promoDelivery);
                        System.out.println("CREATE PROMO " + jenisPromo + " SUCCESS: " + kodePromo);
                    }
                }
            }
        }
    }

    public static void applyPromo(LinkedHashMap<String, Customer> customers, Map<String, Menu> menus, Map<String, Promo> promos, Map<String, Map<String, Integer>> cart, String input) {
        //APPLY_PROMO A001 kodePromo
        String[] data = input.split(" ");
        String idPelanggan = data[1];
        String kodePromo = data[2];

        if (customers.containsKey(idPelanggan) && promos.containsKey(kodePromo)) {
            Customer cus = customers.get(idPelanggan);
            Promo promo = promos.get(kodePromo);
            Map<String, Integer> cartItem = cart.get(idPelanggan);
            int total = 0;

            for (Map.Entry<String, Promo> promoEntry : promos.entrySet()) {
                if (cus instanceof CustomerMember) {
                    Date registrationDate = ((CustomerMember) cus).getTanggalRegis();
                    LocalDate registrationDateLocal = registrationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate todays = LocalDate.now();
                    long selisihHari = DAYS.between(registrationDateLocal, todays);
                    for (Map.Entry<String, Menu> menuEntry : menus.entrySet()) {
                        Menu mnu = menuEntry.getValue();
                        for (Map.Entry<String, Integer> entry : cartItem.entrySet()) {
                            int qty = entry.getValue();
                            double hargaItem = mnu.getHargaMenu();
                            double totalHarga = hargaItem * qty;
                            total = (int) totalHarga;
                        }
                    }
                    CustomerEligible customerEligible = new CustomerEligible(kodePromo, (int) selisihHari);
                    OrderEligible orderEligible = new OrderEligible(total, (int) promo.getMinPembelian());
                    OrderEligible orderEligibleOngkir = new OrderEligible(String.valueOf(cus.getOngkir()), String.valueOf(cus.getOngkir()));

                    Date promoAwal = promo.getTanggalAwal();
                    Date promoAkhir = promo.getTanggalAkhir();
                    LocalDate tanggalPromoAwal = promoAwal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate tanggalPromoAkhir = promoAkhir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate today = LocalDate.now();
                    boolean berlaku = false;

                    if (today.isAfter(tanggalPromoAkhir) || (today.isBefore(tanggalPromoAwal))) {
                        System.out.println("APPLY_PROMO FAILED: " + kodePromo + " IS EXPIRED");
                        berlaku = false;
                        break;
                    } else if (promo.getJenisPromo().equals("DISCOUNT")) {
                        promo.setJenisPromo("DISCOUNT");
                        berlaku = true;
                        Applicable.PercentOffPromo diskonPersen = new Applicable.PercentOffPromo(kodePromo, berlaku, promo.getPersenPotongan() / 100);
                        if (diskonPersen.isCustomerEligible(customerEligible) && diskonPersen.isMinimumPriceEligible(orderEligible) && diskonPersen.isShippingFeeEligible(orderEligibleOngkir)) {
                            double diskon = diskonPersen.applyPromo(total);
                            promo.setHargaDiskon(diskon);
                            System.out.println("APPLY_PROMO SUCCESS: " + kodePromo);
                            appliedPromoCode = kodePromo;
                            appliedPromos.put(idPelanggan, kodePromo);
                            break;
                        } else {
                            System.out.println("APPLY_PROMO FAILED: " + kodePromo);
                            break;
                        }
                    } else if (promo.getJenisPromo().equals("CASHBACK")) {
                        promo.setJenisPromo("CASHBACK");
                        berlaku = true;
                        Applicable.CashBack cashBack = new Applicable.CashBack(kodePromo, berlaku, promo.getPersenPotongan() * 1000);
                        if (cashBack.isCustomerEligible(customerEligible) && cashBack.isMinimumPriceEligible(orderEligible) && cashBack.isShippingFeeEligible(orderEligibleOngkir)) {
                            double cb = cashBack.applyPromo(total);
                            double hargaSetelahCashback = total - cb;
                            promo.setHargaDiskon(hargaSetelahCashback);
                            System.out.println("APPLY_PROMO SUCCESS: " + kodePromo);
                            appliedPromoCode = kodePromo;
                            appliedPromos.put(idPelanggan, kodePromo);
                            break;
                        } else {
                            System.out.println("APPLY_PROMO FAILED: " + kodePromo);
                            break;
                        }
                    } else if (promo.getJenisPromo().equals("DELIVERY")) {
                        promo.setJenisPromo("DELIVERY");
                        berlaku = true;
                        Applicable.ShippingFee shippingFee = new Applicable.ShippingFee(kodePromo, berlaku, promo.getMaksPotongan());
                        if (shippingFee.isCustomerEligible(customerEligible) && shippingFee.isShippingFeeEligible(orderEligible) && shippingFee.isShippingFeeEligible(orderEligibleOngkir)) {
                            double ongkir = shippingFee.applyPromo(total);
                            promo.setHargaDiskon(ongkir);
                            System.out.println("APPLY_PROMO SUCCESS: " + kodePromo);
                            appliedPromoCode = kodePromo;
                            appliedPromos.put(idPelanggan, kodePromo);
                            break;
                        } else {
                            System.out.println("APPLY_PROMO FAILED: " + kodePromo);
                            break;
                        }
                    } else {
                        System.out.println("APPLY PROMO FAILED " + kodePromo);
                    }
                } else if (cus instanceof CustomerGuest) {
                    System.out.println("APPLY PROMO FAILED " + kodePromo);
                    break;
                }
            }
        }
    }

    public static void systemTopup(LinkedHashMap<String, Customer> cust, String input) {
        // TOPUP IDPelanggan Saldo
        String[] data = input.split(" ");
        String ID = data[1];
        double saldoTopup = Double.parseDouble(data[2]);

        Customer cus = cust.get(ID);
        if (cust.containsKey(ID)) {
            double saldoOri = cus.getSaldoAwal();
            double saldoAwal = cus.getSaldoAwal();
            double saldoAkhir = saldoAwal + saldoTopup;
            saldoAwal += saldoTopup;
            cus.setSaldoAwal(saldoAwal);

            System.out.println("TOPUP SUCCESS: " + ID + " " + saldoOri + " => " + saldoAkhir);
        } else {
            System.out.println("TOPUP FAILED: CUSTOMER NOT FOUND");
        }
    }

    public static void checkOut(LinkedHashMap<String, CheckOut> co, LinkedHashMap<String, Customer> customer, Map<String, Menu> menu, Map<String, Map<String, Integer>> cart, Map<String, Promo> promo, String input) {
        //CHECK_OUT IDPel
        String[] data = input.split(" ");
        String idPel = data[1];
        boolean alreadyCheckOut = false;

        if (customer.containsKey(idPel)) {
            Customer customers = customer.get(idPel);
            double saldo = customers.getSaldoAwal();
            Map<String, Integer> cartItem = cart.get(idPel);

            if (customers instanceof CustomerMember) {
                for (Map.Entry<String, Menu> menuItem : menu.entrySet()) {
                    Menu mnu = menuItem.getValue();
                    for (Map.Entry<String, Integer> entryCart : cartItem.entrySet()) {
                        int qty = entryCart.getValue();
                        double hargaItem = mnu.getHargaMenu();
                        double total = hargaItem * qty;

                        if (saldo < total) {
                            System.out.println("CHECK_OUT FAILED: " + idPel + " " + ((CustomerMember) customers).getNamaMember() + " INSUFFICIENT BALANCE");
                            break;
                        } else {
                            System.out.println("CHECK_OUT SUCCESS: " + idPel + " " + ((CustomerMember) customers).getNamaMember());
                            CheckOut checkOut = new CheckOut(idPel);
                            checkOut.setCheckOut(true);
                            co.put(idPel, checkOut);
                            alreadyCheckOut = true;
                            break;
                        }
                    }
                    if (alreadyCheckOut) {
                        break;
                    }
                }

            } else if (customers instanceof CustomerGuest) {
                for (Map.Entry<String, Menu> menuItem : menu.entrySet()) {
                    Menu mnu = menuItem.getValue();
                    for (Map.Entry<String, Integer> entryCart : cartItem.entrySet()) {
                        int qty = entryCart.getValue();
                        double hargaItem = mnu.getHargaMenu();
                        double total = hargaItem * qty;

                        if (saldo < total) {
                            System.out.println("CHECK_OUT FAILED: " + idPel + " INSUFFICIENT BALANCE");
                            break;
                        } else {
                            System.out.println("CHECK_OUT SUCCESS: " + idPel);
                            CheckOut checkOut = new CheckOut(idPel);
                            checkOut.setCheckOut(true);
                            co.put(idPel, checkOut);
                            alreadyCheckOut = true;
                            break;
                        }
                    }
                    if (alreadyCheckOut) {
                        break;
                    }
                }
            }
        } else {
            System.out.println("CHECK_OUT FAILED: NON EXISTENT CUSTOMER");
        }

    }

    public static void systemPrint(Map<String, List<History>> nomorPesananMap1, LinkedHashMap<String, Customer> customer, Map<String, Menu> menu, Map<String, Map<String, Integer>> cart, Map<String, Promo> promos, Map<String, CheckOut> co, String input) {
        String[] data = input.split(" ");
        String idPel = data[1];

        if (customer.containsKey(idPel)) {
            Customer cus = customer.get(idPel);
            Promo promo = promos.get(idPel);
            ShoppingCart scart = cus.getCart();
            double total = 0;
            int totalQty = 0;
            CheckOut checkOut = co.get(idPel);
            double sisaSaldo = 0;

            String customerName = "";
            if (cus instanceof CustomerMember) {
                customerName = ((CustomerMember) cus).getNamaMember();
            } else if (cus instanceof CustomerGuest) {
                customerName = "";
            }

            System.out.println("\t");
            System.out.println("Kode Pelanggan: " + idPel);
            System.out.println("Nama Pelanggan: " + customerName);

            if (checkOut == null) {
                System.out.printf("No   | Menu                            | Qty |   Subtotal\n");
                System.out.println("===============================================================");

                Map<String, Integer> cartItem = cart.get(idPel);
                if (cartItem != null) {
                    int count = 1;
                    for (Map.Entry<String, Integer> entry : cartItem.entrySet()) {
                        String idMenu = entry.getKey();
                        int qty = entry.getValue();

                        if (menu.containsKey(idMenu)) {
                            Menu menus = menu.get(idMenu);
                            double subtotal = menus.getHargaMenu() * qty;
                            total += subtotal;

                            System.out.printf("%-5s | %-30s | %4d | %10.0f%n", count, menus.getNamaMenu(), qty, subtotal);
                            count++;
                        }
                    }
                    for (Map.Entry<String, Promo> promoEntry : promos.entrySet()) {
                        String kodePromo = promoEntry.getKey();
                        Promo prm = promoEntry.getValue();
                        if (appliedPromos.containsKey(idPel) && appliedPromos.get(idPel).equals(kodePromo)) {
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", total);
                            if (prm.getJenisPromo().equals("DISCOUNT")) {
                                double totalPromo = (total + cus.getOngkir());

                                System.out.println("PROMO:" + kodePromo + "                         -" + prm.getHargaDiskon());
                                System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                                System.out.println("===============================================================");
                                System.out.printf("Total:%37.0f%n", totalPromo);
                                System.out.printf("Saldo:%37.0f%n", cus.getSaldoAwal());
                                System.out.println("===============================================================");
                                System.out.println();
                                break;
                            } else if (prm.getJenisPromo().equals("CASHBACK")) {
                                double totalPlesOngkir = total + cus.getOngkir();
                                double saldoCashback = sisaSaldo + prm.getHargaDiskon();

                                System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                                System.out.println("===============================================================");
                                System.out.println("PROMO:" + kodePromo + "                      " + prm.getHargaDiskon());
                                System.out.printf("Total:%37.0f%n", totalPlesOngkir);
                                System.out.printf("Saldo:%37.0f%n", cus.getSaldoAwal());
                                System.out.println("===============================================================");
                                break;
                            } else if (prm.getJenisPromo().equals("DELIVERY")) {
                                double totalPromo = (total + cus.getOngkir());

                                System.out.println("PROMO:" + kodePromo + "                         -" + prm.getHargaDiskon());
                                System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                                System.out.println("===============================================================");
                                System.out.printf("Total:%37.0f%n", totalPromo);
                                System.out.printf("Saldo:%37.0f%n", cus.getSaldoAwal());
                                System.out.println("===============================================================");
                                System.out.println();
                                break;
                            }
                        } else if (!appliedPromos.containsKey(idPel)) {
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", total);
                            System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", total + cus.getOngkir());
                            System.out.printf("Saldo:%37.0f%n", cus.getSaldoAwal());
                            System.out.println("===============================================================");
                            break;
                        }
                    }
                }
            } else if (checkOut.isCheckOut()) {
                double totalDiskon = 0;
                int currentNomor = cus.nomorPesanan();
                System.out.println("Nomor Pesanan: " + currentNomor);
                System.out.println("Tanggal Pesanan : " + LocalDate.now());
                System.out.printf("No   | Menu                            | Qty |   Subtotal\n");
                System.out.println("===============================================================");

                Map<String, Integer> cartItem = cart.get(idPel);
                if (cartItem != null) {
                    int count = 1;

                    Iterator<Map.Entry<String, Integer>> iteratorMenu = cartItem.entrySet().iterator();
                    while (iteratorMenu.hasNext()) {
                        Map.Entry<String, Integer> menuEntry = iteratorMenu.next();
                        String idMenu = menuEntry.getKey();
                        int qty = menuEntry.getValue();

                        if (menu.containsKey(idMenu)) {
                            Menu menus = menu.get(idMenu);
                            double subtotal = menus.getHargaMenu() * qty;
                            total += subtotal;
                            totalQty += qty;

                            menus.setTotalQty(totalQty);
                            cus.setNomor(currentNomor);
                            double totalHarga = total + cus.getOngkir();

                            String kodePromo = appliedPromos.getOrDefault(idPel, null);
                            if (appliedPromos.containsKey(idPel) && appliedPromos.get(idPel).equals(kodePromo)) {
                                Promo promo1 = promos.get(kodePromo);
                                if (promo1.getJenisPromo().equals("DISCOUNT")) {
                                    double totalPromo = (promo1.getPersenPotongan() / 100) * total;
                                    totalHarga -= totalPromo;
                                }
                            }

                            menus.setTotalHarga(totalHarga);

                            System.out.printf("%-5s | %-30s | %4d | %10.0f%n", count, menus.getNamaMenu(), qty, subtotal);
                            count++;

                            History nomorPesanan = new History(cus.getNomor(), menus.getTotalQty(), menus.getTotalHarga(), kodePromo);
                            List<History> historyList = nomorPesananMap1.getOrDefault(idPel, new ArrayList<>());
                            historyList.add(nomorPesanan);

                            nomorPesananMap1.put(idPel, historyList);

                            // Mereset cart
                            iteratorMenu.remove();
                            cartItem.remove(idMenu);
                            cart.remove(idMenu);
                            scart.removeFromCart(idMenu);
                        }
                    }
                }

                for (Map.Entry<String, Promo> promoEntry : promos.entrySet()) {
                    String kodePromo = promoEntry.getKey();
                    Promo prm = promoEntry.getValue();
                    if (appliedPromos.containsKey(idPel) && appliedPromos.get(idPel).equals(kodePromo)) {
                        System.out.println("===============================================================");
                        System.out.printf("Total:%37.0f%n", total);

                        if (prm.getJenisPromo().equals("CASHBACK")) {
                            double totalPlesOngkir = total + cus.getOngkir();
                            double sisa = cus.getSaldoAwal() - totalPlesOngkir;
                            double saldoCashback = prm.getHargaDiskon();
                            sisaSaldo = saldoCashback + sisa;
                            cus.setSaldoAwal(sisaSaldo);

                            System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", totalPlesOngkir);
                            System.out.println("PROMO:" + kodePromo + "                     " + prm.getHargaDiskon());
                            System.out.printf("Sisa saldo:%32.0f%n", sisaSaldo);
                            System.out.println("===============================================================");
                            break;
                        } else if (prm.getJenisPromo().equals("DISCOUNT")) {
                            double hargaDiskon = (prm.getPersenPotongan() / 100) * total;
                            double totalPromo = ((total + cus.getOngkir() - hargaDiskon));
                            sisaSaldo = cus.getSaldoAwal() - totalPromo;
                            cus.setSaldoAwal(sisaSaldo);

                            System.out.println("PROMO:" + kodePromo + "                         -" + hargaDiskon);
                            System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", totalPromo);
                            System.out.printf("Sisa saldo:%32.0f%n", sisaSaldo);
                            System.out.println("===============================================================");
                            System.out.println();
                            break;
                        } else if (prm.getJenisPromo().equals("DELIVERY")) {
                            double totalPromo = (total + cus.getOngkir());

                            System.out.println("PROMO:" + kodePromo + "                         -" + prm.getHargaDiskon());
                            System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                            System.out.println("===============================================================");
                            System.out.printf("Total:%37.0f%n", totalPromo);
                            sisaSaldo = cus.getSaldoAwal() - (total + cus.getOngkir());
                            System.out.printf("Sisa saldo:%32.0f%n", sisaSaldo);
                            System.out.println("===============================================================");
                            System.out.println();
                            break;
                        }
                    }
                    if (!appliedPromos.containsKey(idPel)) {
                        sisaSaldo = cus.getSaldoAwal() - (total + cus.getOngkir());
                        System.out.println("===============================================================");
                        System.out.printf("Total:%37.0f%n", total);
                        System.out.printf("Ongkos Kirim:%30.0f%n", cus.getOngkir());
                        System.out.println("===============================================================");
                        System.out.printf("Total:%37.0f%n", total + cus.getOngkir());
                        System.out.printf("Sisa saldo:%32.0f%n", sisaSaldo);
                        System.out.println("===============================================================");
                        break;
                    }
                }
                cus.setSaldoAwal(sisaSaldo);
            }
        }
    }

    public static void printHistory(Map<String, List<History>> historyMap, LinkedHashMap<String, Customer> customer, Map<String, Menu> menu, Map<String, Map<String, Integer>> cart, Map<String, Promo> promos, Map<String, CheckOut> co, String input) {
        // PRINT_HISTORY A001
        String[] data = input.split(" ");
        String idPelanggan = data[1];
        Customer cus = customer.get(idPelanggan);
        int count = 1;
        Map<String, Integer> cartItem = cart.get(idPelanggan);
        int total = 0;

        System.out.println();
        System.out.println("Kode pelanggan: " + idPelanggan);

        String customerName = "";
        if (cus instanceof CustomerMember) {
            customerName = ((CustomerMember) cus).getNamaMember();
        } else if (cus instanceof CustomerGuest) {
            customerName = "";
        }

        System.out.println("Nama pelanggan: " + customerName);
        System.out.println("Saldo: " + cus.getSaldoAwal());
        System.out.printf("%4s | %13s | %6s | %8s | %-8s\n", "No", "Nomor Pesanan", "Jumlah", "Subtotal", "PROMO");
        System.out.println("===============================================================");

        List<History> customerHistory = historyMap.getOrDefault(idPelanggan, new ArrayList<>());

        if (!customerHistory.isEmpty()) {
            Map<Integer, History> lastHistoryMap = new HashMap<>();
            for (History history : customerHistory) {
                int nomor = history.getNomorPesanan();
                lastHistoryMap.put(nomor, history);
            }

            for (History lastHistory : lastHistoryMap.values()) {
                int nomor = lastHistory.getNomorPesanan();
                int qty = lastHistory.getQty();
                int totalSemua = (int) lastHistory.getSubtotal();
                String kodePromo = lastHistory.getKodePromo();

                if (kodePromo != null) {
                    System.out.printf("%4d | %13d | %6d | %8d | %-8s\n", count, nomor, qty, totalSemua, kodePromo);
                    count++;
                } else {
                    System.out.printf("%4d | %13d | %6d | %8d | %-8s\n", count, nomor, qty, totalSemua, "");
                    count++;
                }
            }
        }
        System.out.println("===============================================================");
    }
}


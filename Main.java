import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        MenuRestoran menu = new MenuRestoran();
        menu.tampilMenuUtama();
    }
}

class Menu {
    String nama;
    int harga;
    String kategori;

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

class MenuRestoran {

    Scanner input = new Scanner(System.in);
    

    // ====== DATA MENU RESTORAN ======
    Menu[] daftarMenu = {
        new Menu("Nasi Goreng", 15000, "Makanan"),
        new Menu("Mie Ayam", 12000, "Makanan"),
        new Menu("Ayam Bakar", 20000, "Makanan"),
        new Menu("Bakso", 18000, "Makanan"),

        new Menu("Es Teh", 5000, "Minuman"),
        new Menu("Es Jeruk", 7000, "Minuman"),
        new Menu("Es Susu", 8000, "Minuman"),
        new Menu("Jus Alpukat", 12000, "Minuman")
    };

    // DATA PESANAN
    ArrayList<String> pesananNama = new ArrayList<>();
    ArrayList<Integer> pesananJumlah = new ArrayList<>();
    int pesananCount = 0;


    // =============================================
    //                 MENU UTAMA
    // =============================================
    public void tampilMenuUtama() {
        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Pemesanan");
            System.out.println("2. Manajemen Menu Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");

            String pilih = input.nextLine();

            switch (pilih) {
                case "1":
                    menuPemesanan();
                    break;

                case "2":
                    menuPengelolaan();
                    break;

                case "3":
                    System.out.println("Terima kasih!");
                    return;

                default:
                    System.out.println("Input tidak valid!");
            }
        }
    }


    // ============================================
    //              TAMPILKAN MENU
    // ============================================
    void tampilkanMenu() {
        System.out.println("\n====== DAFTAR MENU RESTORAN ======\n");

        System.out.println(">>> Makanan:");
        for (int i = 0; i < daftarMenu.length; i++)
            if (daftarMenu[i].kategori.equals("Makanan"))
                System.out.println((i + 1) + ". " + daftarMenu[i].nama + " - Rp" + daftarMenu[i].harga);

        System.out.println("\n>>> Minuman:");
        for (int i = 0; i < daftarMenu.length; i++)
            if (daftarMenu[i].kategori.equals("Minuman"))
                System.out.println((i + 1) + ". " + daftarMenu[i].nama + " - Rp" + daftarMenu[i].harga);
    }


    // ============================================
    //              PEMESANAN
    // ============================================
    void menuPemesanan() {

        pesananCount = 0;

        while (true) {
            tampilkanMenu();
            System.out.println("\nKetik 'selesai' untuk mengakhiri pemesanan.");
            System.out.print("Masukkan nama menu: ");
            String nama = input.nextLine();

            if (nama.equalsIgnoreCase("selesai"))
                break;

            int index = cariMenu(nama);
            if (index == -1) {
                System.out.println("Menu tidak ditemukan! Coba lagi.");
                continue;
            }

            System.out.print("Jumlah: ");
            int qty = Integer.parseInt(input.nextLine());

            pesananNama.add(nama);
            pesananJumlah.add(qty);
            pesananCount++;

            System.out.println("Pesanan ditambahkan!\n");
        }

        cetakStruk();
    }


    // Cari menu dari nama
    int cariMenu(String nama) {
        for (int i = 0; i < daftarMenu.length; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }


    // ============================================
    //                CETAK STRUK
    // ============================================
    void cetakStruk() {

        System.out.println("\n=========== STRUK PEMBAYARAN ===========");
        int subtotal = 0;
        boolean beli1gratis1 = false;

        for (int i = 0; i < pesananCount; i++) {
            int index = cariMenu(pesananNama.get(i));
            if (index == -1) continue;

            Menu m = daftarMenu[index];
            int total = m.harga * pesananJumlah.get(i);
            subtotal += total;

            System.out.println(m.nama + " (" + pesananJumlah.get(i) + " x Rp" + m.harga + ") = Rp" + total);
        }

        double pajak = subtotal * 0.10;
        int service = 20000;
        double diskon = 0;

        if (subtotal > 100000)
            diskon = subtotal * 0.10;

        if (subtotal > 50000)
            beli1gratis1 = true;

        double totalAkhir = subtotal + pajak + service - diskon;

        System.out.println("\n-----------------------------------");
        System.out.println("Subtotal : Rp" + subtotal);
        System.out.println("Pajak (10%): Rp" + (int)pajak);
        System.out.println("Service   : Rp" + service);

        if (diskon > 0)
            System.out.println("Diskon 10% : -Rp" + (int)diskon);

        if (beli1gratis1)
            System.out.println("Promo : Beli 1 Gratis 1 Minuman");

        System.out.println("-----------------------------------");
        System.out.println("TOTAL BAYAR : Rp" + (int)totalAkhir);
        System.out.println("===================================\n");
    }


    // ===================================================
    //              MANAJEMEN RESTORAN
    // ===================================================
    void menuPengelolaan() {
        while (true) {
            System.out.println("\n===== MANAJEMEN MENU =====");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Daftar Menu");
            System.out.println("3. Ubah Harga Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");

            String pilih = input.nextLine();

            switch (pilih) {
                case "1": tambahMenu(); break;
                case "2": tampilkanMenu(); break;
                case "3": ubahMenu(); break;
                case "4": hapusMenu(); break;
                case "5": return;
                default: System.out.println("Pilihan tidak valid!");
            }
        }
    }


    // ============= Tambah Menu ================
    void tambahMenu() {

        System.out.print("Nama Menu: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        int harga = Integer.parseInt(input.nextLine());

        System.out.print("Kategori (Makanan/Minuman): ");
        String kategori = input.nextLine();

        Menu[] baru = new Menu[daftarMenu.length + 1];

        for (int i = 0; i < daftarMenu.length; i++)
            baru[i] = daftarMenu[i];

        baru[daftarMenu.length] = new Menu(nama, harga, kategori);

        daftarMenu = baru;

        System.out.println("Menu berhasil ditambahkan!");
    }


    // ============= Ubah Harga ================
    void ubahMenu() {
        tampilkanMenu();

        System.out.print("\nMasukkan nomor menu yang ingin diubah: ");
        int no = Integer.parseInt(input.nextLine()) - 1;

        if (no < 0 || no >= daftarMenu.length) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin ubah? (Ya/Tidak): ");
        String ok = input.nextLine();

        if (!ok.equalsIgnoreCase("Ya")) {
            System.out.println("Dibatalkan.");
            return;
        }

        System.out.print("Harga baru: ");
        daftarMenu[no].harga = Integer.parseInt(input.nextLine());

        System.out.println("Harga berhasil diubah!");
    }


    // ============= Hapus Menu ================
    void hapusMenu() {

        tampilkanMenu();
        System.out.print("\nNomor menu yang ingin dihapus: ");
        int no = Integer.parseInt(input.nextLine()) - 1;

        if (no < 0 || no >= daftarMenu.length) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin hapus? (Ya/Tidak): ");
        String ok = input.nextLine();

        if (!ok.equalsIgnoreCase("Ya")) {
            System.out.println("Dibatalkan.");
            return;
        }

        Menu[] baru = new Menu[daftarMenu.length - 1];

        int idx = 0;
        for (int i = 0; i < daftarMenu.length; i++)
            if (i != no)
                baru[idx++] = daftarMenu[i];

        daftarMenu = baru;

        System.out.println("Menu berhasil dihapus!");
    }
}
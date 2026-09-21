package sistempakar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Knowledge base dan mekanisme inferensi Forward Chaining.
 */
public final class KnowledgeBase {

    public static final String[] KODE_GEJALA = {
        "G01", "G02", "G03", "G04", "G05",
        "G06", "G07", "G08", "G09", "G10"
    };

    public static final String[] DESKRIPSI_GEJALA = {
        "Motor sulit di-start menggunakan electric starter",
        "Lampu indikator / spedometer tampak redup",
        "Klakson berbunyi pelan atau tidak berbunyi sama sekali",
        "Mesin terasa berebet / tersendat saat digas",
        "Tarikan awal motor terasa bergetar hebat (gredag) di bagian CVT",
        "Terdengar suara decit atau tak wajar dari area CVT saat berjalan",
        "Mesin sering mendadak mati sendiri saat posisi idle (stasioner)",
        "Muncul bau sangit / gosong dari area bawah mesin saat tanjakan",
        "Terdengar suara berdecit keras saat menekan tuas rem",
        "Tuas rem terasa sangat dalam saat ditarik / kurang pakem"
    };

    public static final String KERUSAKAN_TIDAK_TERDETEKSI =
        "Kerusakan Tidak Terdeteksi, silakan konsultasi ke mekanik/bengkel resmi.";

    private KnowledgeBase() {
    }

    /** Hasil inferensi: data kerusakan beserta solusinya. */
    public static class Hasil {
        public final boolean terdeteksi;
        public final String kodeKerusakan;
        public final String kerusakan;
        public final String solusi;

        public Hasil(boolean terdeteksi, String kodeKerusakan, String kerusakan, String solusi) {
            this.terdeteksi = terdeteksi;
            this.kodeKerusakan = kodeKerusakan;
            this.kerusakan = kerusakan;
            this.solusi = solusi;
        }

        /** Teks ringkas kesimpulan, misal "P03: Akumulator / Aki Tekor". */
        public String tampilanKesimpulan() {
            return terdeteksi ? kodeKerusakan + ": " + kerusakan : kerusakan;
        }
    }

    public static String deskripsi(String kode) {
        for (int i = 0; i < KODE_GEJALA.length; i++) {
            if (KODE_GEJALA[i].equals(kode)) {
                return DESKRIPSI_GEJALA[i];
            }
        }
        return kode;
    }

    /** Konversi array jawaban boolean (indeks = G01..G10) menjadi kode gejala terpilih. */
    public static Set<String> kodeTerpilih(boolean[] jawaban) {
        Set<String> set = new LinkedHashSet<>();
        for (int i = 0; i < KODE_GEJALA.length; i++) {
            if (i < jawaban.length && jawaban[i]) {
                set.add(KODE_GEJALA[i]);
            }
        }
        return set;
    }

    /** Daftar keterangan gejala yang dijawab "Ya". */
    public static List<String> gejalaTerpilih(Set<String> ya, boolean withCode) {
        List<String> list = new ArrayList<>();
        for (String kode : KODE_GEJALA) {
            if (ya.contains(kode)) {
                list.add(withCode ? kode + " - " + deskripsi(kode) : deskripsi(kode));
            }
        }
        return list;
    }

    /**
     * Inferensi Forward Chaining: mencocokkan fakta (gejala) secara berurutan
     * dengan basis aturan hingga ditemukan kesimpulan yang tepat.
     */
    public static Hasil forwardChaining(boolean[] jawaban) {
        return forwardChaining(kodeTerpilih(jawaban));
    }

    public static Hasil forwardChaining(Set<String> ya) {
        if (ya.containsAll(Arrays.asList("G01", "G02", "G03"))) {
            return new Hasil(true, "P03", "Akumulator / Aki Tekor",
                "Charge ulang aki atau ganti aki baru.");
        }
        if (ya.containsAll(Arrays.asList("G01", "G04", "G07"))) {
            return new Hasil(true, "P01", "Masalah Busi / Pengapian",
                "Bersihkan elektroda busi atau ganti busi baru.");
        }
        if (ya.containsAll(Arrays.asList("G05", "G06", "G08"))) {
            return new Hasil(true, "P02", "CVT (Roller/Vanbelt) Aus",
                "Ganti komponen CVT yang aus di bengkel.");
        }
        if (ya.containsAll(Arrays.asList("G04", "G07"))) {
            return new Hasil(true, "P04", "Sistem Injeksi / Throttle Body Kotor",
                "Servis dan bersihkan throttle body.");
        }
        if (ya.containsAll(Arrays.asList("G09", "G10"))) {
            return new Hasil(true, "P05", "Kampas Rem Aus",
                "Ganti kampas rem baru.");
        }
        return new Hasil(false, "-", KERUSAKAN_TIDAK_TERDETEKSI,
                "Periksa lebih lanjut ke mekanik/bengkel resmi.");
    }
}